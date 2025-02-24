package com.SpringMVC;

import com.SpringMVC.exception.ExceptionHandlerExceptionResolver;
import com.SpringMVC.exception.HandlerExceptionResolver;
import com.SpringMVC.handler.HandlerExecutionChain;
import com.SpringMVC.handler.adapter.HandlerAdapter;
import com.SpringMVC.handler.exception.NoHandlerFoundException;
import com.SpringMVC.handler.mapping.HandlerMapping;
import com.SpringMVC.utils.RequestContextHolder;
import com.SpringMVC.view.View;
import com.SpringMVC.view.resolver.ViewResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;

/**
 * @author ZhangYihe
 * @since 2025/2/15
 **/
public class DispatcherServlet extends HttpServlet implements ApplicationContextAware {

    private final Logger logger= LoggerFactory.getLogger(getClass());

    private ApplicationContext applicationContext;

    private HandlerMapping handlerMapping;

    private HandlerAdapter handlerAdapter;

    private ViewResolver viewResolver;

    private Collection<ExceptionHandlerExceptionResolver> handlerExceptionResolvers;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;
    }

    @Override
    public void init(){
        this.handlerMapping=this.applicationContext.getBean(HandlerMapping.class);
        this.handlerAdapter=this.applicationContext.getBean(HandlerAdapter.class);
        this.viewResolver=this.applicationContext.getBean(ViewResolver.class);
        this.handlerExceptionResolvers=applicationContext.getBeansOfType(ExceptionHandlerExceptionResolver.class).values();
    }

    /**
     * 响应http请求,注册RequestContextHolder
     * @param request
     * @param response
     */
    @Override
    public void service(HttpServletRequest request, HttpServletResponse response){
        logger.info("dispatcherServlet.service =>url:{}",request.getRequestURI());
        RequestContextHolder.setRequest(request);
        try{
            doDispatch(request,response);
        }catch(Exception e){
            logger.error("Handler the request fail", e);
        }finally{
            RequestContextHolder.resetRequest();
        }

    }

    /**
     * 请求处理的核心逻辑：
     * 1、从HandlerMapping中获取请求处理链HandlerExecutionChain
     * 2、调用拦截器的preHandle方法
     * 3、从HandlerExecutionChain中获取handler，利用HandlerAdapter执行handler的处理逻辑
     * 4、调用拦截器的postHandle方法
     * 5、调用processDispatchResult处理返回结果
     * 6、调用拦截器的triggerAfterCompletion
     * @param request
     * @param response
     */
    private void doDispatch(HttpServletRequest request,HttpServletResponse response) throws Exception {
        HandlerExecutionChain chain=null;
        Exception dispatchException=null;
        try{
            ModelAndView modelAndView=null;
            try{
                chain=handlerMapping.getHandler(request);
                if(!chain.applyPreHandle(request,response))return;
                //这里的返回值可能是null，如果controller标注@ResponseBody的话
                modelAndView=handlerAdapter.handle(request,response,chain.getHandler());
                chain.applyPostHandle(request,response,modelAndView);

            } catch (Exception e) {
                dispatchException=e;
            }
            processDispatchResult(request,response,modelAndView,dispatchException);
        }catch(Exception e){
            dispatchException=e;
            throw e;
        }finally{
            if(chain!=null){
                chain.triggerAfterCompletion(request,response,dispatchException);
            }

        }
    }

    /**
     * 如果之前的处理流程中有报错，则调用processHandlerException处理报错问题
     * 如果没有报错，则调用render方法实现视图解析
     * @param request
     * @param response
     * @param modelAndView
     * @param ex
     */
    private void processDispatchResult(HttpServletRequest request,
                                       HttpServletResponse response,
                                       ModelAndView modelAndView,
                                       Exception ex) throws Exception {
        if(ex!=null){
            modelAndView=processHandlerException(request,response,ex);
        }
        if(modelAndView!=null){
            render(modelAndView,request,response);
            return;
        }
        logger.info("No view rendering, null ModelAndView returned.");
    }

    /**
     * 调用viewResolver执行视图解析获取视图，然后调用视图的render方法执行视图
     * @param modelAndView
     * @param request
     * @param response
     * @throws Exception
     */
    private void render(ModelAndView modelAndView,
                        HttpServletRequest request,
                        HttpServletResponse response) throws Exception{
        View view=null;
        if(modelAndView.getViewName()!=null){
            view= viewResolver.resolveViewName(modelAndView.getViewName());
        }else{
            view=(View) modelAndView.getView();
        }
        //设置response的状态
        if(modelAndView.getStatus()!=null){
            response.setStatus(modelAndView.getStatus().getValue());
        }
        if(view!=null){
            view.render(modelAndView.getModel().asMap(),request,response);
        }
    }

    /**
     * 选择异常处理器处理异常
     * @param request
     * @param response
     * @param ex
     * @return
     * @throws Exception
     */
    private ModelAndView processHandlerException(HttpServletRequest request,
                                                 HttpServletResponse response,
                                                 Exception ex) throws Exception {
//        if(CollectionUtils.isEmpty(handlerExceptionResolvers)){
//            throw ex;
//        }
        for(HandlerExceptionResolver exceptionResolver:handlerExceptionResolvers){
            ModelAndView view=exceptionResolver.resolveException(request,response,ex);
            if(view!=null){
                return view;
            }
        }
        throw ex;
    }
}

