package com.SpringMVC.handler.argument;

import com.SpringMVC.handler.ModelAndViewContainer;
import com.SpringMVC.handler.exception.MissingServletRequestParameterException;
import org.springframework.core.MethodParameter;
import org.springframework.core.convert.ConversionService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author ZhangYihe
 * @since 2025/2/17
 **/

/**
 * 所有参数解析器的注册中心
 */
public class HandlerMethodArgumentResolverComposite implements HandlerMethodArgumentResolver{

    List<HandlerMethodArgumentResolver> resolverList=new ArrayList<>();
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return true;
    }

    /**
     * 从resolverList中选择合适的解析器以解析参数并返回
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
        for(HandlerMethodArgumentResolver resolver:resolverList){
            if(resolver.supportsParameter(parameter)){
                return resolver.resolveArgument(parameter,request,response,container,service);
            }
        }
        throw new IllegalArgumentException("Unsupported parameter type [" +
                parameter.getParameterType().getName() + "]. supportsParameter should be called first.");
    }

    public void addResolver(HandlerMethodArgumentResolver resolver){
        if(resolver!=null){
            resolverList.add(resolver);
        }
    }

    public void addResolver(Collection<HandlerMethodArgumentResolver> resolvers){
        resolverList.addAll(resolvers);
    }

    public void clear(){
        resolverList.clear();
    }
}
