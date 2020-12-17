package com.gerry.pang.fegin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
	
	private String id;
	
	private String username;
	
	private String password;
	
	private String sex;

}
