package com.SpringMVC;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author ZhangYihe
 * @since 2025/2/16
 **/
public class Main {
    static List<Integer> list=new ArrayList<>();
    public static void main(String[] args) {
        addtolist(1);
        System.out.println(list);
    }
    public static void addtolist(Integer...i){
        Collections.addAll(list,i);
    }
}
