package com.gerry.pang.common.demo.netty.simple;

import java.net.InetSocketAddress;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Client {

	private final static String host = "127.0.0.1";
	private final static int port = 9990;

	public static void main(String[] args) throws Exception {
		new Client().start();
	}

	private void start() throws Exception {
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap client = new Bootstrap();
			// 客户端使用非阻塞io NioSocketChannel
			client.group(group).channel(NioSocketChannel.class)
				.remoteAddress(new InetSocketAddress(host, port))
				.option(ChannelOption.TCP_NODELAY, true) // 允许接收大块的数据
				.handler(new ChannelInitializer<Channel>() {

					@Override
					protected void initChannel(Channel ch) throws Exception {
						ch.pipeline().addLast(new ClientHandler());
					}
				});
			ChannelFuture future = client.connect().sync();
			// 在连接建立成功之后添加监听
			future.addListener(new ChannelFutureListener() {
				
				@Override
				public void operationComplete(ChannelFuture future) throws Exception {
					if (future.isSuccess()) {
						log.info("server connect success");
					} else {
						log.info("server connect error, {}", future.cause().getMessage());
					}
				}
			});
			
			future.channel().closeFuture().sync();
		} finally {
			group.shutdownGracefully().sync();
		}
	}
}
