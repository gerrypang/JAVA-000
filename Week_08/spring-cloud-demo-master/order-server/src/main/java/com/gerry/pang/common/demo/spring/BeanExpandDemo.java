package com.gerry.pang.common.demo.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

//@Component
public class BeanExpandDemo implements BeanPostProcessor, BeanFactoryPostProcessor, InstantiationAwareBeanPostProcessor,InitializingBean,DisposableBean {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BeanExpandDemo() {
        System.out.println("==== 实例 BeanExpandDemo =====");

    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        System.out.println("==== BeanFactoryPostProcessor.postProcessBeanFactory =====");
    }

    @Nullable
    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        System.out.println("==== BeanFactoryPostProcessor.postProcessBeforeInstantiation 实例化之前 =====");
        return null;
    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        System.out.println("==== BeanFactoryPostProcessor.postProcessAfterInstantiation 实例化之后 =====");
        return false;
    }

    @Nullable
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("==== BeanPostProcessor.postProcessBeforeInitialization 初始化之前 =====");
        return null;
    }

    @Nullable
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("==== BeanPostProcessor.postProcessAfterInitialization 初始化之后 =====");
        return null;
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("==== DisposableBean.destroy =====");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("==== InitializingBean.afterPropertiesSet  =====");
    }
}
