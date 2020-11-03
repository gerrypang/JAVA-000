package io.github.kimmking.gateway.inbound;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

public class HttpInboundInitializer extends ChannelInitializer<SocketChannel> {
	
	private static Logger logger = LoggerFactory.getLogger(HttpInboundInitializer.class);
	
	private String proxyServer;
	
	public HttpInboundInitializer(String proxyServer) {
		this.proxyServer = proxyServer;
	}
	
	@Override
	public void initChannel(SocketChannel ch) {
		ChannelPipeline pipeline = ch.pipeline();
//		if (sslCtx != null) {
//			p.addLast(sslCtx.newHandler(ch.alloc()));
//		}
		/**
		 * 追加处理器
		 */
		// 针对http编解码处理，只能处理get请求
		pipeline.addLast(new HttpServerCodec());
		//p.addLast(new HttpServerExpectContinueHandler());
		// 针对http编解码处理，可以处理post请求
		pipeline.addLast(new HttpObjectAggregator(1024 * 1024));
		pipeline.addLast(new HttpInboundHandler(this.proxyServer));
		logger.info("=== initChannel 完成 === ");
	}
}
