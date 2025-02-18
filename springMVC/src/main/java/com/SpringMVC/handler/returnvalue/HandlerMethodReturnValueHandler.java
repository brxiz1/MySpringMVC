package com.SpringMVC.handler.returnvalue;

/**
 * @author ZhangYihe
 * @since 2025/2/17
 **/

import com.SpringMVC.handler.ModelAndViewContainer;
import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 返回值解析器
 */
public interface HandlerMethodReturnValueHandler {
    /**
     * 是否支持该返回值类型
     * @param parameter
     * @return
     */
    boolean supportReturnValue(MethodParameter parameter);

    /**
     * 处理返回值
     * @param returnValue
     * @param returnType
     * @param mavContainer
     * @param request
     * @param response
     */
    void handleReturnValue(@Nullable Object returnValue, MethodParameter returnType,
                           ModelAndViewContainer mavContainer,
                           HttpServletRequest request, HttpServletResponse response);
}
