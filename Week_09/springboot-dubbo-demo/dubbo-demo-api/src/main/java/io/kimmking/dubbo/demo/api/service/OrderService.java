package io.kimmking.dubbo.demo.api.service;

import io.kimmking.dubbo.demo.api.dto.Order;

public interface OrderService {

    Order findOrderById(int id);

	void payOrder(int id);
}
