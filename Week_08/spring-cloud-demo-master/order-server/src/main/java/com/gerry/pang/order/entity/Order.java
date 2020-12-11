package com.gerry.pang.order.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {
	
	private String id;
	
	private BigDecimal payment;

	private String paymentType;
	
	private BigDecimal postFee;
	
	private String userId;
	
	private BigDecimal money;
	
	private LocalDateTime createTime;
	private LocalDateTime updateTime;
	private LocalDateTime paymentTime;
	private LocalDateTime consignTime;
	private LocalDateTime closeTime;
	private String shoppingName;
}
