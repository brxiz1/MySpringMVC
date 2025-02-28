package com.SpringMVC;

import com.SpringMVC.config.AppConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author ZhangYihe
 * @since 2025/2/15
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class BaseJunit4Test {
    @Test
    public void test()throws Exception{}
}
