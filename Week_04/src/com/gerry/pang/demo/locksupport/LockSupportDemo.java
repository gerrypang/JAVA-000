package com.gerry.pang.demo.locksupport;

import java.util.concurrent.locks.LockSupport;

public class LockSupportDemo {
	public static void main(String[] args) {
		try {
			demo4();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void demo1() {
		System.out.println("begin park");
		LockSupport.park();
		System.out.println("end park");
	}

	public static void demo2() {
		System.out.println("begin park");
		// 使当前线程获取到许可证
		LockSupport.unpark(Thread.currentThread());
		// 再次調用park
		LockSupport.park();
		System.out.println("end park");
	}
	
	public static void demo3() throws Exception {
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("child thread begin park !");
				LockSupport.park();
				System.out.println("child thread unpark !");
				
			}
		});
		thread.start();
		Thread.sleep(1000);
		System.out.println("main begin park");
		// 使 thread 线程获取到许可证
		LockSupport.unpark(thread);
	}

	public static void demo4() throws Exception {
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("child thread begin park !");
				while(! Thread.currentThread().isInterrupted()) {
					System.out.println("==");
					LockSupport.park();
				}
				System.out.println("child thread unpark !");
			}
		});
		thread.start();
		Thread.sleep(10000);
		System.out.println("main begin park");
		thread.interrupt();
		System.out.println("==111");
		
//		child thread begin park !
//		==
//		main begin park
//		==111
//		child thread unpark !

	}

	public static void demo5() throws Exception {
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("child thread begin park !");
				while(! Thread.currentThread().isInterrupted()) {
					System.out.println("==");
					LockSupport.park();
				}
				System.out.println("child thread unpark !");
			}
		});
		thread.start();
		Thread.sleep(10000);
		System.out.println("main begin park");
		thread.interrupt();
		System.out.println("==111");
		
//		child thread begin park !
//		==
//		main begin park
//		==111
//		child thread unpark !
		
	}
	
}
