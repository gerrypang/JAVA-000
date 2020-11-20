package com.gerry.pang.singleton;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SingletonTest {
	
	public static void main(String[] args) {
		int size = 5;
		CountDownLatch countDownLatch = new CountDownLatch(1);
		ExecutorService executor = Executors.newFixedThreadPool(size);
		for (int i = 0; i < size; i++) {
			executor.execute(new Runnable() {
				@Override
				public void run() {
					try {
						countDownLatch.await();
						System.out.println(EnumSingleton.getInstance() + " -- " + System.currentTimeMillis());
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
		}
		countDownLatch.countDown();
		executor.shutdown();
	}
}
