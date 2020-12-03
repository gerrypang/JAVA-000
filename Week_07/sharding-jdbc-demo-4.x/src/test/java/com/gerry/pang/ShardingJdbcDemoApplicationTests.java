package com.gerry.pang;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.gerry.pang.service.OrderService;
import com.gerry.pang.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ShardingJdbcDemoApplicationTests {

	@Autowired
	private UserService userService;
	
	@Autowired
	private OrderService orderService;
	
	private void demo() {
		log.info("===== test user =====");
		userService.initEnvironment();
		userService.processSuccess();
		
		log.info("===== test order & orderItem =====");
		orderService.initEnvironment();
		orderService.processSuccess();
		while(true) {
			
		}
	}
	
	@Test
	public void contextLoads() {
		this.demo();
	}

}
