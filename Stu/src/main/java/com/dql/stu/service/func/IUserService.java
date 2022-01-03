package com.dql.stu.service.func;


import com.baomidou.mybatisplus.extension.service.IService;
import com.dql.stu.message.Message;
import com.dql.stu.model.StudentBO;
import com.dql.stu.model.TeacherBO;
import com.dql.stu.model.UserBO;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 服务类接口
 * </p>
 *
 * @author dql
 * @since 2021-06-20
 */
@Service
@Primary
public interface IUserService extends IService<UserBO> {


    /**
     * Redis通过cookie获取用户对象
     *
     * @param request  request
     * @param response response
     * @return User对象
     */
    Message getUserByCookie(HttpServletRequest request,
                            HttpServletResponse response);

    /**
     * Redis通过cookie获取教师对象
     * 并再次session存储
     *
     * @param request  request
     * @param response response
     * @return 结果
     */
    Message getTeacherByCookie(HttpServletRequest request, HttpServletResponse response);

    /**
     * Redis通过cookie获取学生对象
     * 并再次session存储
     *
     * @param request  request
     * @param response response
     * @return 结果
     */
    Message getStudentByCookie(HttpServletRequest request, HttpServletResponse response);
    

    /**
     * 通过cookie存储学生
     *
     * @param user 用户
     * @return message
     */
    Message saveUserByCookie(String ticket, HttpServletRequest request, UserBO user);

    /**
     * Redis通过cookie存储教师对象
     *
     * @param teacherBO 教师信息
     * @return 结果
     */
    Message saveTeacherByCookie(String ticket, HttpServletRequest request, TeacherBO teacherBO);

    /**
     * Redis通过cookie存储学生对象
     *
     * @param studentBO 学生信息
     * @return 结果
     */
    Message saveStudentByCookie(String ticket, HttpServletRequest request, StudentBO studentBO);
}
