package io.kimmking.rpcfx.protocol.rest;

import io.kimmking.rpcfx.enums.NettySocketEnum;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpContentDecompressor;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.logging.LoggingHandler;

public class RestChannelInitializer extends ChannelInitializer<Channel> {
	
	public NettySocketEnum socketEnum;
	
	public RestChannelInitializer(NettySocketEnum socketEnum) {
		super();
		this.socketEnum = socketEnum;
	}

	@Override
	protected void initChannel(Channel ch) throws Exception {
		ch.pipeline().addLast(new LoggingHandler());
		
		if (NettySocketEnum.CLIENT.equals(socketEnum)) {
			// http client端编解码
			ch.pipeline().addLast(new HttpClientCodec());
			// http 解压
			ch.pipeline().addLast(new HttpContentDecompressor());
			// http 消息聚合器 512*1024为接收的最大contentlength
			ch.pipeline().addLast(new HttpObjectAggregator(512 * 1024));
			// 请求处理器
//			ch.pipeline().addLast(new CustomRestClientHandler());
		}
		
		if (NettySocketEnum.SERVER.equals(socketEnum)) {
			// http server端编解码
			ch.pipeline().addLast(new HttpServerCodec());
			// http 压缩
			ch.pipeline().addLast(new HttpContentCompressor());
			// http 消息聚合器 512*1024为接收的最大contentlength
			ch.pipeline().addLast(new HttpObjectAggregator(512 * 1024));
		}
		// 请求处理器
		ch.pipeline().addLast(new CustomRestServerHandler());
		
	}

}
