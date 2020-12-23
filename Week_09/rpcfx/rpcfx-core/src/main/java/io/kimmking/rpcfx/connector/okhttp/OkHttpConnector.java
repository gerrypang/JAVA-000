package io.kimmking.rpcfx.connector.okhttp;

import java.io.IOException;

import com.alibaba.fastjson.JSON;

import io.kimmking.rpcfx.api.RpcfxRequest;
import io.kimmking.rpcfx.api.RpcfxResponse;
import io.kimmking.rpcfx.connector.Connector;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class OkHttpConnector implements Connector {
	
	public static final MediaType JSONTYPE = MediaType.get("application/json; charset=utf-8");

	@Override
	public RpcfxResponse sendRequest(RpcfxRequest req, String url) throws IOException {
		String reqJson = JSON.toJSONString(req);
        System.out.println("req json: " + reqJson);

        // 1.可以复用client
        // 2.尝试使用httpclient或者netty client
        OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(JSONTYPE, reqJson))
                .build();
        String respJson = client.newCall(request).execute().body().string();
        System.out.println("resp json: " + respJson);
        return JSON.parseObject(respJson, RpcfxResponse.class);
	}

}
