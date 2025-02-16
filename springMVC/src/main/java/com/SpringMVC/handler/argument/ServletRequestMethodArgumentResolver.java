package com.SpringMVC.handler.argument;

import com.SpringMVC.handler.ModelAndViewContainer;
import org.springframework.core.MethodParameter;
import org.springframework.core.convert.ConversionService;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ZhangYihe
 * @since 2025/2/17
 **/

/**
 * 针对ServletRequest的参数解析器
 */
public class ServletRequestMethodArgumentResolver implements HandlerMethodArgumentResolver{
    /**
     * 判断parameter是否是ServletRequest类型或子类型
     * @param parameter
     * @return
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        Class<?> type=parameter.getParameterType();
        //ServletRequest是否是type的父类
        return ServletRequest.class.isAssignableFrom(type);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, HttpServletRequest request, HttpServletResponse response, ModelAndViewContainer container, ConversionService service) {
        return request;
    }
}
