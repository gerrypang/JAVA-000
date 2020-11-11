package com.gerry.pang;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 方法4：使用Runnable接口 + 线程池 + CyclicBarrier + 全局变量
 * 
 * @author pangguowei
 * @since 2020年11月11日
 */
public class Demo4 {
	private static final int NUM = 1;
	
	private static List<Integer> result = new ArrayList<>();

	public static void main(String[] args) {
		System.out.println("=== Demo4 Main Thread Start! ===");
		long start = System.currentTimeMillis();
		CyclicBarrier cyclicBarrier = new CyclicBarrier(NUM, new Runnable() {
			/**
			 * 执行结束后回调
			 */
			@Override
			public void run() {
				// 确保 拿到result 并输出
				System.out.println("异步计算结果为：" + result.get(0));
			}
		});
		BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(100);
		int corePoolSize = Runtime.getRuntime().availableProcessors();
		ExecutorService executor = new ThreadPoolExecutor(corePoolSize, corePoolSize + 1, 1000, TimeUnit.SECONDS, workQueue);
		executor.execute(new RunnableThread(cyclicBarrier));
		
		System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
		// 然后退出main线程
		executor.shutdown();
		System.out.println("=== Demo4 Main Thread End! ===");
	}

	public static class RunnableThread implements Runnable {
		private CyclicBarrier cyclicBarrier  = null;

		public RunnableThread(CyclicBarrier cyclicBarrier) {
			super();
			this.cyclicBarrier = cyclicBarrier;
		}
		
		@Override
		public void run() {
			try {
				result.add(this.sum());
				cyclicBarrier.await();
			} catch (Exception e) {
				System.err.println(e.getMessage());
			} 
		}
		
		private int sum() {
			return fibo(36);
		}

		private int fibo(int a) {
			if (a < 2)
				return 1;
			return fibo(a - 1) + fibo(a - 2);
		}
	}

}
