package com.SpringMVC.config;

import com.SpringMVC.handler.interceptor.InterceptorRegistry;
import com.SpringMVC.handler.mapping.RequestMappingHandlerMapping;
import com.SpringMVC.intercepter.Test2HandlerInterceptor;
import com.SpringMVC.intercepter.TestHandlerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

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

    @Bean
    public RequestMappingHandlerMapping handlerMapping() {
        InterceptorRegistry interceptorRegistry = new InterceptorRegistry();

        TestHandlerInterceptor interceptor = new TestHandlerInterceptor();
        interceptorRegistry.addInterceptor(interceptor)
                .addExcludePatterns("/ex_test")
                .addIncludePatterns("/in_test");

        Test2HandlerInterceptor interceptor2 = new Test2HandlerInterceptor();
        interceptorRegistry.addInterceptor(interceptor2)
                .addIncludePatterns("/in_test2", "/in_test3");

        RequestMappingHandlerMapping mapping = new RequestMappingHandlerMapping();
        mapping.setInterceptors(interceptorRegistry.getMappedInterceptors());
        return mapping;
    }
}
