package io.kimmking.rpcfx.protocol.rest;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpMessage;
import io.netty.util.CharsetUtil;

public class CustomRestServerHandler extends SimpleChannelInboundHandler<FullHttpMessage> {

private static final Log log = LogFactory.getLog(CustomRestServerHandler.class);
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		ctx.writeAndFlush(Unpooled.copiedBuffer("Hello netty!", CharsetUtil.UTF_8));
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		log.error("client exception: " + cause.getMessage());
		ctx.close();
	}

	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, FullHttpMessage msg) throws Exception {
		if (msg.decoderResult().isSuccess()) {
			System.out.println("=== responce scuccess ===");
		}
		log.info("===>" + msg.content().readableBytes());
	}

	/**
	 * 当Channel上的一个读操作完成时被调用
	 */
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}
}
