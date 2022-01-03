package com.dql.stu.tmodel;

import com.dql.stu.model.StudentBO;
import lombok.Data;

import java.util.List;

/**
 * @author 段启龙
 * @date 2021/12/29 19:09
 * description: 分组-学生信息的查询模型
 */
@Data
public class GroupStudentTO {

    //原组名
    private String oldGroupName;
    //组名
    private String groupName;

    private Integer teacherId;

    //被选择的学生信息
    private List<StudentBO> selectedStudentBOList;

    //还未被选择的学生信息
    private List<StudentBO> noSelectedStudentBOList;

    //被选择的学生id
    private List<Integer> studentIdList;
}
