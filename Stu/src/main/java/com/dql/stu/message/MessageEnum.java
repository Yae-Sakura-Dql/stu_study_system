package com.dql.stu.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Dql
 * @date 2021/6/20   19:57
 * description:公共返回对象的枚举
 */


@AllArgsConstructor
@Getter
public enum MessageEnum {


    /**
     * 服务端异常
     */
    ERROR(500, "服务端异常"),


    /**
     * 用户未注册或用户名错误
     */
    USER_NOT_EXIST(520, "未注册"),


    /**
     * 密码错误
     */
    PASSWORD_ERROR(521, "密码错误"),

    /**
     * 用户名或密码、角色为空
     */
    LOGIN_NULL(522, "用户名或密码、角色为空"),

    /**
     * 参数异常
     */
    PROPERTIES_ERROR(523, "参数异常"),

    /**
     * 用户已注册
     */
    USER_ALREADY_EXIST(524, "用户已注册"),

    /**
     * 新增用户信息失败
     */
    USER_INSERT_ERROR(525, "新增用户信息失败"),

    /**
     * 新增教师信息失败
     */
    TEACHER_INSERT_ERROR(526, "新增教师信息失败"),

    /**
     * 新增学生信息失败
     */
    STUDENT_INSERT_ERROR(527, "新增学生信息失败"),

    /**
     * Cookie中未含有token
     */
    QUERY_COOKIE_ERROR(528, "Cookie中未含有token"),

    /**
     * 查询user缓存失败
     */
    QUERY_USER_REDIS_ERROR(529, "查询user缓存失败"),

    /**
     * 查询教师缓存失败
     */
    QUERY_TEACHER_REDIS_ERROR(530, "查询教师缓存失败"),

    /**
     * 查询学生缓存失败
     */
    QUERY_STUDENT_REDIS_ERROR(531, "查询学生缓存失败"),

    /**
     * 更新user缓存失败
     */
    SAVE_USER_REDIS_ERROR(532, "更新user缓存失败"),

    /**
     * 更新教师缓存失败
     */
    SAVE_TEACHER_REDIS_ERROR(533, "更新教师缓存失败"),

    /**
     * 更新学生缓存失败
     */
    SAVE_STUDENT_REDIS_ERROR(534, "更新学生缓存失败"),

    /**
     * 查询用户信息失败
     */
    QUERY_USER_ERROR(535, "查询用户信息失败"),


    /**
     * 查询教师信息失败
     */
    QUERY_TEACHER_ERROR(601, "查询教师信息失败"),
    /**
     * 教师信息为空
     */
    TEACHER_DATA_NULL(602, "教师信息为空"),

    /**
     * 删除教师信息失败
     */
    DELETE_TEACHER_ERROR(603, "删除教师信息失败"),


    /**
     * 查询学生信息失败
     */
    QUERY_STUDENT_ERROR(701, "查询学生信息失败"),
    /**
     * 学生信息为空
     */
    STUDENT_DATA_NULL(602, "学生信息为空"),

    /**
     * 查询分组信息失败
     */
    QUERY_GROUP_ERROR(801, "查询分组信息失败"),

    /**
     * 组名信息为空
     */
    GROUP_NAME_NULL(802, "组名信息为空"),

    /**
     * 分组学生为空
     */
    SELECT_STUDENT_NULL(803, "分组学生为空"),


    /**
     * 查询聊天记录失败
     */
    QUERY_CHAT_RECORD_ERROR(901, "查询聊天记录失败"),

    /**
     * 新增聊天记录失败
     */
    CREATE_CHAT_RECORD_ERROR(902, "新增聊天记录失败"),

    /**
     * 成功
     */
    SUCCESS(200, "SUCCESS");


    /**
     * 状态码
     */
    private final Integer STATUS;

    /**
     * 消息
     */
    private final String MESSAGE;
}
