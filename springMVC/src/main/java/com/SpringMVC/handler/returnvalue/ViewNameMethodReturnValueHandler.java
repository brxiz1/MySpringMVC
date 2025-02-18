package com.SpringMVC.handler.returnvalue;

import com.SpringMVC.handler.ModelAndViewContainer;
import org.springframework.core.MethodParameter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ZhangYihe
 * @since 2025/2/17
 **/
public class ViewNameMethodReturnValueHandler implements HandlerMethodReturnValueHandler{

    @Override
    public boolean supportReturnValue(MethodParameter parameter) {

        return CharSequence.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, HttpServletRequest request, HttpServletResponse response) {
        if(returnValue instanceof CharSequence){
            mavContainer.setViewName(returnValue.toString());
        }else if(returnValue!=null){
            throw new UnsupportedOperationException("Unexpected return type: " +
                    returnType.getParameterType().getName() + " in method: " + returnType.getMethod());
        }
    }
}
