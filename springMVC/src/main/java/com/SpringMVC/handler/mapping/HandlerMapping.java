package com.SpringMVC.handler.mapping;

/**
 * @author ZhangYihe
 * @since 2025/2/15
 **/

import com.SpringMVC.handler.HandlerExecutionChain;
import com.SpringMVC.handler.exception.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

/**
 * 注册handler和Interceptor，根据url获取对应的HandlerExecutionChain
 */
public interface HandlerMapping {
    HandlerExecutionChain getHandler(HttpServletRequest request) throws NoHandlerFoundException;
}
