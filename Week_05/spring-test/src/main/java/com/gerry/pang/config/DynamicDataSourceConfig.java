package com.gerry.pang.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.gerry.pang.common.properties.DynamicDataSourceProperties;
import com.gerry.pang.utils.datasouce.DataSourceSwitcher;

@Configuration
@EnableConfigurationProperties(DynamicDataSourceProperties.class)
public class DynamicDataSourceConfig {
	
	@Autowired
	private DynamicDataSourceProperties dynamicDsProperties;
	
	
	@Bean
	public DataSourceSwitcher dataSourceSwitcher() {
		DataSourceSwitcher dataSourceSwitcher = new DataSourceSwitcher();
		Map<Object, Object> targetDataSources = new HashMap<>(16);
		dynamicDsProperties.getDatasource().forEach((k,v)->{
			DataSource  dataSource = DataSourceBuilder.create().url(v.getUrl()).username(v.getUsername()).password(v.getPassword()).driverClassName(v.getDriverClassName()).build();
			targetDataSources.put(k, dataSource);
		});
		dataSourceSwitcher.setTargetDataSources(targetDataSources);
		dataSourceSwitcher.setDefaultTargetDataSource(targetDataSources.get(dynamicDsProperties.getDefaultDataSource()));
		return dataSourceSwitcher;
	}
	
}
