package com.gerry.pang.common.demo.disruptor;

import com.lmax.disruptor.EventHandler;

public class OrderEventHandler implements EventHandler<OrderEvent> {

	@Override
	public void onEvent(OrderEvent event, long sequence, boolean endOfBatch) throws Exception {
		//Thread.sleep(100);
		System.err.println("消费者: " + event.getValue());
	}

}
