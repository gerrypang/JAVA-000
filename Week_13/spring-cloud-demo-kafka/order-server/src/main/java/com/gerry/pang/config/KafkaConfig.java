package com.gerry.pang.config;

import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ContainerProperties.AckMode;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;


@Configuration
@EnableKafka
public class KafkaConfig {
	
	@Autowired
	private KafkaProperties kafkaProperties;

	// String consumer configuration
	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerStringContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(stringConsumerFactory());
	    factory.setConcurrency(3);
        factory.getContainerProperties().setPollTimeout(3000);
        //配置手动提交offset
        factory.getContainerProperties().setAckMode(AckMode.MANUAL);
		return factory;
	}

	@Bean
	public ConsumerFactory<String, String> stringConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(
                kafkaProperties.buildConsumerProperties(), new StringDeserializer(), new StringDeserializer()
        );
	}
	
	
	// object consumer configuration
	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory());
		factory.setConcurrency(3);
		factory.getContainerProperties().setPollTimeout(3000);
		//配置手动提交offset
		factory.getContainerProperties().setAckMode(AckMode.MANUAL);
		return factory;
	}
	
	@Bean
	public ConsumerFactory<String, Object> consumerFactory() {
		final JsonDeserializer<Object> jsonDeserializer = new JsonDeserializer<>();
		jsonDeserializer.addTrustedPackages("*");
		return new DefaultKafkaConsumerFactory<>(kafkaProperties.buildConsumerProperties(), new StringDeserializer(), jsonDeserializer);
	}
	
	// byte array consumer configuration
	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, byte[]> kafkaListenerByteArrayContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, byte[]> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(byteArrayConsumerFactory());
		factory.setConcurrency(3);
		factory.getContainerProperties().setPollTimeout(3000);
		//配置手动提交offset
		factory.getContainerProperties().setAckMode(AckMode.MANUAL);
		return factory;
	}
	
	@Bean
	public ConsumerFactory<String, byte[]> byteArrayConsumerFactory() {
		return new DefaultKafkaConsumerFactory<>(kafkaProperties.buildConsumerProperties(), new StringDeserializer(), new ByteArrayDeserializer());
	}

	@Bean
	public ProducerFactory<String, Object> producerFactory() {
		return new DefaultKafkaProducerFactory<>(kafkaProperties.buildProducerProperties(), new StringSerializer(), new JsonSerializer());
	}

	@Bean
	public KafkaTemplate<String, Object> kafkaTemplate() {
		return new KafkaTemplate<String, Object>(producerFactory());
	}
	
	@Bean
	public ProducerFactory<String, String> producerStringFactory() {
		return new DefaultKafkaProducerFactory<>(kafkaProperties.buildProducerProperties(), new StringSerializer(), new StringSerializer());
	}
	
	@Bean
	public KafkaTemplate<String, String> stringKafkaTemplate() {
		return new KafkaTemplate<String, String>(producerStringFactory());
	}

	@Bean
	public ProducerFactory<String, byte[]> producerByteArrayFactory() {
		return new DefaultKafkaProducerFactory<>(kafkaProperties.buildProducerProperties(), new StringSerializer(), new ByteArraySerializer());
	}
	
	@Bean
	public KafkaTemplate<String, byte[]> byteArraykafkaTemplate() {
		return new KafkaTemplate<String, byte[]>(producerByteArrayFactory());
	}

}
