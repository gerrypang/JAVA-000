package io.kimmking.rpcfx.client;

public interface RpcfxClient {
	
	public <T> T create(final Class<T> serviceClass, final String url);
	
}
