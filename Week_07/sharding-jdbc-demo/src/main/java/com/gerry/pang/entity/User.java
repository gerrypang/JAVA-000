package com.gerry.pang.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer userId;
	
	private String userName;
	
	private String pwd;
	
	private String assistedQueryPwd;

}
