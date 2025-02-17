package com.SpringMVC.handler.exception;

import javax.servlet.ServletException;

/**
 * @author ZhangYihe
 * @since 2025/2/17
 **/

/**
 * request中缺失必要的参数或输入流内容
 */
public class MissingServletRequestParameterException extends ServletException {
    /**
     * request中缺失的参数名称
     */
    private String parameterName;

    /**
     * request中缺失的参数代码
     */
    private String parameterType;



    public MissingServletRequestParameterException(String parameterName,String parameterType){
        super("Required " + parameterType + " parameter '" + parameterName + "' is not present");
        this.parameterName=parameterName;
        this.parameterType=parameterType;
    }

    public String getParameterName() {
        return parameterName;
    }

    public String getParameterType() {
        return parameterType;
    }
}
