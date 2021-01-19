package com.gerry.pang.mq;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gerry.pang.DemoServerApplicationTests;
import com.gerry.pang.mq.kafka.Order;
import com.gerry.pang.mq.kafka.producer.Producer;

public class ProducerKafkaTest extends DemoServerApplicationTests {
	
	@Autowired
	private Producer producer;
	
	@Test
	public void testSend() {
		Order order = new Order();
		order.setId(1234l);
		order.setPrice(99.99);
		order.setSymbol("test send");
		order.setTs(2l);
		producer.send(order);
	}
	
	@Test
	public void testSendAsync() {
		for (long i = 0; i < 10; i++) {
			Order order = new Order();
			order.setId(i);
			order.setPrice(100.00 * i);
			order.setSymbol("test send " + i);
			order.setTs(i * 10);
			producer.sendAsync(order);
		}
	}
}
