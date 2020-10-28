package com.gerry.pang.client;

import java.util.Map;

/**
 * rest接口
 * 
 * @author pangguowei
 * @since 2020年10月27日 
 */
public interface RestOperation {
	/**
	 * 创建客户端
	 *
	 * @return
	 */
	public void createClient();
	
	/**
	 * post 请求
	 *
	 * @param url
	 * @param headerVariables
	 * @param urlVariables
	 */
	public void doPost(String url, Map<String, String> headerVariables, Map<String, String> urlVariables);
	
	/**
	 * get 请求
	 *
	 * @param url
	 * @param headerVariables
	 * @param urlVariables
	 */
	public void doGet(String url, Map<String, String> headerVariables, Map<String, String> urlVariables);
}
