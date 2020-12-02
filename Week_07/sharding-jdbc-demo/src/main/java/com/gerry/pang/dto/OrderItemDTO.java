package com.gerry.pang.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItemDTO {
	
	private Long orderId;
	
	private Long orderItemId;
	
	private Integer userId;
	
	private String status;

}
