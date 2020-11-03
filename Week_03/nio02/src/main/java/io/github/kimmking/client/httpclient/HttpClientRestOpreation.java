package io.github.kimmking.client.httpclient;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import io.github.kimmking.client.AbstractRestOperation;

/**
 * Appache HttpClient方式
 * 
 * @author pangguowei
 * @since 2020年10月28日 
 */
public class HttpClientRestOpreation extends AbstractRestOperation {
	
	private CloseableHttpClient client;

	@Override
	public void post(String url, Map<String, String> headerVariables, Map<String, String> urlVariables) {
		// TODO 待扩展
	}

	@Override
	public void get(String url, Map<String, String> headerVariables, Map<String, String> urlVariables) {
		// 配置请求参数
		String requstPathParam = this.setRequstPathParam(urlVariables);
		url += requstPathParam;
		// 创建 HttpGet 请求
		HttpGet httpGet = new HttpGet(url);
		// 配置请求头
		this.setHeader(httpGet, headerVariables);
		
		try (
			// 请求并获得响应结果
			CloseableHttpResponse response = client.execute(httpGet);) {
			HttpEntity httpEntity = response.getEntity();
			// 输出请求结果
			System.out.println("===> " + EntityUtils.toString(httpEntity));
		} catch (Exception e) {
			System.err.println("httpClient get request Excepiton:" + e.getMessage());
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
	 

}
