package com.SpringMVC.exception;

import com.SpringMVC.ModelAndView;
import com.SpringMVC.annotation.ControllerAdvice;
import com.SpringMVC.handler.HandlerMethod;
import com.SpringMVC.handler.InvocableHandlerMethod;
import com.SpringMVC.handler.ModelAndViewContainer;
import com.SpringMVC.handler.argument.*;
import com.SpringMVC.handler.returnvalue.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.convert.ConversionService;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author ZhangYihe
 * @since 2025/2/23
 **/
public class ExceptionHandlerExceptionResolver implements HandlerExceptionResolver, InitializingBean, ApplicationContextAware {

    private Logger logger= LoggerFactory.getLogger(getClass());
    private ApplicationContext applicationContext;
    private Map<ControllerAdviceBean,ExceptionHandlerMethodResolver> exceptionHandlerAdviceCache=new LinkedHashMap<>();
    private ConversionService conversionService;

    private List<HandlerMethodArgumentResolver> customArgumentResolvers;
    private HandlerMethodArgumentResolverComposite argumentResolver;
    private List<HandlerMethodReturnValueHandler> customReturnValueHandlers;
    private HandlerMethodReturnValueHandlerComposite returnValueHandler;

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Exception e) {
        InvocableHandlerMethod handler=getExceptionHandleMethod(e);
        if(handler==null){
            return null;
        }
        ModelAndViewContainer mavContainer=new ModelAndViewContainer();
        try{
            Throwable cause=e.getCause();
            if(cause!=null){
                handler.invokeAndHandle(request,response,mavContainer,cause);
            }else{
                handler.invokeAndHandle(request,response,mavContainer,e);
            }
        }catch(Exception exception){
            logger.error("exceptionHandlerMethod.invokeAndHandle fail", exception);
            return null;
        }
        if(mavContainer.isRequestHandled()){
            return null;
        }
        ModelAndView mav=new ModelAndView();
        mav.setModel(mavContainer.getModel());
        mav.setView(mavContainer.getView());
        mav.setViewName(mavContainer.getViewName());
        return mav;
    }

    /**
     * 为成员变量赋予初始值
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(this.conversionService, "conversionService can not null");
        initExceptionHandlerAdviceCache();
        if(argumentResolver==null){
            List<HandlerMethodArgumentResolver> resolvers=getDefaultArgumentResolvers();
            argumentResolver=new HandlerMethodArgumentResolverComposite();
            argumentResolver.addResolver(resolvers);
        }
        if(returnValueHandler==null){
            List<HandlerMethodReturnValueHandler> handlers=getDefaultReturnValueHandlers();
            returnValueHandler=new HandlerMethodReturnValueHandlerComposite();
            returnValueHandler.addReturnValueHandler(handlers);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;
    }

    /**
     * 根据异常类型，查找对应的处理方法，并构造InvocableHandlerMethod
     * @param ex
     * @return
     */
    private InvocableHandlerMethod getExceptionHandleMethod(Exception ex){
        for(ControllerAdviceBean exceptionHandler:exceptionHandlerAdviceCache.keySet()){
            ExceptionHandlerMethodResolver exceptionResolver=
                    exceptionHandlerAdviceCache.get(exceptionHandler);
            if(exceptionResolver.hasExceptionMappings()){
                Method method=exceptionResolver.resolveMethod(ex);
                if(method!=null){
                    return new InvocableHandlerMethod(exceptionHandler.getBean(),method,
                            argumentResolver,returnValueHandler,conversionService);
                }
            }
        }
        return null;
    }

    /**
     * 初始化默认返回值处理器
     * @return
     */
    private List<HandlerMethodReturnValueHandler> getDefaultReturnValueHandlers() {
        List<HandlerMethodReturnValueHandler> handlers = new ArrayList<>();

        handlers.add(new MapMethodReturnValueHandler());
        handlers.add(new ModelMethodReturnValueHandler());
        handlers.add(new ResponseBodyMethodReturnValueHandler());
        handlers.add(new ViewNameMethodReturnValueHandler());
        handlers.add(new ViewMethodReturnValueHandler());

        if (!CollectionUtils.isEmpty(customReturnValueHandlers)) {
            handlers.addAll(customReturnValueHandlers);
        }

        return handlers;
    }

    /**
     * 初始化默认参数解析器
     * @return
     */
    private List<HandlerMethodArgumentResolver> getDefaultArgumentResolvers() {
        List<HandlerMethodArgumentResolver> resolvers = new ArrayList<>();

        resolvers.add(new ModelMethodArgumentResolver());
        resolvers.add(new RequestParamMethodArgumentResolver());
        resolvers.add(new RequestBodyMethodArgumentResolver());
        resolvers.add(new ServletResponseMethodArgumentResolver());
        resolvers.add(new ServletRequestMethodArgumentResolver());

        if (!CollectionUtils.isEmpty(customArgumentResolvers)) {
            resolvers.addAll(customArgumentResolvers);
        }

        return resolvers;
    }

    /**
     * 从Spring上下文获取bean，
     * 构造ControllerAdviceBean和ExceptionHandlerMethodResolver间的映射关系
     */
    private void initExceptionHandlerAdviceCache(){
        List<ControllerAdviceBean> exceptionHandlers=ControllerAdviceBean.findAnnotatedBeans(applicationContext);
        for(ControllerAdviceBean exceptionHandler:exceptionHandlers){
            Class<?>handlerType=exceptionHandler.getBeanType();
            if (handlerType == null) {
                throw new IllegalStateException("Unresolvable type for ControllerAdviceBean: " + handlerType);
            }
            ExceptionHandlerMethodResolver methodResolver=new ExceptionHandlerMethodResolver(handlerType);

            if(methodResolver.hasExceptionMappings()){
                exceptionHandlerAdviceCache.put(exceptionHandler,methodResolver);
            }
        }
    }

    public Logger getLogger() {
        return logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public Map<ControllerAdviceBean, ExceptionHandlerMethodResolver> getExceptionHandlerAdviceCache() {
        return exceptionHandlerAdviceCache;
    }

    public void setExceptionHandlerAdviceCache(Map<ControllerAdviceBean, ExceptionHandlerMethodResolver> exceptionHandlerAdviceCache) {
        this.exceptionHandlerAdviceCache = exceptionHandlerAdviceCache;
    }

    public ConversionService getConversionService() {
        return conversionService;
    }

    public void setConversionService(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    public List<HandlerMethodArgumentResolver> getCustomArgumentResolvers() {
        return customArgumentResolvers;
    }

    public void setCustomArgumentResolvers(List<HandlerMethodArgumentResolver> customArgumentResolvers) {
        this.customArgumentResolvers = customArgumentResolvers;
    }

    public HandlerMethodArgumentResolverComposite getArgumentResolver() {
        return argumentResolver;
    }

    public void setArgumentResolver(HandlerMethodArgumentResolverComposite argumentResolver) {
        this.argumentResolver = argumentResolver;
    }

    public List<HandlerMethodReturnValueHandler> getCustomReturnValueHandlers() {
        return customReturnValueHandlers;
    }

    public void setCustomReturnValueHandlers(List<HandlerMethodReturnValueHandler> customReturnValueHandlers) {
        this.customReturnValueHandlers = customReturnValueHandlers;
    }

    public HandlerMethodReturnValueHandlerComposite getReturnValueHandler() {
        return returnValueHandler;
    }

    public void setReturnValueHandler(HandlerMethodReturnValueHandlerComposite returnValueHandler) {
        this.returnValueHandler = returnValueHandler;
    }
}
