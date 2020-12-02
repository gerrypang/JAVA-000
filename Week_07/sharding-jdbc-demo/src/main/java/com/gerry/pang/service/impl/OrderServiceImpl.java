package com.gerry.pang.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gerry.pang.entity.Order;
import com.gerry.pang.entity.OrderItem;
import com.gerry.pang.mapper.OrderItemMapper;
import com.gerry.pang.mapper.OrderMapper;
import com.gerry.pang.service.OrderService;

import io.shardingsphere.api.HintManager;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    
	@Autowired
    private OrderMapper orderMapper;
	@Autowired
    private OrderItemMapper orderItemMapper;
    
    
    @Override
    public void initEnvironment() {
    	orderMapper.createTableIfNotExists();
    	orderMapper.truncateTable();
    	orderItemMapper.createTableIfNotExists();
    	orderItemMapper.truncateTable();
    }
    
    @Override
    public void cleanEnvironment() {
    	orderMapper.dropTable();
    	orderItemMapper.dropTable();
    }
    
    @Override
    public void processSuccess() {
        log.info("-------------- Process Success Begin ---------------");
        List<Integer> userIds = insertData();
        printData();
//        List<Integer> delList = userIds.subList(0, userIds.size()/2);
//        deleteData(delList);
//        printData();
        log.info("-------------- Process Success Finish --------------");
    }
    
    private List<Integer> insertData() {
        log.info("---------------------------- Insert Data ----------------------------");
        List<Integer> result = new ArrayList<>(10);
        for (int i = 1; i <= 10; i++) {
            Order order = new Order();
            order.setUserId(i);
            order.setStatus("success");
            orderMapper.insert(order);
            
            
            OrderItem item = new OrderItem();
            item.setOrderId(order.getOrderId());
            item.setUserId(i);
            item.setStatus("success");
            orderItemMapper.insert(item);
            result.add(order.getUserId());
        }
        return result;
    }
    
    @Override
    public void processFailure() {
        log.info("-------------- Process Failure Begin ---------------");
        insertData();
        log.info("-------------- Process Failure Finish --------------");
        throw new RuntimeException("Exception occur for transaction test.");
    }
    
    private void deleteData(final List<Long> userIds) {
        log.info("---------------------------- Delete Data ----------------------------");
        for (Long each : userIds) {
        	orderMapper.delete(each);
        }
    }
    
    @Override
    public void printData() {
		/*
		 * 强制路由主库<br/>
		 * 功能： 多用于处理那些读写分离时，主库写入数据库后，从库延时获取数据的问题，解决方法是直接强制路由从主库查询<br/>
		 * 作用域：只是下面的最近一次查询 <br/>
		 * 参考：https://shardingsphere.apache.org/document/current/cn/manual/sharding-jdbc/usage/hint/
		 */
    	HintManager.getInstance().setMasterRouteOnly();
        log.info("---------------------------- Print Order Data -----------------------");
        for (Object each : orderMapper.selectAll()) {
            log.info("==> Order:{}", each);
        }
        
        // 下面还是走从库，没有强制路由
        log.info("---------------------------- Print OrderItem Data -----------------------");
        for (Object each : orderMapper.selectAllOfItem()) {
            log.info("==> OrderItem:{}", each);
        }
    }
}
