package com.gerry.pang.common.rest;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.http.HttpResponse;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.springframework.stereotype.Component;

/**
 * restTemplate 链接超时策略
 * 
 * @author Gerry_Pang
 */
@Component
public class CustomConnectionKeepAliveStrategy implements ConnectionKeepAliveStrategy {

	private final long DEFAULT_SECONDS = 30;

	@Override
	public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
		return Arrays.asList(response.getHeaders(HTTP.CONN_KEEP_ALIVE))
				.stream()
				.filter(n -> StringUtils.equalsIgnoreCase(n.getName(), "timeout")
						&& StringUtils.isNumeric(n.getValue()))
				.findFirst()
				// 修改原有超时为30s
				.map(n -> NumberUtils.toLong(n.getValue(), DEFAULT_SECONDS))
				// 设置默认超时为30s
				.orElse(DEFAULT_SECONDS) * 1000;
	}

}
