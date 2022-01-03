package com.dql.stu.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dql.stu.exception.BusinessException;
import com.dql.stu.mapper.TeacherBOMapper;
import com.dql.stu.message.Message;
import com.dql.stu.message.MessageEnum;
import com.dql.stu.model.ChatRecordBO;
import com.dql.stu.model.GroupBO;
import com.dql.stu.model.StudentBO;
import com.dql.stu.model.TeacherBO;
import com.dql.stu.service.func.IChatRecordBOService;
import com.dql.stu.service.func.IGroupBOService;
import com.dql.stu.service.func.IStudentBOService;
import com.dql.stu.service.func.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author dql
 * @since 2021-12-09
 */
@Controller
@RequiredArgsConstructor
@Slf4j
public class TeacherBOController {

    final private TeacherBOMapper teacherBOMapper;
    final private IUserService iUserService;
    final private IGroupBOService groupBOService;
    final private IStudentBOService iStudentBOService;
    final private IChatRecordBOService iChatRecordBOService;

    /**
     * 跳至教师界面
     *
     * @return teacher.html
     */
    @RequestMapping("/toTeacher/{pageNum}")
    public String toTeacher(HttpServletRequest request,
                            HttpServletResponse response,
                            @PathVariable int pageNum,
                            Model model) {

        TeacherBO teacherBO = getTeacherBOFromRedis(request, response);
        Page<GroupBO> page = new Page<GroupBO>();
        if (!ObjectUtils.isEmpty(teacherBO)) {
            Message message = groupBOService.queryGroupByTeacherId(teacherBO.getId(), pageNum);
            if (message.getCode() == MessageEnum.SUCCESS.getSTATUS()) {
                page = (Page<GroupBO>) message.getObject();
            }
            log.info("TeacherBOController -> toTeacher查询出的page信息:{}", page.getRecords());
        } else {
            log.info("TeacherBOController -> toTeacher 未从redis中获取到teacherBO");
            try {
                response.sendRedirect("/pre/toLogin");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        model.addAttribute("teacherBO", teacherBO);
        model.addAttribute("page", page);
        return "teacher";
    }


    /**
     * 跳至我的学生界面
     *
     * @return my-student.html
     */
    @RequestMapping("/toMyStudent/{teacherId}")
    public String toTeacher(@PathVariable int teacherId,
                            Model model) {

        List<StudentBO> studentBOList = iStudentBOService.list(new LambdaQueryWrapper<StudentBO>()
                .eq(StudentBO::getTeacherId, teacherId));
        model.addAttribute("studentBOList", studentBOList);
        return "my-student";
    }

    /**
     * 跳至信息修改页面
     *
     * @return teacher-setting.html
     */
    @RequestMapping("/toTeacherSetting")
    public String toTeacherSetting(Model model, HttpServletRequest request, HttpServletResponse response) {

        TeacherBO teacherBO = getTeacherBOFromRedis(request, response);
        model.addAttribute("teacherBO", teacherBO);
        model.addAttribute("isAdmin", false);
        return "teacher-setting";
    }


    /**
     * 修改信息
     *
     * @return teacher-setting.html
     */
    @RequestMapping("/teacherSetting")
    @ResponseBody
    @Transactional
    public Message teacherSetting(HttpServletRequest request,
                                  HttpServletResponse response,
                                  TeacherBO teacherBO) {
        try {
            if (ObjectUtils.isEmpty(teacherBO)) {
                return Message.error(MessageEnum.TEACHER_DATA_NULL);
            }
            log.info(" TeacherBOController -> teacherSetting 传来的teacherBO值:{}", teacherBO);
            TeacherBO teacherBO1 = teacherBOMapper.selectById(teacherBO.getId());
            if (ObjectUtils.isEmpty(teacherBO1)) {
                return Message.error(MessageEnum.QUERY_TEACHER_ERROR);
            }
            teacherBO1.setNickName(teacherBO.getNickName());
            teacherBO1.setPhone(teacherBO.getPhone());
            teacherBO1.setEmail(teacherBO.getEmail());
            teacherBOMapper.updateById(teacherBO1);
            iUserService.saveTeacherByCookie(null, request, teacherBO1);
            updateChatRecordByTeacher(teacherBO1);
            return Message.success();
        } catch (Exception e) {
            log.info(" TeacherBOController -> teacherSetting 捕获到的异常:{}", e.getMessage());
            throw new BusinessException(MessageEnum.ERROR);
        }

    }


    /**
     * 获取教师BO,从缓存中
     *
     * @param request  request
     * @param response request
     * @return teacherBO
     */
    private TeacherBO getTeacherBOFromRedis(HttpServletRequest request,
                                            HttpServletResponse response) {
        Message teacherMessage = iUserService.getTeacherByCookie(request, response);
        if (teacherMessage.getCode() != MessageEnum.SUCCESS.getSTATUS()) {
            log.info(" TeacherBOController -> getTeacherBOFromRedis redis中未获取到teacherBO:{}", teacherMessage.getMessage());
            try {
                response.sendRedirect("/pre/toLogin");
                return null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        TeacherBO teacherBO = (TeacherBO) teacherMessage.getObject();
        if (ObjectUtils.isEmpty(teacherBO)) {
            log.info(" TeacherBOController -> getTeacherBOFromRedis 未获取到teacherBO");
            try {
                response.sendRedirect("/pre/toLogin");
                return null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        log.info(" TeacherBOController -> getTeacherBOFromRedis redis中获取到的teacherBO:{}", teacherBO);
        return teacherBO;
    }

    private void updateChatRecordByTeacher(TeacherBO teacherBO) {
        List<ChatRecordBO> sendChatRecordBOS = iChatRecordBOService.list(new LambdaQueryWrapper<ChatRecordBO>()
                .eq(ChatRecordBO::getSendId, teacherBO.getId()));
        List<ChatRecordBO> receiveChatRecordBOS = iChatRecordBOService.list(new LambdaQueryWrapper<ChatRecordBO>()
                .eq(ChatRecordBO::getReceiveId, teacherBO.getId()));

        sendChatRecordBOS.forEach(chatRecordBO -> {
            chatRecordBO.setSendName(teacherBO.getNickName());
            iChatRecordBOService.updateById(chatRecordBO);
        });
        receiveChatRecordBOS.forEach(chatRecordBO -> {
            chatRecordBO.setSendName(teacherBO.getNickName());
            iChatRecordBOService.updateById(chatRecordBO);
        });
    }
}

