package io.kimmking.dubbo.demo.api.service;

import io.kimmking.dubbo.demo.api.dto.User;

public interface UserService {

    User findById(int id);
    
}
