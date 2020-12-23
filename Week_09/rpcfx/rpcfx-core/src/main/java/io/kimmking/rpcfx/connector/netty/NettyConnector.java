package io.kimmking.rpcfx.connector.netty;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import com.alibaba.fastjson.JSONObject;

import io.kimmking.rpcfx.api.RpcfxRequest;
import io.kimmking.rpcfx.api.RpcfxResponse;
import io.kimmking.rpcfx.connector.Connector;
import io.kimmking.rpcfx.connector.netty.client.NettyClient;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.FullHttpMessage;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;

public class NettyConnector implements Connector {

	@Override
	public RpcfxResponse sendRequest(RpcfxRequest req, String url) throws IOException {
		try {
			URI uri = new URI(url);
			final int port = uri.getPort();
			final String host = uri.getHost();
			NettyClient client = new NettyClient(host, port, 1);
			Channel channel = client.start();
			FullHttpMessage request = this.buildNettyMessage(req, url);
			ChannelFuture future = channel.writeAndFlush(request);
			 
			channel.closeFuture().sync();
			
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 *  把msg转换为FullHttpRequest
	 *
	 * @param req
	 * @param url
	 * @return
	 * @author pangguowei
	 */
	private FullHttpMessage buildNettyMessage(RpcfxRequest req, String url) {
		FullHttpMessage request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_0, 
				HttpMethod.POST, url,
				Unpooled.copiedBuffer(JSONObject.toJSONString(req), CharsetUtil.UTF_8));
		request.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
		request.headers().set(HttpHeaderNames.CONTENT_TYPE, "application/json; charset=utf-8");
		request.headers().set(HttpHeaderNames.CONTENT_LENGTH, request.content().readableBytes());
		return request;
	}

}
