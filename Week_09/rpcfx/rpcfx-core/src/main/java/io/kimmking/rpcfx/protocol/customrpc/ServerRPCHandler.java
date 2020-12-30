package io.kimmking.rpcfx.protocol.customrpc;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

import io.kimmking.rpcfx.api.RpcfxRequest;
import io.kimmking.rpcfx.api.RpcfxResolver;
import io.kimmking.rpcfx.api.RpcfxResponse;
import io.kimmking.rpcfx.protocol.customrpc.message.RpcfxHeader;
import io.kimmking.rpcfx.protocol.customrpc.message.RpcfxMessage;
import io.kimmking.rpcfx.server.RpcfxInvoker;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

public class ServerRPCHandler extends SimpleChannelInboundHandler<RpcfxMessage> {
	
	private static final Log log = LogFactory.getLog(ClientRPCHandler.class);
	private RpcfxResolver resolver;
	
	public void setResolver(RpcfxResolver resolver) {
		this.resolver = resolver;
	}
	
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
		log.info("=== header：" + header);
		RpcfxRequest rpcRequest = (RpcfxRequest) msg.getBody();
		log.info("=== rpcResponse：" + rpcRequest);
		RpcfxResponse rpcResponse = new RpcfxInvoker(resolver).invoke(rpcRequest);
 		log.info("=== rpcResponse：" + rpcResponse.getResult());
 		RpcfxMessage rpcMessage = new RpcfxMessage();
 		rpcMessage.setHeader(new RpcfxHeader());
 		rpcMessage.setBody(rpcResponse);
 		ctx.writeAndFlush(rpcMessage);
	}
	
	/**
	 * 当Channel上的一个读操作完成时被调用
	 */
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}
}
