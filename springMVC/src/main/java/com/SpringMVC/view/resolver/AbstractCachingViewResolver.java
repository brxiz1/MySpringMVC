package com.SpringMVC.view.resolver;

/**
 * @author ZhangYihe
 * @since 2025/2/20
 **/

import com.SpringMVC.view.View;

import java.util.HashMap;
import java.util.Map;

/**
 * 包装了缓存的视图解析器，会缓存视图名称和视图对象
 */
public abstract class AbstractCachingViewResolver implements ViewResolver{

    private final Object lock=new Object();

    /**
     * 未能解析到视图时的默认视图,防止缓存穿透，不会真的返回
     */
    private static final View unResolveView=(model, request, response) ->{};

    //是否存在不同线程的访问？
    private final Map<String,View> cachedViews=new HashMap<>();

    /**
     * 在缓存中查找该视图名称，如果没有则进行解析
     * @param viewName
     * @return
     */
    @Override
    public View resolveViewName(String viewName){
        View view= cachedViews.get(viewName);
        if(view!=null){
            return view;
        }
        synchronized (lock){
            view= cachedViews.get(viewName);
            if(view!=null){
                return view;
            }
            view=createView(viewName);
            if(view==null){
                view=unResolveView;
            }
            cachedViews.put(viewName,view);

        }
        return view==unResolveView?null:view;
    }

    /**
     * 根据视图名解析出视图，找不到合适的view则返回null
     * @param viewName
     * @return
     */
    protected abstract View createView(String viewName);
}
