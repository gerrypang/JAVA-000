package io.kimmking.rpcfx.protocol.rest;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

import com.alibaba.fastjson.JSON;

import io.kimmking.rpcfx.api.RpcfxResponse;
import io.kimmking.rpcfx.utils.GuavaCacheManager;
import io.kimmking.rpcfx.utils.SyncFuture;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.util.CharsetUtil;

@Sharable
public class CustomRestClientHandler2 extends SimpleChannelInboundHandler<FullHttpResponse> {

	private static final Log log = LogFactory.getLog(CustomRestClientHandler2.class);
	private ChannelHandlerContext ctx;

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// ctx.writeAndFlush(Unpooled.copiedBuffer("Hello netty!", CharsetUtil.UTF_8));
		this.ctx = ctx;
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
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, FullHttpResponse msg) throws Exception {
		log.info(" CustomRestServerHandler read0 ===>");
		if (msg instanceof HttpResponse) {
            HttpResponse response = (HttpResponse) msg;
            System.err.println("STATUS: " + response.status());
            System.err.println("VERSION: " + response.protocolVersion());
            System.err.println();

            if (!response.headers().isEmpty()) {
                for (CharSequence name: response.headers().names()) {
                    for (CharSequence value: response.headers().getAll(name)) {
                        System.err.println("HEADER: " + name + " = " + value);
                    }
                }
                System.err.println();
            }

            if (HttpUtil.isTransferEncodingChunked(response)) {
                System.err.println("CHUNKED CONTENT {");
            } else {
                System.err.println("CONTENT {");
            }
        }
        if (msg instanceof HttpContent) {
            HttpContent content = (HttpContent) msg;

            String json = content.content().toString(CharsetUtil.UTF_8);
            System.err.print(json);
            System.err.flush();
            
//    		HttpResponse httpResponse = msg.getResponse();
//    		RpcfxResponse response = (RpcfxResponse) msg.getBody();
            RpcfxResponse rpcResponse = JSON.parseObject(json, RpcfxResponse.class);
    		SyncFuture<RpcfxResponse> syncFuture = GuavaCacheManager.getFutureCache().get(ctx.channel().id().asShortText());
    		System.out.println("=== syncFuture " + syncFuture);
    		System.out.println("=== response " + rpcResponse);
    		syncFuture.setResponse(rpcResponse);
            
            if (content instanceof LastHttpContent) {
                System.err.println("} END OF CONTENT");
                ctx.close();
            }
        }
		
	}

}
