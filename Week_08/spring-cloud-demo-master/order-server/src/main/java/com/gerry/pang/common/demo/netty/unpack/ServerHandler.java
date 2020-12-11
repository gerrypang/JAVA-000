package com.gerry.pang.common.demo.netty.unpack;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

@Sharable
@Slf4j
/**
 * ChannelInboundHandlerAdapter 针对输入数据的处理
 * 
 * @author Gerry_Pang
 * @since 2020年6月27日 上午10:54:37
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
//		// 当客户端连接成功之哈偶会进行此方法调用，明确可以给客户端发送一些信息
//		byte[] data = "服务器连接已经创建，开始进行响应交互&_".getBytes();
//		// netty自己定义的缓存类
//		ByteBuf buffer = Unpooled.buffer(data.length);
//		// 将数据写入缓存中
//		buffer.writeBytes(data);
//		// 强制性发发送所有数据
//		ctx.writeAndFlush(buffer);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		try {
			String inputData = msg.toString().trim();
			String echoData = "[echo] " + inputData + "&_";
			log.info("server received: {}", inputData);
			ByteBuf echoBuffer = Unpooled.copiedBuffer(echoData.getBytes());
			ctx.writeAndFlush(echoBuffer);
		} finally {
			ReferenceCountUtil.release(msg);
		}
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		// 将未决消息冲刷到远程节点，并且关闭该channel
//		ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		log.error("exception ", cause.getMessage());
		// 关闭channel
		ctx.close();
	}

}
