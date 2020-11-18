package com.gerry.pang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * Springboot 启动类
 * 
 * @author pangguowei
 * @since 2020年11月18日 
 */
@SpringBootApplication
@SuppressWarnings("unused")
public class SpringTestApplication {

	private static ApplicationContext applicationContext;
	
	public static void main(String[] args) {
		applicationContext = SpringApplication.run(SpringTestApplication.class, args);
		System.out.println("======= spring boot start success ======= ");
	}

}
