package com.SpringMVC.handler.mapping;

import com.SpringMVC.annotation.RequestMapping;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.core.MethodIntrospector;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.stereotype.Controller;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author ZhangYihe
 * @since 2025/2/15
 **/
public class RequsetMappingHandlerMapping extends ApplicationObjectSupport implements HandlerMapping, InitializingBean {
    private MappingRegistry mappingRegistry=new MappingRegistry();

    /**
     * 获取Controller的@Requestmapping注解中的url前缀
     * @param handlerClass
     * @return
     */
    private String getPathPrefix(Class<?> handlerClass){
        RequestMapping requestMapping=
                AnnotatedElementUtils.findMergedAnnotation(handlerClass, RequestMapping.class);
        if(requestMapping==null)return "";
        return requestMapping.path();
    }

    /**
     * 从Controller和Controller方法中生成RequestMappingInfo
     * @param method Controller中的方法
     * @param type Controller.class
     * @return
     */
    private RequestMappingInfo getMappingForMethod(Method method, Class<?> type){
        RequestMapping requestMapping=
                AnnotatedElementUtils.findMergedAnnotation(method, RequestMapping.class);
        if(requestMapping==null)return null;
        String prefix=getPathPrefix(type);
        return new RequestMappingInfo(prefix,requestMapping);
    }

    /**
     * 获取指定handler中的所有HandlerMethod和RequestMappingInfo
     * @param beanName
     * @param handler
     */
    private void detectHandlerMethods(String beanName,Object handler){
        Class<?> beanType=handler.getClass();
        //获取handler中的所有方法，并对方法调用getMappingForMethod(),获取RequestMappingInfo
        Map<Method,RequestMappingInfo> methodsOfMap= MethodIntrospector.selectMethods(beanType,
                (MethodIntrospector.MetadataLookup<RequestMappingInfo>) method->getMappingForMethod(method,beanType));

        for(Method method: methodsOfMap.keySet()){
            mappingRegistry.register(methodsOfMap.get(method),handler,method);
        }
    }

    /**
     * 判断一个bean是否是Controller
     * @param bean
     * @return
     */
    private boolean isHandler(Object bean){
        Class<?> beanType=bean.getClass();
        return AnnotatedElementUtils.hasAnnotation(beanType, Controller.class);
    }

    /**
     * 遍历所有bean，生成HandlerMethods
     */
    private void initialHandlerMethods(){
        Map<String, Object> beansMap=
                BeanFactoryUtils.beansOfTypeIncludingAncestors(obtainApplicationContext(),Object.class);
        for(String beanName: beansMap.keySet()){
            Object bean=beansMap.get(beanName);
            if(isHandler(bean)){
                detectHandlerMethods(beanName,bean);
            }
        }
    }

    /**
     * 本方法在类初始化后执行
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        initialHandlerMethods();
    }

    public MappingRegistry getMappingRegistry() {
        return mappingRegistry;
    }
}
