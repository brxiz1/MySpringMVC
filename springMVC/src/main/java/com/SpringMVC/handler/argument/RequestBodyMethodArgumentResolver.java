package com.SpringMVC.handler.argument;

import com.SpringMVC.annotation.RequestBody;
import com.SpringMVC.handler.ModelAndViewContainer;
import com.SpringMVC.handler.exception.MissingServletRequestParameterException;
import com.alibaba.fastjson.JSON;
import org.springframework.core.MethodParameter;
import org.springframework.core.convert.ConversionService;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * @author ZhangYihe
 * @since 2025/2/17
 **/

/**
 * 将request中的json解析为对象的参数解析器
 */
public class RequestBodyMethodArgumentResolver implements HandlerMethodArgumentResolver{
    /**
     * 该参数是否标注了RequestBody注解
     * @param parameter
     * @return
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(RequestBody.class);
    }

    /**
     * 将request中的json解析为对象
     * @param parameter
     * @param request
     * @param response
     * @param container
     * @param service
     * @return
     * @throws MissingServletRequestParameterException
     * @throws IOException
     */
    @Override
    public Object resolveArgument(MethodParameter parameter, HttpServletRequest request, HttpServletResponse response, ModelAndViewContainer container, ConversionService service) throws MissingServletRequestParameterException, IOException {
        Class<?> targetType=parameter.getParameterType();
        RequestBody requestBody=parameter.getParameterAnnotation(RequestBody.class);
        if(requestBody==null)return null;
        String json=getHttpMessageBody(request);
        if(StringUtils.isEmpty(json)){
            if(requestBody.required()){
                throw new MissingServletRequestParameterException(parameter.getParameterName(),
                        parameter.getParameterType().getName());
            }else{
                return null;
            }
        }

        return JSON.parseObject(json,targetType);
    }

    /**
     * 从request的输入流中获取json
     * @param request
     * @return
     * @throws IOException
     */
    private String getHttpMessageBody(HttpServletRequest request) throws IOException {
        StringBuilder sb=new StringBuilder();
        char[] buff=new char[1024];
        int len;
        try(BufferedReader reader=request.getReader()) {
            while((len=reader.read(buff))!=-1){
                sb.append(buff,0,len);
            }
            return sb.toString();
        } catch (IOException e) {
            throw e;
        }

    }
}
