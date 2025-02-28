package com.SpringMVC.configuration;

import com.SpringMVC.config.WebMvcConfigurer;
import com.SpringMVC.handler.argument.HandlerMethodArgumentResolver;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author ZhangYihe
 * @since 2025/2/27
 **/
@Configuration
public class MyWebMVCConfigurer implements WebMvcConfigurer {
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new MyHandlerMethodArgumentResolver());
    }
}
