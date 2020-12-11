package com.gerry.pang.demo.aqs.alternating;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 「请寻求最优解，不要简单的synchronized」
 * 请用两个线程交替输出A1B2C3D4...，A线程输出字母，B线程输出数字，要求A线程首先执行，B线程其次执行！ （多种同步机制的运用）
 * 
 * @author pangguowei
 * @since 2020年11月26日
 */
public class Demo3 {
	
	public static void main(String[] args) {
		ReentrantLock lock = new ReentrantLock();
		Condition letter = lock.newCondition();
		Condition number = lock.newCondition();
		new Thread(new PrintFoo(lock, letter, number)).start();
		new Thread(new PrintBar(lock, letter, number)).start();

	}
	
	static class PrintFoo implements Runnable {
		private Condition letterCondition;
		private Condition numberCondition;
		private ReentrantLock lock;
		
		public PrintFoo(ReentrantLock lock, Condition letter, Condition number) {
			super();
			this.lock = lock;
			this.letterCondition = letter;
			this.numberCondition = number;
		}
		
		@Override
		public void run() {
			final List<String> letter = Arrays.asList("A", "B", "C", "D", "E", "F");
			for (int i = 0; i < letter.size(); i++) {
				lock.lock();
				try {
					letterCondition.signal();
					System.out.println(letter.get(i));
					numberCondition.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					lock.unlock();
				}
			}
		}
	}
	
	static class PrintBar implements Runnable {
		private Condition letterCondition;
		private Condition numberCondition;
		private ReentrantLock lock;
		
		public PrintBar(ReentrantLock lock, Condition letter, Condition number) {
			super();
			this.lock = lock;
			this.letterCondition = letter;
			this.numberCondition = number;
		}

		@Override
		public void run() {
			final List<String> number = Arrays.asList("1", "2", "3", "4", "5", "6");
			for (int i = 0; i < number.size(); i++) {
				lock.lock();
				try {
					numberCondition.signal();
					System.out.println(number.get(i));
					letterCondition.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					lock.unlock();
				}
			}
		}
	}
}
