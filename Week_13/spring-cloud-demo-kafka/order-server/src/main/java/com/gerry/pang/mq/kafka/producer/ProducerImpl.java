package com.gerry.pang.mq.kafka.producer;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.alibaba.fastjson.JSON;
import com.gerry.pang.config.KafkaTopicProperties;
import com.gerry.pang.mq.kafka.Order;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProducerImpl implements Producer {

	@Autowired
	private KafkaTopicProperties kafkaTopicProperties;
	@Autowired
	private KafkaTemplate<String, String> stringKafkaTemplate;
	@Autowired
	private KafkaTemplate<String, Object> kafkaTemplate;
	@Autowired
	private KafkaTemplate<String, byte[]> byteArrayKafkaTemplate;
	
	@Override
	public void send(Order order) {
		final ProducerRecord<String, Object> record = createRecord(kafkaTopicProperties.getOrder(), order);
		try {
			SendResult<String, Object> sendResult = kafkaTemplate.send(record).get(10, TimeUnit.SECONDS);
			log.info("==> Sent ok: {}", sendResult.getRecordMetadata());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void sendAsync(Order order) {
		final ProducerRecord<String, Object> record = createRecord(kafkaTopicProperties.getBatchOrder(), order);
		ListenableFuture<SendResult<String, Object>> futureOfSend = kafkaTemplate.send(record);
		futureOfSend.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
			@Override
			public void onSuccess(SendResult<String, Object> result) {
				log.info("==> onSuccess:{}", JSON.toJSONString(result));
			}

			@Override
			public void onFailure(Throwable ex) {
				log.error("==> onFailure:{}", ex);
			}
		});
	}
	
	private ProducerRecord<String, Object> createRecord(String topic, Order order) {
		ProducerRecord<String, Object> record = new ProducerRecord<>(topic, order);
		return record;
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

}
