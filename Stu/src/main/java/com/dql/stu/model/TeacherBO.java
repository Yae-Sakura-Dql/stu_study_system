package com.dql.stu.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author 段启龙
 * @date 2021/12/9   23:31
 * description:TeacherBO
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("teacher_b_o")
public class TeacherBO extends Model<StudentBO> {
    private static final long serialVersionUID = 1L;
    public static final String ID = "id";
    public static final String NICK_NAME = "nick_name";
    public static final String PHONE = "phone";
    public static final String EMAIL = "email";


    @TableId(value = "id", type = IdType.NONE)
    private Integer id;

    private String nickName;

    private String phone;

    private String email;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
