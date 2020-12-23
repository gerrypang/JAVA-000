package io.kimmking.rpcfx.protocol.customrpc;

import io.kimmking.rpcfx.enums.NettySocketEnum;
import io.kimmking.rpcfx.utils.MarshallingCodeCFactory;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.logging.LoggingHandler;

public class CustomChannelInitializer extends ChannelInitializer<Channel> {

	public NettySocketEnum socketEnum;
	
	public CustomChannelInitializer(NettySocketEnum socketEnum) {
		super();
		this.socketEnum = socketEnum;
	}
	
	@Override
	protected void initChannel(Channel ch) throws Exception {
		ch.pipeline().addLast(new LoggingHandler());
		
		if (NettySocketEnum.CLIENT.equals(socketEnum)) {
			// client端编解码
			ch.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingeEncoder());
			ch.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingDecoder());
		}
		
		if (NettySocketEnum.SERVER.equals(socketEnum)) {
			// server端编解码
			ch.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingDecoder());
			ch.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingeEncoder());
		}
		
		// 请求处理器
		ch.pipeline().addLast(new CustomRPCHandler());
	}

}
