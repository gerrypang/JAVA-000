package io.kimmking.rpcfx.protocol.customrpc;

import io.kimmking.rpcfx.api.RpcfxResolver;
import io.kimmking.rpcfx.enums.NettySocketEnum;
import io.kimmking.rpcfx.utils.MarshallingCodeCFactory;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.logging.LoggingHandler;

public class CustomChannelInitializer extends ChannelInitializer<Channel> {

	public NettySocketEnum socketEnum;
	
	private RpcfxResolver resolver;
	
	public CustomChannelInitializer(NettySocketEnum socketEnum) {
		this(socketEnum, null);
	}
	
	public CustomChannelInitializer(NettySocketEnum socketEnum, RpcfxResolver resolver) {
		this.socketEnum = socketEnum;
		this.resolver = resolver;
	}

	@Override
	protected void initChannel(Channel ch) throws Exception {
		ch.pipeline().addLast(new LoggingHandler());
		
		if (NettySocketEnum.CLIENT.equals(socketEnum)) {
			// client端编解码
			ch.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingeEncoder());
			ch.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingDecoder());
			ch.pipeline().addLast(new ClientRPCHandler());
		}
		
		if (NettySocketEnum.SERVER.equals(socketEnum)) {
			// server端编解码
			ch.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingDecoder());
			ch.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingeEncoder());
			ServerRPCHandler serverRPCHandler = new ServerRPCHandler();
			serverRPCHandler.setResolver(resolver);
			ch.pipeline().addLast(serverRPCHandler);
		}
	}

}
