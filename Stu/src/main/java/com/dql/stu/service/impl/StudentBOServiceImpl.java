package com.dql.stu.service.impl;

import com.dql.stu.model.StudentBO;
import com.dql.stu.mapper.StudentBOMapper;
import com.dql.stu.service.func.IStudentBOService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 段启龙
 * @since 2021-12-18
 */
@Service
public class StudentBOServiceImpl extends ServiceImpl<StudentBOMapper, StudentBO> implements IStudentBOService {

}
