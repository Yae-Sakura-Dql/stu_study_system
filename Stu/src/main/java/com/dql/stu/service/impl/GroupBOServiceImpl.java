package com.dql.stu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dql.stu.mapper.GroupBOMapper;
import com.dql.stu.message.Message;
import com.dql.stu.message.MessageEnum;
import com.dql.stu.model.GroupBO;
import com.dql.stu.model.PropertySettingBO;
import com.dql.stu.service.func.IGroupBOService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 段启龙
 * @since 2021-12-19
 */
@Service
@AllArgsConstructor
@Slf4j
public class GroupBOServiceImpl extends ServiceImpl<GroupBOMapper, GroupBO> implements IGroupBOService {

    final private GroupBOMapper groupBOMapper;

    /**
     * 通过教师id查询分组
     *
     * @param teacherId 教师id
     * @param pageNum   页数
     * @return Message
     */
    @Override
    public Message queryGroupByTeacherId(Integer teacherId, int pageNum) {
        Page<GroupBO> groupBOPage;
        try {
            Page<GroupBO> page = new Page<>(pageNum, PropertySettingBO.pageSize);
            LambdaQueryWrapper<GroupBO> queryWrapper = new LambdaQueryWrapper<GroupBO>()
                    .eq(GroupBO::getTeacherId, teacherId)
                    .groupBy(GroupBO::getGroupName);
            groupBOPage = groupBOMapper.selectPage(page, queryWrapper);
            log.info(" GroupBOServiceImpl -> queryGroupByTeacherId 查询出的page:{}", groupBOPage.getRecords());
            return Message.success(groupBOPage);
        } catch (Exception e) {
            log.info(" GroupBOServiceImpl -> queryGroupByTeacherId 查询page出现错误:{}", e.getMessage());
            return Message.error(MessageEnum.QUERY_TEACHER_ERROR);
        }
    }

    /**
     * 通过学生id查询分组
     *
     * @param studentId 学生id
     * @param pageNum   页数
     * @return Message
     */
    @Override
    public Message queryGroupByStudentId(Integer studentId, int pageNum) {
        Page<GroupBO> groupBOPage;
        try {
            Page<GroupBO> page = new Page<>(pageNum, PropertySettingBO.pageSize);
            LambdaQueryWrapper<GroupBO> queryWrapper = new LambdaQueryWrapper<GroupBO>()
                    .eq(GroupBO::getStudentId, studentId)
                    .groupBy(GroupBO::getGroupName);
            groupBOPage = groupBOMapper.selectPage(page, queryWrapper);
            log.info(" GroupBOServiceImpl -> queryGroupByStudentId 查询出的page:{}", groupBOPage.getRecords());
            return Message.success(groupBOPage);
        } catch (Exception e) {
            log.info(" GroupBOServiceImpl -> queryGroupByStudentId 查询page出现错误:{}", e.getMessage());
            return Message.error(MessageEnum.QUERY_TEACHER_ERROR);
        }
    }
}
