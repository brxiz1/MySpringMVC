package com.SpringMVC.view.resolver;

import com.SpringMVC.view.AbstractView;
import com.SpringMVC.view.InternalResourceView;
import com.SpringMVC.view.RedirectView;
import com.SpringMVC.view.View;

/**
 * @author ZhangYihe
 * @since 2025/2/20
 **/
public abstract class UrlBasedViewResolver extends AbstractCachingViewResolver {

    /**
     * 重定向url前缀
     */
    public static final String REDIRECT_URL_PREFIX = "redirect:";

    /**
     * 服务端转发url的前缀
     */
    public static final String FORWARD_URL_PREFIX = "forward:";

    private String prefix = "";

    private String suffix = "";

    /**
     * 解析服务端转发和重定向视图，其余类型的视图解析方法由子类实现
     * @param viewName
     * @return
     */
    @Override
    protected View createView(String viewName){
        if(viewName.startsWith(REDIRECT_URL_PREFIX)){
            viewName= viewName.substring(REDIRECT_URL_PREFIX.length());
            return new RedirectView(viewName);
        }
        if(viewName.startsWith(FORWARD_URL_PREFIX)){
            viewName=viewName.substring(FORWARD_URL_PREFIX.length());
            return new InternalResourceView(viewName);
        }
        return buildView(viewName);
    }


    protected abstract View buildView(String viewName);

    public String getPrefix() {
        return prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
}
