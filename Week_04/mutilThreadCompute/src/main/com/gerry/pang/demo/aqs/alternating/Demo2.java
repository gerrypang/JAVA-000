package com.gerry.pang.demo.aqs.alternating;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * 「请寻求最优解，不要简单的synchronized」
 * 请用两个线程交替输出A1B2C3D4...，A线程输出字母，B线程输出数字，要求A线程首先执行，B线程其次执行！ （多种同步机制的运用）
 * 
 * @author pangguowei
 * @since 2020年11月26日
 */
public class Demo2 {
	
	public static void main(String[] args) {
		Semaphore foo = new Semaphore(1);
		Semaphore bar = new Semaphore(0); // 重点
		
		new Thread(new PrintFoo(foo, bar)).start();
		new Thread(new PrintBar(foo, bar)).start();

	}
	
	static class PrintFoo implements Runnable {
		private Semaphore foo;
		private Semaphore bar;
		
		public PrintFoo(Semaphore foo, Semaphore bar) {
			super();
			this.foo = foo;
			this.bar = bar;
		}
		
		@Override
		public void run() {
			final List<String> letter = Arrays.asList("A", "B", "C", "D", "E", "F");
			for (int i = 0; i < letter.size(); i++) {
				try {
					foo.acquire();
					System.out.println(letter.get(i));
					bar.release();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	static class PrintBar implements Runnable {
		private Semaphore foo;
		private Semaphore bar;
		
		public PrintBar(Semaphore foo, Semaphore bar) {
			super();
			this.foo = foo;
			this.bar = bar;
		}

		@Override
		public void run() {
			final List<String> number = Arrays.asList("1", "2", "3", "4", "5", "6");
			for (int i = 0; i < number.size(); i++) {
				try {
					bar.acquire();
					System.out.println(number.get(i));
					foo.release();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
