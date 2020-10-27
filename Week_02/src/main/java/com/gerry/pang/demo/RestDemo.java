package com.gerry.pang.demo;

import java.util.Collections;

import com.gerry.pang.client.httpclient.HttpClientRestOpreation;

public class RestDemo {
	public static void main(String[] args) {
		new HttpClientRestOpreation().doGet("http://127.0.0.1:8088/api/hello", Collections.emptyMap(), Collections.emptyMap());
	}
}
