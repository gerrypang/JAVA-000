package com.gerry.pang.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.gerry.pang.common.properties.StudentProperties;
import com.gerry.pang.domain.Student;

@Configuration
@ConditionalOnBean(annotation = EnableMySpringTestConfiguration.class)
@EnableConfigurationProperties(StudentProperties.class)
public class MySpringTestAutoConfiguration {
	
	@Autowired
	private StudentProperties properties;
	
	@Bean
    @ConditionalOnMissingBean
//    @ConditionalOnProperty(prefix = "spring.test.student", value = "enabled", havingValue = "true", matchIfMissing = false)
	public Student demoStudent() {
		return Student.builder()
				.name(properties.getName())
				.age(properties.getAge())
				.gender(properties.getGender())
				.address(properties.getAddress())
				.build();
	}

}
