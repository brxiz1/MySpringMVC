package com.SpringMVC.controller;

import com.SpringMVC.annotation.RequestMapping;
import com.SpringMVC.http.RequestMethod;
import org.springframework.stereotype.Controller;

/**
 * @author ZhangYihe
 * @since 2025/2/15
 **/
@Controller
@RequestMapping(path = "/index")
public class IndexController {
    public int index=1;
    @RequestMapping(path = "/test", method = RequestMethod.GET)
    public void test(String name) {

    }

    @RequestMapping(path = "/test2", method = RequestMethod.POST)
    public void test2(String name2) {

    }

    public void test3(String name3) {

    }
}
