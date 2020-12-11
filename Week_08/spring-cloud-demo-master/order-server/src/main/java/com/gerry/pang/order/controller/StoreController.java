package com.gerry.pang.order.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gerry.pang.fegin.UserFeign;
import com.gerry.pang.fegin.dto.User;
import com.gerry.pang.order.entity.Order;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/store")
public class StoreController {
	
	@Autowired
	private UserFeign userFeign;
	
	private final static Random random = new Random();

	@GetMapping
	public List<Order> getStoreList() {
		log.info("===> get store sccuess ");
		List<Order> orderList = new ArrayList<>();
		orderList.add(new Order());
		orderList.add(new Order());
		orderList.add(new Order());
		return orderList;
	}
	
	@GetMapping("/{id}")
	public String getStore(@PathVariable("id") String id) {
		log.info("===> get store sccuess ");
		return "get store " + id;
	}
	
	@GetMapping("/userStore/{id}")
	public User getUserStore(@PathVariable("id") String id) {
		User user = userFeign.getUser(id);
		log.info("===> get user store sccuess ");
		return user;
	}
	
	/**
	 * 当{@link #helloWorld} 方法调⽤用超时或失败时， 
	 * fallback 方法{@link #errorContent()}作为替代返回
	 *
	 * @return
	 * @throws InterruptedException
	 */
	@GetMapping("/hello")
	@HystrixCommand(fallbackMethod = "errorContent", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "100") 
		}
	)
	public String hello() throws InterruptedException {
		log.info("===> test Hystrix hello world ");
		// 如果随机时间大于 100 ，那么触发容错
		int value = random.nextInt(200);
		log.info("===> helloWorld() costs {} ms.", value);
		Thread.sleep(value);
		return "Hello,World";
	}

	public String errorContent() {
		return "Fault-timeout";
	}
}
