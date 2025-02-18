package com.SpringMVC.handler.returnvalue;

import com.SpringMVC.annotation.RequestBody;
import com.SpringMVC.annotation.ResponseBody;
import com.SpringMVC.handler.ModelAndViewContainer;
import com.alibaba.fastjson.JSON;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotatedElementUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author ZhangYihe
 * @since 2025/2/17
 **/

/**
 * 解析标注@ResponseBody的类和方法的返回值，并生成json直接加入HttpServletResponse
 */
public class ResponseBodyMethodReturnValueHandler implements HandlerMethodReturnValueHandler{
    @Override
    public boolean supportReturnValue(MethodParameter parameter) {

        return AnnotatedElementUtils.hasAnnotation(parameter.getContainingClass(), ResponseBody.class)
                || parameter.hasMethodAnnotation(ResponseBody.class);
    }

    /**
     * 把输入对象returnValue解析为json并直接输入response
     * @param returnValue
     * @param returnType
     * @param mavContainer
     * @param request
     * @param response
     */
    @Override
    public void handleReturnValue(Object returnValue,
                                  MethodParameter returnType,
                                  ModelAndViewContainer mavContainer,
                                  HttpServletRequest request,
                                  HttpServletResponse response) {
        mavContainer.setRequestHandled(true);
        outputMessage(response, JSON.toJSONString(returnValue));
    }

    /**
     * 将json字符串输入response输出流
     * @param response
     * @param json
     */
    private void outputMessage(HttpServletResponse response,String json){
        try(PrintWriter writer=response.getWriter()) {
            writer.write(json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
