package com.SpringMVC.servlet;

import com.SpringMVC.DispatcherServlet;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletPath;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.util.Assert;

/**
 * @author ZhangYihe
 * @since 2025/2/26
 **/

/**
 * ServletContextInitializer的实现类
 */
public class SpringMvcDispatcherServletRegistrationBean extends ServletRegistrationBean<DispatcherServlet>
        implements DispatcherServletPath {
    /**
     * servlet的拦截路径
     */
    private final String path;
    public SpringMvcDispatcherServletRegistrationBean(DispatcherServlet servlet, String path){
        super(servlet);
        Assert.notNull(path, "Path must not be null");
        this.path = path;
        super.addUrlMappings(getServletUrlMapping());
    }
    @Override
    public String getPath() {
        return path;
    }

    @Override
    public String getRelativePath(String path) {
        return DispatcherServletPath.super.getRelativePath(path);
    }

    @Override
    public String getPrefix() {
        return DispatcherServletPath.super.getPrefix();
    }

    @Override
    public String getServletUrlMapping() {
        return DispatcherServletPath.super.getServletUrlMapping();
    }
}
