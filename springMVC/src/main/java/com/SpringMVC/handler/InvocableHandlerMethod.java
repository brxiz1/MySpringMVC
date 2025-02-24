package com.SpringMVC.handler;

import com.SpringMVC.handler.argument.HandlerMethodArgumentResolverComposite;
import com.SpringMVC.handler.exception.MissingServletRequestParameterException;
import com.SpringMVC.handler.returnvalue.HandlerMethodReturnValueHandlerComposite;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.MethodParameter;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.core.convert.ConversionService;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ZhangYihe
 * @since 2025/2/18
 **/

/**
 * 解析所需的各种参数，调用控制器方法，处理用户请求。
 */
public class InvocableHandlerMethod extends HandlerMethod{

    private ParameterNameDiscoverer parameterNameDiscoverer=new DefaultParameterNameDiscoverer();

    private HandlerMethodArgumentResolverComposite argumentResolver;

    private HandlerMethodReturnValueHandlerComposite returnValueHandler;

    private ConversionService service;


    public InvocableHandlerMethod(HandlerMethod handlerMethod,
                                  HandlerMethodArgumentResolverComposite argumentResolver,
                                  HandlerMethodReturnValueHandlerComposite returnValueHandler,
                                  ConversionService service){
        super(handlerMethod);
        parameterNameDiscoverer=new DefaultParameterNameDiscoverer();
        this.argumentResolver=argumentResolver;
        this.returnValueHandler=returnValueHandler;
        this.service=service;
    }

    public InvocableHandlerMethod(Object handler, Method method,
                                  HandlerMethodArgumentResolverComposite argumentResolver,
                                  HandlerMethodReturnValueHandlerComposite returnValueHandler,
                                  ConversionService service){
        super(handler,method);
        parameterNameDiscoverer=new DefaultParameterNameDiscoverer();
        this.argumentResolver=argumentResolver;
        this.returnValueHandler=returnValueHandler;
        this.service=service;
    }

    /**
     * 检查提供的参数中是否有满足参数类型要求的，有则返回该参数
     * @param parameter
     * @param providedArgs
     * @return
     */
    protected Object findProvidedArguments(MethodParameter parameter,Object...providedArgs){
        if(!ObjectUtils.isEmpty(providedArgs)){
            Class<?> paramType=parameter.getParameterType();
            for(Object providedArg:providedArgs){
                if(paramType.isInstance(providedArg)){
                    return providedArg;
                }
            }
        }
        return null;
    }
    /**
     * 获取处理请求的handler方法的参数列表，并用参数解析器处理
     * @param request
     * @param response
     * @param mavContainer
     * @param providedArgs
     * @return
     */
    private List<Object> getMethodArgumentValues(HttpServletRequest request,
                                                 HttpServletResponse response,
                                                 ModelAndViewContainer mavContainer,
                                                 Object...providedArgs) throws MissingServletRequestParameterException, IOException {
        Assert.notNull(argumentResolver,"HandlerMethodArgumentResolver can not be null");
        List<MethodParameter> parameters=this.getParameters();
        //参数解析器处理参数将MethodParameter转为实际的参数
        List<Object> args=new ArrayList<>();
        for(MethodParameter parameter:parameters){
            //解析参数名称
            parameter.initParameterNameDiscovery(parameterNameDiscoverer);
            //如果提供的参数中有满足参数类型要求的，则直接选择该参数，不进行解析
            Object providedArg=findProvidedArguments(parameter,providedArgs);
            if(providedArg!=null){
                args.add(providedArg);
                continue;
            }

            args.add(argumentResolver.resolveArgument(parameter,request,response,mavContainer,service));
        }
        return args;
    }

    /**
     * 反射的调用handler方法处理请求并返回结果
     * @param args 调用方法时用到的参数列表
     * @return
     */
    private Object doInvoke(List<Object> args) throws InvocationTargetException,IllegalAccessException {
        return this.getMethod().invoke(this.getBean(),args.toArray());
    }

    /**
     * 调用handler处理请求,包括对方法参数和返回值的解析过程
     * @param request
     * @param response
     * @param mavContainer
     * @param providedArgs
     */
    public void invokeAndHandle(HttpServletRequest request,
                                HttpServletResponse response,
                                ModelAndViewContainer mavContainer,
                                Object...providedArgs) throws Exception{
        List<Object> args=getMethodArgumentValues(request,response,mavContainer,providedArgs);
        Object res=doInvoke(args);

        //返回值为空时
        if(res==null){
            //如果响应是否已经提交
            if(response.isCommitted()){
                mavContainer.setRequestHandled(true);
                return;
            }else{
                throw new IllegalStateException("Controller handler return value is null");
            }
        }

        mavContainer.setRequestHandled(false);

        Assert.state(this.returnValueHandler != null, "No return value handler");

        //获取返回值类型
        MethodParameter returnType=new MethodParameter(this.getMethod(),-1);

        returnValueHandler.handleReturnValue(res,returnType,mavContainer,request,response);
    }
}
