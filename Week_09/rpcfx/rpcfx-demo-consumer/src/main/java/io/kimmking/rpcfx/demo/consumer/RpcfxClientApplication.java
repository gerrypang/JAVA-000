package io.kimmking.rpcfx.demo.consumer;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.kimmking.rpcfx.client.RpcfxClient;
import io.kimmking.rpcfx.client.byteBuddy.RpcfxClientByteBuddy;
import io.kimmking.rpcfx.client.jdk.RpcfxClientJDK;
import io.kimmking.rpcfx.connector.netty.NettyClientConnector;
import io.kimmking.rpcfx.demo.api.Order;
import io.kimmking.rpcfx.demo.api.OrderService;
import io.kimmking.rpcfx.demo.api.User;
import io.kimmking.rpcfx.demo.api.UserService;
import io.kimmking.rpcfx.enums.NettySocketEnum;
import io.kimmking.rpcfx.protocol.customrpc.CustomChannelInitializer;

@SpringBootApplication
public class RpcfxClientApplication {

	public static void main(String[] args) {
//		jdkHttpClient();
//		byteBuddyHttpClient();
//		jdkNetty();
//		byteBuddyNetty();
		byteBuddyNettyByte();
	}
	
	/**
	 * jdk动态代理 + httpClient + json
	 */
	public static void jdkHttpClient() {
		System.out.println("============ jdk动态代理 + httpClient + json ==============start");
		RpcfxClient client = new RpcfxClientJDK();
		
		UserService userService= client.create(UserService.class, "http://localhost:8080/");
		User user = userService.findById(1);
		System.out.println("find user id=1 from server: " + user.getName());
		
		OrderService orderService = client.create(OrderService.class, "http://localhost:8080/");
		Order order = orderService.findOrderById(1992129);
		System.out.println(String.format("find order name=%s, amount=%f", order.getName(), order.getAmount()));
		System.out.println("============ jdk动态代理 + httpClient + json ==============end");
	}
	
	/**
	 * byteBuddy + httpClient + json
	 */
	public static void byteBuddyHttpClient() {
		System.out.println("============ byteBuddy + httpClient + json ==============start");
		RpcfxClient client = new RpcfxClientByteBuddy();
		
		UserService userService = client.create(UserService.class, "http://localhost:8080/");
		User user = userService.findById(1);
		System.out.println("find user id=1 from server: " + user.getName());
		
		OrderService orderService = client.create(OrderService.class, "http://localhost:8080/");
		Order order = orderService.findOrderById(1992129);
		System.out.println(String.format("find order name=%s, amount=%f", order.getName(), order.getAmount()));
		System.out.println("============ byteBuddy + httpClient + json ==============end");
	}
	
	/**
	 * jdk动态代理 + Netty + json
	 */
	public static void jdkNetty() {
		System.out.println("============ jdk动态代理 + Netty + json ==============start");
		RpcfxClient client = new RpcfxClientJDK(new NettyClientConnector());
		
		UserService userService= client.create(UserService.class, "http://localhost:8889/");
		User user = userService.findById(1);
		System.out.println("find user id=1 from server: " + user.getName());
		
		OrderService orderService = client.create(OrderService.class, "http://localhost:8889/");
		Order order = orderService.findOrderById(1992129);
		System.out.println(String.format("find order name=%s, amount=%f", order.getName(), order.getAmount()));
		System.out.println("============ jdk动态代理 + Netty + json ==============end");
		
	}

	/**
	 * byteBuddy + Netty + json
	 */
	public static void byteBuddyNetty() {
		System.out.println("============ byteBuddy + Netty + json 方式 ==============start");
		RpcfxClient client = new RpcfxClientByteBuddy(new NettyClientConnector());
		
		UserService userService = client.create(UserService.class, "http://localhost:8889/");
		User user = userService.findById(1);
		System.out.println("find user id=1 from server: " + user.getName());
		System.out.println("============ byteBuddy + Netty + json 方式 ==============end");
	}

	/**
	 * byteBuddy + Netty + customByte
	 */
	public static void byteBuddyNettyByte() {
		System.out.println("============ byteBuddy + Netty + customByte 方式 ==============start");
		NettyClientConnector connctor = new NettyClientConnector();
		connctor.setInitializerHandler(new CustomChannelInitializer(NettySocketEnum.CLIENT));
		RpcfxClient client = new RpcfxClientByteBuddy(connctor);
		
		UserService userService = client.create(UserService.class, "http://localhost:8887/");
		User user = userService.findById(1);
		System.out.println("find user id=1 from server: " + user.getName());
		System.out.println("============ byteBuddy + Netty + customByte 方式 ==============end");
	}

}
