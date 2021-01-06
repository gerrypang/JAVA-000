package com.gerry.pang.order.service.impl;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;

import com.alibaba.fastjson.JSON;
import com.gerry.pang.order.dto.TbOrderDTO;
import com.gerry.pang.order.dto.TbOrderProductRelationDTO;
import com.gerry.pang.order.entity.TbOrder;
import com.gerry.pang.order.enums.OrderStatusEnum;
import com.gerry.pang.order.mapper.TbOrderMapper;
import com.gerry.pang.order.service.OrderService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private TbOrderMapper orderMapper;

	/** 分布式锁key */
	public final static String REDISSION_LOCK_NAME = "order:%s";

	@Autowired
	private RedissonClient redissonClient;

	@Autowired
	private PlatformTransactionManager transactionManager;
	@Autowired
	private TransactionDefinition transactionDefinition;

	/**
	 * 使用 redisson 中的 RLock 作为分布式锁 通过手工控制事务缩短事务范围，提升锁性能
	 */
	@Override
	public String createOrder(TbOrderDTO orderDTO) {
		log.info(">>>>> createOrder start, orderDTO:{}", JSON.toJSONString(orderDTO));
		Integer orderId = null;
		// redission 锁
		final String lockKey = String.format(REDISSION_LOCK_NAME, orderDTO.getSequence());
		RLock lock = redissonClient.getLock(lockKey);

		TransactionStatus transactionStatus = null;
		try {
			// 500s拿不到锁, 就认为获取锁失败。30000ms即30s是锁失效时间。
			boolean isLock = lock.tryLock(500, 30000, TimeUnit.MILLISECONDS);
			log.info("===> 获取分布式锁，redlock key:{} isLock:{}", lockKey, isLock);

			if (!isLock) {
				throw new IllegalArgumentException("订单已存在，不可重复录入");
			}

			// 手动设置事物隔离级别，开启新事务
			transactionStatus = transactionManager.getTransaction(transactionDefinition);

			log.info("==> setp.1 create order");
			TbOrder order = new TbOrder();
			order.setOrderStatus(OrderStatusEnum.CREATE_ORDER.getCode());
			order.setUserId(orderDTO.getUserId());
			order.setIsDeleted(false);
			order.setVersion(0);
			order.setUpdateTime(new Date());
			order.setCreateTime(new Date());
			orderId = orderMapper.insert(order);

			// 手动提交
			transactionManager.commit(transactionStatus);
		} catch (IllegalArgumentException e) {
			log.error("订单保存错误", e);
			throw e;
		} catch (Exception e) {
			log.error("订单保存异常", e);
			// 手动回滚
			transactionManager.rollback(transactionStatus);
			throw new IllegalArgumentException(e);
		} finally {
			log.info("==== 分布式锁 unlock:{} =====", lock.getName());
			if (lock.isLocked() && lock.isHeldByCurrentThread()) {
				lock.unlock();
			}
		}

		log.info("<<<<< createOrder end");
		return orderId + "";
	}

	@Override
	public List<TbOrder> getOrderList() {
		return orderMapper.selectAll();
	}

	@Override
	public Boolean payOrder(TbOrderProductRelationDTO orderProductRelationDTO) {
		// 获取库存加在在 redis
		
		return null;
	}


}
