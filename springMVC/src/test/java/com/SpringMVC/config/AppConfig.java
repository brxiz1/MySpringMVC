package com.SpringMVC.config;

import com.SpringMVC.DispatcherServlet;
import com.SpringMVC.exception.ExceptionHandlerExceptionResolver;
import com.SpringMVC.exception.HandlerExceptionResolver;
import com.SpringMVC.handler.adapter.HandlerAdapter;
import com.SpringMVC.handler.adapter.RequestMappingHandlerAdapter;
import com.SpringMVC.handler.interceptor.InterceptorRegistry;
import com.SpringMVC.handler.mapping.HandlerMapping;
import com.SpringMVC.handler.mapping.RequestMappingHandlerMapping;
import com.SpringMVC.intercepter.Test2HandlerInterceptor;
import com.SpringMVC.intercepter.TestHandlerInterceptor;
import com.SpringMVC.view.resolver.ContentNegotiatingViewResolver;
import com.SpringMVC.view.resolver.InternalResourceViewResolver;
import com.SpringMVC.view.resolver.ViewResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.format.support.DefaultFormattingConversionService;

import java.util.Collections;

/**
 * @author ZhangYihe
 * @since 2025/2/15
 **/
@Configuration
@ComponentScan(basePackages = "com.SpringMVC")
public class AppConfig {
//    @Bean
//    public RequestMappingHandlerMapping handlerMapping() {
//        return new RequestMappingHandlerMapping();
//    }

//    @Bean
//    public RequestMappingHandlerMapping handlerMapping() {
//        InterceptorRegistry interceptorRegistry = new InterceptorRegistry();
//
//        TestHandlerInterceptor interceptor = new TestHandlerInterceptor();
//        interceptorRegistry.addInterceptor(interceptor)
//                .addExcludePatterns("/ex_test")
//                .addIncludePatterns("/in_test");
//
//        Test2HandlerInterceptor interceptor2 = new Test2HandlerInterceptor();
//        interceptorRegistry.addInterceptor(interceptor2)
//                .addIncludePatterns("/in_test2", "/in_test3");
//
//        RequestMappingHandlerMapping mapping = new RequestMappingHandlerMapping();
//        mapping.setInterceptors(interceptorRegistry.getMappedInterceptors());
//        return mapping;
//    }

    @Bean
    public HandlerMapping handlerMapping() {
        return new RequestMappingHandlerMapping();
    }
    @Bean
    public HandlerAdapter handlerAdapter(ConversionService conversionService) {
        RequestMappingHandlerAdapter handlerAdapter = new RequestMappingHandlerAdapter();
        handlerAdapter.setConversionService(conversionService);
        return handlerAdapter;
    }
    @Bean
    public ConversionService conversionService() {
        DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService();
        DateFormatter dateFormatter = new DateFormatter();
        dateFormatter.setPattern("yyyy-MM-dd HH:mm:ss");
        conversionService.addFormatter(dateFormatter);
        return conversionService;
    }
    @Bean
    public ViewResolver viewResolver() {
        ContentNegotiatingViewResolver negotiatingViewResolver = new ContentNegotiatingViewResolver();
        negotiatingViewResolver.setViewResolvers(Collections.singletonList(new InternalResourceViewResolver()));
        return negotiatingViewResolver;
    }
    @Bean
    public DispatcherServlet dispatcherServlet() {
        return new DispatcherServlet();
    }

    @Bean
    public HandlerExceptionResolver handlerExceptionResolver(ConversionService conversionService) {
        ExceptionHandlerExceptionResolver resolver = new ExceptionHandlerExceptionResolver();
        resolver.setConversionService(conversionService);
        return resolver;
    }
}
