package com.gerry.pang.common.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ConfigurationProperties(prefix="spring.test.student")
public class StudentProperties {
	
		private Boolean enable;
	
		private String id;

		private String name;
		
		private Integer age;
		
		private String gender;

		private String address;
		
}
