package com.SpringMVC.controller;

import com.SpringMVC.annotation.RequestBody;
import com.SpringMVC.annotation.RequestMapping;
import com.SpringMVC.annotation.RequestParam;
import com.SpringMVC.http.RequestMethod;
import com.SpringMVC.vo.UserVo;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author ZhangYihe
 * @since 2025/2/15
 **/
@Service
public class TestController {
    public int index=2;
    @RequestMapping(path = "/test4", method = RequestMethod.POST)
    public void test4(@RequestParam(name = "name") String name,
                      @RequestParam(name = "age") Integer age,
                      @RequestParam(name = "birthday") Date birthday,
                      HttpServletRequest request) {
    }

    @RequestMapping(path = "/user", method = RequestMethod.POST)
    public void user(@RequestBody UserVo userVo) {
    }
}
