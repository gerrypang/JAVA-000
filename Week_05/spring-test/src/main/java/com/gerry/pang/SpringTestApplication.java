package com.gerry.pang;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gerry.pang.common.properties.StudentProperties;
import com.gerry.pang.config.EnableMySpringTestConfiguration;
import com.gerry.pang.domain.Student;

/**
 * Springboot 启动类
 * 
 * @author pangguowei
 * @since 2020年11月18日 
 */
@RestController
@EnableMySpringTestConfiguration
@SpringBootApplication
@SuppressWarnings("unused")
public class SpringTestApplication {

	private static ApplicationContext applicationContext;
	
	@Autowired
	private StudentProperties configurationProperties;

	@Autowired
	private Student demoStudent;
	
	@GetMapping("/getDemoSudent")
	public Student getDemoSudent() {
		return demoStudent;
	}
	
	public static void main(String[] args) {
		applicationContext = SpringApplication.run(SpringTestApplication.class, args);
		System.out.println("======= spring boot start success ======= ");
	}

}
