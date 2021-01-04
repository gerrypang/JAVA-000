package io.kimmking.dubbo.demo.api.dto;

import lombok.Data;

@Data
public class UserPaymentDTO implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private String username;
	
	private Integer userId;
	
	private Integer orderId;

}
