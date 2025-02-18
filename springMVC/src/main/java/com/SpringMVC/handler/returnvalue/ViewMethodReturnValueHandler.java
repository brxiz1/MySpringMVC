package com.SpringMVC.handler.returnvalue;

import com.SpringMVC.handler.ModelAndViewContainer;
import com.SpringMVC.view.View;
import org.springframework.core.MethodParameter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ZhangYihe
 * @since 2025/2/17
 **/

/**
 * 解析View对象返回值
 */
public class ViewMethodReturnValueHandler implements HandlerMethodReturnValueHandler{
    @Override
    public boolean supportReturnValue(MethodParameter parameter) {
        return View.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, HttpServletRequest request, HttpServletResponse response) {
        if(returnValue instanceof View){
            mavContainer.setView(returnValue);
        }else if(returnValue!=null){
            throw new UnsupportedOperationException("Unexpected return type: " +
                    returnType.getParameterType().getName() + " in method: " + returnType.getMethod());
        }
    }
}
