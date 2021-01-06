package io.kimmking.dubbo.demo.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("io.kimmking.dubbo.demo.provider.mapper")
public class MyBatisConfig {

}
