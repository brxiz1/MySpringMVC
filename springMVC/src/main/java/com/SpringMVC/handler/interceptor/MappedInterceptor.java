package com.SpringMVC.handler.interceptor;

/**
 * @author ZhangYihe
 * @since 2025/2/16
 **/

import com.SpringMVC.ModelAndView;
import com.SpringMVC.handler.mapping.HandlerMapping;
import com.SpringMVC.handler.mapping.MappingRegistry;
import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Interceptor的代理类，管理Interceptor的url列表
 */
public class MappedInterceptor implements HandlerInterceptor{

    private List<String> includePatterns=new ArrayList<>();

    private List<String> excludePatterns=new ArrayList<>();

    private HandlerInterceptor interceptor;

    public MappedInterceptor(HandlerInterceptor interceptor){
        this.interceptor=interceptor;
    }

    /**
     * 添加Interceptor url白名单
     * @param patterns
     */
    public MappedInterceptor addIncludePatterns(String... patterns){
        includePatterns.addAll(Arrays.asList(patterns));
        return this;
    }

    /**
     * 添加Interceptor url黑名单
     * @param patterns
     */
    public MappedInterceptor addExcludePatterns(String... patterns){
        excludePatterns.addAll(Arrays.asList(patterns));
        return this;
    }

    /**
     * 判断path是否能与本拦截器匹配
     * @param path
     * @return
     */
    public boolean matches(String path){
        if(!excludePatterns.isEmpty()){
            if(excludePatterns.contains(path)){
                return false;
            }
        }
        if(CollectionUtils.isEmpty(includePatterns)){
            return true;
        }
        return includePatterns.contains(path);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object handler) {
        return interceptor.preHandle(request,response,handler);
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler,
                           @Nullable ModelAndView modelAndView) {
        interceptor.postHandle(request,response,handler,modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler,
                                @Nullable Exception exception) {
        interceptor.afterCompletion(request,response,handler,exception);
    }
}
