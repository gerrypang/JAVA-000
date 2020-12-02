package com.bfxy.server;

import com.bfxy.codec.MarshallingCodeCFactory;

import com.bfxy.generator.GeneratorId;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class NettyServer {

	private int port;

	public NettyServer(int port) {
		this.port = port;
	}

	public void run() {
		//1. 创建两个工作线程组: 一个用于接受网络请求的线程组. 另一个用于实际处理业务的线程组
		EventLoopGroup bossGroup = new NioEventLoopGroup(1);
		EventLoopGroup workGroup = new NioEventLoopGroup(16);
		//2 辅助类
		ServerBootstrap serverBootstrap = new ServerBootstrap();
		try {
			serverBootstrap.group(bossGroup, workGroup)
					.channel(NioServerSocketChannel.class)
					// 对应的是tcp/ip协议listen函数中的backlog参数，多个客户端来的时候，服务端将不能处理的客户端连接请求放在队列中等待处理，backlog参数指定队列的大小
					.option(ChannelOption.SO_BACKLOG, 3*1024)
					/**
					 * 与Nagle算法有关，
					 * Nagle算法是将小的数据包组装为更大的帧然后进行发送，而不是输入一次发送一次，
					 * 因此在数据包不足的时候会等待其他数据的到了，组装成大的数据包进行发送，
					 * 虽然该方式有效提高网络的有效负载，但是却造成了延时
					 */
					.option(ChannelOption.TCP_NODELAY, true)
					// 允许重复使用本地地址和端口 ？？
					.option(ChannelOption.SO_REUSEADDR, true)
					// 配置接收缓冲区32k
					.option(ChannelOption.SO_RCVBUF, 32 * 1024)
					// 配置发送缓冲区32k
					.option(ChannelOption.SO_SNDBUF, 32 * 1024)
					/* 表示缓存区动态调配（自适应）
					.option(ChannelOption.RCVBUF_ALLOCATOR, AdaptiveRecvByteBufAllocator.DEFAULT)
					*/
					// 支持多个进程或者线程绑定到同一端口，提高服务器程序的性能
					.option(EpollChannelOption.SO_REUSEPORT, true)
					// 客户端，保持长连接
					.childOption(ChannelOption.SO_KEEPALIVE, true)
					//缓存区 池化操作
					.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
					.handler(new LoggingHandler(LogLevel.INFO))
					.childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel sc) throws Exception {
							sc.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingDecoder());
							sc.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingEncoder());
							sc.pipeline().addLast(new ServerHandler());
						}
					});

			//绑定端口，同步等等请求连接
			ChannelFuture cf = serverBootstrap.bind(port).sync();
			System.err.println("Server Startup...");
			cf.channel().closeFuture().sync();

			// 向缓存中存储100个key
			GeneratorId generatorId = new GeneratorId();
			generatorId.generator();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			//优雅停机
			bossGroup.shutdownGracefully();
			workGroup.shutdownGracefully();
			System.err.println("Sever ShutDown...");
		}
	}
	
}
