package com.dql.stu.tmodel;

import lombok.Data;


/**
 * @author Dql
 * @date 2021/6/20   20:15
 * description:登录时封装的对象
 */
@Data
public class UserLoginTO {

    private String username;

    private String password;

    private String userRoleDict;
}
