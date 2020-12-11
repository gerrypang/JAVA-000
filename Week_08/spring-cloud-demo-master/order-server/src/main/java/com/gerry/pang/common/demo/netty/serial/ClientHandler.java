package com.gerry.pang.common.demo.netty.serial;

import com.gerry.pang.common.demo.netty.dto.MessageData;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Sharable
public class ClientHandler extends ChannelInboundHandlerAdapter {
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		log.info("=== client channelActive === ");
		for (int i = 0; i < 10; i++) {
			MessageData request = new MessageData();
			request.setId("" + i);
			request.setType("请求消息类型：demo");
			request.setMessage("请求消息内容： " + i);
			ctx.writeAndFlush(request);
		}
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		try {
			MessageData readData = (MessageData) msg;
			log.info("client receive: {}", readData);
		} finally {
			// 一定要注意 用完了缓存 要进行释放
			ReferenceCountUtil.release(msg);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		log.error("client exception: {}", cause.getMessage());
		ctx.close();
	}
}
