package io.github.kimmking.gateway.filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

public interface HttpRequestFilter {
	
	void init(ChannelHandlerContext ctx);
    
    void filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx);

    void destroy(ChannelHandlerContext ctx);
    
}
