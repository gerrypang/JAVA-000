package com.gerry.pang.common.demo.netty.unpack;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Sharable
public class ClientHandler extends ChannelInboundHandlerAdapter {
	private int count;
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		String inputData = null;
		for (int i = 0; i < 100; i++) {
			inputData += "开始发送内容:" + "hello netty " + i + "&_";
		}
		ByteBuf sendBuf = Unpooled.copiedBuffer(inputData.getBytes());
		ctx.writeAndFlush(sendBuf);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		try {
			// 使用了StringEnCoder之后可以直接接受字符串
			String readData = msg.toString().trim();
			log.info("client receive-{}: {} ", (++count), readData);
		} finally {
			ReferenceCountUtil.release(msg);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		log.error("client exception: {}", cause.getMessage());
		ctx.close();
	}
}
