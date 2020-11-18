package com.gerry.pang.loadbean;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.gerry.pang.config.AppConfig;
import com.gerry.pang.domain.Car;
import com.gerry.pang.domain.Person;

public class ByAnnotationConfigApplicationContextTest {
	
	@Test
	@SuppressWarnings("resource")
	public void testLoadBean() {
		/**
		 * 通过 AnnotationConfigApplicationContext 获取 bean
		 * 
		 * AnnotationConfigApplicationContext 是基于注解方式，在4.x以上开始的时候常用
		 * 在beanFactory容器基础的上的包装容器，提供了更丰富的内容例如国际化、event等内容
		 */
		AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext();
		// 注册 Configuration Class（配置类）
		appContext.register(AppConfig.class);
		// 启动 Spring 应用上下文
		appContext.refresh();
		
		// 通过@Bean方式创建bean
		Person chris = (Person) appContext.getBean("chris");
		Car ghost = (Car) appContext.getBean("ghost");
		
		assertEquals("chris", chris.getName());
		assertEquals("Ghost", ghost.getName());
		
		// 显示地关闭 Spring 应用上下文
		appContext.close();
	}

}
