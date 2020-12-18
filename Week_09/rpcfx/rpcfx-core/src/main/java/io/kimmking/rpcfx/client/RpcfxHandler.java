package io.kimmking.rpcfx.client;

import java.io.IOException;

import org.aspectj.lang.annotation.Aspect;

import io.kimmking.rpcfx.api.RpcfxRequest;
import io.kimmking.rpcfx.api.RpcfxResponse;

@Aspect
public final class RpcfxHandler<T> {
	private final Class<?> serviceClass;
	private final String url;

	public RpcfxHandler(Class<T> serviceClass, String url) {
		this.serviceClass = serviceClass;
		this.url = url;
	}

	public Object invoke() throws Throwable {
		
//		RpcfxRequest request = new RpcfxRequest();
//		request.setServiceClass(this.serviceClass.getName());
//		request.setMethod(method.getName());
//		request.setParams(params);
//
//		RpcfxResponse response = post(request, url);
//
//		// 这里判断response.status，处理异常
//		// 考虑封装一个全局的RpcfxException
//
//		return JSON.parse(response.getResult().toString());
		
		return null;
	}
	
	public void beforeInvoke() {
		
	}

	public void afterInvoke() {
		
	}

	private RpcfxResponse post(RpcfxRequest req, String url) throws IOException {
		return null;
//		String reqJson = JSON.toJSONString(req);
//		System.out.println("req json: " + reqJson);
//
//		// 1.可以复用client
//		// 2.尝试使用httpclient或者netty client
//		OkHttpClient client = new OkHttpClient();
//		final Request request = new Request.Builder().url(url).post(RequestBody.create(JSONTYPE, reqJson)).build();
//		String respJson = client.newCall(request).execute().body().string();
//		System.out.println("resp json: " + respJson);
//		return JSON.parseObject(respJson, RpcfxResponse.class);
	}

}
