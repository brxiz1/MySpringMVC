package com.SpringMVC.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author ZhangYihe
 * @since 2025/2/21
 **/

/**
 * 这里用于指定异常处理类，原代码中还有更多功能
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component //在注解上标注Component。组合注解？
public @interface ControllerAdvice {
}
