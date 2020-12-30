package io.kimmking.rpcfx.connector.netty;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import io.kimmking.rpcfx.api.RpcfxRequest;
import io.kimmking.rpcfx.api.RpcfxResponse;
import io.kimmking.rpcfx.connector.Connector;
import io.kimmking.rpcfx.connector.netty.client.NettyClient;
import io.kimmking.rpcfx.utils.GuavaCacheManager;
import io.kimmking.rpcfx.utils.SyncFuture;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

// https://segmentfault.com/a/1190000020507086
// https://www.cnblogs.com/carl10086/p/6185095.html
public class NettyClientConnector implements Connector {
	
	private static final Log log = LogFactory.getLog(NettyClientConnector.class);
	
	private ChannelInitializer initializerHandler;
	
	public void setInitializerHandler(ChannelInitializer initializerHandler) {
		this.initializerHandler = initializerHandler;
	}

	@Override
	public RpcfxResponse sendRequest(RpcfxRequest req, String url) throws IOException {
		RpcfxResponse result = null;
		try {
			URI uri = new URI(url);
			final int port = uri.getPort();
			final String host = uri.getHost();
			NettyClient client = new NettyClient(host, port, initializerHandler);
			Channel channel = client.start();
			SyncFuture<RpcfxResponse> syncFuture = new SyncFuture<>();
			System.out.println("====" + channel.id().asShortText());
			GuavaCacheManager.getFutureCache().put(channel.id().asShortText(), syncFuture);
			ChannelFuture channelFuture = client.sendMessage(req, url);
			
			channelFuture.addListener(new GenericFutureListener<Future<? super Void>>() {
				@Override
				public void operationComplete(Future<? super Void> future) throws Exception {
					if (future.isSuccess()) {
						log.info("NettyConnector.sendRequest 发送成功");
					} else {
						log.error("NettyConnector.sendRequest 发送失败， cause: " + future.cause());
					}
				}
			});
			 
			result = syncFuture.get(8, TimeUnit.SECONDS);
			log.info("==> sendRequest result: " + result.getResult());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
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
