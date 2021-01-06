package io.kimmking.dubbo.demo.provider.service;

import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import io.kimmking.dubbo.demo.api.dto.User;
import io.kimmking.dubbo.demo.api.service.OrderService;
import io.kimmking.dubbo.demo.api.service.UserService;

@DubboService(version = "1.0.0")
public class UserServiceImpl implements UserService {

	@Autowired 
	private OrderService orderService;
	
    @Override
    public User findById(int id) {
        return new User(id, "KK" + System.currentTimeMillis());
    }
}
