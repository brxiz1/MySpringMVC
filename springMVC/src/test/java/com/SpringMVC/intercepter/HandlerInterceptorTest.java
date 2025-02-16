package com.SpringMVC.intercepter;

import com.SpringMVC.controller.TestHandlerController;
import com.SpringMVC.handler.HandlerExecutionChain;
import com.SpringMVC.handler.HandlerMethod;
import com.SpringMVC.handler.exception.NoHandlerFoundException;
import com.SpringMVC.handler.interceptor.InterceptorRegistry;
import com.SpringMVC.handler.interceptor.MappedInterceptor;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import java.util.List;

/**
 * @author ZhangYihe
 * @since 2025/2/16
 **/
public class HandlerInterceptorTest {
    private InterceptorRegistry interceptorRegistry = new InterceptorRegistry();

    @Test
    public void test() throws Exception {
        TestHandlerInterceptor interceptor = new TestHandlerInterceptor();

        interceptorRegistry.addInterceptor(interceptor)
                .addExcludePatterns("/ex_test")
                .addIncludePatterns("/in_test");

        List<MappedInterceptor> interceptors = interceptorRegistry.getMappedInterceptors();

        Assert.assertEquals(interceptors.size(), 1);

        MappedInterceptor mappedInterceptor = interceptors.get(0);

        Assert.assertTrue(mappedInterceptor.matches("/in_test"));
        Assert.assertFalse(mappedInterceptor.matches("/ex_test"));

        mappedInterceptor.preHandle(null, null, null);
        mappedInterceptor.postHandle(null, null, null, null);
        mappedInterceptor.afterCompletion(null, null, null, null);
    }


}
