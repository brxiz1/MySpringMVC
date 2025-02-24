package com.SpringMVC.exception;

/**
 * @author ZhangYihe
 * @since 2025/2/23
 **/
public class TestException extends RuntimeException {
    private String name;

    public TestException(String message, String name) {
        super(message);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
