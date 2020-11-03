package io.github.kimmking.client.okhttp;

import java.io.IOException;
import java.util.Map;

import io.github.kimmking.client.AbstractRestOperation;
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
                	System.out.println("===> " + response.body().string());
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
}
