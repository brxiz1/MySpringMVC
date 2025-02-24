package com.SpringMVC.handler.adapter;

import com.SpringMVC.ModelAndView;
import com.SpringMVC.handler.HandlerMethod;
import com.SpringMVC.handler.InvocableHandlerMethod;
import com.SpringMVC.handler.ModelAndViewContainer;
import com.SpringMVC.handler.argument.*;
import com.SpringMVC.handler.returnvalue.*;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author ZhangYihe
 * @since 2025/2/18
 **/

/**
 * 有@RequestMapping标注的Handler的执行中心
 */
public class RequestMappingHandlerAdapter implements HandlerAdapter, InitializingBean {
    /**
     * 自定义的参数解析器列表
     */
    private List<HandlerMethodArgumentResolver> argumentResolverList;

    private HandlerMethodArgumentResolverComposite argumentResolverComposite;
    /**
     * 自定义的返回值解析器列表
     */
    private List<HandlerMethodReturnValueHandler> returnHandlerList;

    private HandlerMethodReturnValueHandlerComposite returnHandlerComposite;

    private ConversionService conversionService;

    /**
     * 执行请求的处理逻辑，获取处理结果
     * @param request
     * @param response
     * @param handlermethod
     * @return
     * @throws Exception
     */
    @Override
    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, HandlerMethod handlermethod) throws Exception {
        InvocableHandlerMethod invocableHandlerMethod=createInvocableHandlerMethod(handlermethod);
        ModelAndViewContainer mavContainer=new ModelAndViewContainer();
        invocableHandlerMethod.invokeAndHandle(request,response,mavContainer);
        return getModelAndView(mavContainer);
    }

    /**
     * 从ModelAndViewContainer中获取ModelAndView对象
     * @param mavContainer
     * @return
     */
    private ModelAndView getModelAndView(ModelAndViewContainer mavContainer){
        //如果controller方法标注了@ResponseBody注解，则数据已经都写入response，这里直接返回null
        if(mavContainer.isRequestHandled()){
            return null;
        }
        ModelAndView mav=new ModelAndView();
        mav.setModel(mavContainer.getModel());
        mav.setStatus(mavContainer.getStatus());
        mav.setView(mavContainer.getView());
        return mav;
    }

    /**
     * 创建Handler执行器
     * @param handlerMethod
     * @return
     */
    private InvocableHandlerMethod createInvocableHandlerMethod(HandlerMethod handlerMethod){
        return new InvocableHandlerMethod(handlerMethod,
                argumentResolverComposite,
                returnHandlerComposite,
                conversionService);
    }

    /**
     * 初始化后执行，检查成员变量是否初始化
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(conversionService,"conversionService can not null");
        if(argumentResolverComposite==null){
            List<HandlerMethodArgumentResolver> resolverList=getDefaultArgumentResolvers();
            argumentResolverComposite=new HandlerMethodArgumentResolverComposite();
            argumentResolverComposite.addResolver(resolverList);
        }

        if(returnHandlerComposite==null){
            List<HandlerMethodReturnValueHandler> returnList=getDefaultReturnHandlers();
            returnHandlerComposite=new HandlerMethodReturnValueHandlerComposite();
            returnHandlerComposite.addReturnValueHandler(returnList);
        }
    }

    /**
     * 初始化默认的返回值处理器
     * @return
     */
    private List<HandlerMethodReturnValueHandler> getDefaultReturnHandlers(){
        List<HandlerMethodReturnValueHandler> handlers=new ArrayList<>();

        handlers.add(new MapMethodReturnValueHandler());
        handlers.add(new ModelMethodReturnValueHandler());
        handlers.add(new ViewMethodReturnValueHandler());
        handlers.add(new ResponseBodyMethodReturnValueHandler());
        handlers.add(new ViewNameMethodReturnValueHandler());
        if(!CollectionUtils.isEmpty(returnHandlerList)){
            handlers.addAll(returnHandlerList);
        }
        return handlers;
    }

    /**
     * 初始化默认参数解析器
     * @return
     */
    private List<HandlerMethodArgumentResolver> getDefaultArgumentResolvers(){
        List<HandlerMethodArgumentResolver> handlers=new ArrayList<>();
        handlers.add(new ServletResponseMethodArgumentResolver());
        handlers.add(new ServletRequestMethodArgumentResolver());
        handlers.add(new RequestBodyMethodArgumentResolver());
        handlers.add(new ModelMethodArgumentResolver());
        handlers.add(new RequestParamMethodArgumentResolver());
        if(!CollectionUtils.isEmpty(argumentResolverList)){
            handlers.addAll(argumentResolverList);
        }
        return handlers;
    }

    public List<HandlerMethodArgumentResolver> getArgumentResolverList() {
        return argumentResolverList;
    }

    public void setArgumentResolverList(List<HandlerMethodArgumentResolver> argumentResolverList) {
        this.argumentResolverList = argumentResolverList;
    }

    public HandlerMethodArgumentResolverComposite getArgumentResolverComposite() {
        return argumentResolverComposite;
    }

    public void setArgumentResolverComposite(HandlerMethodArgumentResolverComposite argumentResolverComposite) {
        this.argumentResolverComposite = argumentResolverComposite;
    }

    public List<HandlerMethodReturnValueHandler> getReturnHandlerList() {
        return returnHandlerList;
    }

    public void setReturnHandlerList(List<HandlerMethodReturnValueHandler> returnHandlerList) {
        this.returnHandlerList = returnHandlerList;
    }

    public HandlerMethodReturnValueHandlerComposite getReturnHandlerComposite() {
        return returnHandlerComposite;
    }

    public void setReturnHandlerComposite(HandlerMethodReturnValueHandlerComposite returnHandlerComposite) {
        this.returnHandlerComposite = returnHandlerComposite;
    }

    public ConversionService getConversionService() {
        return conversionService;
    }

    public void setConversionService(ConversionService conversionService) {
        this.conversionService = conversionService;
    }
}
