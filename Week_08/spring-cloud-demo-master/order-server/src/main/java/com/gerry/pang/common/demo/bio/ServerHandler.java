package com.gerry.pang.common.demo.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ServerHandler implements Runnable {

	private Socket socket;

	public ServerHandler(Socket socket) {
		super();
		this.socket = socket;
	}

	@Override
	public void run() {
		BufferedReader in = null;
		PrintWriter out = null;

		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);
			String message = null;
			String result = null;

			while (true) {
				if ((message = in.readLine()) != null) {
					log.info("server receive message :{}", message);
					result = Calculator.call(message);
					// 注意由于是readline操作 这里 要是使用out.println方法，否则读取会一直阻塞
					out.println(result);
				} else {
					break;
				}
			}

		} catch (Exception e) {
			log.error("ServerHandler run error:{}", e);
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

}
