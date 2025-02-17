package com.SpringMVC.handler.argument;

import com.SpringMVC.annotation.RequestParam;
import com.SpringMVC.handler.ModelAndViewContainer;
import com.SpringMVC.handler.exception.MissingServletRequestParameterException;
import org.springframework.core.MethodParameter;
import org.springframework.core.convert.ConversionService;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ZhangYihe
 * @since 2025/2/17
 **/

/**
 * 从request中解析@RequestParam注解标注的参数
 */
public class RequestParamMethodArgumentResolver implements HandlerMethodArgumentResolver{
    /**
     * 检查该参数是否带有RequestParam注解
     * @param parameter
     * @return
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {

        return parameter.hasParameterAnnotation(RequestParam.class);
    }

    /**
     * 解析带有@RequestParam注解的参数
     * @param parameter
     * @param request
     * @param response
     * @param container
     * @param service
     * @return
     * @throws MissingServletRequestParameterException
     */
    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  HttpServletRequest request,
                                  HttpServletResponse response,
                                  ModelAndViewContainer container,
                                  ConversionService service) throws MissingServletRequestParameterException {
        RequestParam requestParam=parameter.getParameterAnnotation(RequestParam.class);
        if(requestParam==null)return null;
        String name= requestParam.name();
        String value=request.getParameter(name);
        if(StringUtils.isEmpty(value)){
            if(requestParam.required()){
                throw new MissingServletRequestParameterException(parameter.getParameterName(),parameter.getParameterType().getName());
            }
            value=requestParam.defaultValue();
        }
        if(!StringUtils.isEmpty(value)){
            return service.convert(value,parameter.getParameterType());
        }
        return null;
    }
}
