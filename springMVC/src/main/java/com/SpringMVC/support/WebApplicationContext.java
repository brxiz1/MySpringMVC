package com.SpringMVC.support;

import org.springframework.context.ApplicationContext;

import javax.servlet.ServletContext;

/**
 * @author ZhangYihe
 * @since 2025/2/26
 **/

/**
 * 拓展ApplicationContext，提供ServletContext的获取方法
 */
public interface WebApplicationContext extends ApplicationContext {

    String ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE = WebApplicationContext.class.getName() + ".ROOT";

    ServletContext getServletContext();
}
