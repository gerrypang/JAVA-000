package com.gerry.pang.fegin;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.gerry.pang.fegin.dto.User;

@FeignClient(name = "product-server", fallbackFactory = FeginClientFallbackFactory.class)
public interface UserFeign {

	@GetMapping("/user/get/{id}")
	public User getUser(@PathVariable("id") String id);
}
