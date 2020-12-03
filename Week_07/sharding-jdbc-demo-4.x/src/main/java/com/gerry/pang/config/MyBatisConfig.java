package com.gerry.pang.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = "com.gerry.pang.mapper")
public class MyBatisConfig {

}
