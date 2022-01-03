package com.dql.stu.config;

import com.dql.stu.interceptor.LoginInterceptor;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author duqnq
 * @date 2021/5/15   17:25
 * description:注册拦截器,配置路径
 */

@Configuration
@AllArgsConstructor
public class InterceptorConfig implements WebMvcConfigurer {

    final private LoginInterceptor loginInterceptor;

    /**
     * 注册拦截器
     *
     * @param registry 拦截器仓库
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/css/**",
                        "/webjars/**",
                        "/img/**",
                        "/js/**",
                        "/layui/**",
                        "/",
                        "/pre/**");
    }
}
