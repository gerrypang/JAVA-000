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
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.util.CharsetUtil;

@Sharable
public class CustomRestClientHandler extends SimpleChannelInboundHandler<HttpObject> {

	private static final Log log = LogFactory.getLog(CustomRestClientHandler.class);

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
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
		log.info("===== CustomRestClientHandler read0 start ===== ");
		if (msg instanceof HttpResponse) {
            HttpResponse response = (HttpResponse) msg;
            log.info("STATUS: " + response.status());
            log.info("VERSION: " + response.protocolVersion());

            if (!response.headers().isEmpty()) {
                for (CharSequence name: response.headers().names()) {
                    for (CharSequence value: response.headers().getAll(name)) {
                    	log.info("HEADER: " + name + " = " + value);
                    }
                }
            }

            if (HttpUtil.isTransferEncodingChunked(response)) {
            	log.info("CHUNKED CONTENT {");
            } else {
            	log.info("CONTENT {");
            }
        }
		
        if (msg instanceof HttpContent) {
            HttpContent content = (HttpContent) msg;
            String json = content.content().toString(CharsetUtil.UTF_8);
            RpcfxResponse rpcResponse = JSON.parseObject(json, RpcfxResponse.class);
    		SyncFuture<RpcfxResponse> syncFuture = GuavaCacheManager.getFutureCache().get(ctx.channel().id().asShortText());
    		log.info("=== syncFuture：" + syncFuture);
    		log.info("=== rpcResponse：" + rpcResponse);
    		syncFuture.setResponse(rpcResponse);
    		
            if (content instanceof LastHttpContent) {
            	log.info("} END OF CONTENT");
                ctx.close();
            }
        }
        log.info("===== CustomRestClientHandler read0 end ===== ");
	}

}
