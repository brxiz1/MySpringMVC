package com.SpringMVC;

import com.SpringMVC.annotation.EnableWebMvc;
import com.SpringMVC.config.WebMvcConfigurationSupport;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author ZhangYihe
 * @since 2025/2/27
 **/
@Configuration
@ConditionalOnMissingBean(WebMvcConfigurationSupport.class)
//导入两个配置类
@Import({SpringMVCDispatcherServletAutoConfiguration.class, ServletWebServerFactoryAutoConfiguration.class})
public class WebMvcAutoConfiguration {
    @EnableWebMvc
    @EnableConfigurationProperties({WebMvcProperties.class})
    public static class EnableWebMvcAutoConfiguration {
    }
}
