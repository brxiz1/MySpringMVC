package com.SpringMVC.handler.interceptor;

import com.SpringMVC.ModelAndView;
import com.SpringMVC.handler.mapping.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ZhangYihe
 * @since 2025/2/16
 **/

/**
 * 所有的拦截器都需要实现这个接口
 */
public interface HandlerInterceptor {
    /**
     * handler处理前执行本方法
     * @param request
     * @param response
     * @param handler
     * @return
     */
    boolean preHandle(HttpServletRequest request,
                      HttpServletResponse response,
                      Object handler) throws Exception;

    /**
     * handler处理后执行本方法
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     *
     */
    void postHandle(HttpServletRequest request,
                       HttpServletResponse response,
                       Object handler,
                       ModelAndView modelAndView) throws Exception;

    /**
     * 有很多执行场景，Interceptor处理结束后执行
     * @param request
     * @param response
     * @param handler
     * @param exception
     */
    void afterCompletion(HttpServletRequest request,
                         HttpServletResponse response,
                         Object handler,
                         Exception exception) throws Exception;


}
