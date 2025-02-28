package com.SpringMVC.support;

import org.springframework.context.ConfigurableApplicationContext;

import javax.servlet.ServletContext;

/**
 * @author ZhangYihe
 * @since 2025/2/26
 **/

/**
 * 为WebApplicationContext提供配置方法
 */
public interface ConfigurableWebApplicationContext extends WebApplicationContext, ConfigurableApplicationContext {

    void setServletContext(ServletContext context);

}
