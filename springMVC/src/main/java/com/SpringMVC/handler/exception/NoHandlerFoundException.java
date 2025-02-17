package com.SpringMVC.handler.exception;


import javax.servlet.http.HttpServletRequest;

/**
 * @author ZhangYihe
 * @since 2025/2/16
 **/

/**
 * 没有找到能匹配URL的handler
 */
public class NoHandlerFoundException extends Exception{
    /**
     * GET\POST
     */
    private String httpMethod;


    private String requestURL;
    public NoHandlerFoundException(HttpServletRequest request){
        if(request==null)return;
        httpMethod=request.getMethod();
        requestURL=request.getRequestURL().toString();
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public String getRequestURL() {
        return requestURL;
    }
}
