package com.SpringMVC.exception;

import com.SpringMVC.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ZhangYihe
 * @since 2025/2/23
 **/
public interface HandlerExceptionResolver {

    /**
     * 处理异常返回ModelAndView
     * @param request
     * @param response
     * @param e
     * @return
     */
    ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response,Exception e);
}
