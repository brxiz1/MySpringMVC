package com.SpringMVC.config;

import com.SpringMVC.handler.mapping.RequestMappingHandlerMapping;
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
    @Bean
    public RequestMappingHandlerMapping handlerMapping() {
        return new RequestMappingHandlerMapping();
    }
}
