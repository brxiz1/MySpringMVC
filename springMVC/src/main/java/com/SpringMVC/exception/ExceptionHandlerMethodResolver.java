package com.SpringMVC.exception;

import com.SpringMVC.annotation.ExceptionHandler;
import org.springframework.core.MethodIntrospector;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ConcurrentReferenceHashMap;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ZhangYihe
 * @since 2025/2/21
 **/
public class ExceptionHandlerMethodResolver {
    /**
     * 判断器：方法上是否标注了 ExceptionHandler注解
     */
    public static final ReflectionUtils.MethodFilter EXCEPTION_HANDLER_METHOD=
            method-> AnnotatedElementUtils.hasAnnotation(method, ExceptionHandler.class);

    /**
     * 异常类型和异常处理方法间的映射
     */
    private final Map<Class<? extends Throwable>, Method> mappedMethods=new ConcurrentReferenceHashMap<>();


    public ExceptionHandlerMethodResolver(Class<?> handlerType){
        for(Method method: MethodIntrospector.selectMethods(handlerType,EXCEPTION_HANDLER_METHOD)){
            for(Class<? extends Throwable> exceptionType:detectExceptionMappings(method)){
                mappedMethods.put(exceptionType,method);
            }
        }
    }

    /**
     * 查询method支持处理的Exception类型
     * @param method
     * @return
     */
    private List<Class<? extends  Throwable>> detectExceptionMappings(Method method){
        ExceptionHandler exceptionHandler=
                AnnotatedElementUtils.getMergedAnnotation(method, ExceptionHandler.class);
        Assert.state(exceptionHandler != null, "No ExceptionHandler annotation");
        return Arrays.asList(exceptionHandler.value());
    }

    public Map<Class<? extends Throwable>, Method> getMappedMethods(){
        return mappedMethods;
    }

    /**
     * 是否有异常处理方法,用于检验本对象是否有效
     * @return
     */
    public boolean hasExceptionMappings(){
        return !CollectionUtils.isEmpty(mappedMethods);
    }

    /**
     * 通过exception对象获取对应的处理方法
     * @param exception
     * @return
     */
    public Method resolveMethod(Exception exception){
        Class<? extends Throwable> exceptionType=exception.getClass();
        Method method=resolveMethodByExceptionType(exceptionType);
        if(method==null){
            Throwable cause=exception.getCause();
            if(cause!=null){

                method=resolveMethodByExceptionType(cause.getClass());
            }
        }
        return method;
    }

    private Method resolveMethodByExceptionType(Class<? extends Throwable> exceptionType){
        return mappedMethods.get(exceptionType);
    }
}
