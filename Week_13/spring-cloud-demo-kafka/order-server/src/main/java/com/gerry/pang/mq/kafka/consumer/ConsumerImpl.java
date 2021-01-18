package com.gerry.pang.mq.kafka.consumer;

import java.util.Iterator;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.Headers;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ConsumerImpl implements Consumer {

	@Override
	public void consumeOrder() {
		
	}
	
	@KafkaListener(topics="${kafka.topic.order}")
	public void receiveMessage(ConsumerRecord<String, String> record, Acknowledgment ack) {
		log.info("receive a message from kafka server start");

		String receiveMessage = String.valueOf(record.value());
		log.info("==> receive a message:{}", JSON.toJSONString(receiveMessage));
		Headers headers = record.headers();
		Iterator<Header> it = headers.iterator();
		while (it.hasNext()) {
			Header one = it.next();
			log.info("==> header-{}:{}", one.key(), String.valueOf(one.value()));
		}
		// 手动提交偏移量
		ack.acknowledge();
		log.info("receive a message from kafka server end");
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

}
