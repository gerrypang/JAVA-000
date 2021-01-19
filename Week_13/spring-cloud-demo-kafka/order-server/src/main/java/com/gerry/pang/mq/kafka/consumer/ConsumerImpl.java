package com.gerry.pang.mq.kafka.consumer;

import java.util.Iterator;
import java.util.List;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.Headers;
import org.springframework.core.annotation.Order;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ConsumerImpl implements Consumer {

	@Override
	public void consumeOrder() {
		
	}
	
	@KafkaListener(topics="${kafka.topic.order}", clientIdPrefix = "string", containerFactory = "kafkaListenerStringContainerFactory")
	public void receiveMessageString(ConsumerRecord<String, String> record, Acknowledgment ack) {
		log.info("receive a message string start");

		String receiveMessage = String.valueOf(record.value());
		log.info("==> receive a message:{}", receiveMessage);
		Headers headers = record.headers();
		Iterator<Header> it = headers.iterator();
		while (it.hasNext()) {
			Header one = it.next();
			log.info("==> header-{}:{}", one.key(), String.valueOf(one.value()));
		}
		// 手动提交偏移量
		ack.acknowledge();
		log.info("receive a message string end");
	}

	@KafkaListener(topics="${kafka.topic.order}", clientIdPrefix = "json", containerFactory = "kafkaListenerContainerFactory")
	public void receiveMessageJSON(ConsumerRecord<String, Order> record, Acknowledgment ack) {
		log.info("receive a message json start ");
		log.info("==> receive a message:{}", record.value());
		Headers headers = record.headers();
		Iterator<Header> it = headers.iterator();
		while (it.hasNext()) {
			Header one = it.next();
			log.info("==> header-{}:{}", one.key(), String.valueOf(one.value()));
		}
		// 手动提交偏移量
		ack.acknowledge();
		log.info("receive a message json end");
	}

	@KafkaListener(topics="${kafka.topic.batchOrder}", clientIdPrefix = "list", containerFactory = "kafkaBatchListenerContainerFactory")
	public void receiveMessageJSON(List<ConsumerRecord<String, Order>> records, Acknowledgment ack) {
		log.info("receive batch message json start ");
		log.info("==> receive batch message size:{}", records.size());
		try {
			records.stream().forEach(n -> {
				log.info("==> receive message:{}", n.value());
				Headers headers = n.headers();
				Iterator<Header> it = headers.iterator();
				while (it.hasNext()) {
					Header one = it.next();
					log.info("==> header-{}:{}", one.key(), String.valueOf(one.value()));
				}
			});
		} catch (Exception e) {
			log.error("批量处理消息异常", e);
		} finally {
			// 手动提交偏移量
			ack.acknowledge();
		}
		log.info("receive batch message json end");
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

}
