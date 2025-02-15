package com.SpringMVC.handler.mapping;

import com.SpringMVC.handler.HandlerMethod;
import com.SpringMVC.http.RequestMethod;
import com.SpringMVC.BaseJunit4Test;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Map;

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
}
