package com.gerry.pang.order.service;

import com.gerry.pang.order.dto.TbOrderDTO;

public interface OrderService {
	
	public void payOrder();

	public String createOrder(TbOrderDTO orderDTO);
}
