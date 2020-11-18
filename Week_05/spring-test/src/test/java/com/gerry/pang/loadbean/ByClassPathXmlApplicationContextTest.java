package com.gerry.pang.loadbean;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.gerry.pang.domain.Person;

public class ByClassPathXmlApplicationContextTest {
	
	@Test
	@SuppressWarnings("resource")
	public void testLoadBean() {
		/**
		 * 通过 ClassPathXmlApplicationContext 获取 bean
		 * 
		 * ClassPathXmlApplicationContext 是基于xml方式，在3.x的时候常用
		 * 在beanFactory容器基础的上的包装容器，提供了更丰富的内容例如国际化、event等内容
		 * 
		 * BeanFactory 和 ApplicationContext 的区别：
		 * - BeanFactory 是按需创建，即第一次获取Bean时才创建这个Bean
		 * - ApplicationContext 会一次性创建所有的Bean
		 */
		ApplicationContext appContext = new ClassPathXmlApplicationContext("beanFactoryDemo.xml");
		
		Person gerry = (Person) appContext.getBean("gerry");
		Person tom = (Person) appContext.getBean("tom");
		
		assertEquals("gerry", gerry.getName());
		assertEquals("tom", tom.getName());
		
	}

}
