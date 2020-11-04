package io.github.kimmking.gateway.client.okhttp;

import static io.netty.handler.codec.http.HttpResponseStatus.NO_CONTENT;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

import java.io.IOException;
import java.util.Map;

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
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * OkHttp方式
 * 
 * @author pangguowei
 * @since 2020年10月28日 
 */
public class OkHttpRestOpreation extends AbstractRestOperation {
	
	private static Logger logger = LoggerFactory.getLogger(OkHttpRestOpreation.class);
	
	private OkHttpClient client;
	
	@Override
	public void createClient() {
		client = new OkHttpClient();
	}

	@Override
	public void post(String url, Map<String, String> headerVariables, Map<String, String> urlVariables) {
		// TODO 待扩展
	}

	@Override
	public void get(String url, Map<String, String> headerVariables, Map<String, String> urlVariables) {
		// 配置请求参数
		String requstPathParam = this.setRequstPathParam(urlVariables);
		url += requstPathParam;
		// 创建一个Request
		Request request = new Request.Builder().get()
				// 配置header
				.headers(this.setHeader(headerVariables))
        		.url(url).build();
		 
        // 通过client发起请求
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            	System.err.println("httpClient get request Excepiton:" + e.getMessage());
            }
            
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                	try {
						handleResponse(getFullRequest(), getChannelHandlerContext(), response);
					} catch (Exception e) {
						e.printStackTrace();
					}
                }
            }
        });
	}
	
	/**
	 * 设置请求头	
	 * 
	 * @param httpGet
	 * @param headerVariables
	 * @author pangguowei
	 * @since 2020年10月27日  
	 */
	private Headers setHeader(Map<String, String> headerVariables) {
		Headers headerbuild = new Headers.Builder().build();
		if (headerVariables != null && headerVariables.size() > 0) {
			headerbuild = Headers.of(headerVariables);
		}
		return headerbuild;
	}
	
    private void handleResponse(final FullHttpRequest fullRequest, final ChannelHandlerContext ctx, final Response endpointResponse) throws Exception {
        FullHttpResponse response = null;
        try {
            byte[] body = endpointResponse.body().bytes();
            response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(body));
            response.headers().set("Content-Type", "application/json");
            response.headers().setInt("Content-Length", Integer.parseInt(endpointResponse.headers().get("Content-Length")));
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
