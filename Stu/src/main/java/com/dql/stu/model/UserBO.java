package com.dql.stu.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 * 用户对象
 *
 * @author dql
 * @since 2021-06-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("user_b_o")
public class UserBO extends Model<StudentBO> {

    public static final String ID = "id";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String USER_ROLE_DICT = "user_role_dict";
    public static final String REGISTER_DATE = "register_date";
    private static final long serialVersionUID = 1L;



    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String username;

    private String password;


    private String userRoleDict;

    private LocalDateTime registerDate;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
