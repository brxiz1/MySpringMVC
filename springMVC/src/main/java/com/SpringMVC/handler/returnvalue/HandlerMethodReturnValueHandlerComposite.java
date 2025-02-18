package com.SpringMVC.handler.returnvalue;

import com.SpringMVC.handler.ModelAndViewContainer;
import org.springframework.core.MethodParameter;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author ZhangYihe
 * @since 2025/2/17
 **/

/**
 * 返回值解析器注册中心
 */
public class HandlerMethodReturnValueHandlerComposite implements HandlerMethodReturnValueHandler{
    private List<HandlerMethodReturnValueHandler> returnValueHandlerList=new ArrayList<>();
    @Override
    public boolean supportReturnValue(MethodParameter parameter) {
        return true;
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, HttpServletRequest request, HttpServletResponse response) {
        for(HandlerMethodReturnValueHandler returnValueHandler:returnValueHandlerList){
            if(returnValueHandler.supportReturnValue(returnType)){
                returnValueHandler.handleReturnValue(returnValue,returnType,
                        mavContainer,request,response);
                return;
            }
        }
        throw new IllegalArgumentException("Unsupported parameter type [" +
                returnType.getParameterType().getName() + "]. supportsParameter should be called first.");
    }

    /**
     * 向注册中心添加返回值解析器
     * @param handler
     */
    public void addReturnValueHandler(HandlerMethodReturnValueHandler...handler){
        Collections.addAll(returnValueHandlerList,handler);
    }

    public void addReturnValueHandler(Collection<HandlerMethodReturnValueHandler> handlers){
        returnValueHandlerList.addAll(handlers);
    }
    public void clear(){
        returnValueHandlerList.clear();
    }

}
