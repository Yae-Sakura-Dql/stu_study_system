package com.dql.stu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dql.stu.mapper.UserMapper;
import com.dql.stu.message.Message;
import com.dql.stu.message.MessageEnum;
import com.dql.stu.model.StudentBO;
import com.dql.stu.model.TeacherBO;
import com.dql.stu.model.UserBO;
import com.dql.stu.service.func.IUserService;
import com.dql.stu.utils.CookieUtil;
import com.dql.stu.utils.RedisUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author dql
 * @since 2021-06-20
 */
@Service
@Slf4j
@AllArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, UserBO> implements IUserService {


    /**
     * Redis操作封装类
     */
    final private RedisUtil redisUtil;


    /**
     * Redis通过cookie获取用户对象
     * 并再次session存储
     *
     * @param request  request
     * @param response response
     * @return 结果
     */
    @Override
    public Message getUserByCookie(HttpServletRequest request, HttpServletResponse response) {
        String ticket = CookieUtil.getToken(request);
        if (StringUtils.isEmpty(ticket)) {
            log.info(" UserServiceImpl -> getUserByCookie捕获到的异常:{}", MessageEnum.QUERY_COOKIE_ERROR);
            return Message.error(MessageEnum.QUERY_COOKIE_ERROR);
        }

        UserBO userBO = (UserBO) redisUtil.get("user:" + ticket + ":userBO");
        log.info(" UserServiceImpl -> getUserByCookie  redis获取userBO:{}", userBO);
        if (!ObjectUtils.isEmpty(userBO)) {
            CookieUtil.setToken(response, ticket);
            redisUtil.expire("user:" + ticket + ":userBO", 1800);
            return Message.success(userBO);
        }
        return Message.error(MessageEnum.QUERY_USER_REDIS_ERROR);
    }

    /**
     * Redis通过cookie获取教师对象
     * 并再次session存储
     *
     * @param request  request
     * @param response response
     * @return 结果
     */
    @Override
    public Message getTeacherByCookie(HttpServletRequest request, HttpServletResponse response) {
        String ticket = CookieUtil.getToken(request);
        if (StringUtils.isEmpty(ticket)) {
            log.info(" UserServiceImpl -> getTeacherByCookie捕获到的异常 :{}", MessageEnum.QUERY_COOKIE_ERROR);
            return Message.error(MessageEnum.QUERY_COOKIE_ERROR);
        }

        TeacherBO teacherBO = (TeacherBO) redisUtil.get("user:" + ticket + ":teacherBO");
        log.info(" UserServiceImpl -> getTeacherByCookie redis获取teacherBO:{}", teacherBO);
        if (!ObjectUtils.isEmpty(teacherBO)) {

            CookieUtil.setToken(response, ticket);
            redisUtil.expire("user:" + ticket + ":teacherBO", 1800);
//
            return Message.success(teacherBO);
        }
        return Message.error(MessageEnum.QUERY_TEACHER_REDIS_ERROR);
    }


    /**
     * Redis通过cookie获取学生对象
     * 并再次session存储
     *
     * @param request  request
     * @param response response
     * @return 结果
     */
    @Override
    public Message getStudentByCookie(HttpServletRequest request, HttpServletResponse response) {
        String ticket = CookieUtil.getToken(request);
        if (StringUtils.isEmpty(ticket)) {
            log.info(" UserServiceImpl -> getStudentByCookie 捕获到的异常 :{}", MessageEnum.QUERY_COOKIE_ERROR);
            return Message.error(MessageEnum.QUERY_COOKIE_ERROR);
        }

        StudentBO studentBO = (StudentBO) redisUtil.get("user:" + ticket + ":studentBO");
        log.info(" UserServiceImpl ->  getStudentByCookie redis获取studentBO:{}", studentBO);
        if (!ObjectUtils.isEmpty(studentBO)) {
            CookieUtil.setToken(response, ticket);

            redisUtil.expire("user:" + ticket + ":studentBO", 1800);
            return Message.success(studentBO);
        }
        return Message.error(MessageEnum.QUERY_STUDENT_REDIS_ERROR);
    }

    /**
     * 通过cookie存储学生
     *
     * @param user 用户
     * @return message
     */
    @Override
    public Message saveUserByCookie(String ticket, HttpServletRequest request, UserBO user) {
        if (!ObjectUtils.isEmpty(request) && StringUtils.isEmpty(ticket)) {
            ticket = CookieUtil.getToken(request);
        }
        if (StringUtils.isEmpty(ticket)) {
            log.info(" UserServiceImpl ->  saveUserByCookie 捕获到的异常:{}", MessageEnum.QUERY_COOKIE_ERROR);
            return Message.error(MessageEnum.QUERY_COOKIE_ERROR);
        }

        boolean result = redisUtil.set("user:" + ticket + ":userBO", user, 1800);
        log.info(" UserServiceImpl -> saveUserByCookie redis存储userBO:{},结果:{}", user, result);
        return Message.success();
    }

    /**
     * Redis通过cookie存储教师对象
     *
     * @param teacherBO 教师信息
     * @return 结果
     */
    @Override
    public Message saveTeacherByCookie(String ticket, HttpServletRequest request, TeacherBO teacherBO) {
        if (!ObjectUtils.isEmpty(request) && StringUtils.isEmpty(ticket)) {
            ticket = CookieUtil.getToken(request);
        }
        if (StringUtils.isEmpty(ticket)) {
            log.info(" UserServiceImpl -> saveTeacherByCookie捕获到的异常:{}", MessageEnum.QUERY_COOKIE_ERROR);
            return Message.error(MessageEnum.QUERY_COOKIE_ERROR);
        }

        boolean result = redisUtil.set("user:" + ticket + ":teacherBO", teacherBO, 1800);
        log.info(" UserServiceImpl -> saveTeacherByCookie redis存储teacherBO:{},结果:{}", teacherBO, result);
        return Message.success();
    }

    /**
     * Redis通过cookie存储学生对象
     *
     * @param studentBO 学生信息
     * @return 结果
     */
    @Override
    public Message saveStudentByCookie(String ticket, HttpServletRequest request, StudentBO studentBO) {
        if (!ObjectUtils.isEmpty(request) && StringUtils.isEmpty(ticket)) {
            ticket = CookieUtil.getToken(request);
        }
        if (StringUtils.isEmpty(ticket)) {
            log.info(" UserServiceImpl -> saveStudentByCookie 捕获到的异常 :{}", MessageEnum.QUERY_COOKIE_ERROR);
            return Message.error(MessageEnum.QUERY_COOKIE_ERROR);
        }

        boolean result = redisUtil.set("user:" + ticket + ":studentBO", studentBO, 1800);
        log.info(" UserServiceImpl -> saveStudentByCookie redis存储studentBO:{},结果:{}", studentBO, result);
        return Message.success();
    }


}

