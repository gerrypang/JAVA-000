package com.gerry.pang.client.httpclient;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.gerry.pang.client.RestOperation;

public class HttpClientRestOpreation implements RestOperation {

	@Override
	public void doPost(String url, Map<String, String> headerVariables, Map<String, String> urlVariables) {
		// TODO 待扩展
	}

	@Override
	public void doGet(String url, Map<String, String> headerVariables, Map<String, String> urlVariables) {
		// 如果请求带有参数配置参数
		String requstPathParam = this.setRequstPathParam(urlVariables);
		url = url + "?" + requstPathParam;
		// 创建 HttpGet 请求
		HttpGet httpGet = new HttpGet(url);
		// 配置请求头
		this.setHeader(httpGet, headerVariables);
		try (
				// 请求并获得响应结果
				CloseableHttpResponse httpResponse = getHttpClient().execute(httpGet);) {
			HttpEntity httpEntity = httpResponse.getEntity();
			// 输出请求结果
			 System.out.println("===> " + EntityUtils.toString(httpEntity));
		} catch (Exception e) {
			System.err.println("httpClient get request Excepiton:" + e.getMessage());
		}
	}
	/**
	 * 创建 HttpClient 客户端
	 *
	 * @return
	 * @author pangguowei
	 * @since 2020年10月27日  
	 */
	public CloseableHttpClient getHttpClient() {
		return HttpClients.createDefault();
	}

	/**
	 * 设置请求头	 *
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
	
	/**
	 * 请求参数配置
	 *
	 * @param urlVariables
	 * @author pangguowei
	 * @since 2020年10月27日 
	 */
	private String setRequstPathParam(Map<String, String> urlVariables) {
		String removeLastChar = "";
		if (urlVariables != null && urlVariables.size() > 0) {
			StringBuilder pathParam = new StringBuilder(32);
			Iterator<Entry<String, String>> iter = urlVariables.entrySet().iterator();
			while (iter.hasNext()) {
				Entry<String, String> entry = iter.next();
				pathParam.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
			}
			removeLastChar = pathParam.substring(0, pathParam.length() - 1);
		}
		return removeLastChar;
	}

}
