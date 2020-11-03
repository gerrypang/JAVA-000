package io.github.kimmking.gateway.filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

/**
 * 自定义filter
 * 
 * @author pangguowei
 * @since 2020年11月3日 
 */
public class CustomHttpRequestFIlter implements HttpRequestFilter {

	@Override
	public void filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
		// 拿到请求头，在原请求头基础上，添加nio：pangguowei
		
	}

	@Override
	public void init(ChannelHandlerContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroy(ChannelHandlerContext ctx) {
		// TODO Auto-generated method stub
		
	}

}
