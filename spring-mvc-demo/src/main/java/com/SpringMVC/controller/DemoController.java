package com.SpringMVC.controller;

import com.SpringMVC.VO.UserVo;
import com.SpringMVC.annotation.RequestMapping;
import com.SpringMVC.annotation.RequestParam;
import com.SpringMVC.annotation.ResponseBody;
import com.SpringMVC.configuration.MyUserParam;
import com.SpringMVC.http.RequestMethod;
import org.springframework.stereotype.Controller;

import java.util.Date;

/**
 * @author ZhangYihe
 * @since 2025/2/27
 **/
@Controller
public class DemoController {
    @RequestMapping(path = "/redirect", method = RequestMethod.GET)
    public String redirect() {
        return "redirect:get?userId=111";
    }

    //http://localhost:8080/build?user=user_name1,123
    @ResponseBody
    @RequestMapping(path = "/build", method = RequestMethod.GET)
    public UserVo build(@MyUserParam(name = "user") UserVo vo) {
        return vo;
    }

    //http://localhost:8080/get?userId=123
    @ResponseBody
    @RequestMapping(path = "/get", method = RequestMethod.GET)
    public UserVo get(@RequestParam(name = "userId") Long userId) {
        UserVo userVo = new UserVo();
        userVo.setName(userId + "_silently9527");
        userVo.setAge(25);
        userVo.setBirthday(new Date());
        return userVo;
    }


}
