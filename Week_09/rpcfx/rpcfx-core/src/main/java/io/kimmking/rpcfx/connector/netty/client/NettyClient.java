package io.kimmking.rpcfx.connector.netty.client;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

import com.alibaba.fastjson.JSONObject;

import io.kimmking.rpcfx.api.RpcfxRequest;
import io.kimmking.rpcfx.enums.NettySocketEnum;
import io.kimmking.rpcfx.protocol.customrpc.CustomChannelInitializer;
import io.kimmking.rpcfx.protocol.customrpc.message.RpcfxHeader;
import io.kimmking.rpcfx.protocol.customrpc.message.RpcfxMessage;
import io.kimmking.rpcfx.protocol.rest.RestChannelInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.HttpVersion;

//https://www.cnblogs.com/scy251147/p/10721736.html
public class NettyClient {
	
	private static final Log log = LogFactory.getLog(NettyClient.class);
	private final String host ;
	private final int port ;
	// 事件池
	private final EventLoopGroup group;
	// 客户端通道
	private Channel clientChannel;
	private ChannelHandler initializerHandler;
	
	public NettyClient(String host, int port, ChannelHandler initializerHandler) {
		super();
		this.host = host;
		this.port = port;
		this.group = new NioEventLoopGroup();
		if (initializerHandler == null) {
			this.initializerHandler = new RestChannelInitializer(NettySocketEnum.CLIENT);
		} else {
			this.initializerHandler = initializerHandler;
		}
	}
	
	public NettyClient(String host, int port) {
		this(host, port, null);
	}

	public Channel start() throws Exception {
		Bootstrap client = new Bootstrap();

		// 客户端使用非阻塞io NioSocketChannel
		client.group(group).channel(NioSocketChannel.class)
			 .remoteAddress(new InetSocketAddress(host, port))
			 // 将小数据包包装成更大数据包，提高网络负载，会导致tcp响应延迟
			 .option(ChannelOption.TCP_NODELAY, true)
             // 长连接
             .option(ChannelOption.SO_KEEPALIVE, true)
             // 允许重复使用本地地址和端口 ？？
             .option(ChannelOption.SO_REUSEADDR, true)
             // 配置接收缓冲区32k
             .option(ChannelOption.SO_RCVBUF, 32 * 1024)
             // 配置发送缓冲区32k
             .option(ChannelOption.SO_SNDBUF, 32 * 1024)
             // 缓存区 池化操作
             .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
             .handler(initializerHandler);
		ChannelFuture channelFuture = client.connect();

		// 注册连接事件listener
		channelFuture.addListener(new ChannelFutureListener() {
			@Override
			public void operationComplete(ChannelFuture future) throws Exception {
				if (future.isSuccess()) {
					clientChannel = channelFuture.channel();
					log.info("client connect server success");
				} else {
					log.info("客户端[" + channelFuture.channel().localAddress().toString() + "]连接失败，重新连接中...");
					future.channel().close();
					log.info("client connect server error, " + future.cause().getMessage());
				}
			}
		});
		
		// 注册关闭事件
        channelFuture.channel().closeFuture().addListener(cfl -> {
            close();
            log.info("客户端[" + channelFuture.channel().localAddress().toString() + "]已断开...");
        });
        log.info("===> clientChannel: "+ JSONObject.toJSONString(clientChannel));
		return channelFuture.channel();
	}
	
	public void close() throws InterruptedException {
		// 关闭客户端套接字
		if (clientChannel != null) {
			clientChannel.close();
			log.info("clientChannel disconnect success");
		}
		// 关闭客户端线程组
		if (group != null) {
			group.shutdownGracefully().sync();
			log.info("group disconnect success");
		}
	}
	
	public ChannelFuture sendMessage(RpcfxRequest req, String url) {
		ChannelFuture channelFuture = null;
		
		if (initializerHandler instanceof RestChannelInitializer) {
			FullHttpRequest request = this.buildHttpMessage(req, url);
			channelFuture = this.clientChannel.writeAndFlush(request);
		}
		
		if (initializerHandler instanceof CustomChannelInitializer) {
			RpcfxMessage request = this.buildByteMessage(req, url);
			channelFuture = this.clientChannel.writeAndFlush(request);
		}
		return channelFuture;
	}

	private RpcfxMessage buildByteMessage(RpcfxRequest req, String url) {
		RpcfxMessage request = new RpcfxMessage();
		request.setBody(req);
		request.setHeader(new RpcfxHeader());
		return request;
	}

	/**
	 *  把msg转换为FullHttpRequest
	 *
	 * @param req
	 * @param url
	 * @return
	 * @author pangguowei
	 */
	private FullHttpRequest buildHttpMessage(RpcfxRequest req, String url) {
		String jsonStr = JSONObject.toJSONString(req);
		ByteBuf body = Unpooled.copiedBuffer(jsonStr, Charset.forName("utf-8"));
		FullHttpRequest httpRequest = new DefaultFullHttpRequest(HttpVersion.HTTP_1_0, HttpMethod.POST, url, body);
		httpRequest.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
		httpRequest.headers().set(HttpHeaderNames.CONTENT_TYPE, "application/json; charset=utf-8");
		HttpUtil.setContentLength(httpRequest, body.readableBytes());
		return httpRequest;
	}

	public Channel getClientChannel() {
		return clientChannel;
	}
}
