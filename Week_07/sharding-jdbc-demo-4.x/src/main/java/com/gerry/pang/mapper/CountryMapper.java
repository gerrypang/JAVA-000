package com.gerry.pang.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.gerry.pang.entity.Country;

@Mapper
public interface CountryMapper extends CommonMapper<Country, Integer> {
	
}
