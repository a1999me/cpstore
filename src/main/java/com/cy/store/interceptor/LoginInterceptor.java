package com.cy.store.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.handler.Handler;

/**
 * 定义一个拦截器
 *检测session全局是否有uid数据，如果有则放行，没有重定向到登录
 */
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        //根据session的值，来动态设定true或false
        Object obj = request.getSession().getAttribute("uid");
        if (obj==null){
            //说明用户没有登陆过系统，则重定向login.html页面
            response.sendRedirect("/web/login.html");
            //结束后期的调用
            return false;
        }

        return true;
    }
}
