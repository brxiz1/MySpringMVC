package com.SpringMVC.handler.argument;

import com.SpringMVC.handler.ModelAndViewContainer;
import org.springframework.core.MethodParameter;
import org.springframework.core.convert.ConversionService;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ZhangYihe
 * @since 2025/2/17
 **/

/**
 * 针对ServletResponse的参数解析器
 */
public class ServletResponseMethodArgumentResolver implements HandlerMethodArgumentResolver{
    /**
     * 判断parameter是否是ServletResponse类型或子类型
     * @param parameter
     * @return
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        Class<?> type=parameter.getParameterType();

        return ServletResponse.class.isAssignableFrom(type);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, HttpServletRequest request, HttpServletResponse response, ModelAndViewContainer container, ConversionService service) {
        return response;
    }
}
