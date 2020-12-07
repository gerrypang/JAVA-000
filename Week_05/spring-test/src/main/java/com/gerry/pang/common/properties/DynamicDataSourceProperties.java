package com.gerry.pang.common.properties;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * 多数据源配置
 * 
 * @author pangguowei
 * @since 2020年12月7日 上午10:58:51
 */
@Data
@ConfigurationProperties(prefix = "dynamic")
public class DynamicDataSourceProperties {

	private String defaultDataSource;
	
	private Map<String, DataSourceProperties> datasource = new LinkedHashMap<>();
	
}
