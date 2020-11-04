package io.github.kimmking.gateway.client.httpclient;

import static io.netty.handler.codec.http.HttpResponseStatus.NO_CONTENT;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.kimmking.gateway.client.AbstractRestOperation;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpUtil;

/**
 * Appache HttpClient方式
 * 
 * @author pangguowei
 * @since 2020年10月28日 
 */
public class HttpClientRestOpreation extends AbstractRestOperation {
	private static Logger logger = LoggerFactory.getLogger(HttpClientRestOpreation.class);
	
	private CloseableHttpClient client;

	@Override
	public void post(String url, Map<String, String> headerVariables, Map<String, String> urlVariables) {
		// TODO 待扩展
	}

	@Override
	public void get(String url, Map<String, String> headerVariables, Map<String, String> urlVariables) {
		// 配置请求参数
		String requstPathParam = this.setRequstPathParam(urlVariables);
		if (requstPathParam != null && requstPathParam.length() > 0) {
			url += requstPathParam;
		}
		// 创建 HttpGet 请求
		HttpGet httpGet = new HttpGet(url);
		// 配置请求头
		this.setHeader(httpGet, headerVariables);
		try (
			// 请求并获得响应结果
			CloseableHttpResponse response = client.execute(httpGet);) {
			this.handleResponse(this.getFullRequest(), this.getChannelHandlerContext(), response);
		} catch (Exception e) {
			logger.error("httpClient get request Excepiton:{}", e);
		}
	}
	
	/**
	 * 创建客户端
	 *
	 * @return
	 * @author pangguowei
	 * @since 2020年10月27日  
	 */
	@Override
	public void createClient() {
		client = HttpClients.createDefault();
	}

	/**
	 * 设置请求头	
	 * 
	 * @param httpGet
	 * @param headerVariables
	 * @author pangguowei
	 * @since 2020年10月27日  
	 */
	private void setHeader(HttpGet httpGet, Map<String, String> headerVariables) {
		if (headerVariables != null && headerVariables.size() > 0) {
			Iterator<Entry<String, String>> iter = headerVariables.entrySet().iterator();
			while (iter.hasNext()) {
				Entry<String, String> entry = iter.next();
				httpGet.setHeader(entry.getKey(), entry.getValue());
			}
		}
	}
	
	private void handleResponse(final FullHttpRequest fullRequest, final ChannelHandlerContext ctx, final CloseableHttpResponse httpResponse) throws Exception {
        FullHttpResponse response = null;
        try {
            byte[] body = EntityUtils.toByteArray(httpResponse.getEntity());
            response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(body));
            response.headers().set("Content-Type", "application/json");
            response.headers().setInt("Content-Length", Integer.parseInt(httpResponse.getFirstHeader("Content-Length").getValue()));
        } catch (Exception e) {
            response = new DefaultFullHttpResponse(HTTP_1_1, NO_CONTENT);
            exceptionCaught(ctx, e);
        } finally {
            if (fullRequest != null) {
                if (!HttpUtil.isKeepAlive(fullRequest)) {
                    ctx.write(response).addListener(ChannelFutureListener.CLOSE);
                } else {
                    //response.headers().set(CONNECTION, KEEP_ALIVE);
                    ctx.write(response);
                }
            }
            ctx.flush();
            //ctx.close();
        }
    }
    
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.error("处理相应发生异常", cause.getMessage());
        ctx.close();
    }
	 

}
