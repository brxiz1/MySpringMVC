package com.SpringMVC.view.resolver;

/**
 * @author ZhangYihe
 * @since 2025/2/20
 **/

import com.SpringMVC.view.View;

/**
 * 视图解析器，通过视图名称获取视图
 */
public interface ViewResolver {

    /**
     * 通过视图名称获取视图
     * @param viewName
     * @return
     * @throws Exception
     */
    View resolveViewName(String viewName) throws Exception;

}
