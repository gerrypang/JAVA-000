package com.gerry.pang.fegin;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.gerry.pang.fegin.dto.TbProductDTO;

@FeignClient(name = "product-server" )
public interface ProductFeign {

	@PostMapping("/demo/orderProduct")
	public void orderProduct(@RequestBody TbProductDTO product);
	 
}
