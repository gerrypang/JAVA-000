package com.gerry.pang.common.demo.disruptor;

import com.lmax.disruptor.EventFactory;

public class OrderEventFactory implements EventFactory<OrderEvent> {

	@Override
	public OrderEvent newInstance() {
		return new OrderEvent();
	}

}
