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

@SpringBootApplication
public class RpcfxClientApplication {

	public static void main(String[] args) {

		System.out.println("============ jdk 动态代理方式 ==============start");
		RpcfxClient client1 = new RpcfxClientJDK(new NettyClientConnector());
		UserService userService1= client1.create(UserService.class, "http://localhost:8889/");
		User user1 = userService1.findById(1);
		System.out.println("find user id=1 from server: " + user1.getName());
		
		OrderService orderService = client1.create(OrderService.class, "http://localhost:8080/");
		Order order = orderService.findOrderById(1992129);
		System.out.println(String.format("find order name=%s, amount=%f", order.getName(), order.getAmount()));

		System.out.println("============ jdk 动态代理方式 ==============end");
		System.out.println("============ ByteBuddy AOP 方式 ==============start");
		RpcfxClient client2 = new RpcfxClientByteBuddy(new NettyClientConnector());
		UserService userService2 = client2.create(UserService.class, "http://localhost:8080/");
		User user2 = userService2.findById(1);
		System.out.println("find user id=1 from server: " + user2.getName());
		System.out.println("============ ByteBuddy AOP 方式 ==============end");

	}

}
