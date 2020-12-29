package io.kimmking.rpcfx.connector.netty;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSONObject;

import io.kimmking.rpcfx.api.RpcfxRequest;
import io.kimmking.rpcfx.api.RpcfxResponse;
import io.kimmking.rpcfx.connector.Connector;
import io.kimmking.rpcfx.connector.netty.client.NettyClient;
import io.kimmking.rpcfx.utils.GuavaCacheManager;
import io.kimmking.rpcfx.utils.SyncFuture;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

// https://segmentfault.com/a/1190000020507086
// https://www.cnblogs.com/carl10086/p/6185095.html
public class NettyClientConnector implements Connector {
	
	private static final Log log = LogFactory.getLog(NettyClientConnector.class);
	
	@Override
	public RpcfxResponse sendRequest(RpcfxRequest req, String url) throws IOException {
		RpcfxResponse result = null;
		try {
			URI uri = new URI(url);
			final int port = uri.getPort();
			final String host = uri.getHost();
			NettyClient client = new NettyClient(host, port);
			Channel channel = client.start();
			FullHttpRequest request = this.buildNettyMessage(req, url);
			SyncFuture<RpcfxResponse> syncFuture = new SyncFuture<>();
			System.out.println("====" + channel.id().asShortText());
			GuavaCacheManager.getFutureCache().put(channel.id().asShortText(), syncFuture);
			ChannelFuture channelFuture = channel.writeAndFlush(request);
			
			channelFuture.addListener(new GenericFutureListener<Future<? super Void>>() {
				@Override
				public void operationComplete(Future<? super Void> future) throws Exception {
					if (future.isSuccess()) {
						log.info("NettyConnector.sendRequest 发送成功");
					} else {
						log.info("NettyConnector.sendRequest 发送失败");
					}
				}
			});
			 
			result = syncFuture.get(8, TimeUnit.SECONDS);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 *  把msg转换为FullHttpRequest
	 *
	 * @param req
	 * @param url
	 * @return
	 * @author pangguowei
	 */
	private FullHttpRequest buildNettyMessage(RpcfxRequest req, String url) {
		String jsonStr = JSONObject.toJSONString(req);
		ByteBuf body = Unpooled.copiedBuffer(jsonStr, Charset.forName("utf-8"));
		FullHttpRequest httpRequest = new DefaultFullHttpRequest(HttpVersion.HTTP_1_0, HttpMethod.POST, url, body);
		httpRequest.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
		httpRequest.headers().set(HttpHeaderNames.CONTENT_TYPE, "application/json; charset=utf-8");
//		HttpJsonRequest jsonRequest = new HttpJsonRequest(httpRequest, req);
		HttpUtil.setContentLength(httpRequest, body.readableBytes());
		return httpRequest;
	}
	
//	/**
//	 *  把msg转换为FullHttpRequest
//	 *
//	 * @param req
//	 * @param url
//	 * @return
//	 * @author pangguowei
//	 */
//	private HttpJsonRequest buildNettyMessage(RpcfxRequest req, String url) {
//		String jsonStr = JSONObject.toJSONString(req);
//		ByteBuf encodeBuf = Unpooled.copiedBuffer(jsonStr, Charset.forName("utf-8"));
//		FullHttpRequest httpRequest = new DefaultFullHttpRequest(HttpVersion.HTTP_1_0, HttpMethod.POST, url, encodeBuf);
//		httpRequest.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
//		httpRequest.headers().set(HttpHeaderNames.CONTENT_TYPE, "application/json; charset=utf-8");
////		HttpJsonRequest jsonRequest = new HttpJsonRequest(httpRequest, req);
//		return jsonRequest;
//	}

}
