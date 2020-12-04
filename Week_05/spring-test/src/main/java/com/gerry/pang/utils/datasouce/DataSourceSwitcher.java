package com.gerry.pang.utils.datasouce;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DataSourceSwitcher extends AbstractRoutingDataSource {

	private static final ThreadLocal<String> dataSourceKey = new ThreadLocal<String>();

	@Override
	protected Object determineCurrentLookupKey() {
		String key = dataSourceKey.get();
		return key;
	}

	public static void setDataSourceKey(String dataSource) {
		log.info("thread:{} set dataSource:{}", Thread.currentThread().getName(), dataSource);
		dataSourceKey.set(dataSource);
	}

	public static void clearDataSourceType() {
		log.info("thread:{} remove dataSource:{}", Thread.currentThread().getName());
		// 以免堆栈溢出
		dataSourceKey.remove();
	}
}
