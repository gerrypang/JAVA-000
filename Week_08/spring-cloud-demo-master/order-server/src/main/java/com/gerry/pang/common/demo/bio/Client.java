package com.gerry.pang.common.demo.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Client {
	public static final int SERVER_PORT = 9990;
	public static final String SERVER_IP = "127.0.0.1";

	public static void sendMessage(String message) {
		sendToServer(message, SERVER_IP, SERVER_PORT);
	}

	private static void sendToServer(String message, String serverIp, int serverPort) {
		Socket socket = null;
		BufferedReader in = null;
		PrintWriter out = null;

		try {
			socket = new Socket(serverIp, serverPort);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);

			out.println(message);
			log.info("==> 返回結果：{}", in.readLine());

		} catch (Exception e) {
			log.error("send to server error：{}", e);
		} finally {
			if (socket != null) {
				try {
					socket.close();
					socket = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (in != null) {
				try {
					in.close();
					in = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (out != null) {
				out.close();
				out = null;
			}
		}
	}
	
	public static void main(String[] args) {
		Random random = new Random(System.currentTimeMillis());
		int i = random.nextInt(10);
		System.out.println("===== start client =====" + i);
		Client.sendMessage("hello world " + i);
	}
}
