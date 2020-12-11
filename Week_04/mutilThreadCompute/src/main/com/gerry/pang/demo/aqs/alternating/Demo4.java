package com.gerry.pang.demo.aqs.alternating;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 「请寻求最优解，不要简单的synchronized」
 * 请用两个线程交替输出A1B2C3D4...，A线程输出字母，B线程输出数字，要求A线程首先执行，B线程其次执行！ （多种同步机制的运用）
 * 
 * @author pangguowei
 * @since 2020年11月26日
 */
public class Demo4 {
	
	public static void main(String[] args) {
		AtomicInteger number = new AtomicInteger();
		
		CyclicBarrier cyclicBarrier = new CyclicBarrier(1, ()-> {
			System.out.println(number.incrementAndGet());
		});
		new Thread(new PrintFoo(cyclicBarrier)).start();
	}
	
	static class PrintFoo implements Runnable {
		private CyclicBarrier cyclicBarrier;
		
		public PrintFoo(CyclicBarrier cyclicBarrier) {
			super();
			this.cyclicBarrier = cyclicBarrier;
		}
		
		@Override
		public void run() {
			final List<String> letter = Arrays.asList("A", "B", "C", "D", "E", "F");
			for (int i = 0; i < letter.size(); i++) {
				try {
					System.out.println(letter.get(i));
					cyclicBarrier.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (BrokenBarrierException e) {
					e.printStackTrace();
				}  
			}
		}
	}
	
}
