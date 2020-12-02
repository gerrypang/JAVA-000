package com.bfxy.disruptor;

import com.bfxy.entity.TranslatorData;
import com.bfxy.entity.TranslatorDataWapper;
import com.bfxy.generator.GeneratorId;
import com.lmax.disruptor.RingBuffer;

import io.netty.channel.ChannelHandlerContext;

import java.util.concurrent.ExecutionException;

public class MessageProducer {

	private String producerId;
	
	private RingBuffer<TranslatorDataWapper> ringBuffer;
	
	public MessageProducer(String producerId, RingBuffer<TranslatorDataWapper> ringBuffer) {
		this.producerId = producerId;
		this.ringBuffer = ringBuffer;
	}
	
	public void onData(TranslatorData data, ChannelHandlerContext ctx) {
		long sequence = ringBuffer.next();
		try {
			Integer id = GeneratorId.CAHCHE.get("key_"+sequence);
			TranslatorDataWapper wapper = ringBuffer.get(sequence);
			data.setMessage(data.getMessage() + ", id:" + id);
			wapper.setData(data);
			wapper.setCtx(ctx);
		} catch (ExecutionException e) {
			e.printStackTrace();
		} finally {
			ringBuffer.publish(sequence);
		}
	}

}
