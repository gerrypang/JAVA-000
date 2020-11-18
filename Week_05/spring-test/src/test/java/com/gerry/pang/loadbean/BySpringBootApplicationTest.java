package com.gerry.pang.loadbean;

import javax.annotation.Resource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.gerry.pang.SpringTestApplication;
import com.gerry.pang.utils.SpringContextUtil;

@SpringBootApplication
public class BySpringBootApplicationTest {
	// 获取ApplicationContext
	private static ApplicationContext appContext;
	
	@Resource
	private ApplicationContext ctx;
		
	public static void main(String[] args) {
		// 会返回一个 ConfigurableApplicationContext
		appContext = SpringApplication.run(SpringTestApplication.class, args);
		/**
		 * 通过@SpringBootApplication，基于spring boot启动创建容器
		 */
		ApplicationContext applicationContext = SpringContextUtil.getApplicationContext();
		
		// 方法1：通过SpringContextUtil类实现ApplicationContextAware接口，自动注入applicationContext
		System.out.println(applicationContext.getBean("dougLea"));
		
		// 方法2：SpringApplication.run() 返回appContext
		System.out.println(appContext.getBean("ghost"));
		
		// TRUE 
		System.out.println(applicationContext.equals(appContext));

	}
	
	public void getPerson() {
		// 方法3：直接注入
		System.out.println(ctx.getBean("ghost"));
	}

}
