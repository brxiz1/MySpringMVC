package com.SpringMVC.controller;

import com.SpringMVC.annotation.ResponseBody;
import com.SpringMVC.view.View;
import com.SpringMVC.vo.UserVo;
import org.springframework.ui.Model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ZhangYihe
 * @since 2025/2/17
 **/
public class TestReturnValueController {
    @ResponseBody
    public UserVo testResponseBody() {
        UserVo userVo = new UserVo();
        userVo.setBirthday(new Date());
        userVo.setAge(20);
        userVo.setName("Silently9527");
        return userVo;
    }

    public String testViewName() {
        return "/jsp/index.jsp";
    }

    public View testView() {
        return new View() {
        };
    }

    public Model testModel(Model model) {
        model.addAttribute("testModel", "Silently9527");
        return model;
    }

    public Map<String, Object> testMap() {
        Map<String, Object> params = new HashMap<>();
        params.put("testMap", "Silently9527");
        return params;
    }

}
