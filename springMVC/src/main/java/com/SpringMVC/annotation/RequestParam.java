package com.SpringMVC.annotation;

import java.lang.annotation.*;

/**
 * @author ZhangYihe
 * @since 2025/2/17
 **/
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
/**
 * 标注request需要提取的变量
 */
public @interface RequestParam {
    /**
     * 从request中应该取出的参数名称
     * @return
     */
    String name();

    /**
     * 该参数是否必填
     * @return
     */
    boolean required() default true;

    /**
     * 如果request中没有该参数，则采用此默认值
     * @return
     */
    String defaultValue() default "";
}
