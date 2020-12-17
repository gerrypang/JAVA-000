package com.gerry.pang.order.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gerry.pang.order.dto.TbOrderDTO;
import com.gerry.pang.order.service.OrderService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/store")
public class StoreController {
	
	@Autowired
	private OrderService orderService;

	@GetMapping("/getOrderList")
	public List<TbOrderDTO> getOrderList() {
		log.info("===> get store sccuess ");
		List<TbOrderDTO> orderList = new ArrayList<>();
		orderList.add(new TbOrderDTO());
		orderList.add(new TbOrderDTO());
		orderList.add(new TbOrderDTO());
		return orderList;
	}
	
	@GetMapping("/{id}")
	public String getOrder(@PathVariable("id") String id) {
		log.info("===> get store sccuess ");
		return "get order " + id;
	}
	
	@PostMapping("/createOrder")
	public String createOrder(@RequestBody TbOrderDTO orderDTO) {
		log.info("===== 开始创建订单 ======");
		return orderService.createOrder(orderDTO);
	}
	

}
