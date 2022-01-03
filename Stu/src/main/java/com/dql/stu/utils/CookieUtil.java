package com.dql.stu.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Dql
 * @date 2021/6/21   9:50
 * description:存放Cookie工具类
 */
@Slf4j
public class CookieUtil {
    private final static String COOKIE_DOMAIN = "localhost";
    private final static String COOKIE_NAME = "userKey";

    /**
     * 获取cookie
     * @param request request
     * @return cookie
     */
    public static String getToken(HttpServletRequest request){
        Cookie[] cks = request.getCookies();
        if(cks != null){
            for(Cookie ck : cks){
                log.info("read cookieName:{},cookieValue:{}",ck.getName(),ck.getValue());
                if(StringUtils.equals(ck.getName(),COOKIE_NAME)){
                    log.info("return cookieName:{},cookieValue:{}",ck.getName(),ck.getValue());
                    return ck.getValue();
                }
            }
        }
        return null;
    }

    /**
     * 存放cookie
     * @param response response
     * @param token cookie
     */
    public static void setToken(HttpServletResponse response, String token){
        Cookie ck = new Cookie(COOKIE_NAME,token);
        ck.setDomain(COOKIE_DOMAIN);
        //设置在根目录
        ck.setPath("/");
        ck.setHttpOnly(true);
        //单位是秒。
        //如果这个maxage不设置的话，cookie就不会写入硬盘，而是写在内存。只在当前页面有效。
        //如果是-1，代表永久
        ck.setMaxAge(60 * 60 * 24 * 365);
        log.info("write cookieName:{},cookieValue:{}",ck.getName(),ck.getValue());
        response.addCookie(ck);
    }

    /**
     * 删除cookie
     * @param request request
     * @param response response
     */
    public static void deleteToken(HttpServletRequest request, HttpServletResponse response){
        Cookie[] cks = request.getCookies();
        if(cks != null){
            for(Cookie ck : cks){
                if(StringUtils.equals(ck.getName(),COOKIE_NAME)){
                    ck.setDomain(COOKIE_DOMAIN);
                    ck.setPath("/");
                    //设置成0，代表删除此cookie。
                    ck.setMaxAge(0);
                    response.addCookie(ck);
                    return;
                }
            }
        }
    }
}
