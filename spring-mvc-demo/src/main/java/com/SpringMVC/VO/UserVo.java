package com.SpringMVC.VO;

import java.util.Date;

/**
 * @author ZhangYihe
 * @since 2025/2/27
 **/
public class UserVo {
    String name;
    int age;
    Date birthday;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}
