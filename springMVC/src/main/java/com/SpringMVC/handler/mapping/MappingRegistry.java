package com.SpringMVC.handler.mapping;

/**
 * @author ZhangYihe
 * @since 2025/2/15
 **/

import com.SpringMVC.handler.HandlerMethod;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * RequestMappingInfo和HandlerMethod的注册中心
 */
public class MappingRegistry {
    /**
     * key:url
     */
    private Map<String,RequestMappingInfo> pathMappingInfo=new ConcurrentHashMap<>();
    /**
     * key:url
     */
    private Map<String, HandlerMethod> pathHandlerMethod=new ConcurrentHashMap<>();

    /**
     * 根据handler和method创建HandlerMethod，并把RequestMappingInfo和HandlerMethod注册在map中
     * @param rmInfo
     * @param handler
     * @param method
     */
    public void register(RequestMappingInfo rmInfo, Object handler, Method method){
        String url=rmInfo.getPath();
        pathMappingInfo.put(url,rmInfo);
        HandlerMethod hm=new HandlerMethod(handler,method);
        pathHandlerMethod.put(url,hm);
    }

    public Map<String, RequestMappingInfo> getPathMappingInfo() {
        return pathMappingInfo;
    }

    public Map<String, HandlerMethod> getPathHandlerMethod() {
        return pathHandlerMethod;
    }

    /**
     *
     * @param path url
     * @return
     */
    public RequestMappingInfo getMappingByPath(String path){
        return pathMappingInfo.get(path);
    }

    /**
     *
     * @param path url
     * @return
     */
    public HandlerMethod getHandlerMethodByPath(String path){
        return pathHandlerMethod.get(path);
    }
}
