package com.gerry.pang.common.demo.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Server {

	private static ServerSocket serverSocket;

	public static void start(int port) {
		if (serverSocket != null) {
			return;
		}

		try {
			serverSocket = new ServerSocket(port);
			while (true) {
				Socket client = serverSocket.accept();
				log.info("==> 接收客户端连接");
				new Thread(new ServerHandler(client)).start();
			}
		} catch (Exception e) {
			log.error("server start error:{}", e);
		} finally {
			if (serverSocket != null) {
				try {
					serverSocket.close();
					serverSocket = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("===== start server =====");
				Server.start(9990);
			}
		}).start();

		Thread.sleep(10000);
	}
}
