package io.kimmking.rpcfx.connector.netty.server;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

import io.kimmking.rpcfx.api.RpcfxResolver;
import io.kimmking.rpcfx.enums.NettySocketEnum;
import io.kimmking.rpcfx.protocol.ProtoclEnum;
import io.kimmking.rpcfx.protocol.customrpc.CustomChannelInitializer;
import io.kimmking.rpcfx.protocol.rest.RestChannelInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer {
	
	private static final Log log = LogFactory.getLog(NettyServer.class);
	
	private final EventLoopGroup bossGroup ;
	private final EventLoopGroup workerGroup ;
	public final int port;
	// 服务端通道
	private Channel serverChannel;
	private ProtoclEnum protoclEnum;

	public NettyServer(int port, int bossNum, int wokerNum) {
		this(port, bossNum, wokerNum, ProtoclEnum.REST_RPC);
	}
	
	public NettyServer(int port, int bossNum, int wokerNum, ProtoclEnum protoclEnum) {
		super();
		this.port = port;
		bossGroup = new NioEventLoopGroup(bossNum);
		workerGroup = new NioEventLoopGroup(wokerNum);
		this.protoclEnum = protoclEnum;
	}
	
	public Channel start(RpcfxResolver resolver) throws Exception {
        // 创建一个服务端启动类
		final ServerBootstrap serverBootstrap = new ServerBootstrap();
    	// 设定线程池已经线程类型，服务端使用非阻塞io NioServerSocketChannel
		serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class);
        // 对应的是tcp/ip协议listen函数中的backlog参数，多个客户端来的时候，服务端将不能处理的客户端连接请求放在队列中等待处理，backlog参数指定队列的大小
		serverBootstrap.option(ChannelOption.SO_BACKLOG, 128)
    		/**
    		 * 与Nagle算法有关， 
    		 * Nagle算法是将小的数据包组装为更大的帧然后进行发送，而不是输入一次发送一次，
    		 * 因此在数据包不足的时候会等待其他数据的到了，组装成大的数据包进行发送，
    		 * 虽然该方式有效提高网络的有效负载，但是却造成了延时
    		 */
            .option(ChannelOption.TCP_NODELAY, true)
            // 长连接
            .option(ChannelOption.SO_KEEPALIVE, true)
            // 允许重复使用本地地址和端口 ？？
            .option(ChannelOption.SO_REUSEADDR, true)
            // 配置接收缓冲区32k
            .option(ChannelOption.SO_RCVBUF, 32 * 1024)
            // 配置发送缓冲区32k
            .option(ChannelOption.SO_SNDBUF, 32 * 1024)
            // 支持多个进程或者线程绑定到同一端口，提高服务器程序的性能  ？？
            .option(EpollChannelOption.SO_REUSEPORT, true)
            // 客户端，保持长连接
            .childOption(ChannelOption.SO_KEEPALIVE, true)
            // 缓存区 池化操作
            .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
		
		if (ProtoclEnum.REST_RPC.equals(protoclEnum)) {
			serverBootstrap.childHandler(new RestChannelInitializer(NettySocketEnum.SERVER, resolver)); 
		}
		if (ProtoclEnum.CUSTOM_BYTE_RPC.equals(protoclEnum)) {
			serverBootstrap.childHandler(new CustomChannelInitializer(NettySocketEnum.SERVER, resolver)); 
		}
        // 异步地绑定服务器调用sync方法阻塞直到绑定完成
        Channel channel = serverBootstrap.bind(port).sync().channel();
		log.info("开启netty http服务器，监听地址和端口为 http://127.0.0.1:" + port);
		return channel;
	}
	
	public void close() throws InterruptedException {
		// 关闭服务端套接字
		if (serverChannel != null) {
			serverChannel.close();
			log.info("serverChannel disconnect success");
		}
		// 关闭客户端线程组
		if (bossGroup != null) {
			// 优雅停机，关闭EventLoopGroup释放所有资源
            bossGroup.shutdownGracefully();
		}
		if (workerGroup != null) {
			workerGroup.shutdownGracefully();
			log.info("server disconnect success");
		}
	}
}
