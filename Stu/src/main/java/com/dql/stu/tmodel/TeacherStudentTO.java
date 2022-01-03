package com.dql.stu.tmodel;

import com.dql.stu.model.TeacherBO;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 段启龙
 * @date 2022/1/2   19:03
 * description:管理员更新教师/学生信息
 */
@Data
public class TeacherStudentTO {

    private Integer id;

    private String username;

    private String password;


    private String userRoleDict;

    private LocalDateTime registerDate;

    private String nickName;

    private String classroom;

    private Integer teacherId;

    private List<TeacherBO> teacherBOList;
}
