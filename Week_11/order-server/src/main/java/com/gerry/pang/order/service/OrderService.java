package com.gerry.pang.order.service;

import java.util.List;

import com.gerry.pang.order.dto.TbOrderDTO;
import com.gerry.pang.order.dto.TbOrderProductRelationDTO;
import com.gerry.pang.order.entity.TbOrder;

public interface OrderService {
	
	public Boolean payOrder(TbOrderProductRelationDTO orderProductRelationDTO);

	public String createOrder(TbOrderDTO orderDTO);

	public List<TbOrder> getOrderList();
}
