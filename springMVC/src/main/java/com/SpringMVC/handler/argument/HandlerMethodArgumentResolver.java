package com.SpringMVC.handler.argument;

import com.SpringMVC.handler.ModelAndViewContainer;
import org.springframework.core.MethodParameter;
import org.springframework.core.convert.ConversionService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ZhangYihe
 * @since 2025/2/17
 **/

/**
 * 参数解析器
 */
public interface HandlerMethodArgumentResolver {
    /**
     * 该参数解析器是否支持parameter类型
     * @param parameter
     * @return
     */
    boolean supportsParameter(MethodParameter parameter);

    /**
     * 从request中解析出parameter类对象
     * @param parameter
     * @param request
     * @param response
     * @param container
     * @param service
     * @return
     */
    Object resolveArgument(MethodParameter parameter,
                           HttpServletRequest request,
                           HttpServletResponse response,
                           ModelAndViewContainer container,
                           ConversionService service);
}
