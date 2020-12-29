package io.kimmking.rpcfx.client.jdk;

import java.lang.reflect.Proxy;

import io.kimmking.rpcfx.client.RpcfxClient;
import io.kimmking.rpcfx.client.RpcfxInvocationHandler;
import io.kimmking.rpcfx.connector.Connector;
import io.kimmking.rpcfx.connector.okhttp.OkHttpConnector;

/**
 * JDK 动态代理
 * 
 * @author pangguowei
 */
public final class RpcfxClientJDK implements RpcfxClient {

	public final Connector connector;
	
	public RpcfxClientJDK() {
		super();
		connector = new OkHttpConnector();
	}

	public RpcfxClientJDK(Connector connector) {
		super();
		this.connector = connector;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T create(final Class<T> serviceClass, final String url) {
        return (T) Proxy.newProxyInstance(
        		RpcfxClientJDK.class.getClassLoader(), 
        		new Class[]{serviceClass}, 
        		new RpcfxInvocationHandler(serviceClass, url, connector));
    }

}
