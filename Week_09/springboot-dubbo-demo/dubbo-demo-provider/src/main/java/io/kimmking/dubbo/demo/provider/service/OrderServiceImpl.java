package io.kimmking.dubbo.demo.provider.service;

import io.kimmking.dubbo.demo.api.dto.Order;
import io.kimmking.dubbo.demo.api.service.OrderService;

import java.util.Random;

import org.apache.dubbo.config.annotation.DubboService;
import org.dromara.hmily.annotation.HmilyTCC;

@DubboService(version = "1.0.0")
public class OrderServiceImpl implements OrderService {

	@Override
	public Order findOrderById(int id) {
		return new Order(id, "Cuijing" + System.currentTimeMillis(), 9.9f);
	}

	@HmilyTCC(confirmMethod = "payOrderConfrim", cancelMethod = "payOrderCancel")
	@Override
	public void payOrder(int id) {
		System.out.println(">>>>> payOrder ======" + id);
		Random random = new Random();
		if (random.nextInt(5) % 2 == 0) {
			System.out.println("===== payOrder success ======");
		} else {
			System.out.println("===== payOrder error ======");
			int a =	0 / 1 ;
		}
		System.out.println("<<<<< payOrder ======" + id);
	}

	public void payOrderConfrim(int id) {
		System.out.println("===== pay Order Confrim =====" + id);
	}

	public void payOrderCancel(int id) {
		System.out.println("===== pay Order Cancel =====" + id);
	}
}
