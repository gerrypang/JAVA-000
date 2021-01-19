package com.gerry.pang.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix="kafka.topic")
public class KafkaTopicProperties {
	
	private String order;

	private String batchOrder;
	
}
