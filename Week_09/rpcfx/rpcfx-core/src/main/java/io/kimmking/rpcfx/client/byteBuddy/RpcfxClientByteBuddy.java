package io.kimmking.rpcfx.client.byteBuddy;

import io.kimmking.rpcfx.client.RpcfxClient;
import io.kimmking.rpcfx.client.RpcfxInvocationHandler;
import io.kimmking.rpcfx.connector.Connector;
import io.kimmking.rpcfx.connector.okhttp.OkHttpConnector;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.InvocationHandlerAdapter;

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
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T create(Class<T> serviceClass, String url) {
		T result = null;
		try {
			result = (T) new ByteBuddy()
					.subclass(Object.class)
					.implement(serviceClass)
					.intercept(InvocationHandlerAdapter.of(new RpcfxInvocationHandler(serviceClass, url, connector)))
					.make()
					.load(RpcfxClientByteBuddy.class.getClassLoader())
					.getLoaded()
					.getDeclaredConstructor()
					.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
