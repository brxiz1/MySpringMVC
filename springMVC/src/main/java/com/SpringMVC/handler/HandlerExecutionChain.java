package com.SpringMVC.handler;

import com.SpringMVC.ModelAndView;
import com.SpringMVC.handler.interceptor.HandlerInterceptor;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ZhangYihe
 * @since 2025/2/15
 **/

/**
 * 定义了一个请求的执行链
 */
public class HandlerExecutionChain {
    private HandlerMethod handler;
    /**
     * 与请求匹配的拦截器链
     */
    private List<HandlerInterceptor> interceptors=new ArrayList<>();
    private int interceptorIndex=-1;

    public HandlerExecutionChain(HandlerMethod handler, List<HandlerInterceptor> interceptors){
        this.handler=handler;
        if(!CollectionUtils.isEmpty(interceptors)){
            this.interceptors=interceptors;
        }

    }

    /**
     * 执行所有interceptor的preHandle方法
     * @param request
     * @param response
     */
    public boolean applyPreHandle(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if(CollectionUtils.isEmpty(interceptors)){
            return true;
        }
        for(int i=0;i<interceptors.size();i++){
            if(!interceptors.get(i).preHandle(request,response,handler)){
                triggerAfterCompletion(request,response,null);
                return false;
            }
            interceptorIndex=i;
        }
        return true;
    }

    /**
     * 执行所有interceptor的postHandle方法
     * @param request
     * @param response
     * @param modelAndView
     */
    public void applyPostHandle(HttpServletRequest request,
                                HttpServletResponse response,
                                ModelAndView modelAndView) throws Exception {
        if(CollectionUtils.isEmpty(interceptors)){
            return ;
        }
        for(HandlerInterceptor interceptor:interceptors){
            interceptor.postHandle(request,response,handler,modelAndView);
        }
    }

    /**
     * 执行index小等于interceptorIndex的所有拦截器的afterCompletion
     * @param request
     * @param response
     * @param exception
     */
    public void triggerAfterCompletion(HttpServletRequest request,
                                       HttpServletResponse response,
                                       Exception exception) throws Exception {
        if(CollectionUtils.isEmpty(interceptors)){
            return;
        }
        for(int i=interceptorIndex;i>=0;i--){
            interceptors.get(i).afterCompletion(request,response,handler,exception);
        }
    }

    /**
     * 获取本处理链的处理器（对应的Controller方法）
     * @return
     */
    public HandlerMethod getHandler() {
        return handler;
    }

    /**
     * 获取本处理链的所有拦截器
     * @return
     */
    public List<HandlerInterceptor> getInterceptors() {
        return interceptors;
    }
}
