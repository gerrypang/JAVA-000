package com.gerry.pang.client;

import java.util.Map;

/**
 * 
 * @author pangguowei
 * @since
 */
public interface RestOperation {
	
	public void doPost(String url, Map<String, String> headerVariables, Map<String, String> urlVariables);
	
	public void doGet(String url, Map<String, String> headerVariables, Map<String, String> urlVariables);
}
