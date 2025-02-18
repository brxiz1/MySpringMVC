package com.SpringMVC.handler;

import org.springframework.core.MethodParameter;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ZhangYihe
 * @since 2025/2/15
 **/

/**
 * 对应Controller中的方法
 */
public class HandlerMethod {

    /**
     * Controller bean
     */
    private Object bean;


    /**
     * Controller类型
     */
    private Class<?> beanType;

    /**
     * Controller中的方法
     */
    private Method method;



    /**
     * 方法的参数列表
     */
    private List<MethodParameter> parameters;

    /**
     * handler:Controller bean
     * method: Controller的方法
     */
    public HandlerMethod(Object handler,Method method){
        bean=handler;
        beanType=handler.getClass();
        this.method=method;

        parameters=new ArrayList<>();
        int parameterCount=method.getParameterCount();
        for(int i=0;i<parameterCount;i++){
            parameters.add(new MethodParameter(method,i));
        }
    }

    public HandlerMethod(HandlerMethod handlerMethod){
        this.bean=handlerMethod.getBean();
        beanType=handlerMethod.getBeanType();
        method= handlerMethod.getMethod();
        parameters=handlerMethod.getParameters();
    }

    public Object getBean() {
        return bean;
    }

    public Class<?> getBeanType() {
        return beanType;
    }

    public Method getMethod() {
        return method;
    }

    public List<MethodParameter> getParameters() {
        return parameters;
    }
}
