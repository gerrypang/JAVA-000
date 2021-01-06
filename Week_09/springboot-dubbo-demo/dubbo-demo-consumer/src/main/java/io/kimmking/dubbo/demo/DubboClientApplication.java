package io.kimmking.dubbo.demo;

import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.kimmking.dubbo.demo.api.service.ForeginExchageTranscationService;
import io.kimmking.dubbo.demo.api.service.OrderService;
import io.kimmking.dubbo.demo.api.service.UserService;

@SpringBootApplication
public class DubboClientApplication {
	/**
	 * hmily 和 dobbu 结合注意事项
	 *
	 * 1、支持一下几种 hmilyConsistentHash, hmilyLeastActive, hmilyRandom, hmilyRoundRobin 几种方式均是继承dubbo原生的
	 * 2、需要进行分布式事务的dubbo接口，调用放要设置为永远不重试(retries="0")
	 */
	@DubboReference(version = "1.0.0", url = "dubbo://127.0.0.1:12345", loadbalance = "hmilyConsistentHash", retries = 0)
	private ForeginExchageTranscationService foreginExchageTranscationService;

	@DubboReference(version = "1.0.0", url = "dubbo://127.0.0.1:12345")
	private UserService userService;

	@DubboReference(version = "1.0.0", url = "dubbo://127.0.0.1:12345")
	private OrderService orderService;
	
	public static void main(String[] args) {
		SpringApplication.run(DubboClientApplication.class);
		System.out.println("===== consumer server start success =====");
	}
}
