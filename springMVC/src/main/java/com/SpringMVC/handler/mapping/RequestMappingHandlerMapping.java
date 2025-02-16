package com.SpringMVC.handler.mapping;

import com.SpringMVC.annotation.RequestMapping;
import com.SpringMVC.handler.HandlerExecutionChain;
import com.SpringMVC.handler.HandlerMethod;
import com.SpringMVC.handler.exception.NoHandlerFoundException;
import com.SpringMVC.handler.interceptor.HandlerInterceptor;
import com.SpringMVC.handler.interceptor.MappedInterceptor;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.core.MethodIntrospector;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author ZhangYihe
 * @since 2025/2/15
 **/
public class RequestMappingHandlerMapping extends ApplicationObjectSupport implements HandlerMapping, InitializingBean {
    /**
     * HandlerMapping和RequestMapping的注册中心
     */
    private MappingRegistry mappingRegistry=new MappingRegistry();

    /**
     * 所有已注册的interceptor
     */
    private List<MappedInterceptor> interceptorList=new ArrayList<>();

    /**
     * 获取Controller的@Requestmapping注解中的url前缀
     * @param handlerClass
     * @return
     */
    private String getPathPrefix(Class<?> handlerClass){
        RequestMapping requestMapping=
                AnnotatedElementUtils.findMergedAnnotation(handlerClass, RequestMapping.class);
        if(requestMapping==null)return "";
        return requestMapping.path();
    }

    /**
     * 从Controller和Controller方法中生成RequestMappingInfo
     * @param method Controller中的方法
     * @param type Controller.class
     * @return
     */
    private RequestMappingInfo getMappingForMethod(Method method, Class<?> type){
        RequestMapping requestMapping=
                AnnotatedElementUtils.findMergedAnnotation(method, RequestMapping.class);
        if(requestMapping==null)return null;
        String prefix=getPathPrefix(type);
        return new RequestMappingInfo(prefix,requestMapping);
    }

    /**
     * 获取指定handler中的所有HandlerMethod和RequestMappingInfo
     * @param beanName
     * @param handler
     */
    private void detectHandlerMethods(String beanName,Object handler){
        Class<?> beanType=handler.getClass();
        //获取handler中的所有方法，并对方法调用getMappingForMethod(),获取RequestMappingInfo
        Map<Method, RequestMappingInfo> methodsOfMap= MethodIntrospector.selectMethods(beanType,
                (MethodIntrospector.MetadataLookup<RequestMappingInfo>) method->getMappingForMethod(method,beanType));

        for(Method method: methodsOfMap.keySet()){
            mappingRegistry.register(methodsOfMap.get(method),handler,method);
        }
    }

    /**
     * 判断一个bean是否是Controller
     * @param bean
     * @return
     */
    private boolean isHandler(Object bean){
        Class<?> beanType=bean.getClass();
        return AnnotatedElementUtils.hasAnnotation(beanType, Controller.class);
    }

    /**
     * 遍历所有bean，生成HandlerMethods
     */
    private void initialHandlerMethods(){
        Map<String, Object> beansMap=
                BeanFactoryUtils.beansOfTypeIncludingAncestors(obtainApplicationContext(),Object.class);
        for(String beanName: beansMap.keySet()){
            Object bean=beansMap.get(beanName);
            //测试
//            if(beanName.equals("indexController")||beanName.equals("testController")){
//                Map<Method, Object> methods = MethodIntrospector.selectMethods(bean.getClass(),
//                        (MethodIntrospector.MetadataLookup<Object>) method -> {
//                            // 这里可以根据需要返回方法的元数据
//                            return method.getName(); // 返回方法名
//                        });
//
//                // 打印所有方法名
//                methods.forEach((method, name) -> {
//                    System.out.println("Method name: " + name);
//                });
//            }


            //测试

            if(isHandler(bean)){
                detectHandlerMethods(beanName,bean);
            }
        }
    }

    /**
     * 本方法在类初始化后执行
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        initialHandlerMethods();
    }

    /**
     * 获取HandlerMapping注册中心
     * @return
     */
    public MappingRegistry getMappingRegistry() {
        return mappingRegistry;
    }

    public void setInterceptors(List<MappedInterceptor> interceptors){
        interceptorList=interceptors;
    }

    /**
     * 从请求映射到对应的处理链
     * @param request
     * @return
     */
    public HandlerExecutionChain getHandler(HttpServletRequest request) throws NoHandlerFoundException {

//        String url= String.valueOf(request.getRequestURL());
        //注意这里需要的剔除协议头部(http:....)的url
        String uri=request.getRequestURI();
        HandlerMethod handler=mappingRegistry.getHandlerMethodByPath(uri);
        if(handler==null){
            throw new NoHandlerFoundException(request);
        }
        return createExecutionChain(uri,handler);
    }

    /**
     * 根据url和handler创建对应的处理链
     * @param path
     * @param handler
     * @return
     */
    private HandlerExecutionChain createExecutionChain(String path, HandlerMethod handler){
        //构造HandlerExecutionChain使用HandlerInterceptor对象
        // 而不是MappedInterceptor，因为由path获取interceptor的功能到此为止，不再需要
        List<HandlerInterceptor> interceptors=new ArrayList<>();
        for(MappedInterceptor interceptor:interceptorList){
            if(interceptor==null)continue;
            if(interceptor.matches(path)){
                interceptors.add(interceptor);
            }
        }

        return new HandlerExecutionChain(handler,interceptors);
    }

}
