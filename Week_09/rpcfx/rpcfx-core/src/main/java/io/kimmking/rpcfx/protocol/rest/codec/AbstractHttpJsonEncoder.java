package io.kimmking.rpcfx.protocol.rest.codec;

import java.nio.charset.Charset;

import com.alibaba.fastjson.JSONObject;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

public abstract class AbstractHttpJsonEncoder<I> extends MessageToMessageEncoder<I> {
	
	private final static Charset UTF_8 = Charset.forName("utf-8");

	protected ByteBuf encode0(ChannelHandlerContext ctx, Object body) {
		String jsonStr = JSONObject.toJSONString(body);
		ByteBuf encodeBuf = Unpooled.copiedBuffer(jsonStr, UTF_8);
		return encodeBuf;
	}

}
