package com.gerry.pang.common.demo.aqs;

import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.apache.tomcat.util.threads.ThreadPoolExecutor;

public class CountDownSumDemo {
	private static final int NUM = 4;
	
	private static final List<Integer> resultList = new CopyOnWriteArrayList<>();

	public static void main(String[] args) {
		CountDownLatch countDown = new CountDownLatch(NUM);
		BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>(100);
		int corePoolSize = Runtime.getRuntime().availableProcessors();
		ExecutorService executor = new ThreadPoolExecutor(corePoolSize, corePoolSize + 1, 1000, TimeUnit.SECONDS, workQueue);
		try {
			for (int i = 0; i < NUM; i++) {
				executor.execute(new Runner(countDown));
			}
			countDown.await();
			int sum = 0;
			for (Integer one : resultList) {
				sum += one.intValue();
			}
			System.out.println(sum);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		executor.shutdown();
	}

	public static class Runner implements Runnable {
		private CountDownLatch countDown = null;

		public Runner(CountDownLatch countDown) {
			super();
			this.countDown = countDown;
		}

		@Override
		public void run() {
			try {
				int a = new Random().nextInt(10);
				System.out.println(Thread.currentThread().getName() + ": " + a);
				resultList.add(a);
			} catch (Exception e) {
				System.err.println(e);
			} finally {
				countDown.countDown();
			}
		}
	}
}
