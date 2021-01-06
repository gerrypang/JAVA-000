package io.kimmking.dubbo.demo.consumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.kimmking.dubbo.demo.api.dto.UserTransactionDTO;
import io.kimmking.dubbo.demo.consumer.service.UserAssetsService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserAssetsService userAssetsService;

	@PostMapping("/exchageTranscation")
	public Boolean exchageTranscation(@RequestBody UserTransactionDTO userTransactionDTO) {
		Boolean result = userAssetsService.exchageTranscation(userTransactionDTO);
		return result;
	}
	
}
