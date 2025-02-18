package com.SpringMVC.annotation;

import java.lang.annotation.*;

/**
 * @author ZhangYihe
 * @since 2025/2/17
 **/
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ResponseBody {

}
