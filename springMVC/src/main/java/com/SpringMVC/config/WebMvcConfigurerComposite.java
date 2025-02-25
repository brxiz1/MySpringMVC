package com.SpringMVC.config;

import com.SpringMVC.handler.argument.HandlerMethodArgumentResolver;
import com.SpringMVC.handler.interceptor.InterceptorRegistry;
import com.SpringMVC.handler.returnvalue.HandlerMethodReturnValueHandler;
import com.SpringMVC.view.View;
import com.SpringMVC.view.resolver.ViewResolver;
import org.springframework.format.FormatterRegistry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author ZhangYihe
 * @since 2025/2/24
 **/
public class WebMvcConfigurerComposite implements WebMvcConfigurer{
    private List<WebMvcConfigurer> delegates = new ArrayList<>();

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        delegates.forEach(configurer -> configurer.addArgumentResolvers(argumentResolvers));
    }

    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
        delegates.forEach(configurer -> configurer.addReturnValueHandlers(returnValueHandlers));
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        delegates.forEach(configurer -> configurer.addInterceptors(registry));
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        delegates.forEach(configurer -> configurer.addFormatters(registry));
    }

    @Override
    public void addDefaultViews(List<View> views) {
        delegates.forEach(configurer -> configurer.addDefaultViews(views));
    }

    @Override
    public void addViewResolvers(List<ViewResolver> viewResolvers) {
        delegates.forEach(configurer -> configurer.addViewResolvers(viewResolvers));
    }

    public WebMvcConfigurerComposite addWebMvcConfigurers(WebMvcConfigurer... webMvcConfigurers) {
        Collections.addAll(this.delegates, webMvcConfigurers);
        return this;
    }

    public WebMvcConfigurerComposite addWebMvcConfigurers(List<WebMvcConfigurer> configurers) {
        this.delegates.addAll(configurers);
        return this;
    }
}
