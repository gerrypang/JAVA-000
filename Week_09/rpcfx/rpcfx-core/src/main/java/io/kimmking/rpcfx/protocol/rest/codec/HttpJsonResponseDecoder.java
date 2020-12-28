package io.kimmking.rpcfx.protocol.rest.codec;

import java.util.List;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpResponse;

public class HttpJsonResponseDecoder extends AbstractHttpJsonDecoder<FullHttpResponse> {

	protected HttpJsonResponseDecoder(Class<?> clazz) {
		this(clazz, false);
	}

	public HttpJsonResponseDecoder(Class<?> clazz, boolean isPrint) {
		super(clazz, isPrint);
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, FullHttpResponse msg, List<Object> out) throws Exception {
		System.out.println("开始解码...");
		out.add(new HttpJsonResponse(msg, decode0(ctx, msg.content())));
	}

}
