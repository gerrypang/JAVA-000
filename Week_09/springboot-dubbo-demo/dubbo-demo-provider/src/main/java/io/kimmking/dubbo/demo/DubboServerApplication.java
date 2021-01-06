package io.kimmking.dubbo.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DubboServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DubboServerApplication.class, args);
		System.out.println("===== provicer server start success =====");
	}
	
//	@Bean
//	public BeanPostProcessor refererAnnotationBeanPostProcessor() {
//	    return new RefererAnnotationBeanPostProcessor();
//	}
}
