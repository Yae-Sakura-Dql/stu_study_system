package com.dql.stu.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dql.stu.exception.BusinessException;
import com.dql.stu.message.Message;
import com.dql.stu.message.MessageEnum;
import com.dql.stu.model.ChatRecordBO;
import com.dql.stu.model.GroupBO;
import com.dql.stu.model.StudentBO;
import com.dql.stu.model.TeacherBO;
import com.dql.stu.service.func.*;
import lombok.AllArgsConstructor;
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
 * @author 段启龙
 * @since 2021-12-18
 */
@Controller
@Slf4j
@AllArgsConstructor
public class StudentBOController {


    final private IUserService iUserService;
    final private IGroupBOService iGroupBOService;
    final private ITeacherBOService iTeacherBOService;
    final private IStudentBOService iStudentBOService;
    final private IChatRecordBOService iChatRecordBOService;

    /**
     * 跳至学生界面
     *
     * @return student.html
     */
    @RequestMapping("/toStudent/{pageNum}")
    public String toStudent(HttpServletRequest request,
                            HttpServletResponse response,
                            @PathVariable int pageNum,
                            Model model) {

        StudentBO studentBO = getStudentBOFromRedis(request, response);
        Page<GroupBO> page = new Page<>();
        TeacherBO teacherBO;
        if (!ObjectUtils.isEmpty(studentBO)) {
            Message message = iGroupBOService.queryGroupByStudentId(studentBO.getId(), pageNum);
            if (message.getCode() == MessageEnum.SUCCESS.getSTATUS()) {
                page = (Page<GroupBO>) message.getObject();
            }
            log.info("TeacherBOController -> toStudent 查询出的page信息:{}", page.getRecords());
            teacherBO = iTeacherBOService.getById(studentBO.getTeacherId());
        } else {
            log.info("TeacherBOController -> toStudent 未从redis中获取到studentBO");
            try {
                response.sendRedirect("/pre/toLogin");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        model.addAttribute("studentBO", studentBO);
        model.addAttribute("teacherBO", teacherBO);
        model.addAttribute("page", page);
        return "student";
    }

    /**
     * 跳至信息修改页面
     *
     * @return student-setting.html
     */
    @RequestMapping("/toStudentSetting")
    public String toStudentSetting(Model model, HttpServletRequest request, HttpServletResponse response) {

        StudentBO studentBO = getStudentBOFromRedis(request, response);
        model.addAttribute("studentBO", studentBO);
        model.addAttribute("isAdmin", false);
        return "student-setting";
    }


    /**
     * 修改信息
     *
     * @return message
     */
    @RequestMapping("/studentSetting")
    @ResponseBody
    @Transactional
    public Message studentSetting(HttpServletRequest request,
                                  HttpServletResponse response,
                                  StudentBO studentBO) {
        try {
            if (ObjectUtils.isEmpty(studentBO)) {
                return Message.error(MessageEnum.STUDENT_DATA_NULL);
            }
            log.info(" StudentBOController -> studentSetting 传来的studentBO值:{}", studentBO);
            StudentBO studentBO1 = iStudentBOService.getById(studentBO.getId());
            if (ObjectUtils.isEmpty(studentBO1)) {
                return Message.error(MessageEnum.QUERY_TEACHER_ERROR);
            }
            studentBO1.setNickName(studentBO.getNickName());
            studentBO1.setPhone(studentBO.getPhone());
            studentBO1.setEmail(studentBO.getEmail());
            iStudentBOService.updateById(studentBO1);
            updateChatRecordByStudent(studentBO1);
            iUserService.saveStudentByCookie(null, request, studentBO1);
            return Message.success();
        } catch (Exception e) {
            log.info(" StudentBOController -> studentSetting 捕获到的异常:{}", e.getMessage());
            throw new BusinessException(MessageEnum.ERROR);
        }

    }


    /**
     * 跳至我的同学界面
     *
     * @return my-student.html
     */
    @RequestMapping("/toMyClassmates/{studentId}")
    public String toTeacher(@PathVariable int studentId,
                            Model model) {

        StudentBO studentBOById = iStudentBOService.getById(studentId);
        TeacherBO teacherBO = iTeacherBOService.getById(studentBOById.getTeacherId());

        List<StudentBO> studentBOList = iStudentBOService.list(new LambdaQueryWrapper<StudentBO>()
                .eq(StudentBO::getTeacherId, studentBOById.getTeacherId()));
        studentBOList.stream().filter(studentBO -> {
            return studentBO.getId() != studentId;
        });
        model.addAttribute("studentBOList", studentBOList);
        model.addAttribute("teacherBO", teacherBO);
        return "my-classmates";
    }

    /**
     * 获取教师学生BO,从缓存中
     *
     * @param request  request
     * @param response request
     * @return studentBO
     */
    private StudentBO getStudentBOFromRedis(HttpServletRequest request,
                                            HttpServletResponse response) {
        Message studentMessage = iUserService.getStudentByCookie(request, response);
        if (studentMessage.getCode() != MessageEnum.SUCCESS.getSTATUS()) {
            log.info(" StudentBOController -> getStudentBOFromRedis redis中未获取到studentBO:{}", studentMessage.getMessage());
            try {
                response.sendRedirect("/pre/toLogin");
                return null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        StudentBO studentBO = (StudentBO) studentMessage.getObject();
        if (ObjectUtils.isEmpty(studentBO)) {
            log.info(" StudentBOController -> getStudentBOFromRedis 未获取到studentBO");
            try {
                response.sendRedirect("/pre/toLogin");
                return null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        log.info(" StudentBOController -> getStudentBOFromRedis redis中获取到的studentBO:{}", studentBO);
        return studentBO;
    }


    private void updateChatRecordByStudent(StudentBO studentBO) {
        List<ChatRecordBO> sendChatRecordBOS = iChatRecordBOService.list(new LambdaQueryWrapper<ChatRecordBO>()
                .eq(ChatRecordBO::getSendId, studentBO.getId()));
        List<ChatRecordBO> receiveChatRecordBOS = iChatRecordBOService.list(new LambdaQueryWrapper<ChatRecordBO>()
                .eq(ChatRecordBO::getReceiveId, studentBO.getId()));

        sendChatRecordBOS.forEach(chatRecordBO -> {
            chatRecordBO.setSendName(studentBO.getNickName());
            iChatRecordBOService.updateById(chatRecordBO);
        });
        receiveChatRecordBOS.forEach(chatRecordBO -> {
            chatRecordBO.setSendName(studentBO.getNickName());
            iChatRecordBOService.updateById(chatRecordBO);
        });
    }
}

