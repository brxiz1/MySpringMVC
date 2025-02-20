package com.SpringMVC.view.resolver;

import com.SpringMVC.view.InternalResourceView;
import com.SpringMVC.view.View;

/**
 * @author ZhangYihe
 * @since 2025/2/20
 **/
public class InternalResourceViewResolver extends  UrlBasedViewResolver{

    /**
     * 拼接url的前缀和后缀，解析出对应的InternalResourceView
     * @param viewName
     * @return
     */
    @Override
    protected View buildView(String viewName) {
        String url=getPrefix()+viewName+getSuffix();
        return new InternalResourceView(url);
    }
}
