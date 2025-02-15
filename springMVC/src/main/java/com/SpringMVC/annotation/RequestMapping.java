package com.SpringMVC.annotation;

import com.SpringMVC.http.RequestMethod;

/**
 * @author ZhangYihe
 * @since 2025/2/15
 **/
public @interface RequestMapping {
    String path();

    RequestMethod method() default RequestMethod.GET;
}
