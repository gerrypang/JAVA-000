package io.kimmking.rpcfx.demo.consumer;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.kimmking.rpcfx.client.RpcfxClient;
import io.kimmking.rpcfx.client.jdk.RpcfxClientJDK;
import io.kimmking.rpcfx.connector.netty.NettyConnector;
import io.kimmking.rpcfx.demo.api.Order;
import io.kimmking.rpcfx.demo.api.OrderService;
import io.kimmking.rpcfx.demo.api.User;
import io.kimmking.rpcfx.demo.api.UserService;

@SpringBootApplication
public class RpcfxClientApplication {

	public static void main(String[] args) {

		RpcfxClient client = new RpcfxClientJDK(new NettyConnector());
		UserService userService = client.create(UserService.class, "http://localhost:8080/");
		User user = userService.findById(1);
		System.out.println("find user id=1 from server: " + user.getName());

		OrderService orderService = client.create(OrderService.class, "http://localhost:8080/");
		Order order = orderService.findOrderById(1992129);
		System.out.println(String.format("find order name=%s, amount=%f", order.getName(), order.getAmount()));

	}

}
