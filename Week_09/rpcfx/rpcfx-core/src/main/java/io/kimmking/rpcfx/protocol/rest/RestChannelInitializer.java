package io.kimmking.rpcfx.protocol.rest;

import io.kimmking.rpcfx.api.RpcfxRequest;
import io.kimmking.rpcfx.api.RpcfxResponse;
import io.kimmking.rpcfx.enums.NettySocketEnum;
import io.kimmking.rpcfx.protocol.rest.codec.HttpJsonRequestDecoder;
import io.kimmking.rpcfx.protocol.rest.codec.HttpJsonRequestEncoder;
import io.kimmking.rpcfx.protocol.rest.codec.HttpJsonResponseDecoder;
import io.kimmking.rpcfx.protocol.rest.codec.HttpJsonResponseEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class RestChannelInitializer extends ChannelInitializer<SocketChannel> {
	
	public NettySocketEnum socketEnum;
	
	public RestChannelInitializer(NettySocketEnum socketEnum) {
		super();
		this.socketEnum = socketEnum;
	}

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		pipeline.addLast("LoggingHandler", new LoggingHandler(LogLevel.DEBUG));
		pipeline.addLast("HttpClientCodec", new HttpClientCodec());
//		pipeline.addLast("HttpObjectAggregator", new HttpObjectAggregator(512 *	1024));
		pipeline.addLast("CustomRestClientHandler", new CustomRestClientHandler());

//		pipeline.addLast("HttpClientCodec", new HttpClientCodec());
		
		
		
		if (NettySocketEnum.CLIENT.equals(socketEnum)) {
//			pipeline.addLast("HttpJsonResponseDecoder", new HttpJsonResponseDecoder(RpcfxResponse.class, true));

			
//			pipeline.addLast("HttpJsonRequestEncoder", new HttpJsonRequestEncoder());

			// http client端编解码
			// http 解压
//			pipeline.addLast("HttpContentDecompressor", new HttpContentDecompressor());
			// http 消息聚合器 512*1024为接收的最大contentlength			
//			pipeline.addLast("CustomRestClientHandler", new CustomRestClientHandler());
		}
		
//		if (NettySocketEnum.SERVER.equals(socketEnum)) {
//			// http server端编解码
//			pipeline.addLast("HttpRequestDecoder", new HttpRequestDecoder());
//			// http 压缩
////			pipeline.addLast("HttpContentCompressor", new HttpContentCompressor());
//			// http 消息聚合器 512*1024为接收的最大contentlength
//			pipeline.addLast("HttpObjectAggregator", new HttpObjectAggregator(512 * 1024));
//			pipeline.addLast("HttpJsonRequestDecoder", new HttpJsonRequestDecoder(RpcfxRequest.class, true));
//			pipeline.addLast("HttpResponseEncoder", new HttpResponseEncoder());
//			pipeline.addLast("HttpJsonResponseEncoder", new HttpJsonResponseEncoder());
//		}
	}

}
