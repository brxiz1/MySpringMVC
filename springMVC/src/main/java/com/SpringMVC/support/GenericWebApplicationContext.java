package com.SpringMVC.support;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import javax.servlet.ServletContext;

/**
 * @author ZhangYihe
 * @since 2025/2/26
 **/

/**
 * 通过继承GenericApplicationContext实现编程化的bean注册，提供基础容器功能
 */
public class GenericWebApplicationContext extends GenericApplicationContext implements ConfigurableWebApplicationContext {
    private ServletContext servletContext;
    @Override
    public void setServletContext(ServletContext context) {
        servletContext=context;
    }

    @Override
    public ServletContext getServletContext() {
        return servletContext;
    }

    public GenericWebApplicationContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public GenericWebApplicationContext() {
    }

}
