package com.gerry.pang.common.demo.netty.unpack;

import java.net.InetSocketAddress;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
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
						// 追加处理器
						// 方法一：使用系统默认换行作为拆包分割符（\n or \r\n）
						// ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
						// 方法二：使用自定义分割符作为拆包分割符
						ByteBuf delimiter = Unpooled.copiedBuffer("&_".getBytes());
						ch.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, delimiter));
						ch.pipeline().addLast(new StringEncoder());
						ch.pipeline().addLast(new StringDecoder());
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
