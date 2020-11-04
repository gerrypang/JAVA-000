package io.github.kimmking.gateway.filter;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

public class FilterChain {
	private List<HttpRequestFilter> chain = new LinkedList<>();
	
	private final static int MAX_SIZE = 100;
	
	public FilterChain add(HttpRequestFilter requestFilter) throws Exception {
		if (chain.size() > MAX_SIZE) {
			throw new Exception("超过调用链最大长度限制");
		}
		chain.add(requestFilter);
		return this;
	} 
	
	public void doFilter(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
		if (chain != null && chain.size() > 0) {
			Iterator<HttpRequestFilter> it = chain.iterator();
			while (it.hasNext()) {
				HttpRequestFilter requestFilter = it.next();
				requestFilter.init(ctx);
				requestFilter.filter(fullRequest, ctx);
				requestFilter.destroy(ctx);
			}
		}
	}
	
}
