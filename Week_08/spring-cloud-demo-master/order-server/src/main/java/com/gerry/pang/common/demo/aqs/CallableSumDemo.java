package com.gerry.pang.common.demo.aqs;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class CallableSumDemo {

	private static final int NUM = 4;

	public static void main(String[] args) {
		List<Future<Integer>> resultList = new ArrayList<>(4);
		BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>(100);
		int corePoolSize = Runtime.getRuntime().availableProcessors();
		ExecutorService executor = new ThreadPoolExecutor(corePoolSize, corePoolSize + 1, 1000, TimeUnit.SECONDS, workQueue);
		for (int i = 0; i < NUM; i++) {
			Future<Integer> oneResult = executor.submit(new Runner());
			resultList.add(oneResult);
		}
		System.out.println("==============");
		int sum = 0;
		try {
			for (Future<Integer> future : resultList) {
				sum += future.get().intValue();
			}
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		System.out.println(sum);
		executor.shutdown();
	}

	public static class Runner implements Callable<Integer> {
		@Override
		public Integer call() throws Exception {
			int a = new Random().nextInt(10);
			System.out.println(Thread.currentThread().getName() + ": " + a);
			return a;
		}
	}

}
