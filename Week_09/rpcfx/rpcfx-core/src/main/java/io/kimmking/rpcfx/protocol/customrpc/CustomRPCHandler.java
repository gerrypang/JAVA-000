package io.kimmking.rpcfx.protocol.customrpc;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

import io.kimmking.rpcfx.protocol.customrpc.message.RpcfxHeader;
import io.kimmking.rpcfx.protocol.customrpc.message.RpcfxMessage;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

public class CustomRPCHandler extends SimpleChannelInboundHandler<RpcfxMessage> {
	
	private static final Log log = LogFactory.getLog(CustomRPCHandler.class);
	
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
	protected void channelRead0(ChannelHandlerContext ctx, RpcfxMessage msg) throws Exception {
		RpcfxHeader header = msg.getHeader();
		Object body = msg.getBody();
	}
	
	/**
	 * 当Channel上的一个读操作完成时被调用
	 */
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}
}
