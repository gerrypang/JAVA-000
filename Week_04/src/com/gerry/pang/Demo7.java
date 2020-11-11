package com.gerry.pang;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 方法7：使用Runnable接口 + ReentrantLock
 * 
 * @author pangguowei
 * @since 2020年11月11日
 */
public class Demo7 {
	private static Integer result = null;

	public static void main(String[] args) {
		System.out.println("=== Demo7 Main Thread Start! ===");
		long start = System.currentTimeMillis();
		Lock lock = new ReentrantLock();
		Condition condition = lock.newCondition();
		new Thread(new RunnableThread(lock, condition)).start();
		
		try {
			lock.lock();
			// 确保 拿到result 并输出
			while (result == null) {
				condition.await();
			}
			System.out.println("异步计算结果为：" + result);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}

		System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
		// 然后退出main线程
		System.out.println("=== Demo7 Main Thread End! ===");
	}

	public static class RunnableThread implements Runnable {
		private Lock lock = null;
		private Condition condition = null;

		public RunnableThread(Lock lock, Condition condition) {
			super();
			this.lock = lock;
			this.condition = condition;
		}

		@Override
		public void run() {
			try {
				lock.lock();
				result = this.sum();
				condition.signalAll();
			} catch (Exception e) {
				System.err.println(e.getMessage());
			} finally {
				lock.unlock();
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
