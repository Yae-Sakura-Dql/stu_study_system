package com.dql.stu.controller;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dql.stu.dict.UserRoleDict;
import com.dql.stu.exception.BusinessException;
import com.dql.stu.message.Message;
import com.dql.stu.message.MessageEnum;
import com.dql.stu.model.PropertySettingBO;
import com.dql.stu.model.StudentBO;
import com.dql.stu.model.TeacherBO;
import com.dql.stu.model.UserBO;
import com.dql.stu.service.func.IStudentBOService;
import com.dql.stu.service.func.ITeacherBOService;
import com.dql.stu.service.func.IUserService;
import com.dql.stu.tmodel.TeacherStudentTO;
import com.dql.stu.utils.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
public class AdminController {

    final private ITeacherBOService iTeacherBOService;
    final private IUserService iUserService;
    final private IStudentBOService iStudentBOService;
    final private RedisUtil redisUtil;

    /**
     * 跳至管理员界面
     *
     * @return teacher.html
     */
    @RequestMapping("/toAdmin/{teacherPageNum}/{studentPageNum}")
    public String toAdmin(@PathVariable int teacherPageNum,
                          @PathVariable int studentPageNum,
                          Model model) {

        Page<TeacherBO> teacherPage = new Page<>(teacherPageNum, PropertySettingBO.pageSize);
        Page<StudentBO> studentPage = new Page<>(studentPageNum, PropertySettingBO.pageSize);

        Page<TeacherBO> teacherBOPage = iTeacherBOService.page(teacherPage);
        Page<StudentBO> studentBOPage = iStudentBOService.page(studentPage);
        model.addAttribute("teacherBOPage", teacherBOPage);
        model.addAttribute("studentBOPage", studentBOPage);
        return "admin";
    }


    /**
     * 跳至信息修改页面
     *
     * @return teacher-setting.html
     */
    @RequestMapping("/toAdminSetting/{role}/{id}")
    public String toAdminSetting(Model model,
                                 @PathVariable String role,
                                 @PathVariable Integer id) {
        if (UserRoleDict.TEACHER.equals(role)) {

            UserBO userBO = iUserService.getById(id);
            if (ObjectUtils.isEmpty(userBO)) {
                throw new BusinessException(MessageEnum.QUERY_USER_ERROR);
            }
            TeacherBO teacherBO = iTeacherBOService.getById(id);
            if (ObjectUtils.isEmpty(teacherBO)) {
                throw new BusinessException(MessageEnum.QUERY_TEACHER_ERROR);
            }
            TeacherStudentTO teacherStudentTO = new TeacherStudentTO();
            teacherStudentTO.setId(id);
            teacherStudentTO.setUsername(userBO.getUsername());
            teacherStudentTO.setPassword(userBO.getPassword());
            teacherStudentTO.setUserRoleDict(UserRoleDict.TEACHER);
            teacherStudentTO.setNickName(teacherBO.getNickName());

            model.addAttribute("teacherStudentTO", teacherStudentTO);
            model.addAttribute("teacherRole", UserRoleDict.TEACHER);
            return "admin-setting";
        } else {
            UserBO userBO = iUserService.getById(id);
            if (ObjectUtils.isEmpty(userBO)) {
                throw new BusinessException(MessageEnum.QUERY_USER_ERROR);
            }
            StudentBO studentBO = iStudentBOService.getById(id);
            if (ObjectUtils.isEmpty(studentBO)) {
                throw new BusinessException(MessageEnum.QUERY_STUDENT_ERROR);
            }
            List<TeacherBO> teacherBOList = iTeacherBOService.list();
            if (CollectionUtils.isEmpty(teacherBOList)) {
                throw new BusinessException(MessageEnum.QUERY_TEACHER_ERROR);
            }

            TeacherStudentTO teacherStudentTO = new TeacherStudentTO();
            teacherStudentTO.setClassroom(studentBO.getClassroom());
            teacherStudentTO.setTeacherId(studentBO.getTeacherId());
            teacherStudentTO.setTeacherBOList(teacherBOList);
            teacherStudentTO.setId(id);
            teacherStudentTO.setUsername(userBO.getUsername());
            teacherStudentTO.setPassword(userBO.getPassword());
            teacherStudentTO.setUserRoleDict(UserRoleDict.STUDENT);
            teacherStudentTO.setNickName(studentBO.getNickName());

            model.addAttribute("teacherStudentTO", teacherStudentTO);
            return "admin-setting";
        }
    }


    /**
     * 跳至信息修改页面
     *
     * @return teacher-setting.html
     */
    @RequestMapping("/toAddAdminSetting/{role}")
    public String toAdminSetting(Model model, @PathVariable String role) {


        TeacherStudentTO teacherStudentTO = new TeacherStudentTO();
        if (UserRoleDict.TEACHER.equals(role)) {
            teacherStudentTO.setUserRoleDict(UserRoleDict.TEACHER);

        } else {
            List<TeacherBO> teacherBOList = iTeacherBOService.list();
            if (CollectionUtils.isEmpty(teacherBOList)) {
                throw new BusinessException(MessageEnum.QUERY_TEACHER_ERROR);
            }
            teacherStudentTO.setUserRoleDict(UserRoleDict.STUDENT);
            teacherStudentTO.setTeacherBOList(teacherBOList);

        }
        model.addAttribute("teacherStudentTO", teacherStudentTO);
        return "admin-setting";
    }


    /**
     * 信息修改页面
     *
     * @return admin-setting
     */
    @RequestMapping("/adminSetting")
    @ResponseBody
    @Transactional
    public Message adminSetting(TeacherStudentTO teacherStudentTO,
                                HttpServletRequest request) {
        try {
            UserBO userById = iUserService.getById(teacherStudentTO.getId());


            UserBO userBO = new UserBO();
            userBO.setId(teacherStudentTO.getId());
            userBO.setUsername(teacherStudentTO.getUsername());
            userBO.setPassword(teacherStudentTO.getPassword());
            userBO.setUserRoleDict(teacherStudentTO.getUserRoleDict());
            userBO.setRegisterDate(LocalDateTime.now());
            if (ObjectUtils.isEmpty(userById)) {
                iUserService.save(userBO);
                userBO = iUserService.getOne(new LambdaQueryWrapper<UserBO>()
                        .eq(UserBO::getUsername, userBO.getUsername())
                        .eq(UserBO::getPassword, userBO.getPassword()));
            } else {
                iUserService.updateById(userBO);

            }


            if (teacherStudentTO.getUserRoleDict().equals(UserRoleDict.TEACHER)) {
                TeacherBO teacherBO = new TeacherBO();
                teacherBO.setId(userBO.getId());
                teacherBO.setNickName(teacherStudentTO.getNickName());
                if (ObjectUtils.isEmpty(userById)) {
                    iTeacherBOService.save(teacherBO);
                } else {
                    iTeacherBOService.updateById(teacherBO);
                }

            }

            if (teacherStudentTO.getUserRoleDict().equals(UserRoleDict.STUDENT)) {
                StudentBO studentBO = new StudentBO();
                studentBO.setId(userBO.getId());
                studentBO.setNickName(teacherStudentTO.getNickName());
                studentBO.setClassroom(teacherStudentTO.getClassroom());
                studentBO.setTeacherId(teacherStudentTO.getTeacherId());
                if (ObjectUtils.isEmpty(userById)) {
                    iStudentBOService.save(studentBO);
                } else {
                    iStudentBOService.updateById(studentBO);
                }

            }

            redisUtil.flushDB();
            iUserService.saveUserByCookie(null, request, userBO);
            return Message.success();
        } catch (Exception e) {
            throw new BusinessException(MessageEnum.ERROR);
        }

    }

}

