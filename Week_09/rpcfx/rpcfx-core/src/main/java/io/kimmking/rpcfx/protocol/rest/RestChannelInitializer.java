package io.kimmking.rpcfx.protocol.rest;

import io.kimmking.rpcfx.api.RpcfxResolver;
import io.kimmking.rpcfx.enums.NettySocketEnum;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class RestChannelInitializer extends ChannelInitializer<SocketChannel> {
	
	public final NettySocketEnum socketEnum;
	
	private RpcfxResolver resolver;
	
	public RestChannelInitializer(NettySocketEnum socketEnum, RpcfxResolver resolver) {
		super();
		this.socketEnum = socketEnum;
		this.resolver = resolver;
	}
	public RestChannelInitializer(NettySocketEnum socketEnum) {
		this(socketEnum, null);
	}
	

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		pipeline.addLast("LoggingHandler", new LoggingHandler(LogLevel.DEBUG));
		
		if (NettySocketEnum.CLIENT.equals(socketEnum)) {
			pipeline.addLast("HttpClientCodec", new HttpClientCodec());
//			pipeline.addLast("HttpContentCompressor", new HttpContentCompressor());
			pipeline.addLast("CustomRestClientHandler", new CustomRestClientHandler());
		}
			
		if (NettySocketEnum.SERVER.equals(socketEnum)) {
			pipeline.addLast("HttpServerCodec", new HttpServerCodec());
//			pipeline.addLast("HttpContentCompressor", new HttpContentCompressor());
			CustomRestServerHandler customRestServerHandler = new CustomRestServerHandler();
			customRestServerHandler.setResolver(resolver);
			pipeline.addLast("CustomRestServerHandler", customRestServerHandler);
		}
	}

}
