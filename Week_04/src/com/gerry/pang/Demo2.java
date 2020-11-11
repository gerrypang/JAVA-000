package com.gerry.pang;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 方法2：使用Callable接口 + 线程池
 * 
 * @author pangguowei
 * @since 2020年11月11日
 */
public class Demo2 {

	public static void main(String[] args) {
		System.out.println("=== Demo2 Main Thread Start! ===");
		long start = System.currentTimeMillis();
		
		BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(10);
		int corePoolSize = Runtime.getRuntime().availableProcessors();
		ExecutorService executor = new ThreadPoolExecutor(corePoolSize, corePoolSize + 1, 1000, TimeUnit.SECONDS, workQueue);

		Callable<Integer> callable = new CallableThread();
		FutureTask<Integer> task = new FutureTask<>(callable); 
		executor.execute(task);
		// 确保 拿到result 并输出
		try {
			System.out.println("异步计算结果为：" + task.get());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		
		System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
		// 然后退出main线程
		executor.shutdown();
		System.out.println("=== Demo2 Main Thread End! ===");
	}

	public static class CallableThread implements Callable<Integer> {
		@Override
		public Integer call() throws Exception {
			return sum();
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
