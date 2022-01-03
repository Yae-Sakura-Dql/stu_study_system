package com.dql.stu.controller;

import java.time.LocalDateTime;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dql.stu.dict.ChatRecordTypeDict;
import com.dql.stu.dict.UserRoleDict;
import com.dql.stu.exception.BusinessException;
import com.dql.stu.message.Message;
import com.dql.stu.message.MessageEnum;
import com.dql.stu.model.ChatRecordBO;
import com.dql.stu.model.StudentBO;
import com.dql.stu.model.TeacherBO;
import com.dql.stu.model.UserBO;
import com.dql.stu.service.func.IChatRecordBOService;
import com.dql.stu.service.func.IStudentBOService;
import com.dql.stu.service.func.ITeacherBOService;
import com.dql.stu.service.func.IUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 段启龙
 * @since 2022-01-01
 */
@Controller
@Slf4j
@AllArgsConstructor
public class ChatRecordBOController {

    final private IChatRecordBOService iChatRecordBOService;
    final private IUserService iUserService;
    final private ITeacherBOService iTeacherBOService;
    final private IStudentBOService iStudentBOService;

    /**
     * 获取群聊信息
     *
     * @param request   request
     * @param response  response
     * @param groupName 当前分组
     * @param model     model
     * @return 群聊界面
     */
    @RequestMapping("/toGroupChatRecord/{groupName}")
    public String toGroupChatRecord(HttpServletRequest request,
                                    HttpServletResponse response,
                                    @PathVariable String groupName,
                                    Model model) {
        try {
            List<ChatRecordBO> chatRecordBOS = iChatRecordBOService.list(new LambdaQueryWrapper<ChatRecordBO>()
                    .eq(ChatRecordBO::getReceiveName, groupName)
                    .orderByAsc(ChatRecordBO::getCreateTime));
            Message userByCookie = iUserService.getUserByCookie(request, response);
            UserBO userBO = (UserBO) userByCookie.getObject();

            model.addAttribute("userBO", userBO);
            model.addAttribute("groupName", groupName);
            model.addAttribute("chatRecordBOS", chatRecordBOS);
            return "group-chat-record";
        } catch (Exception e) {
            log.info("ChatRecordBOController -> toGroupChatRecord 捕获到的错误:{}", e.getMessage());
            throw new BusinessException(MessageEnum.QUERY_CHAT_RECORD_ERROR);
        }
    }


    /**
     * 获取私聊信息
     *
     * @param request  request
     * @param response response
     * @param id       对象的id
     * @param model    model
     * @return 私聊界面
     */
    @RequestMapping("/toPersonChatRecord/{id}")
    public String toPersonChatRecord(HttpServletRequest request,
                                     HttpServletResponse response,
                                     @PathVariable Integer id,
                                     Model model) {
        try {
            Message userByCookie = iUserService.getUserByCookie(request, response);
            UserBO userBO = (UserBO) userByCookie.getObject();

            List<ChatRecordBO> chatRecordBOS = iChatRecordBOService.list(new LambdaQueryWrapper<ChatRecordBO>()
                    .eq(ChatRecordBO::getSendId, id)
                    .eq(ChatRecordBO::getReceiveId, userBO.getId())
                    .or()
                    .eq(ChatRecordBO::getReceiveId, id)
                    .eq(ChatRecordBO::getSendId, userBO.getId())
                    .orderByAsc(ChatRecordBO::getCreateTime));


            model.addAttribute("id", id);
            model.addAttribute("userBO", userBO);
            model.addAttribute("chatRecordBOS", chatRecordBOS);
            return "person-chat-record";
        } catch (Exception e) {
            log.info("ChatRecordBOController -> toPersonChatRecord 捕获到的错误:{}", e.getMessage());
            throw new BusinessException(MessageEnum.QUERY_CHAT_RECORD_ERROR);
        }
    }


    /**
     * 发送群聊信息
     *
     * @return 群聊界面
     */
    @RequestMapping("/sendGroupChatRecord")
    @ResponseBody
    public Message sendGroupChatRecord(HttpServletRequest request,
                                       HttpServletResponse response,
                                       ChatRecordBO chatRecordBO) {
        try {
            Message userByCookie = iUserService.getUserByCookie(request, response);
            UserBO userBO = (UserBO) userByCookie.getObject();
            String sendName = getNameByUser(userBO);

            chatRecordBO.setSendId(userBO.getId());
            chatRecordBO.setSendName(sendName);
            chatRecordBO.setReceiveId(0);
            chatRecordBO.setType(ChatRecordTypeDict.GROUP);
            chatRecordBO.setCreateTime(LocalDateTime.now());

            iChatRecordBOService.save(chatRecordBO);

            return Message.success();
        } catch (Exception e) {
            log.info("ChatRecordBOController -> sendGroupChatRecord 捕获到的错误:{}", e.getMessage());
            throw new BusinessException(MessageEnum.CREATE_CHAT_RECORD_ERROR);
        }
    }


    /**
     * 发送私聊信息
     *
     * @return 群聊界面
     */
    @RequestMapping("/sendPersonChatRecord")
    @ResponseBody
    public Message sendPersonChatRecord(HttpServletRequest request,
                                        HttpServletResponse response,
                                        ChatRecordBO chatRecordBO) {
        try {
            Message userByCookie = iUserService.getUserByCookie(request, response);
            UserBO sendUser = (UserBO) userByCookie.getObject();
            UserBO receiveUser = iUserService.getById(chatRecordBO.getReceiveId());
            String sendName = getNameByUser(sendUser);
            String receiveName = getNameByUser(receiveUser);
            chatRecordBO.setSendId(sendUser.getId());
            chatRecordBO.setSendName(sendName);
            chatRecordBO.setReceiveName(receiveName);
            chatRecordBO.setType(ChatRecordTypeDict.PERSON);
            chatRecordBO.setCreateTime(LocalDateTime.now());

            iChatRecordBOService.save(chatRecordBO);

            return Message.success();
        } catch (Exception e) {
            log.info("ChatRecordBOController -> sendPersonChatRecord 捕获到的错误:{}", e.getMessage());
            throw new BusinessException(MessageEnum.CREATE_CHAT_RECORD_ERROR);
        }
    }


    private String getNameByUser(UserBO userBO) {
        String sendName = null;
        if (UserRoleDict.TEACHER.equals(userBO.getUserRoleDict())) {
            TeacherBO teacherBO = iTeacherBOService.getById(userBO.getId());
            sendName = teacherBO.getNickName();
        } else if (UserRoleDict.STUDENT.equals(userBO.getUserRoleDict())) {
            StudentBO studentBO = iStudentBOService.getById(userBO.getId());
            sendName = studentBO.getNickName();
        }
        return sendName;
    }
}

