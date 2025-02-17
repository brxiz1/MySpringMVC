package com.SpringMVC.annotation;

import java.lang.annotation.*;

/**
 * @author ZhangYihe
 * @since 2025/2/17
 **/
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestBody {
    boolean required() default true;
}
