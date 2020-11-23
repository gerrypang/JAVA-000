package com.gerry.pang.singleton;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SingletonTest {
	
	public static void main(String[] args) {
//		demoByCycleBarrier();
		
//		demoByCountDownLatch();
		demoByCountDownLatch2();
	}
	
	
	public static void demoByCycleBarrier() {
		int size = 5;
		CyclicBarrier cycleBarrier = new CyclicBarrier(size, new Runnable() {
			@Override
			public void run() {
				System.out.println("====== start run =======" + System.currentTimeMillis());
			}
		});
		ExecutorService executor = Executors.newFixedThreadPool(size);
		for (int i = 0; i < size; i++) {
			executor.execute(new Runnable() {
				@Override
				public void run() {
					try {
						cycleBarrier.await();
						System.out.println(EnumSingleton.getInstance() + " -- " + System.currentTimeMillis());
					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (BrokenBarrierException e) {
						e.printStackTrace();
					}
				}
			});
		}
		
		executor.shutdown();
	}
	
	public static void demoByCountDownLatch() {
		int size = 5;
		CountDownLatch countDownLatch = new CountDownLatch(1);
		ExecutorService executor = Executors.newFixedThreadPool(size);
		for (int i = 0; i < size; i++) {
			executor.execute(new Runnable() {
				@Override
				public void run() {
					try {
						countDownLatch.await();
						System.out.println(EnumSingleton.getInstance() + " -- " + System.currentTimeMillis());
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
		}
		countDownLatch.countDown();
		System.out.println("====== start run =======" + System.currentTimeMillis());
		executor.shutdown();
	}
	
	public static void demoByCountDownLatch2() {
		int size = 5;
		CountDownLatch startSingal = new CountDownLatch(1);
		CountDownLatch doneSingal = new CountDownLatch(size);
		ExecutorService executor = Executors.newFixedThreadPool(size);
		for (int i = 0; i < size; i++) {
			executor.execute(new Runnable() {
				@Override
				public void run() {
					try {
						startSingal.await();
						System.out.println(EnumSingleton.getInstance() + " -- " + System.currentTimeMillis());
						doneSingal.countDown();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
		}
		startSingal.countDown();
		System.out.println("====== start run =======" + System.currentTimeMillis());
		
		try {
			doneSingal.await();
			System.out.println("====== all finish run =======" + System.currentTimeMillis());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		executor.shutdown();
	}
	
}
