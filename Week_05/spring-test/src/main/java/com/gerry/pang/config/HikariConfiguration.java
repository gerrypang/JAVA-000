package com.gerry.pang.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HikariConfiguration {

	// spring boot 在stater中默认使用HikariCP连接池，只要配置文件上有配置，启动会自动注入数据源
	@Autowired
	public DataSource dataSource;
	
}
