package io.kimmking.rpcfx.demo.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import io.kimmking.rpcfx.api.RpcfxResolver;
import io.kimmking.rpcfx.connector.netty.server.NettyServer;
import io.kimmking.rpcfx.demo.provider.resolver.SimpleResolver;
import io.kimmking.rpcfx.server.RpcfxInvoker;

@SpringBootApplication
public class RpcfxServerApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext appContext = SpringApplication.run(RpcfxServerApplication.class, args);
		System.out.println("==== RpcfxServerApplication start success =====");
		NettyServer server = new NettyServer(8889, 1, 5);
		try {
			server.start(appContext.getBean(SimpleResolver.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Bean
	public RpcfxInvoker createInvoker(@Autowired RpcfxResolver resolver) {
		return new RpcfxInvoker(resolver);
	}

	@Bean
	public RpcfxResolver createResolver() {
		return new SimpleResolver();
	}

}
