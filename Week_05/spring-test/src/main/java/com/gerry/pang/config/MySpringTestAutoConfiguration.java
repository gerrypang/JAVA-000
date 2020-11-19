package com.gerry.pang.config;

import org.apache.commons.lang3.StringUtils;
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
	
//	@Autowired
//	private StudentProperties properties;
	
	@Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "spring.test.student", value = "enabled", havingValue = "true", matchIfMissing = true)
	public Student demoStudent(StudentProperties properties) {
		Student student = Student.builder().build();
		if (StringUtils.isNotBlank(properties.getId())) {
			student.setId(properties.getId());
		}
		if (StringUtils.isNotBlank(properties.getName())) {
			student.setName(properties.getName());
		}
		if (properties.getAge() != null) {
			student.setAge(properties.getAge());
		}
		if (StringUtils.isNotBlank(properties.getGender())) {
			student.setGender(properties.getGender());
		}
		if (StringUtils.isNotBlank(properties.getAddress())) {
			student.setAddress(properties.getAddress());
		}
		return student;
	}

}
