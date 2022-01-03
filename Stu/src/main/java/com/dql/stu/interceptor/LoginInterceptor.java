package com.dql.stu.interceptor;

import com.dql.stu.message.Message;
import com.dql.stu.message.MessageEnum;
import com.dql.stu.service.func.IUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author duqnq
 * @date 2021/5/15   17:25
 * description:自定义拦截器
 */

@Slf4j
@Component
@AllArgsConstructor
public class LoginInterceptor implements HandlerInterceptor {

    final private IUserService iUserService;

    /**
     * 对未登录的用户进行拦截
     * (获取不到cookie)
     *
     * @param request  request
     * @param response response
     * @param handler  handler
     * @return 是否有cookie
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        //获取精确路径
        log.info(" LoginInterceptor 的拦截路径:{}", request.getRequestURI());

        Message message = iUserService.getUserByCookie(request, response);

        if (message.getCode() != MessageEnum.SUCCESS.getSTATUS()) {
            log.info(" LoginInterceptor 用户未登录:{}", message.getMessage());
            response.sendRedirect("/pre/toLogin");
            return false;
        }
        return true;
    }
}