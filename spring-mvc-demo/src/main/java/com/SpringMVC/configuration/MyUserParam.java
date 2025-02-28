package com.SpringMVC.configuration;

import java.lang.annotation.*;

/**
 * @author ZhangYihe
 * @since 2025/2/27
 **/
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyUserParam {

    String name();
}
