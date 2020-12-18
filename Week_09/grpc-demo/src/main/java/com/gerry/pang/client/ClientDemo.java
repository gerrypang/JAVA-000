package com.gerry.pang.client;

import java.util.Scanner;

import com.gerry.pang.grpc.api.HelloRequest;
import com.gerry.pang.grpc.api.HelloResponse;
import com.gerry.pang.grpc.api.HelloServiceGrpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

public class ClientDemo {
	public static void main(String[] args) throws InterruptedException {
		ManagedChannelBuilder<?> channelBuilder = ManagedChannelBuilder.forAddress("localhost", 8899).usePlaintext();
		ManagedChannel channel= channelBuilder.build();
		System.out.println("====== Client start ======");
		
		StreamObserver<HelloRequest> requestObserver = HelloServiceGrpc.newStub(channel).sayHello(new StreamObserver<HelloResponse>() {

			@Override
			public void onNext(HelloResponse value) {
				System.out.println("[收到服务端发来] : " + value.getMessageData());
			}

			@Override
			public void onError(Throwable throwable) {
				throwable.printStackTrace();
			}

			@Override
			public void onCompleted() {
				
			}
		});
		
		 Scanner scanner = new Scanner(System.in);
		 while (true) {
			String str = scanner.nextLine();
			if (str.equals("EOF")) {
				requestObserver.onCompleted();
				System.out.println("====== Client exit ======");
				break;
			}
			
			try {
                requestObserver.onNext(HelloRequest.newBuilder().setMessageData(str).build());
            } catch (Exception e) {
                e.printStackTrace();
            }
		}
	}
}
