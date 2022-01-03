package com.dql.stu.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author 段启龙
 * @since 2021-12-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class GroupBO extends Model<GroupBO> {

    private static final long serialVersionUID = 1L;
    public static final String ID = "id";
    public static final String GROUP_NAME = "group_name";
    public static final String TEACHER_ID = "teacher_id";
    public static final String STUDENT_ID = "student_id";
    public static final String LAST_BROWSE_DATE = "last_browse_date";


    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String groupName;
    private Integer teacherId;
    private Integer studentId;
    private LocalDateTime lastBrowseDate;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
