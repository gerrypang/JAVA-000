package com.gerry.pang.loadbean;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;

import com.gerry.pang.domain.Car;
import com.gerry.pang.domain.Person;
import com.gerry.pang.factory.CarFactoryBean;

public class ByDefaultListableBeanFactoryTest {
	
	@Test
	public void testLoadBean() {
		/**
		 * DefaultListableBeanFactory 是整个 bean加载的核心部分，是 Spring 注册及加载 bean 的默认实现
		 * 
		 * 通过注册BeanDefinition方式，注册一个自定义的FactoryBean
		 */
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
		// 向beanFactory中注册一个新BeanDefinition
		beanFactory.registerBeanDefinition("carBean", new RootBeanDefinition(CarFactoryBean.class));
		beanFactory.registerBeanDefinition("gerry", new RootBeanDefinition(Person.class));
		
		// 获取bean
		Car x5 = (Car) beanFactory.getBean("carBean");
		// 通过&方式获取BeanFacory bean
		CarFactoryBean carFactory = (CarFactoryBean) beanFactory.getBean("&carBean");
		
		assertEquals("X5", x5.getName());
		assertEquals(CarFactoryBean.class, carFactory.getClass());
	}

}
