package com.gerry.pang.common.demo.disruptor;

import java.nio.ByteBuffer;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

public class DisruptorDemo {

	public static void main(String[] args) {
		OrderEventFactory eventFactory = new OrderEventFactory();
		int ringBufferSize = 1024 * 1024;
		ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);

		Disruptor<OrderEvent> disruptor = new Disruptor<OrderEvent>(eventFactory, ringBufferSize, executor,
				ProducerType.SINGLE, new BlockingWaitStrategy());

		// 2. 添加消费者的监听 (构建disruptor 与 消费者的一个关联关系)
		disruptor.handleEventsWith(new OrderEventHandler());

		// 3. 启动disruptor
		disruptor.start();

		// 4. 获取实际存储数据的容器: RingBuffer
		RingBuffer<OrderEvent> ringBuffer = disruptor.getRingBuffer();
		OrderEventProducer producer = new OrderEventProducer(ringBuffer);

		ByteBuffer bb = ByteBuffer.allocate(8);

		for (long i = 0; i < 50; i++) {
			bb.putLong(0, i);
			producer.sendData(bb);
		}

		disruptor.shutdown();
		executor.shutdown();
	}
}
