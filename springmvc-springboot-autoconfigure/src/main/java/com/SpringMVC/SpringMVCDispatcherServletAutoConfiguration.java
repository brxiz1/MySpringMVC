package com.SpringMVC;

import com.SpringMVC.servlet.SpringMvcDispatcherServletRegistrationBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ZhangYihe
 * @since 2025/2/27
 **/

/**
 * 提供DispatcherServlet和对应的ServletInitializer的注册
 */
@Configuration
@ConditionalOnClass(DispatcherServlet.class)
@EnableConfigurationProperties(WebMvcProperties.class) //开启配置类WebMvcProperties
public class SpringMVCDispatcherServletAutoConfiguration {

    public static final String DEFAULT_DISPATCHER_SERVLET_BEAN_NAME = "springMvcDispatcherServlet";

    @Bean
    @ConditionalOnMissingBean(value = DispatcherServlet.class)
    public DispatcherServlet dispatcherServlet() {
        return new DispatcherServlet();
    }

    @Bean
    @ConditionalOnBean(value = DispatcherServlet.class)
    public SpringMvcDispatcherServletRegistrationBean dispatcherServletRegistration(
            DispatcherServlet dispatcherServlet, WebMvcProperties webMvcProperties) {
        SpringMvcDispatcherServletRegistrationBean registration = new SpringMvcDispatcherServletRegistrationBean(dispatcherServlet,
                webMvcProperties.getServlet().getPath()); //通过webMvcProperties配置Servlet的拦截路径
        registration.setName(DEFAULT_DISPATCHER_SERVLET_BEAN_NAME);
        registration.setLoadOnStartup(webMvcProperties.getServlet().getLoadOnStartup());
        return registration;
    }
}
