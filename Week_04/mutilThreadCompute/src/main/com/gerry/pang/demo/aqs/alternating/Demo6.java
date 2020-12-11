package com.gerry.pang.demo.aqs.alternating;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 「请寻求最优解，不要简单的synchronized」
 * 请用两个线程交替输出A1B2C3D4...，A线程输出字母，B线程输出数字，要求A线程首先执行，B线程其次执行！ （多种同步机制的运用）
 * 
 * @author pangguowei
 * @since 2020年11月26日
 */
public class Demo6 {
	
	volatile static boolean permitFoo = true;

	public static void main(String[] args) {
		// 注意：依靠公平锁交替打印并不稳定
		ReentrantLock lock = new ReentrantLock(true);
		new Thread(new PrintFoo(lock)).start();
		new Thread(new PrintBar(lock)).start();

	}
	
	static class PrintFoo implements Runnable {
		private ReentrantLock lock;
		
		public PrintFoo(ReentrantLock lock) {
			super();
			this.lock = lock;
		}
		
		@Override
		public void run() {
			final List<String> letter = Arrays.asList("A", "B", "C", "D", "E", "F");
			for (int i = 0; i < letter.size(); i++) {
				lock.lock();
				try {
					if (permitFoo) {
						System.out.println(letter.get(i));
						permitFoo = false;
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					lock.unlock();
				}
			}
		}
	}
	
	static class PrintBar implements Runnable {
		private ReentrantLock lock;
		
		public PrintBar(ReentrantLock lock) {
			super();
			this.lock = lock;
		}

		@Override
		public void run() {
			final List<String> number = Arrays.asList("1", "2", "3", "4", "5", "6");
			for (int i = 0; i < number.size(); i++) {
				lock.lock();
				try {
					if (!permitFoo) {
						System.out.println(number.get(i));
						permitFoo = true;
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					lock.unlock();
				}
			}
		}
	}
}
