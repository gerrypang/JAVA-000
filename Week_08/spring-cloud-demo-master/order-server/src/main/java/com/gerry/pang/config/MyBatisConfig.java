package com.gerry.pang.config;

import org.springframework.context.annotation.Configuration;
import tk.mybatis.spring.annotation.MapperScan;

@Configuration
@MapperScan("com.gerry.pang.order.mapper")
public class MyBatisConfig {

}
