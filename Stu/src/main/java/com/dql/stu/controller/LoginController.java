package com.dql.stu.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dql.stu.dict.UserRoleDict;
import com.dql.stu.exception.BusinessException;
import com.dql.stu.mapper.StudentBOMapper;
import com.dql.stu.mapper.TeacherBOMapper;
import com.dql.stu.message.Message;
import com.dql.stu.message.MessageEnum;
import com.dql.stu.model.StudentBO;
import com.dql.stu.model.TeacherBO;
import com.dql.stu.model.UserBO;
import com.dql.stu.service.func.ILoginService;
import com.dql.stu.service.func.IUserService;
import com.dql.stu.tmodel.UserLoginTO;
import com.dql.stu.utils.CookieUtil;
import com.dql.stu.utils.UUIDUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @author Dql
 * @date 2021/6/20   18:57
 * description:登录页面控制器
 */
@Controller
@Slf4j
@AllArgsConstructor
public class LoginController {

    //登录服务
    final private ILoginService iLoginService;

    final private IUserService iUserService;


    final private TeacherBOMapper teacherBOMapper;
    final private StudentBOMapper studentBOMapper;


    /**
     * 跳至首页
     *
     * @return login.html
     */
    @RequestMapping("/")
    public String toIndex() {
        return "login";
    }


    /**
     * 跳至登录界面
     *
     * @return login.html
     */
    @RequestMapping("/pre/toLogin")
    public String toLogin() {
        return "login";
    }


    /**
     * 登录
     * 在IUserService中验证
     */
    @RequestMapping("/pre/login")
    @ResponseBody
    @Transactional
    public Message login(@Valid UserLoginTO userLoginTO,
                         HttpServletResponse response) {
        log.info(" LoginController -> login 登录时传递的参数:{}", userLoginTO);
        //获取用户名和密码
        String username = userLoginTO.getUsername();
        String password = userLoginTO.getPassword();
        String userRoleDict = userLoginTO.getUserRoleDict();

        if (ObjectUtils.isEmpty(username)
                || ObjectUtils.isEmpty(password)
                || ObjectUtils.isEmpty(userRoleDict)) {
            throw new BusinessException(MessageEnum.LOGIN_NULL);
        }
        //根据用户名获取user对象
        UserBO user = iLoginService.getOne(new LambdaQueryWrapper<UserBO>()
                .eq(UserBO::getUsername, username)
                .eq(UserBO::getUserRoleDict, userRoleDict));
        log.info(" LoginController -> login 查询到的userBO:{}", user);


        //判断用户名密码是否正确
        if (user == null) {
            throw new BusinessException(MessageEnum.USER_NOT_EXIST);
        }
        if (!password.equals(user.getPassword())) {
            throw new BusinessException(MessageEnum.PASSWORD_ERROR);
        }


        //生成cookie
        String ticket = UUIDUtil.uuid();


        CookieUtil.setToken(response, ticket);
        //存储user缓存
        iUserService.saveUserByCookie(ticket, null, user);

        switch (userRoleDict) {
            case UserRoleDict.ADMIN: {
                break;
            }
            case UserRoleDict.TEACHER: {
                TeacherBO teacherBO = teacherBOMapper.selectById(user.getId());
                if (ObjectUtils.isEmpty(teacherBO)) {
                    throw new BusinessException(MessageEnum.QUERY_TEACHER_ERROR);
                }
                log.info(" LoginController -> login 查询到的teacherBO:{}", teacherBO);
                //存储教师缓存
                iUserService.saveTeacherByCookie(ticket, null, teacherBO);
                break;
            }
            case UserRoleDict.STUDENT: {
                StudentBO studentBO = studentBOMapper.selectById(user.getId());
                if (ObjectUtils.isEmpty(studentBO)) {
                    throw new BusinessException(MessageEnum.QUERY_STUDENT_ERROR);
                }
                log.info(" LoginController -> login 查询到的studentBO:{}", studentBO);
                //存储学生缓存
                iUserService.saveStudentByCookie(ticket, null, studentBO);
                break;
            }
            default: {
                throw new BusinessException(MessageEnum.PROPERTIES_ERROR);
            }
        }


        return Message.success(user);
    }

}
