package com.SpringMVC.http;

/**
 * @author ZhangYihe
 * @since 2025/2/17
 **/

/**
 * 200...http响应状态
 */
public enum HttpStatus {
    OK(200,"OK");
    /**
     * 响应代码
     */
    private int value;

    /**
     * 响应信息
     */
    private String reasonPhrase;

    HttpStatus(int value, String reasonPhrase){
        this.value=value;
        this.reasonPhrase=reasonPhrase;
    }

    public int getValue() {
        return value;
    }

    public String getReasonPhrase() {
        return reasonPhrase;
    }
}
