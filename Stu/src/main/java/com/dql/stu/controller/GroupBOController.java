package com.dql.stu.controller;


import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dql.stu.exception.BusinessException;
import com.dql.stu.message.Message;
import com.dql.stu.message.MessageEnum;
import com.dql.stu.model.ChatRecordBO;
import com.dql.stu.model.GroupBO;
import com.dql.stu.model.StudentBO;
import com.dql.stu.service.func.IChatRecordBOService;
import com.dql.stu.service.func.IGroupBOService;
import com.dql.stu.service.func.IStudentBOService;
import com.dql.stu.tmodel.GroupStudentTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 段启龙
 * @since 2021-12-19
 */
@Controller
@Slf4j
@AllArgsConstructor
public class GroupBOController {

    final private IGroupBOService iGroupBOService;

    final private IStudentBOService iStudentBOService;
    final private IChatRecordBOService iChatRecordBOService;

    @ResponseBody
    @Transactional
    @RequestMapping("/deleteBatch")
    public Message deleteBatch(@RequestBody List<String> groupNameList) {
        try {
            groupNameList.forEach(group -> {
                log.info("GroupBOController -> deleteBatch 获取到的组名:{}", group);
                iGroupBOService.remove(new LambdaQueryWrapper<GroupBO>().eq(GroupBO::getGroupName, group));
            });
            return Message.success();
        } catch (Exception e) {
            log.info("GroupBOController -> deleteBatch 捕获到的错误:{}", e.getMessage());
            return Message.error(MessageEnum.DELETE_TEACHER_ERROR);
        }
    }

    /**
     * 通过组名查询分组信息,学生信息
     *
     * @param groupName 组名
     * @return 分组信息 学生信息
     */
    @RequestMapping("/toEditGroup/{teacherId}/{groupName}")
    public String toEditGroup(@PathVariable Integer teacherId, @PathVariable String groupName, Model model) {
        try {
            //分组信息
            List<GroupBO> currentGroupBOList = iGroupBOService.list(new LambdaQueryWrapper<GroupBO>()
                    .eq(GroupBO::getGroupName, groupName)
                    .eq(GroupBO::getTeacherId, teacherId));


            //教师的所有学生
            List<StudentBO> allStudentBOList = iStudentBOService.list(new LambdaQueryWrapper<StudentBO>()
                    .eq(StudentBO::getTeacherId, teacherId));
            log.info("GroupBOController -> toEditGroup获取到的所有学生:{}", allStudentBOList);
            //当前组内的学生
            List<StudentBO> currentStudentBOList = new ArrayList<>();

            currentGroupBOList.forEach(groupBO -> {
                StudentBO studentBO = iStudentBOService.getById(groupBO.getStudentId());
                if (!ObjectUtil.isEmpty(studentBO)) {
                    currentStudentBOList.add(studentBO);
                }
            });
            log.info("GroupBOController -> toEditGroup获取到的当前组内学生:{}", currentStudentBOList);
            allStudentBOList.removeAll(currentStudentBOList);
            log.info("GroupBOController -> toEditGroup获取到的未被选择学生:{}", allStudentBOList);
            //组装数据
            GroupStudentTO groupStudentTO = new GroupStudentTO();
            groupStudentTO.setGroupName(groupName);
            groupStudentTO.setOldGroupName(groupName);
            groupStudentTO.setSelectedStudentBOList(currentStudentBOList);
            groupStudentTO.setNoSelectedStudentBOList(allStudentBOList);
            groupStudentTO.setTeacherId(teacherId);
            model.addAttribute("groupStudentTO", groupStudentTO);
            return "edit-group";
        } catch (Exception e) {
            log.info("GroupBOController -> toEditGroup 捕获到的异常{}", e.getMessage());
            throw new BusinessException(MessageEnum.ERROR);
        }
    }


    /**
     * 查询学生信息
     *
     * @return 学生信息
     */
    @RequestMapping("/toCreateGroup/{teacherId}")
    public String toCreateGroup(@PathVariable Integer teacherId, Model model) {
        try {
            //教师的所有学生
            List<StudentBO> allStudentBOList = iStudentBOService.list(new LambdaQueryWrapper<StudentBO>()
                    .eq(StudentBO::getTeacherId, teacherId));
            log.info("GroupBOController -> toEditGroup获取到的所有学生:{}", allStudentBOList);
            //组装数据
            GroupStudentTO groupStudentTO = new GroupStudentTO();
            groupStudentTO.setNoSelectedStudentBOList(allStudentBOList);
            groupStudentTO.setTeacherId(teacherId);
            model.addAttribute("groupStudentTO", groupStudentTO);
            return "edit-group";
        } catch (Exception e) {
            log.info("GroupBOController -> toEditGroup 捕获到的异常{}", e.getMessage());
            throw new BusinessException(MessageEnum.ERROR);
        }
    }


    /**
     * 编辑/新增分组
     *
     * @param groupStudentTO 分组信息
     * @return 结果信息
     */
    @ResponseBody
    @Transactional
    @RequestMapping("/editGroup")
    public Message editGroup(@RequestBody GroupStudentTO groupStudentTO) {
        String groupName = groupStudentTO.getGroupName();
        String oldGroupName = groupStudentTO.getOldGroupName();
        //组名信息为空
        if (ObjectUtil.isEmpty(groupName)) {
            return Message.error(MessageEnum.GROUP_NAME_NULL);
        }

        if (!groupStudentTO.getOldGroupName().equals(groupStudentTO.getGroupName())) {
            //更新聊天记录中的组名
            updateChatRecordGroupName(groupStudentTO.getOldGroupName(), groupStudentTO.getGroupName());
        }

        Integer teacherId = groupStudentTO.getTeacherId();
        //教师信息为空
        if (ObjectUtil.isEmpty(teacherId)) {
            return Message.error(MessageEnum.TEACHER_DATA_NULL);
        }

        List<Integer> studentIdList = groupStudentTO.getStudentIdList();
        //选择的学生为空
        if (CollectionUtils.isEmpty(studentIdList)) {
            return Message.error(MessageEnum.SELECT_STUDENT_NULL);
        }

        //分组信息
        List<GroupBO> currentGroupBOList = iGroupBOService.list(new LambdaQueryWrapper<GroupBO>()
                .eq(GroupBO::getGroupName, oldGroupName)
                .eq(GroupBO::getTeacherId, teacherId));

        try {
            if (!CollectionUtils.isEmpty(currentGroupBOList)) {
                //更新时,先删除所有,再新增
                iGroupBOService.removeByIds(currentGroupBOList.stream().map(GroupBO::getId).collect(Collectors.toList()));
            }
            studentIdList.forEach(id -> {
                GroupBO groupBO = new GroupBO();
                groupBO.setGroupName(groupName);
                groupBO.setStudentId(id);
                groupBO.setTeacherId(teacherId);
                groupBO.setLastBrowseDate(LocalDateTime.now());
                iGroupBOService.save(groupBO);
            });
            return Message.success();
        } catch (Exception e) {
            log.info("GroupBOController -> editGroup 捕获到的异常{}", e.getMessage());
            log.info("GroupBOController -> editGroup 捕获到的异常位置{}", e.getStackTrace());
            return Message.error(MessageEnum.ERROR, e.getMessage());
        }
    }

    /**
     * 更新聊天记录中的组名
     *
     * @param groupName 新组名
     */
    private void updateChatRecordGroupName(String oldGroupName, String groupName) {
        List<ChatRecordBO> chatRecordBOS = iChatRecordBOService.list(new LambdaQueryWrapper<ChatRecordBO>()
                .eq(ChatRecordBO::getReceiveName, oldGroupName));

        chatRecordBOS.forEach(chatRecordBO -> {
            chatRecordBO.setReceiveName(groupName);
            iChatRecordBOService.updateById(chatRecordBO);
        });


    }
}
