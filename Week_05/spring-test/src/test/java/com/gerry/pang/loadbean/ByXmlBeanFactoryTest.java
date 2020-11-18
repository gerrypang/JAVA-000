package com.gerry.pang.loadbean;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

import com.gerry.pang.domain.Person;

@SuppressWarnings("deprecation")
public class ByXmlBeanFactoryTest {
	
	@Test
	public void testLoadBean() {
		/**
		 * 通过最原始的XmlBeanFactory方式，此种方式官方已不推荐
		 * 内部是会创建BeanFactory容器，然后通过getBean方法获取bean
		 */
		BeanFactory beanFactory = new XmlBeanFactory(new ClassPathResource("beanFactoryDemo.xml"));
		Person gerry = (Person) beanFactory.getBean("gerry");
		Person tom = (Person) beanFactory.getBean("tom");
		
		assertEquals("gerry", gerry.getName());
		assertEquals("tom", tom.getName());
	}

}
