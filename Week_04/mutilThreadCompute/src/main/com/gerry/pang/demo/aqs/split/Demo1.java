package com.gerry.pang.demo.aqs.split;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 「请寻求最优解，不要只是粗暴wait（）」
有一个总任务A，分解为子任务A1 A2 A3 ...，任何一个子任务失败后要快速取消所有任务，请写程序模拟。
 * 
 * @author pangguowei
 * @since 2020年11月26日
 */
public class Demo1 {
	public final static int SIZE = 3;
	public final static AtomicInteger flag = new AtomicInteger();
	
	static class ParentTask implements Runnable {
		
		private ExecutorService executor;

		
		public ParentTask( ) {
			super();
		}
		
		public ParentTask(ExecutorService executor) {
			super();
			this.executor = executor;
		}
		
		@Override
		public void run() {
			System.out.println("==> parent start "+ System.currentTimeMillis());
			CountDownLatch startSingle = new CountDownLatch(1);
			CountDownLatch doneSingle = new CountDownLatch(SIZE);
			List<FutureTask<Boolean>> futureSubList = new ArrayList<>(10);
			List<SubTask> subList = new ArrayList<>(10);
			for (int i = 0; i < SIZE; i++) {
				SubTask one = new SubTask(startSingle, doneSingle);
				FutureTask<Boolean> future = new FutureTask<>(one);
				futureSubList.add(future);
				subList.add(one);
				executor.submit(future);
			}
			startSingle.countDown();
			
			try {
				doneSingle.await();
				if (flag.get() > 0) {
					for (int i = 0; i < SIZE; i++) {
						subList.get(i).rollback();
					}
				}
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}  
			System.out.println("==> parent end "+ System.currentTimeMillis());
			executor.shutdown();
		}
	}

	static class SubTask extends ParentTask implements Callable<Boolean> {
		
		private CountDownLatch startSingle;
		private CountDownLatch doneSingle;
		
		public SubTask(CountDownLatch countDownLatch, CountDownLatch doneSingle) {
			super();
			this.startSingle = countDownLatch;
			this.doneSingle = doneSingle;
		}

		public void rollback() {
			System.out.println("==== rollback " + Thread.currentThread().getName() + "_" + System.currentTimeMillis());
		}

		@Override
		public Boolean call() throws Exception {
			startSingle.await();
			Boolean result = true;
			System.out.println("==== start " + Thread.currentThread().getName() + "_" + System.currentTimeMillis());
			try {
				Thread.sleep(new Random().nextInt(3000));
				if (new Random().nextInt(10) % 2 == 0) {
					result = false;
					System.out.println(result + "__" + Thread.currentThread().getName());
					flag.incrementAndGet();
				} 
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			System.out.println("==== end " + Thread.currentThread().getName() + "【" + flag.get() + "】 " + System.currentTimeMillis());
			doneSingle.countDown();
			return result;
		}
	}
	
	public static void main(String[] args) {
		ExecutorService executor = Executors.newFixedThreadPool(10);
		new Thread(new ParentTask(executor)).start();
	}
}
