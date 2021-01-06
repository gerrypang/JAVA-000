package com.gerry.pang.order.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gerry.pang.order.dto.TbOrderDTO;
import com.gerry.pang.order.dto.TbOrderProductRelationDTO;
import com.gerry.pang.order.entity.TbOrder;
import com.gerry.pang.order.service.OrderService;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/store")
public class StoreController {
	
	@Autowired
	private OrderService orderService;

	@GetMapping("/getOrderList")
	@ApiOperation("查询全量订单列表")
	public List<TbOrder> getOrderList() {
		log.info("===> get store sccuess ");
		return orderService.getOrderList();
	}
	
	@PostMapping("/createOrder")
	@ApiOperation("基于分布式式锁创建订单")
	public String createOrder(@RequestBody TbOrderDTO orderDTO) {
		Assert.hasLength(orderDTO.getSequence(), "订单序号为空");
		log.info("===== 开始创建订单 ======");
		return orderService.createOrder(orderDTO);
	}
	
	@GetMapping("/payOrder")
	@ApiOperation("查询全量订单列表")
	public Long payOrder(TbOrderProductRelationDTO orderProductRelationDTO) {
		log.info("===> get store sccuess ");
		return orderService.payOrder(orderProductRelationDTO);
	}
}
