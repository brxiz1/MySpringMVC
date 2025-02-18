package com.SpringMVC.handler.mapping;

import com.SpringMVC.ModelAndView;
import com.SpringMVC.controller.TestController;
import com.SpringMVC.controller.TestHandlerController;
import com.SpringMVC.controller.TestInvocableHandlerMethodController;
import com.SpringMVC.controller.TestReturnValueController;
import com.SpringMVC.handler.HandlerExecutionChain;
import com.SpringMVC.handler.HandlerMethod;
import com.SpringMVC.handler.InvocableHandlerMethod;
import com.SpringMVC.handler.ModelAndViewContainer;
import com.SpringMVC.handler.adapter.RequestMappingHandlerAdapter;
import com.SpringMVC.handler.argument.*;
import com.SpringMVC.handler.exception.NoHandlerFoundException;
import com.SpringMVC.handler.interceptor.MappedInterceptor;
import com.SpringMVC.handler.returnvalue.*;
import com.SpringMVC.BaseJunit4Test;
import com.SpringMVC.intercepter.Test2HandlerInterceptor;
import com.SpringMVC.intercepter.TestHandlerInterceptor;
import com.SpringMVC.view.RedirectView;
import com.SpringMVC.view.View;
import com.SpringMVC.vo.UserVo;
import com.alibaba.fastjson.JSON;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.MethodParameter;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ZhangYihe
 * @since 2025/2/15
 **/
public class RequestMappingHandlerMappingTest extends BaseJunit4Test {
    @Autowired
    private RequestMappingHandlerMapping requestMappingHandlerMapping;

//    @Test
//    public void test(){
//        MappingRegistry mappingRegistry = requestMappingHandlerMapping.getMappingRegistry();
//
//        String path = "/index/test";
//        String path1 = "/index/test2";
//        String path4 = "/test4";
//
//        Assert.assertEquals(mappingRegistry.getPathHandlerMethod().size(), 2);
//
//        HandlerMethod handlerMethod = mappingRegistry.getHandlerMethodByPath(path);
//        HandlerMethod handlerMethod2 = mappingRegistry.getHandlerMethodByPath(path1);
//        HandlerMethod handlerMethod4 = mappingRegistry.getHandlerMethodByPath(path4);
//
//        Assert.assertNull(handlerMethod4);
//        Assert.assertNotNull(handlerMethod);
//        Assert.assertNotNull(handlerMethod2);
//
//
//        RequestMappingInfo mapping = mappingRegistry.getMappingByPath(path);
//        RequestMappingInfo mapping2 = mappingRegistry.getMappingByPath(path1);
//
//        Assert.assertNotNull(mapping);
//        Assert.assertNotNull(mapping2);
//        Assert.assertEquals(mapping.getHttpMethod(), RequestMethod.GET);
//        Assert.assertEquals(mapping2.getHttpMethod(), RequestMethod.POST);
//    }

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

//    @Test
//    public void test1() throws NoSuchMethodException {
//        TestController testController = new TestController();
//        Method method = testController.getClass().getMethod("test4",
//                String.class, Integer.class, Date.class, HttpServletRequest.class);
//
//        //构建HandlerMethod对象
//        HandlerMethod handlerMethod = new HandlerMethod(testController, method);
//
//        //构建模拟请求的request
//        MockHttpServletRequest request = new MockHttpServletRequest();
//        request.setParameter("name", "Silently9527");
//        request.setParameter("age", "25");
//        request.setParameter("birthday", "2020-11-12 13:00:00");
//
//        //添加支持的解析器
//        HandlerMethodArgumentResolverComposite resolverComposite = new HandlerMethodArgumentResolverComposite();
//        resolverComposite.addResolver(new RequestParamMethodArgumentResolver());
//        resolverComposite.addResolver(new ServletRequestMethodArgumentResolver());
//
//        //定义转换器
//        DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService();
//        DateFormatter dateFormatter = new DateFormatter();
//        dateFormatter.setPattern("yyyy-MM-dd HH:mm:ss");
//        conversionService.addFormatter(dateFormatter);
//
//        MockHttpServletResponse response = new MockHttpServletResponse();
//
//        //用于查找方法参数名
//        DefaultParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();
//        handlerMethod.getParameters().forEach(methodParameter -> {
//            try {
//                methodParameter.initParameterNameDiscovery(parameterNameDiscoverer);
//
//                Object value = resolverComposite.resolveArgument(methodParameter, request,response, null, conversionService);
//                System.out.println(methodParameter.getParameterName() + " : " + value + "   type: " + value.getClass());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        });
//    }

//    @Test
//    public void test2() throws NoSuchMethodException {
//        TestController testController = new TestController();
//        Method method = testController.getClass().getMethod("user", UserVo.class);
//
//        HandlerMethod handlerMethod = new HandlerMethod(testController, method);
//
//        MockHttpServletRequest request = new MockHttpServletRequest();
//        UserVo userVo = new UserVo();
//        userVo.setName("Silently9527");
//        userVo.setAge(25);
//        userVo.setBirthday(new Date());
//        request.setContent(JSON.toJSONString(userVo).getBytes()); //模拟JSON参数
//
//        HandlerMethodArgumentResolverComposite resolverComposite = new HandlerMethodArgumentResolverComposite();
//        resolverComposite.addResolver(new RequestBodyMethodArgumentResolver());
//
//        MockHttpServletResponse response = new MockHttpServletResponse();
//
//        DefaultParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();
//        handlerMethod.getParameters().forEach(methodParameter -> {
//            try {
//                methodParameter.initParameterNameDiscovery(parameterNameDiscoverer);
//                Object value = resolverComposite.resolveArgument(methodParameter, request, response, null, null);
//                System.out.println(methodParameter.getParameterName() + " : " + value + "   type: " + value.getClass());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        });
//    }

//    @Test
//    public void test() throws Exception {
//        HandlerMethodReturnValueHandlerComposite composite = new HandlerMethodReturnValueHandlerComposite();
//        composite.addReturnValueHandler(new ModelMethodReturnValueHandler());
//        composite.addReturnValueHandler(new MapMethodReturnValueHandler());
//        composite.addReturnValueHandler(new ResponseBodyMethodReturnValueHandler());
//        composite.addReturnValueHandler(new ViewMethodReturnValueHandler());
//        composite.addReturnValueHandler(new ViewNameMethodReturnValueHandler());
//
//        ModelAndViewContainer mvContainer = new ModelAndViewContainer();
//        TestReturnValueController controller = new TestReturnValueController();
//
//        //测试方法testViewName
//        Method viewNameMethod = controller.getClass().getMethod("testViewName");
//        MethodParameter viewNameMethodParameter = new MethodParameter(viewNameMethod, -1); //取得返回值的MethodParameter
//        composite.handleReturnValue(controller.testViewName(), viewNameMethodParameter, mvContainer, null, null);
//        Assert.assertEquals(mvContainer.getViewName(), "/jsp/index.jsp");
//
//        //测试方法testView
//        Method viewMethod = controller.getClass().getMethod("testView");
//        MethodParameter viewMethodParameter = new MethodParameter(viewMethod, -1);
//        composite.handleReturnValue(controller.testView(), viewMethodParameter, mvContainer, null, null);
//        Assert.assertTrue(mvContainer.getView() instanceof View);
//
//        //测试方法testResponseBody
//        Method responseBodyMethod = controller.getClass().getMethod("testResponseBody");
//        MethodParameter resBodyMethodParameter = new MethodParameter(responseBodyMethod, -1);
//        MockHttpServletResponse response = new MockHttpServletResponse();
//        composite.handleReturnValue(controller.testResponseBody(), resBodyMethodParameter, mvContainer, null, response);
//        System.out.println(response.getContentAsString()); //打印出Controller中返回的JSON字符串
//
//        //测试方法testModel
//        Method modelMethod = controller.getClass().getMethod("testModel", Model.class);
//        MethodParameter modelMethodParameter = new MethodParameter(modelMethod, -1);
//        composite.handleReturnValue(controller.testModel(mvContainer.getModel()), modelMethodParameter, mvContainer, null, null);
//        Assert.assertEquals(mvContainer.getModel().getAttribute("testModel"), "Silently9527");
//
//        //测试方法testMap
//        Method mapMethod = controller.getClass().getMethod("testMap");
//        MethodParameter mapMethodParameter = new MethodParameter(mapMethod, -1);
//        composite.handleReturnValue(controller.testMap(), mapMethodParameter, mvContainer, null, null);
//        Assert.assertEquals(mvContainer.getModel().getAttribute("testMap"), "Silently9527");
//    }

    @Test
    public void test1() throws Exception {
        TestInvocableHandlerMethodController controller = new TestInvocableHandlerMethodController();

        Method method = controller.getClass().getMethod("testRequestAndResponse",
                HttpServletRequest.class, HttpServletResponse.class);

        //初始化handlerMethod、HandlerMethodArgumentResolverComposite
        HandlerMethod handlerMethod = new HandlerMethod(controller, method);
        HandlerMethodArgumentResolverComposite argumentResolver = new HandlerMethodArgumentResolverComposite();
        argumentResolver.addResolver(new ServletRequestMethodArgumentResolver());
        argumentResolver.addResolver(new ServletResponseMethodArgumentResolver());

        //本测试用例中使用不到返回值处理器和转换器，所以传入null
        InvocableHandlerMethod inMethod = new InvocableHandlerMethod(handlerMethod, argumentResolver, null, null);

        ModelAndViewContainer mvContainer = new ModelAndViewContainer();

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setParameter("name", "Silently9527"); //设置参数name

        MockHttpServletResponse response = new MockHttpServletResponse();
        response.setCommitted(true);
        //开始调用控制器的方法testRequestAndResponse
        inMethod.invokeAndHandle(request, response, mvContainer);

        System.out.println("输出到前端的内容:");
        System.out.println(response.getContentAsString());
    }

    @Test
    public void test2() throws Exception {
        TestInvocableHandlerMethodController controller = new TestInvocableHandlerMethodController();
        Method method = controller.getClass().getMethod("testViewName", Model.class);

        //初始化handlerMethod、HandlerMethodArgumentResolverComposite
        HandlerMethod handlerMethod = new HandlerMethod(controller, method);
        HandlerMethodArgumentResolverComposite argumentResolver = new HandlerMethodArgumentResolverComposite();
        argumentResolver.addResolver(new ModelMethodArgumentResolver());

        //由于testViewName的方法有返回值，所以需要设置返回值处理器
        HandlerMethodReturnValueHandlerComposite returnValueHandler = new HandlerMethodReturnValueHandlerComposite();
        returnValueHandler.addReturnValueHandler(new ViewNameMethodReturnValueHandler());

        InvocableHandlerMethod inMethod = new InvocableHandlerMethod(handlerMethod, argumentResolver, returnValueHandler, null);

        ModelAndViewContainer mvContainer = new ModelAndViewContainer();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        //执行调用
        inMethod.invokeAndHandle(request, response, mvContainer);

        System.out.println("ModelAndViewContainer:");
        System.out.println(JSON.toJSONString(mvContainer.getModel()));
        System.out.println("viewName: " + mvContainer.getViewName());
    }

    @Test
    public void handle() throws Exception {
        TestInvocableHandlerMethodController controller = new TestInvocableHandlerMethodController();

        Method method = controller.getClass().getMethod("testViewName", Model.class);
        HandlerMethod handlerMethod = new HandlerMethod(controller, method);

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService();
        DateFormatter dateFormatter = new DateFormatter();
        dateFormatter.setPattern("yyyy-MM-dd HH:mm:ss");
        conversionService.addFormatter(dateFormatter);

        RequestMappingHandlerAdapter handlerAdapter = new RequestMappingHandlerAdapter();
        handlerAdapter.setConversionService(conversionService);
        handlerAdapter.afterPropertiesSet();

        ModelAndView modelAndView = handlerAdapter.handle(request, response, handlerMethod);

        System.out.println("modelAndView:");
        System.out.println(JSON.toJSONString(modelAndView));
    }

    @Test
    public void test() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setContextPath("/path");

        MockHttpServletResponse response = new MockHttpServletResponse();

        Map<String, Object> model = new HashMap<>();
        model.put("name", "silently9527");
        model.put("url", "http://silently9527.cn");

        RedirectView redirectView = new RedirectView("/redirect/login");
        redirectView.render(model, request, response);

        response.getHeaderNames().forEach(headerName ->
                System.out.println(headerName + ":" + response.getHeader(headerName)));
    }
}
