package com.gerry.pang.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.gerry.pang.order.mapper")
public class MyBatisConfig {

}
