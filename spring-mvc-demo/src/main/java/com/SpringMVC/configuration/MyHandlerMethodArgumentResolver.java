package com.SpringMVC.configuration;

import com.SpringMVC.VO.UserVo;
import com.SpringMVC.handler.ModelAndViewContainer;
import com.SpringMVC.handler.argument.HandlerMethodArgumentResolver;
import org.springframework.core.MethodParameter;
import org.springframework.core.convert.ConversionService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ZhangYihe
 * @since 2025/2/27
 **/
public class MyHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(MyUserParam.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, HttpServletRequest request,
                                  HttpServletResponse response, ModelAndViewContainer container,
                                  ConversionService conversionService)  {
        MyUserParam annotation = parameter.getParameterAnnotation(MyUserParam.class);
        String param = request.getParameter(annotation.name());
        String[] split = param.split(",");

        UserVo userVo = new UserVo();
        userVo.setName(split[0]);
        userVo.setAge(Integer.valueOf(split[1]));
        return userVo;
    }
}
