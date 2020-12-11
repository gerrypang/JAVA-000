package com.gerry.pang.config;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.gerry.pang.common.rest.CustomConnectionKeepAliveStrategy;

/**
 * RestTemplate 配置
 */
@Configuration
public class RestTemplateConfig {
	
	@Bean
	public HttpComponentsClientHttpRequestFactory requestFactory() {
		PoolingHttpClientConnectionManager connectionManager = 
				new PoolingHttpClientConnectionManager(30, TimeUnit.SECONDS);
		// 设置最大连接数
		connectionManager.setMaxTotal(200);
		// 将每个路由默认最大连接数
		connectionManager.setDefaultMaxPerRoute(20);

		CloseableHttpClient httpClient = HttpClients.custom()
				.setConnectionManager(connectionManager)
				// 空闲连接30s关闭
				.evictIdleConnections(30, TimeUnit.SECONDS)
				// 关闭自动重试机制
				.disableAutomaticRetries()
				// 有 Keep-Alive 认里面的值，没有的话永久有效
				//.setKeepAliveStrategy(DefaultConnectionKeepAliveStrategy.INSTANCE)
				// 换成自定义的
				.setKeepAliveStrategy(new CustomConnectionKeepAliveStrategy())
				.build();
		
		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
		return requestFactory;
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder
				// 建立连接的超时时间
				.setConnectTimeout(Duration.ofMillis(10000))
				// 传递数据的超时时间
				.setReadTimeout(Duration.ofMillis(50000))
				.requestFactory(this::requestFactory)
				.build();
	}

}
