package com.gerry.pang.order.outter;

import java.net.URI;
import java.util.List;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.gerry.pang.order.entity.Coffee;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OutterRestDemo {
	@Autowired
	private RestTemplate restTemplate;
	
	/**
	 * 设置自定义http请求头
	 */
	public void restWithCustomHeader() {
		// 配置get请求，以及请求uri和参数
		URI uri = UriComponentsBuilder
				.fromUriString("http://localhost:8080/coffee/?name={name}")
				.build("mocha");
		// RequestEntity定义请求头
		RequestEntity<Void> req = RequestEntity.get(uri)
				.accept(MediaType.APPLICATION_XML)
				.build();
		// 通过exchange方式可以设置rest请求的RequestEntity
		ResponseEntity<String> resp = restTemplate.exchange(req, String.class);
		log.info("Response Status: {}, Response Headers: {}", resp.getStatusCode(), resp.getHeaders().toString());
		log.info("Coffee: {}", resp.getBody());
	}
	
	/**
	 * post请求
	 */
	public void postRest() {
		String coffeeUri = "http://localhost:8080/coffee/";
		Coffee request = Coffee.builder()
				.name("Americano")
				.price(Money.of(CurrencyUnit.of("CNY"), 25.00))
				.build();
		// post请求
		Coffee response = restTemplate.postForObject(coffeeUri, request, Coffee.class);
		log.info("New Coffee: {}", response);
	}
	
	/**
	 * 请求结果泛型解析
	 * 通过ParameterizedTypeReference解决泛型类型映射
	 */
	public void restParameterizedTypeReference() {
		String coffeeUri = "http://localhost:8080/coffee/";
		// 结果是类型的泛型
		ParameterizedTypeReference<List<Coffee>> ptr = new ParameterizedTypeReference<List<Coffee>>() {};
		ResponseEntity<List<Coffee>> list = restTemplate
				.exchange(coffeeUri, HttpMethod.GET, null, ptr);
		list.getBody().forEach(c -> log.info("Coffee: {}", c));
	}
}
