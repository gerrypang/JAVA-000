package io.kimmking.rpcfx.client.byteBuddy;

import io.kimmking.rpcfx.client.RpcfxClient;
import io.kimmking.rpcfx.connector.Connector;
import io.kimmking.rpcfx.connector.okhttp.OkHttpConnector;

public class RpcfxClientByteBuddy implements RpcfxClient {

	public final Connector connector;
	
	public RpcfxClientByteBuddy() {
		super();
		connector = new OkHttpConnector();
	}

	public RpcfxClientByteBuddy(Connector connector) {
		super();
		this.connector = connector;
	}
	
	@Override
	public <T> T create(Class<T> serviceClass, String url) {
		return null;
	}

}
