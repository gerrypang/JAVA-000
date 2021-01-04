package io.kimmking.dubbo.demo.consumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.kimmking.dubbo.demo.api.dto.UserPaymentDTO;
import io.kimmking.dubbo.demo.api.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/userPayOrder")
	public void userPayOrder(@RequestBody UserPaymentDTO userPaymentDTO) {
		userService.userPayOrder(userPaymentDTO);
	}
}
