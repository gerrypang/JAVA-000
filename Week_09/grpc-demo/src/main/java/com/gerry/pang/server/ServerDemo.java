package com.gerry.pang.server;

import java.util.Scanner;

import com.gerry.pang.grpc.api.HelloResponse;

import io.grpc.Server;
import io.grpc.ServerBuilder;

public class ServerDemo {
	public static void main(String[] args) {
		System.out.println("====== Server start ======");
		ServerBuilder<?> serverBuilder = ServerBuilder.forPort(8899);
		HelloServiceImpl helloServiceImpl = new HelloServiceImpl();
		serverBuilder.addService(helloServiceImpl);
		Server server = serverBuilder.build();
		try {
			server.start();
			new Thread(() -> {
				Scanner scanner = new Scanner(System.in);
				while(true) {
					String str = scanner.nextLine();
					if (str.equals("EOF")) {
						System.out.println("====== Server exit ======");
						break;
					}
					try {
						helloServiceImpl.getResponseObj().onNext(HelloResponse.newBuilder().setMessageData(str).build());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}).start();
			server.awaitTermination();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
