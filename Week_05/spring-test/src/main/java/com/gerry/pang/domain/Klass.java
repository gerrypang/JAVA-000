package com.gerry.pang.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Klass {
	
	private Integer id;
	
	private String name;
	
	private String code;
}
