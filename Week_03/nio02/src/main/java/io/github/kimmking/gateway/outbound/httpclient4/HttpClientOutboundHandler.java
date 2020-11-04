package io.github.kimmking.gateway.outbound.httpclient4;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.kimmking.gateway.client.AbstractRestOperation;
import io.github.kimmking.gateway.client.httpclient.HttpClientRestOpreation;
import io.github.kimmking.gateway.outbound.HttpOutboundHandler;
import io.github.kimmking.gateway.support.GatewagThreadPool;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;

public class HttpClientOutboundHandler implements HttpOutboundHandler {
	private static Logger logger = LoggerFactory.getLogger(HttpClientOutboundHandler.class);
	private ExecutorService proxyService;
	private AbstractRestOperation restOpreation;
	private String backendUrl;

	public HttpClientOutboundHandler(String backendUrl) {
		super();
		this.backendUrl = backendUrl;
		proxyService = GatewagThreadPool.getThreadPool();
		restOpreation = new HttpClientRestOpreation();
	}

	@Override
	public void handle(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
		final String url = fullRequest.uri();
		proxyService.submit(() -> fetchGet(fullRequest, ctx, url));
	}

	private void fetchGet(FullHttpRequest fullRequest, ChannelHandlerContext ctx, String url) {
		String pathParams = backendUrl.contains("?") ? backendUrl.substring(backendUrl.indexOf("?"), backendUrl.length() - 1) : backendUrl;
		url = pathParams != null && pathParams.length() > 0 ? url + "?" + pathParams : url;
		
		Map<String, String> headerVariables = new HashMap<>(16);
		HttpHeaders headers = fullRequest.headers();
		if (headers != null) {
			headers.forEach(n -> {
				headerVariables.put(n.getKey(), n.getValue());
			});
		}
	
		restOpreation.setChannelHandlerContext(ctx);
		restOpreation.setFullRequest(fullRequest);
		restOpreation.doGet(url, headerVariables);
	}

}
