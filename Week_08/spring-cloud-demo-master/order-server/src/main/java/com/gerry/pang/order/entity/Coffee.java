package com.gerry.pang.order.entity;

import java.util.Date;

import org.joda.money.Money;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Coffee {
	
	private Long id;
	
	private String name;
	
	private Money price;
	
	private Date createTime;
	
	private Date updateTime;
}
