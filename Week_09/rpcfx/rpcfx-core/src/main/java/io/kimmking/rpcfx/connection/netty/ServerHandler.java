package io.kimmking.rpcfx.connection.netty;

import java.net.URI;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpVersion;
// https://www.jianshu.com/p/ed0177a9b2e3
public class ServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

	private static final Log log = LogFactory.getLog(ServerHandler.class);

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// 当客户端连接成功之后会进行此方法调用，明确可以给客户端发送一些信息
		URI url = new URI("/test");
		String meg = "hello";

		// 配置HttpRequest的请求数据和一些配置信息
		FullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_0, HttpMethod.GET, url.toASCIIString(),
				Unpooled.wrappedBuffer(meg.getBytes("UTF-8")));

		request.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain;charset=UTF-8")
				// 开启长连接
				.set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE)
				// 设置传递请求内容的长度
				.set(HttpHeaderNames.CONTENT_LENGTH, request.content().readableBytes());

		// 发送数据
		ctx.writeAndFlush(request);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		log.error("server exception " + cause.getMessage());
		// 关闭channel
		ctx.close();
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest req) throws Exception {
//		//100 Continue
//        if (is100ContinueExpected(req)) {
//            ctx.write(new DefaultFullHttpResponse(
//                           HttpVersion.HTTP_1_1,            
//                           HttpResponseStatus.CONTINUE));
//        }

	}

	/**
	 * 当Channel上的一个读操作完成时被调用
	 */
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}
	
	
}
