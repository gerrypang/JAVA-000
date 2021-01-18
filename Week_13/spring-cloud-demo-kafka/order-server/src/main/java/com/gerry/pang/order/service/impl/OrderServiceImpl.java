package com.gerry.pang.order.service.impl;

import java.util.Date;

import org.dromara.hmily.annotation.HmilyTCC;
import org.dromara.hmily.common.exception.HmilyRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gerry.pang.fegin.ProductFeign;
import com.gerry.pang.fegin.dto.TbProductDTO;
import com.gerry.pang.order.dto.TbOrderDTO;
import com.gerry.pang.order.entity.TbOrder;
import com.gerry.pang.order.entity.TbOrderProductRelation;
import com.gerry.pang.order.enums.OrderStatusEnum;
import com.gerry.pang.order.mapper.TbOrderMapper;
import com.gerry.pang.order.mapper.TbOrderProductRelationMapper;
import com.gerry.pang.order.service.OrderService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	private ProductFeign productFeign;
	
	@Autowired
	private TbOrderMapper orderMapper;
	
	@Autowired
	private TbOrderProductRelationMapper orderProductRelationMapper;

	@Override
	public void payOrder() {

	}
	
	@Override
	public String createOrder(TbOrderDTO orderDTO) {
		log.info("==> setp.1 create order");
		TbOrder order = new TbOrder();
		order.setOrderStatus(OrderStatusEnum.CREATE_ORDER.getCode());
		order.setUserId(orderDTO.getUserId());
		order.setIsDeleted(false);
		order.setVersion(0);
		order.setUpdateTime(new Date());
		order.setCreateTime(new Date());
		int orderId = orderMapper.insert(order);
		
		log.info("==> setp.3 productFeign orderProduct");
		this.orderInProduct(order, orderDTO);
		
		return orderId + "";
	}
	
	@HmilyTCC(confirmMethod = "confirmOrderInProduct", cancelMethod = "cancelOrderInProduct")
	public void orderInProduct(TbOrder order, TbOrderDTO orderDTO) {
		log.info("==> setp.2 create orderProductRelation");
		TbOrderProductRelation orderProductRelation = new TbOrderProductRelation();
		orderProductRelation.setIsDeleted(false);
		orderProductRelation.setVersion(0);
		orderProductRelation.setUpdateTime(new Date());
		orderProductRelation.setCreateTime(new Date());
		orderProductRelation.setProductId(orderDTO.getProductId());
		orderProductRelation.setNum(orderDTO.getNum());
		orderProductRelation.setOrderId(order.getId());
		orderProductRelationMapper.insert(orderProductRelation);
		
		TbProductDTO productDto = new TbProductDTO();
		productDto.setId(orderDTO.getProductId());
		productDto.setOrderNum(orderDTO.getNum());
		try {
			productFeign.orderProduct(productDto);
		} catch (Exception e) {
			throw new HmilyRuntimeException("库存扣减异常！");
		}
	}
	
	public void confirmOrderInProduct(TbOrder order, TbOrderDTO orderDTO) {
		log.info("==> setp.4 confirmOrderInProduct ");
		order.setVersion(order.getVersion() + 1);
		order.setUpdateTime(new Date());
		order.setOrderStatus(OrderStatusEnum.ORDER_SUCCESS.getCode());
		orderMapper.updateByPrimaryKey(order);
	}

	public void cancelOrderInProduct(TbOrder order, TbOrderDTO orderDTO) {
		log.info("==> setp.4 cancelOrderInProduct ");
		order.setVersion(order.getVersion() + 1);
		order.setUpdateTime(new Date());
		order.setOrderStatus(OrderStatusEnum.ORDER_FAIL.getCode());
		orderMapper.updateByPrimaryKey(order);
	}

}
