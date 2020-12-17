package com.gerry.pang.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.gerry.pang.product.dto.TbProductDTO;
import com.gerry.pang.product.service.ProductService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/demo")
public class ProductController {
	
	@Autowired
	public ProductService productService;
	
	@PostMapping("/orderProduct")
	public void orderProduct(@RequestBody TbProductDTO product) {
		log.info(">>>>> orderProduct product:{}", JSONObject.toJSONString(product));
		productService.saveProduct(product);
	}
}
