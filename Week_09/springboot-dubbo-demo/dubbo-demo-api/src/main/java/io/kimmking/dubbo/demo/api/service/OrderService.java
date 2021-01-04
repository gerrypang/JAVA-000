package io.kimmking.dubbo.demo.api.service;

import org.dromara.hmily.annotation.Hmily;

import io.kimmking.dubbo.demo.api.dto.Order;

public interface OrderService {

    Order findOrderById(int id);

    @Hmily
	void payOrder(int id);
}
