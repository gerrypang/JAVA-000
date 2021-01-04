package io.kimmking.dubbo.demo.api.service;

import org.dromara.hmily.annotation.Hmily;

import io.kimmking.dubbo.demo.api.dto.User;
import io.kimmking.dubbo.demo.api.dto.UserPaymentDTO;

public interface UserService {

    User findById(int id);

    @Hmily
	public void userPayOrder(UserPaymentDTO userPaymentDTO);
}
