package com.SpringMVC.handler.interceptor;

/**
 * @author ZhangYihe
 * @since 2025/2/16
 **/

import java.util.ArrayList;
import java.util.List;

/**
 * 拦截器注册中心
 */
public class InterceptorRegistry {
    private List<MappedInterceptor> mappedInterceptors=new ArrayList<>();

    /**
     * 添加新的拦截器
     * @param interceptor
     * @return
     */
    public MappedInterceptor addInterceptor(HandlerInterceptor interceptor){
        MappedInterceptor mappedInterceptor=new MappedInterceptor(interceptor);
        mappedInterceptors.add(mappedInterceptor);
        return mappedInterceptor;
    }

    /**
     * 获取注册的拦截器列表
     * @return
     */
    public List<MappedInterceptor> getMappedInterceptors(){
        return mappedInterceptors;
    }

}
