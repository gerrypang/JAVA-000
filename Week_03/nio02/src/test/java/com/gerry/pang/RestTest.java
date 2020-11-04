package com.gerry.pang;

import java.util.Collections;

import io.github.kimmking.gateway.client.AbstractRestOperation;
import io.github.kimmking.gateway.client.httpclient.HttpClientRestOpreation;
import io.github.kimmking.gateway.client.okhttp.OkHttpRestOpreation;

public class RestTest {
	public static void main(String[] args) {
		final String URL = "http://127.0.0.1:8088/api/hello";
		// HttpClient
		AbstractRestOperation httpClient = new HttpClientRestOpreation();
		httpClient.doGet(URL, Collections.emptyMap(), Collections.emptyMap());
		
		// OkHttp
		AbstractRestOperation okHttp = new OkHttpRestOpreation();
		okHttp.doGet(URL, Collections.emptyMap(), Collections.emptyMap());
	}
}
