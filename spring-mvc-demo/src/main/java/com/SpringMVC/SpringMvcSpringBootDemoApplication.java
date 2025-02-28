package com.SpringMVC;

import com.SpringMVC.annotation.EnableWebMvc;
import com.SpringMVC.annotation.RequestMapping;
import com.SpringMVC.http.RequestMethod;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.SpringMVC.context.ServletWebServerApplicationContext;
import org.springframework.stereotype.Controller;


/**
 * @author ZhangYihe
 * @since 2025/2/27
 **/
@SpringBootApplication
//@EnableWebMvc
public class SpringMvcSpringBootDemoApplication {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(SpringMvcSpringBootDemoApplication.class);
        application.setApplicationContextClass(ServletWebServerApplicationContext.class);
        application.run(args);
    }


}
