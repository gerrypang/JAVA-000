package com.gerry.pang.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.gerry.pang.entity.User;

@Mapper
public interface UserMapper extends CommonMapper<User, Integer> {
	
}
