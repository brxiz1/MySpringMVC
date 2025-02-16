package com.SpringMVC.controller;

import com.SpringMVC.annotation.RequestMapping;
import com.SpringMVC.http.RequestMethod;
import org.springframework.stereotype.Controller;

/**
 * @author ZhangYihe
 * @since 2025/2/16
 **/

@Controller
public class TestHandlerController {

    @RequestMapping(path = "/ex_test", method = RequestMethod.POST)
    public void exTest() {
    }

    @RequestMapping(path = "/in_test", method = RequestMethod.POST)
    public void inTest() {
    }


    @RequestMapping(path = "/in_test2", method = RequestMethod.POST)
    public void inTest2() {
    }

    @RequestMapping(path = "/in_test3", method = RequestMethod.POST)
    public void inTest3() {
    }

}
