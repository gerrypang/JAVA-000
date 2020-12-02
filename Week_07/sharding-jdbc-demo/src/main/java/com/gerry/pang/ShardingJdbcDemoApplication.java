package com.gerry.pang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
@ComponentScan("com.gerry.pang")
public class ShardingJdbcDemoApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(ShardingJdbcDemoApplication.class, args);
		log.info("=====  start success =====");
	}
}
