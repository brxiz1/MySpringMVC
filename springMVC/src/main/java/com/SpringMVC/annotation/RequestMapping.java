package com.SpringMVC.annotation;

import com.SpringMVC.http.RequestMethod;

import java.lang.annotation.*;

/**
 * @author ZhangYihe
 * @since 2025/2/15
 **/
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestMapping {
    String path();

    RequestMethod method() default RequestMethod.GET;
}
