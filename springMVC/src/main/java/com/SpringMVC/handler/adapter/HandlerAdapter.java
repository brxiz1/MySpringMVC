package com.SpringMVC.handler.adapter;

import com.SpringMVC.ModelAndView;
import com.SpringMVC.handler.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ZhangYihe
 * @since 2025/2/18
 **/

/**
 * 调用处理器处理请求并返回结果
 */
public interface HandlerAdapter {

    ModelAndView handle(HttpServletRequest request,
                        HttpServletResponse response,
                        HandlerMethod handlermethod) throws Exception;
}
