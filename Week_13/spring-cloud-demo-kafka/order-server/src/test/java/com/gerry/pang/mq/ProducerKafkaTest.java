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
		Order order = new Order();
		order.setId(1234l);
		order.setPrice(99.99);
		order.setSymbol("test send");
		order.setTs(2l);
		producer.sendAsync(order);
	}
}
