package com.gerry.pang.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student {
	
	private Integer id;

	private String name;
	
	private Integer age;
	
	private String gender;

	private String address;
	
	private School school;
	
	private Klass klass;
}
