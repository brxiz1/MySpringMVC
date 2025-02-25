package com.SpringMVC.config;

import com.SpringMVC.exception.ExceptionHandlerExceptionResolver;
import com.SpringMVC.exception.HandlerExceptionResolver;
import com.SpringMVC.handler.adapter.HandlerAdapter;
import com.SpringMVC.handler.adapter.RequestMappingHandlerAdapter;
import com.SpringMVC.handler.argument.HandlerMethodArgumentResolver;
import com.SpringMVC.handler.interceptor.InterceptorRegistry;
import com.SpringMVC.handler.interceptor.MappedInterceptor;
import com.SpringMVC.handler.mapping.HandlerMapping;
import com.SpringMVC.handler.mapping.RequestMappingHandlerMapping;
import com.SpringMVC.handler.returnvalue.HandlerMethodReturnValueHandler;
import com.SpringMVC.view.View;
import com.SpringMVC.view.resolver.ContentNegotiatingViewResolver;
import com.SpringMVC.view.resolver.InternalResourceViewResolver;
import com.SpringMVC.view.resolver.ViewResolver;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.ConversionService;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author ZhangYihe
 * @since 2025/2/24
 **/
public class WebMvcConfigurationSupport implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    private List<MappedInterceptor> interceptorList;

    private List<HandlerMethodArgumentResolver> argumentResolvers;

    private List<HandlerMethodReturnValueHandler> returnValueHandlers;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;
    }

    /**
     * 数据转换器
     * @return
     */
    @Bean
    public FormattingConversionService mvcConversionService(){
        FormattingConversionService conversionService=new DefaultFormattingConversionService();
        addFormatters(conversionService);
        return conversionService;
    }

    /**
     * 数据转换格式化暴露对外的扩展点
     * @param registry
     */
    protected void addFormatters(FormatterRegistry registry){

    }


    protected List<MappedInterceptor> getInterceptors(FormattingConversionService mvcConversionService){
        if(this.interceptorList==null){
            InterceptorRegistry interceptorRegistry=new InterceptorRegistry();
            addInterceptors(interceptorRegistry);
            interceptorList=interceptorRegistry.getMappedInterceptors();
        }
        return interceptorList;
    }

    /**
     * 拦截器暴露在外的拓展点
     * @param registry
     */
    protected void addInterceptors(InterceptorRegistry registry) {

    }

    @Bean
    public HandlerMapping handlerMapping(FormattingConversionService mvcConversionService){
        RequestMappingHandlerMapping handlerMapping = new RequestMappingHandlerMapping();
        handlerMapping.setInterceptors(getInterceptors(mvcConversionService));
        return handlerMapping;
    }


    @Bean
    public HandlerAdapter handlerAdapter(ConversionService conversionService) {
        RequestMappingHandlerAdapter handlerAdapter = new RequestMappingHandlerAdapter();
        handlerAdapter.setConversionService(conversionService);
        handlerAdapter.setArgumentResolverList(getArgumentResolvers());
        handlerAdapter.setReturnHandlerList(getReturnValueHandlers());
        return handlerAdapter;
    }

    protected List<HandlerMethodReturnValueHandler> getReturnValueHandlers() {
        if (this.returnValueHandlers == null) {
            this.returnValueHandlers = new ArrayList<>();
            addReturnValueHandlers(this.returnValueHandlers);
        }
        return this.returnValueHandlers;
    }

    /**
     * 返回值解析器拓展点
     * @param returnValueHandlers
     */
    protected void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {

    }

    protected List<HandlerMethodArgumentResolver> getArgumentResolvers() {
        if (this.argumentResolvers == null) {
            this.argumentResolvers = new ArrayList<>();
            addArgumentResolvers(this.argumentResolvers);
        }
        return this.argumentResolvers;
    }

    /**
     * 参数解析器拓展点
     * @param argumentResolvers
     */
    protected void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {

    }

    /**
     * 全局异常处理器
     * @param mvcConversionService
     * @return
     */
    @Bean
    public HandlerExceptionResolver handlerExceptionResolver(FormattingConversionService mvcConversionService) {
        ExceptionHandlerExceptionResolver exceptionResolver = new ExceptionHandlerExceptionResolver();
        exceptionResolver.setCustomArgumentResolvers(getArgumentResolvers());
        exceptionResolver.setCustomReturnValueHandlers(getReturnValueHandlers());
        exceptionResolver.setConversionService(mvcConversionService);
        return exceptionResolver;
    }

    /**
     * 视图协同器
     * @return
     */
    @Bean
    public ViewResolver viewResolver() {
        ContentNegotiatingViewResolver negotiatingViewResolver = new ContentNegotiatingViewResolver();

        List<ViewResolver> viewResolvers = new ArrayList<>();
        //注册视图解析器
        addViewResolvers(viewResolvers);
        if (CollectionUtils.isEmpty(viewResolvers)) {
            negotiatingViewResolver.setViewResolvers(Collections.singletonList(new InternalResourceViewResolver()));
        } else {
            negotiatingViewResolver.setViewResolvers(viewResolvers);
        }
        //注册视图
        List<View> views = new ArrayList<>();
        addDefaultViews(views);
        if (!CollectionUtils.isEmpty(views)) {
            negotiatingViewResolver.setDefaultViews(views);
        }

        return negotiatingViewResolver;
    }

    //视图的扩展点
    protected void addDefaultViews(List<View> views) {

    }

    //视图解析器的扩展点
    protected void addViewResolvers(List<ViewResolver> viewResolvers) {

    }

}
