package com.gerry.pang.server;

import com.gerry.pang.grpc.api.HelloRequest;
import com.gerry.pang.grpc.api.HelloResponse;
import com.gerry.pang.grpc.api.HelloServiceGrpc.HelloServiceImplBase;

import io.grpc.stub.StreamObserver;

public class HelloServiceImpl extends HelloServiceImplBase {
	
	private StreamObserver<HelloResponse> responseObj;

	@Override
	public StreamObserver<HelloRequest> sayHello(StreamObserver<HelloResponse> responseObserver) {
		this.responseObj = responseObserver;
		return new StreamObserver<HelloRequest>() {

			@Override
			public void onNext(HelloRequest value) {
				System.out.println("[收到客户端消息]: " + value.getMessageData());
				responseObserver.onNext(HelloResponse.newBuilder().setMessageData("hello client ,I'm Java grpc Server,your message '" + value.getMessageData() + "'").build());
			}

			@Override
			public void onError(Throwable throwable) {
				throwable.fillInStackTrace();
			}

			@Override
			public void onCompleted() {
				responseObserver.onCompleted();
			}
		};
	}
	
	public StreamObserver<HelloResponse> getResponseObj() {
		return responseObj;
	}
}
