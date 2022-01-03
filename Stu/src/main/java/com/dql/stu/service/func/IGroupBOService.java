package com.dql.stu.service.func;

import com.dql.stu.message.Message;
import com.dql.stu.model.GroupBO;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 段启龙
 * @since 2021-12-19
 */
@Service
public interface IGroupBOService extends IService<GroupBO> {

    /**
     * 通过教师id查询分组
     *
     * @return Message
     */
    Message queryGroupByTeacherId(Integer teacherId, int pageNum);


    /**
     * 通过学生id查询分组
     *
     * @return Message
     */
    Message queryGroupByStudentId(Integer studentId, int pageNum);
}
