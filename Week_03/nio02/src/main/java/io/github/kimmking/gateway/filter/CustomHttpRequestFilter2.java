package io.github.kimmking.gateway.filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;

/**
 * 自定义filter
 * 
 * @author pangguowei
 * @since 2020年11月3日 
 */
public class CustomHttpRequestFilter2 implements HttpRequestFilter {

	private final static String CONST_VALUE = "123456";
	
	@Override
	public void filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
		// 拿到请求头，在原请求头基础上，添加nio：pangguowei
		HttpHeaders headers = fullRequest.headers();
		if (headers == null) {
			headers = new DefaultHttpHeaders();
		}  
		headers.add("hello", CONST_VALUE);
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
