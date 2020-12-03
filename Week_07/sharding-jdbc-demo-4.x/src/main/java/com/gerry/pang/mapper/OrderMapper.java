package com.gerry.pang.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gerry.pang.dto.OrderItemDTO;
import com.gerry.pang.entity.Order;

@Mapper
public interface OrderMapper extends CommonMapper<Order, Long> {
	
	public List<OrderItemDTO> selectAllOfItem();
}
