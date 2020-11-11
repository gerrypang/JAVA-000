package com.gerry.pang;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 方法3：使用Runnable接口 + 线程池 + CountDownLatch + 全局变量
 * 执行结果截图：https://github.com/gerrypang/JAVA-000/blob/main/Week_04/images/Demo3.png
 * 
 * @author pangguowei
 * @since 2020年11月11日
 */
public class Demo3 {
	private static final int NUM = 1;
	
	private static Integer result = new Integer(-1);

	public static void main(String[] args) {
		System.out.println("=== Demo3 Main Thread Start! ===");
		long start = System.currentTimeMillis();
		CountDownLatch countDown = new CountDownLatch(NUM);
		BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(100);
		int corePoolSize = Runtime.getRuntime().availableProcessors();
		ExecutorService executor = new ThreadPoolExecutor(corePoolSize, corePoolSize + 1, 1000, TimeUnit.SECONDS, workQueue);
		executor.execute(new RunnableThread(countDown));
		
		// 确保 拿到result 并输出
		try {
			countDown.await(10, TimeUnit.SECONDS);
			System.out.println("异步计算结果为：" + result);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
		// 然后退出main线程
		executor.shutdown();
		System.out.println("=== Demo3 Main Thread End! ===");
	}

	public static class RunnableThread implements Runnable {
		private CountDownLatch countDown = null;

		public RunnableThread(CountDownLatch countDown) {
			super();
			this.countDown = countDown;
		}
		
		@Override
		public void run() {
			try {
				result = this.sum();
			} catch (Exception e) {
				System.err.println(e.getMessage());
			} finally {
				countDown.countDown();
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
