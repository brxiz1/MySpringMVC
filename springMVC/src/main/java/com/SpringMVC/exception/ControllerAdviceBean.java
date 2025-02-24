package com.SpringMVC.exception;

/**
 * @author ZhangYihe
 * @since 2025/2/21
 **/

import com.SpringMVC.annotation.ControllerAdvice;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotatedElementUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 表示被@ControllerAdvice标注的bean
 */
public class ControllerAdviceBean {

    private Object bean;

    private String beanName;

    private Class<?> beanType;

    public ControllerAdviceBean(String beanName,Object bean){
        this.bean=bean;
        this.beanName=beanName;
        beanType=bean.getClass();
    }

    /**
     * 从Spring上下文中获取所有标注了@ControllerAdvice的Bean
     * @param context
     * @return
     */
    public static List<ControllerAdviceBean> findAnnotatedBeans(ApplicationContext context){
        Map<String,Object> beanMap= BeanFactoryUtils.beansOfTypeIncludingAncestors(context,Object.class);
        List<ControllerAdviceBean> beanList=new ArrayList<>();
        for(String beanName:beanMap.keySet()) {
            Object bean = beanMap.get(beanName);
            if (hasControllerAdvice(bean)) {
                beanList.add(new ControllerAdviceBean(beanName, bean));
            }
        }
        return beanList;
    }

    /**
     * 检查一个bean的类是否有标注@ControllerAdvice
     * @param bean
     * @return
     */
    private static boolean hasControllerAdvice(Object bean){
        Class<?> type=bean.getClass();
        return AnnotatedElementUtils.hasAnnotation(type,ControllerAdvice.class);
    }

    public Object getBean() {
        return bean;
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public Class<?> getBeanType() {
        return beanType;
    }

    public void setBeanType(Class<?> beanType) {
        this.beanType = beanType;
    }
}
