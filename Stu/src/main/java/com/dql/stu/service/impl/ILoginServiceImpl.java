package com.dql.stu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dql.stu.exception.BusinessException;
import com.dql.stu.mapper.UserMapper;
import com.dql.stu.message.Message;
import com.dql.stu.message.MessageEnum;
import com.dql.stu.model.UserBO;
import com.dql.stu.service.func.ILoginService;
import com.dql.stu.tmodel.UserLoginTO;
import com.dql.stu.utils.CookieUtil;
import com.dql.stu.utils.RedisUtil;
import com.dql.stu.utils.UUIDUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 段启龙
 * @date 2021/12/7   23:58
 * description:
 */
@Service
@Slf4j
@AllArgsConstructor
public class ILoginServiceImpl extends ServiceImpl<UserMapper, UserBO> implements ILoginService {


}
