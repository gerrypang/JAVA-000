package com.gerry.pang.common.demo.netty.unpack;

import java.net.InetSocketAddress;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Server {
	public final static int port = 9990;

	public static void main(String[] args) throws Exception {
		new Server().start();
	}

	private void start() throws Exception {
		final ServerHandler serverHandler = new ServerHandler();
		EventLoopGroup bossGroup = new NioEventLoopGroup(10);
		EventLoopGroup workerGroup = new NioEventLoopGroup(20);
		log.info("服务器启动成功，监听端口{}", port);
		try {
			// 创建一个服务端启动类
			ServerBootstrap serverBootstrap = new ServerBootstrap();
			// 设定线程池已经线程类型，服务端使用非阻塞io NioServerSocketChannel
			serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class);
			// 绑定本地端口
			serverBootstrap.localAddress(new InetSocketAddress(port));
			serverBootstrap.childHandler(new ChannelInitializer<Channel>() {
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
					ch.pipeline().addLast(serverHandler);
				}
			});

			serverBootstrap.option(ChannelOption.SO_BACKLOG, 128);
			// 长连接
			serverBootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
			// 异步地绑定服务器调用sysnc方法阻塞直到绑定完成
			ChannelFuture future = serverBootstrap.bind().sync();
			// 获取channel的closeFuture并且阻塞但其线程直到它完成，等待socket关闭
			future.channel().closeFuture().sync();

		} finally {
			// 关闭EventLoopGroup释放所有资源
			bossGroup.shutdownGracefully().sync();
			workerGroup.shutdownGracefully().sync();
		}
	}
}
