package com.dql.stu.service.func;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dql.stu.message.Message;
import com.dql.stu.model.UserBO;
import com.dql.stu.tmodel.UserLoginTO;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 段启龙
 * @date 2021/12/7   23:58
 * description:登录服务
 */
@Service
@Primary
public interface ILoginService extends IService<UserBO> {

}
