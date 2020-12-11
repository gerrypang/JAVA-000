package com.gerry.pang.common.demo.netty.serial;

import java.net.InetSocketAddress;

import com.gerry.pang.common.utils.MarshallingCodeCFactory;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.AdaptiveRecvByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
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
					ch.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingDecoder());
					ch.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingEncoder());
					ch.pipeline().addLast(serverHandler);
				}
			});

			serverBootstrap.option(ChannelOption.SO_BACKLOG, 1024)
				// 表示缓存区动态调配（自适应）
				.option(ChannelOption.RCVBUF_ALLOCATOR, AdaptiveRecvByteBufAllocator.DEFAULT)
				// 缓存区 池化操作
				.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
			// 长连接
			serverBootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
			// 异步地绑定服务器调用sysnc方法阻塞直到绑定完成
			ChannelFuture future = serverBootstrap.bind().sync();
			// 获取channel的closeFuture并且阻塞但其线程直到它完成，等待socket关闭
			future.channel().closeFuture().sync();

		} finally {
			// 优雅停机,关闭EventLoopGroup释放所有资源
			bossGroup.shutdownGracefully().sync();
			workerGroup.shutdownGracefully().sync();
		}
	}
}
