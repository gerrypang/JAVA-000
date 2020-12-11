package com.gerry.pang.common.demo.bio;

import java.util.Random;

public class Test {

	public static void main(String[] args) throws InterruptedException {
		Random random = new Random(System.currentTimeMillis());
		new Thread(new Runnable() {

			@Override
			public void run() {
				System.out.println("===== start server =====");
				Server.start(9999);
			}
		}).start();

		Thread.sleep(10000);

//		new Thread(new Runnable() {
//
//			@Override
//			public void run() {
				int i = random.nextInt(10);
				System.out.println("===== start client =====" + i);
				Client.sendMessage("hello world " + i+"\n");
//			}
//		}).start();

	}
}
