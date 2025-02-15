package com.SpringMVC.handler.mapping;

/**
 * @author ZhangYihe
 * @since 2025/2/15
 **/

import com.SpringMVC.annotation.RequestMapping;
import com.SpringMVC.http.RequestMethod;

/**
 * 对应RequestMapping注解信息
 */
public class RequestMappingInfo {
    private String path;
    private RequestMethod httpMethod;

    /**
     * 初始化
     * @param prefix url前缀
     * @param mapping
     */
    public RequestMappingInfo(String prefix, RequestMapping mapping){
        path=prefix+mapping.path();
        httpMethod=mapping.method();
    }
    public String getPath(){
        return path;
    }

    public RequestMethod getHttpMethod(){
        return httpMethod;
    }

}
