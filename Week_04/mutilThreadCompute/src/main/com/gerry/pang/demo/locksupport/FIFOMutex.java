package com.gerry.pang.demo.locksupport;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.LockSupport;

public class FIFOMutex {
	private final AtomicBoolean locked = new AtomicBoolean(false);
	private final Queue<Thread> waiters = new ConcurrentLinkedQueue<>();

	public void lock() {
		boolean wasInterruped = false;
		Thread current = Thread.currentThread();
		waiters.add(current);
		
		while(waiters.peek() != current || !locked.compareAndSet(false, true)) {
			LockSupport.park(this);
			if (Thread.interrupted()) {
				wasInterruped = true;
			}
		}
		
		waiters.remove();
		if (wasInterruped) {
			current.interrupt();
		}
	}
	
	public void unlock() {
		locked.set(false);
		LockSupport.unpark(waiters.peek());
	}
	
	public static void main(String[] args) {
		FIFOMutex mutex = new FIFOMutex();
//		Thread a = new Thread(()-> {
			mutex.lock();
//		});
//		a.start();

//		Thread b = new Thread(()-> {
//			mutex.lock();
//		});
//		b.start();
		
		System.out.println("=====");
	}
}
