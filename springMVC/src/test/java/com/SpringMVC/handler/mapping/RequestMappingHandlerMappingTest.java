package com.SpringMVC.handler.mapping;

import com.SpringMVC.controller.TestController;
import com.SpringMVC.controller.TestHandlerController;
import com.SpringMVC.handler.HandlerExecutionChain;
import com.SpringMVC.handler.HandlerMethod;
import com.SpringMVC.handler.argument.HandlerMethodArgumentResolverComposite;
import com.SpringMVC.handler.argument.RequestBodyMethodArgumentResolver;
import com.SpringMVC.handler.argument.RequestParamMethodArgumentResolver;
import com.SpringMVC.handler.argument.ServletRequestMethodArgumentResolver;
import com.SpringMVC.handler.exception.NoHandlerFoundException;
import com.SpringMVC.handler.interceptor.MappedInterceptor;
import com.SpringMVC.http.RequestMethod;
import com.SpringMVC.BaseJunit4Test;
import com.SpringMVC.intercepter.Test2HandlerInterceptor;
import com.SpringMVC.intercepter.TestHandlerInterceptor;
import com.SpringMVC.vo.UserVo;
import com.alibaba.fastjson.JSON;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;

import java.lang.reflect.Method;
import java.util.Date;

/**
 * @author ZhangYihe
 * @since 2025/2/15
 **/
public class RequestMappingHandlerMappingTest extends BaseJunit4Test {
    @Autowired
    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    @Test
    public void test(){
        MappingRegistry mappingRegistry = requestMappingHandlerMapping.getMappingRegistry();

        String path = "/index/test";
        String path1 = "/index/test2";
        String path4 = "/test4";

        Assert.assertEquals(mappingRegistry.getPathHandlerMethod().size(), 2);

        HandlerMethod handlerMethod = mappingRegistry.getHandlerMethodByPath(path);
        HandlerMethod handlerMethod2 = mappingRegistry.getHandlerMethodByPath(path1);
        HandlerMethod handlerMethod4 = mappingRegistry.getHandlerMethodByPath(path4);

        Assert.assertNull(handlerMethod4);
        Assert.assertNotNull(handlerMethod);
        Assert.assertNotNull(handlerMethod2);


        RequestMappingInfo mapping = mappingRegistry.getMappingByPath(path);
        RequestMappingInfo mapping2 = mappingRegistry.getMappingByPath(path1);

        Assert.assertNotNull(mapping);
        Assert.assertNotNull(mapping2);
        Assert.assertEquals(mapping.getHttpMethod(), RequestMethod.GET);
        Assert.assertEquals(mapping2.getHttpMethod(), RequestMethod.POST);
    }

//    @Test
//    public void testBeanMaps() {
////        Map<String, Object> beanMaps = applicationContext.getBeansWithAnnotation(Controller.class);
//        for (Map.Entry<String, Object> entry : beanMaps.entrySet()) {
//            System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
//        }
//    }

    @Test
    public void testGetHandler() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();

        //测试TestHandlerInterceptor拦截器生效
        request.setRequestURI("/in_test");
        HandlerExecutionChain executionChain = requestMappingHandlerMapping.getHandler(request);

        HandlerMethod handlerMethod = executionChain.getHandler();
        Assert.assertTrue(handlerMethod.getBean() instanceof TestHandlerController);
        Assert.assertTrue(((MappedInterceptor) executionChain.getInterceptors().get(0)).getInterceptor()
                instanceof TestHandlerInterceptor);

        //测试TestHandlerInterceptor拦截器不生效
        request.setRequestURI("/ex_test");
        executionChain = requestMappingHandlerMapping.getHandler(request);
        Assert.assertEquals(executionChain.getInterceptors().size(), 0);

        //测试找不到Handler,抛出异常
        request.setRequestURI("/in_test454545");
        try {
            requestMappingHandlerMapping.getHandler(request);
        } catch (NoHandlerFoundException e) {
            System.out.println("异常URL:" + e.getRequestURL());
        }

        //测试Test2HandlerInterceptor拦截器对in_test2、in_test3都生效
        request.setRequestURI("/in_test2");
        executionChain = requestMappingHandlerMapping.getHandler(request);
        Assert.assertEquals(executionChain.getInterceptors().size(), 1);
        Assert.assertTrue(((MappedInterceptor) executionChain.getInterceptors().get(0)).getInterceptor()
                instanceof Test2HandlerInterceptor);

        request.setRequestURI("/in_test3");
        executionChain = requestMappingHandlerMapping.getHandler(request);
        Assert.assertEquals(executionChain.getInterceptors().size(), 1);
        Assert.assertTrue(((MappedInterceptor) executionChain.getInterceptors().get(0)).getInterceptor()
                instanceof Test2HandlerInterceptor);
    }

    @Test
    public void test1() throws NoSuchMethodException {
        TestController testController = new TestController();
        Method method = testController.getClass().getMethod("test4",
                String.class, Integer.class, Date.class, HttpServletRequest.class);

        //构建HandlerMethod对象
        HandlerMethod handlerMethod = new HandlerMethod(testController, method);

        //构建模拟请求的request
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setParameter("name", "Silently9527");
        request.setParameter("age", "25");
        request.setParameter("birthday", "2020-11-12 13:00:00");

        //添加支持的解析器
        HandlerMethodArgumentResolverComposite resolverComposite = new HandlerMethodArgumentResolverComposite();
        resolverComposite.addResolver(new RequestParamMethodArgumentResolver());
        resolverComposite.addResolver(new ServletRequestMethodArgumentResolver());

        //定义转换器
        DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService();
        DateFormatter dateFormatter = new DateFormatter();
        dateFormatter.setPattern("yyyy-MM-dd HH:mm:ss");
        conversionService.addFormatter(dateFormatter);

        MockHttpServletResponse response = new MockHttpServletResponse();

        //用于查找方法参数名
        DefaultParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();
        handlerMethod.getParameters().forEach(methodParameter -> {
            try {
                methodParameter.initParameterNameDiscovery(parameterNameDiscoverer);

                Object value = resolverComposite.resolveArgument(methodParameter, request,response, null, conversionService);
                System.out.println(methodParameter.getParameterName() + " : " + value + "   type: " + value.getClass());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Test
    public void test2() throws NoSuchMethodException {
        TestController testController = new TestController();
        Method method = testController.getClass().getMethod("user", UserVo.class);

        HandlerMethod handlerMethod = new HandlerMethod(testController, method);

        MockHttpServletRequest request = new MockHttpServletRequest();
        UserVo userVo = new UserVo();
        userVo.setName("Silently9527");
        userVo.setAge(25);
        userVo.setBirthday(new Date());
        request.setContent(JSON.toJSONString(userVo).getBytes()); //模拟JSON参数

        HandlerMethodArgumentResolverComposite resolverComposite = new HandlerMethodArgumentResolverComposite();
        resolverComposite.addResolver(new RequestBodyMethodArgumentResolver());

        MockHttpServletResponse response = new MockHttpServletResponse();

        DefaultParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();
        handlerMethod.getParameters().forEach(methodParameter -> {
            try {
                methodParameter.initParameterNameDiscovery(parameterNameDiscoverer);
                Object value = resolverComposite.resolveArgument(methodParameter, request, response, null, null);
                System.out.println(methodParameter.getParameterName() + " : " + value + "   type: " + value.getClass());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

}
