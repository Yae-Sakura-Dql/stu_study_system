package com.dql.stu.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dql.stu.model.UserBO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author dql
 * @since 2021-06-20
 */
@Mapper
public interface UserMapper extends BaseMapper<UserBO> {

}
