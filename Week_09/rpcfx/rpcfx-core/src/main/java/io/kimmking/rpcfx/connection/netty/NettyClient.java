package io.kimmking.rpcfx.connection.netty;

import java.net.InetSocketAddress;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpContentDecompressor;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.logging.LoggingHandler;

public class NettyClient {
	
	private static final Log log = LogFactory.getLog(NettyClient.class);
	
	private final String host ;
	private final int port ;
	private final EventLoopGroup group = new NioEventLoopGroup();

	public NettyClient(String host, int port) {
		super();
		this.host = host;
		this.port = port;
	}

	public void start() throws Exception {
		Bootstrap client = new Bootstrap();
		try {
			// 客户端使用非阻塞io NioSocketChannel
			client.group(group).channel(NioSocketChannel.class)
				 .remoteAddress(new InetSocketAddress(host, port))
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
                 // 缓存区 池化操作
                 .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                 .handler(new ChannelInitializer<Channel>() {
					@Override
					protected void initChannel(Channel ch) throws Exception {
						ch.pipeline().addLast(new LoggingHandler());
	                    // 客户端接收到的是httpResponse响应，所以要使用HttpResponseDecoder进行解码
	                    //ch.pipeline().addLast(new HttpResponseDecoder());
	                    // 客户端发送的是httprequest，所以要使用HttpRequestEncoder进行编码
	                    //ch.pipeline().addLast(new HttpRequestEncoder());
						
						// http client端编解码
						ch.pipeline().addLast(new HttpClientCodec());
						// http 解压
						ch.pipeline().addLast(new HttpContentDecompressor());
						// http 消息聚合器  512*1024为接收的最大contentlength
						ch.pipeline().addLast(new HttpObjectAggregator(512*1024)); 
						// 请求处理器
						ch.pipeline().addLast(new ClientHandler());
					}
				});
			ChannelFuture future = client.connect().sync();
			
			// 在连接建立成功之后添加监听
			future.addListener(new ChannelFutureListener() {
				@Override
				public void operationComplete(ChannelFuture future) throws Exception {
					if (future.isSuccess()) {
						log.info("client connect server success");
					} else {
						log.info("client connect server error, " + future.cause().getMessage());
					}
				}
			});
			
			future.channel().closeFuture().sync();
		} finally {
			group.shutdownGracefully().sync();
			log.info("client disconnect success");
		}
	}
}
