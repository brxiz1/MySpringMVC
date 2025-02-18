package com.SpringMVC.handler.returnvalue;

import com.SpringMVC.handler.ModelAndViewContainer;
import org.springframework.core.MethodParameter;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ZhangYihe
 * @since 2025/2/17
 **/

/**
 * 解析Model类返回值
 */
public class ModelMethodReturnValueHandler implements HandlerMethodReturnValueHandler{
    @Override
    public boolean supportReturnValue(MethodParameter parameter) {
        return Model.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public void handleReturnValue(Object returnValue,
                                  MethodParameter returnType,
                                  ModelAndViewContainer mavContainer,
                                  HttpServletRequest request,
                                  HttpServletResponse response) {
        if(returnValue instanceof Model){
            mavContainer.getModel().addAttribute(returnValue);

        }else if(returnValue!=null){
            throw new UnsupportedOperationException("Unexpected return type: " +
                    returnType.getParameterType().getName() + " in method: " + returnType.getMethod());
        }
    }
}
