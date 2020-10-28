package com.gerry.pang.client;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Rest接口抽象类型
 * 
 * @author pangguowei
 * @since 2020年10月28日 下午5:43:23
 */
public abstract class AbstractRestOperation implements RestOperation {

	/**
	 * 请求参数配置
	 *
	 * @param urlVariables
	 * @author pangguowei
	 * @since 2020年10月27日 
	 */
	public String setRequstPathParam(Map<String, String> urlVariables) {
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
