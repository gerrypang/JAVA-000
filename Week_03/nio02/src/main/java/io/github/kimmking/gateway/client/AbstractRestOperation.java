package io.github.kimmking.gateway.client;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.kimmking.gateway.outbound.httpclient4.HttpAsyncClientOutboundHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

/**
 * Rest接口抽象类型
 * 
 * @author pangguowei
 * @since 2020年10月28日 下午5:43:23
 */
public abstract class AbstractRestOperation implements RestOperation  {
	
	private static Logger logger = LoggerFactory.getLogger(HttpAsyncClientOutboundHandler.class);
	
	private FullHttpRequest fullRequest;
	
	private ChannelHandlerContext channelHandlerContext;

	public void doPost(String url, Map<String, String> headerVariables, Map<String, String> urlVariables) {
		this.createClient();
		this.post(url, headerVariables, urlVariables);
	};

	public void doGet(String url, Map<String, String> headerVariables, Map<String, String> urlVariables) {
		this.createClient();
		this.get(url, headerVariables, urlVariables);
	}
	
	public void doGet(String url, Map<String, String> headerVariables ) {
		this.createClient();
		this.get(url, headerVariables, null);
	}
	
	/**
	 * 请求参数配置
	 *
	 * @param urlVariables
	 * @author pangguowei
	 * @since 2020年10月27日 
	 */
	public String setRequstPathParam(Map<String, String> urlVariables) {
		String removeLastChar = "";
		if (urlVariables != null && urlVariables.size() > 0) {
			StringBuilder pathParam = new StringBuilder(32);
			pathParam.append("?");
			Iterator<Entry<String, String>> iter = urlVariables.entrySet().iterator();
			while (iter.hasNext()) {
				Entry<String, String> entry = iter.next();
				pathParam.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
			}
			removeLastChar = pathParam.substring(0, pathParam.length() - 1);
		}
		return removeLastChar;
	}
	
	public FullHttpRequest getFullRequest() {
		return fullRequest;
	}

	public void setFullRequest(FullHttpRequest fullRequest) {
		this.fullRequest = fullRequest;
	}

	public ChannelHandlerContext getChannelHandlerContext() {
		return channelHandlerContext;
	}

	public void setChannelHandlerContext(ChannelHandlerContext channelHandlerContext) {
		this.channelHandlerContext = channelHandlerContext;
	}

}
