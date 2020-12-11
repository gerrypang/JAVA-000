package com.gerry.pang;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.gerry.pang.common.demo.spring.BeanExpandDemo;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Slf4j
@EnableDiscoveryClient
@EnableFeignClients
@EnableCircuitBreaker // 启用Hystrix断路器
@SpringBootApplication
@EnableSwagger2
@RestController
public class OrderServerApplication {
//	@Autowired
//	private BeanExpandDemo beanExpandDemo;

	public static void main(String[] args) {
		SpringApplication.run(OrderServerApplication.class, args);
		log.info("******** order server start success ********");
//		BeanFactory beanFactory = new ClassPathXmlApplicationContext("classpath:/META-INF/bean-demo.xml");
//		BeanExpandDemo user = beanFactory.getBean("beanExpandDemo", BeanExpandDemo.class);
//		System.out.println("=======");
	}

	@Autowired
	private DiscoveryClient discoveryClient;

	@GetMapping("/services")
	public Set<String> getServices() {
		return new LinkedHashSet<>(discoveryClient.getServices());
	}

	/**
	 * @param serviceName
	 * @return
	 * @PathParam 属于 JAX-RS 标准 Java REST 注解
	 * @PathVariable 属于 Spring Web MVC
	 */
	@GetMapping("/services/{serviceName}")
	public List<ServiceInstance> getServiceInstances(@PathVariable String serviceName) {
		return discoveryClient.getInstances(serviceName);
	}

	@GetMapping("/services/{serviceName}/{instanceId}")
	public ServiceInstance getServiceInstance(@PathVariable String serviceName, @PathVariable String instanceId) {
		return getServiceInstances(serviceName).stream()
				.filter(serviceInstance -> instanceId.equals(serviceInstance.getInstanceId())).findFirst()
				.orElseThrow(() -> new RuntimeException("No Such service instance"));
	}

}
