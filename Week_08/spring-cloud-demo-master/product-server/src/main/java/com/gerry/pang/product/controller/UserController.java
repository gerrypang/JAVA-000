package com.gerry.pang.product.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gerry.pang.product.entity.User;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
	
//	@HystrixCommand(fallbackMethod = "findByIdFallBack", commandProperties = {
//			@HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value="5000"),
//			@HystrixProperty(name="metrics.rollingPercentile.timeInMilliseconds", value="10000")
//	}, threadPoolProperties = {
//			@HystrixProperty(name="coreSize", value="1"),
//			@HystrixProperty(name="maxQueueSize", value="10")
//	})
	@GetMapping("/get/{id}")
	public User getUser(@PathVariable("id") String id) {
		User user = new User();
		user.setId(id);
		user.setUsername("demo");
		user.setPassword("123456");
		user.setSex("man");
//		try {
//			Thread.sleep(20000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		log.info("===> get user sccuess ");
		return user;
	}
	
	public User findByIdFallBack(String id) {
		User user = new User();
		user.setId(id);
		user.setUsername("defaultUser");
		return user;
	}
}
