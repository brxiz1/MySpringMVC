package com.SpringMVC.controller;

import com.SpringMVC.annotation.RequestMapping;
import com.SpringMVC.http.RequestMethod;
import org.springframework.stereotype.Service;

/**
 * @author ZhangYihe
 * @since 2025/2/15
 **/
@Service
public class TestController {
    public int index=2;
    @RequestMapping(path = "/test4", method = RequestMethod.POST)
    public void test4(String name2) {

    }
}
