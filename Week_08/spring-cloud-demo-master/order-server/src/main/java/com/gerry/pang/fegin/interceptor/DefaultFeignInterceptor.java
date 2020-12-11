package com.gerry.pang.fegin.interceptor;

import org.springframework.stereotype.Component;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;

/**
 * 使用方式
 * 
 * 一，全局所有feign客户端
 * @EnableFeignClients(defaultConfiguration = {DefaultFeignInterceptor.class})
 * 二，指定feign客户端加
 * @FeignClient(name = "xxx",configuration = {DefaultFeignInterceptor.class})
 */
@Slf4j
@Component
public class DefaultFeignInterceptor implements RequestInterceptor {

	@Override
	public void apply(RequestTemplate template) {
		log.info("FeignInterceptorConfig defaultFeignInterceptor url:{}", template.request().url());
	}

}
