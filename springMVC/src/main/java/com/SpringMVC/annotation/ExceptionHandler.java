package com.SpringMVC.annotation;

import java.lang.annotation.*;

/**
 * @author ZhangYihe
 * @since 2025/2/21
 **/

/**
 * 指定异常处理方法
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExceptionHandler {
    Class<? extends Throwable>[] value() default {};
}
