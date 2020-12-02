package com.gerry.pang.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gerry.pang.entity.User;
import com.gerry.pang.mapper.UserMapper;
import com.gerry.pang.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    
    @Resource
    private UserMapper userMapper;
    
    @Override
    public void initEnvironment() {
        userMapper.createTableIfNotExists();
        userMapper.truncateTable();
    }
    
    @Override
    public void cleanEnvironment() {
        userMapper.dropTable();
    }
    
    @Override
    public void processSuccess() {
        log.info("-------------- Process Success Begin ---------------");
        List<Integer> userIds = insertData();
        printData();
        List<Integer> delList = userIds.subList(0, userIds.size()/2);
        deleteData(delList);
        printData();
        log.info("-------------- Process Success Finish --------------");
    }
    
    private List<Integer> insertData() {
        log.info("---------------------------- Insert Data ----------------------------");
        List<Integer> result = new ArrayList<>(10);
        for (int i = 1; i <= 10; i++) {
            User user = new User();
            user.setUserId(i);
            user.setUserName("test_mybatis_" + i);
            user.setPwd("pwd_mybatis_" + i);
            userMapper.insert(user);
            result.add(user.getUserId());
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
    
    private void deleteData(final List<Integer> userIds) {
        log.info("---------------------------- Delete Data ----------------------------");
        for (Integer each : userIds) {
            userMapper.delete(each);
        }
    }
    
    @Override
    public void printData() {
        log.info("---------------------------- Print User Data -----------------------");
        for (Object each : userMapper.selectAll()) {
            log.info("==> USER:{}", each);
        }
    }
}
