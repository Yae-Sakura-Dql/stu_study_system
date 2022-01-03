package com.dql.stu.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author 段启龙
 * @since 2021-12-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class StudentBO extends Model<StudentBO> {


    private static final long serialVersionUID = 1L;
    public static final String ID = "id";
    public static final String NICK_NAME = "nick_name";
    public static final String PHONE = "phone";
    public static final String EMAIL = "email";
    public static final String CLASSROOM = "classroom";
    public static final String TEACHER_ID = "teacher_id";

    @TableId(value = "id", type = IdType.NONE)
    private Integer id;

    private String nickName;

    private String phone;

    private String email;

    private String classroom;

    private Integer teacherId;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
