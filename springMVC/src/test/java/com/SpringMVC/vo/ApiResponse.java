package com.SpringMVC.vo;

/**
 * @author ZhangYihe
 * @since 2025/2/23
 **/
public class ApiResponse {
    private int code;
    private String message;
    private String data;

    public ApiResponse(int code, String message, String data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
    //getter setter

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
