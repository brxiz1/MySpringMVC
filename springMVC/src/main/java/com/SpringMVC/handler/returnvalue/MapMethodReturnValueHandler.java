package com.SpringMVC.handler.returnvalue;

import com.SpringMVC.handler.ModelAndViewContainer;
import org.springframework.core.MethodParameter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author ZhangYihe
 * @since 2025/2/17
 **/

/**
 * 解析Map类型的返回值
 */
public class MapMethodReturnValueHandler implements HandlerMethodReturnValueHandler{
    @Override
    public boolean supportReturnValue(MethodParameter parameter) {
        return Map.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, HttpServletRequest request, HttpServletResponse response) {
        if(returnValue instanceof Map){
            mavContainer.getModel().addAllAttributes((Map)returnValue);
        }else if(returnValue!=null){
            throw new UnsupportedOperationException("Unexpected return type: " +
                    returnType.getParameterType().getName() + " in method: " + returnType.getMethod());
        }

    }
}
