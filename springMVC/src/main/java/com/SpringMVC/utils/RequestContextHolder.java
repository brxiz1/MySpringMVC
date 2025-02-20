package com.SpringMVC.utils;

import org.springframework.core.NamedInheritableThreadLocal;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ZhangYihe
 * @since 2025/2/20
 **/

/**
 * 缓存当前线程对应的request
 */
public abstract class RequestContextHolder {
    private static final ThreadLocal<HttpServletRequest> inheritableRequestHolder=
            new NamedInheritableThreadLocal<>("Request context");

    public static void resetRequest(){
        inheritableRequestHolder.remove();
    }

    public static HttpServletRequest getRequest(){
        return inheritableRequestHolder.get();
    }

    public static void setRequest(HttpServletRequest request){
        inheritableRequestHolder.set(request);
    }
}
