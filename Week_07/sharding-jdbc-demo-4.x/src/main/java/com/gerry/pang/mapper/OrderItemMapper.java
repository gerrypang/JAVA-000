package com.gerry.pang.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.gerry.pang.entity.OrderItem;

@Mapper
public interface OrderItemMapper extends CommonMapper<OrderItem, Long> {
	
}
