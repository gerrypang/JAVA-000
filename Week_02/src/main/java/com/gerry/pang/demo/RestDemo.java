package com.gerry.pang.demo;

import java.util.Collections;

import com.gerry.pang.client.RestOperation;
import com.gerry.pang.client.httpclient.HttpClientRestOpreation;
import com.gerry.pang.client.okhttp.OkHttpRestOpreation;

public class RestDemo {
	public static void main(String[] args) {
		final String URL = "http://127.0.0.1:8088/api/hello";
		// HttpClient
		RestOperation httpClient = new HttpClientRestOpreation();
		httpClient.doGet(URL, Collections.emptyMap(), Collections.emptyMap());
		
		// OkHttp
		RestOperation okHttp = new OkHttpRestOpreation();
		okHttp.doGet(URL, Collections.emptyMap(), Collections.emptyMap());
	}
}
