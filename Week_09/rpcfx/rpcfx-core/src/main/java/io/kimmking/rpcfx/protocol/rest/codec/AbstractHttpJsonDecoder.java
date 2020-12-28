package io.kimmking.rpcfx.protocol.rest.codec;

import java.nio.charset.Charset;

import com.alibaba.fastjson.JSON;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

public abstract class AbstractHttpJsonDecoder<I> extends MessageToMessageDecoder<I> {
	
	private Class<?> clazz;
	
	private boolean isPrint;
	
	private final static Charset UTF_8 = Charset.forName("UTF-8");

	protected AbstractHttpJsonDecoder(Class<?> clazz) {
		this(clazz, false);
	}

	protected AbstractHttpJsonDecoder(Class<?> clazz, boolean isPrint) {
		this.clazz = clazz;
		this.isPrint = isPrint;
	}

	protected Object decode0(ChannelHandlerContext ctx, ByteBuf body) {
		String content = body.toString(UTF_8);
		if (isPrint)
			System.out.println("The body is : " + content);
		Object result = JSON.parseObject(content, clazz);
		return result;
	}
}
