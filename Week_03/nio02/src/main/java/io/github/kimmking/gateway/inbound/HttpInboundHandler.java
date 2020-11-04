package io.github.kimmking.gateway.inbound;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.kimmking.gateway.client.enums.ClientTypeEnum;
import io.github.kimmking.gateway.filter.CustomHttpRequestFilter;
import io.github.kimmking.gateway.filter.CustomHttpRequestFilter2;
import io.github.kimmking.gateway.filter.FilterChain;
import io.github.kimmking.gateway.filter.HttpRequestFilter;
import io.github.kimmking.gateway.outbound.HttpOutboundHandler;
import io.github.kimmking.gateway.outbound.httpclient4.HttpAsyncClientOutboundHandler;
import io.github.kimmking.gateway.outbound.httpclient4.HttpClientOutboundHandler;
import io.github.kimmking.gateway.outbound.okhttp.OkhttpOutboundHandler;
import io.github.kimmking.gateway.router.HttpEndpointRouter;
import io.github.kimmking.gateway.router.RandomRouter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.util.ReferenceCountUtil;

public class HttpInboundHandler extends ChannelInboundHandlerAdapter {

    private static Logger logger = LoggerFactory.getLogger(HttpInboundHandler.class);
    private final String proxyServer;
    private HttpOutboundHandler handler;
    private HttpRequestFilter filer;
    private HttpEndpointRouter router;
    private static List<String> endpoints = new ArrayList<>(10);
    static {
    	// TODO 定义路由列表
    	endpoints.add("http://127.0.0.1:8087/api/hello");
    	endpoints.add("http://127.0.0.1:8088/api/hello");
    	endpoints.add("http://127.0.0.1:8089/api/hello");
    }
    
    public HttpInboundHandler(String proxyServer, ClientTypeEnum clientType) {
        this.proxyServer = proxyServer;
		if (ClientTypeEnum.HTTP_ASYC_CLIENT.equals(clientType)) {
			logger.info("==> client type is HttpAsyncClient");
			handler = new HttpAsyncClientOutboundHandler(this.proxyServer);
		} else if (ClientTypeEnum.HTTP_CLIENT.equals(clientType)) {
			handler = new HttpClientOutboundHandler(this.proxyServer);
			logger.info("==> client type is HttpClient");
		} else if (ClientTypeEnum.HTTP_CLIENT.equals(clientType)) {
			handler = new OkhttpOutboundHandler(this.proxyServer);
			logger.info("==> client type is OkHttp");
		} else {
			handler = new OkhttpOutboundHandler(this.proxyServer);
			logger.info("==> client type is OkHttp");
		}
        filer = new CustomHttpRequestFilter();
        router = new RandomRouter();
    }

	@Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
    	LocalDateTime startTime = LocalDateTime.now();
        try {
            logger.info("channelRead流量接口请求开始，时间:{}", startTime);
            FullHttpRequest fullRequest = (FullHttpRequest) msg;
            String uri = fullRequest.uri();
            logger.info("接收到的请求url:{}", uri);
           
            // 通过filter过滤器
            FilterChain filterChain = new FilterChain();
            filterChain.add(filer).add(new CustomHttpRequestFilter2());
            filterChain.doFilter(fullRequest, ctx);
            
            String routePath = router.route(endpoints);
            fullRequest.setUri(routePath);
            logger.info("==> routePath:{}", routePath);
            
            // 调用不同类型handler
            handler.handle(fullRequest, ctx);
        } catch(Exception e) {
        	logger.error("channelRead流量接口请求异常", e);
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }
}
