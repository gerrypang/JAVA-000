package io.kimmking.rpcfx.protocol.customrpc;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

import io.kimmking.rpcfx.api.RpcfxResponse;
import io.kimmking.rpcfx.protocol.customrpc.message.RpcfxHeader;
import io.kimmking.rpcfx.protocol.customrpc.message.RpcfxMessage;
import io.kimmking.rpcfx.utils.GuavaCacheManager;
import io.kimmking.rpcfx.utils.SyncFuture;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ClientRPCHandler extends SimpleChannelInboundHandler<RpcfxMessage> {
	
	private static final Log log = LogFactory.getLog(ClientRPCHandler.class);
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		log.info("===== 请求连接成功 ===== ");
		super.channelActive(ctx);
	}
	
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		log.info("===== 请求连接被断开 ===== ");
//		final EventLoop eventLoop = ctx.channel().eventLoop();
//		eventLoop.schedule(() -> {
//			
//		}, 20, TimeUnit.SECONDS);
		super.channelInactive(ctx);
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		log.error("client exception: " + cause.getMessage());
		Channel channel = ctx.channel();
		if (channel.isActive()) {
			ctx.close();
		}
		ctx.close();
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, RpcfxMessage msg) throws Exception {
		log.info("===== ClientRPCHandler read0 start ===== ");
		RpcfxHeader header = msg.getHeader();
		log.info("=== header：" + header);
		RpcfxResponse rpcResponse = (RpcfxResponse) msg.getBody();
 		SyncFuture<RpcfxResponse> syncFuture = GuavaCacheManager.getFutureCache().get(ctx.channel().id().asShortText());
 		log.info("=== syncFuture：" + syncFuture);
 		log.info("=== rpcResponse：" + rpcResponse);
 		syncFuture.setResponse(rpcResponse);
 		log.info("===== ClientRPCHandler read0 end ===== ");
// 		ctx.close();
	}
}
