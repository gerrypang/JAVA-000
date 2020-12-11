package com.gerry.pang.demo.aqs.alternating;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 「请寻求最优解，不要简单的synchronized」
 * 请用两个线程交替输出A1B2C3D4...，A线程输出字母，B线程输出数字，要求A线程首先执行，B线程其次执行！ （多种同步机制的运用）
 * 
 * @author pangguowei
 * @since 2020年11月26日
 */
public class Demo5 {

	// 这种object锁方式，虽然能够实现，但是最后的线程并没有正常结束
	public static void main(String[] args) {
		ExecutorService executorService = Executors.newFixedThreadPool(2);
		Object lockObject = new Object();
		executorService.execute(() -> {
			final List<String> letter = Arrays.asList("A", "B", "C", "D", "E", "F");
			for (int i = 0; i < letter.size(); i++) {
				synchronized (lockObject) {
					try {
						lockObject.notifyAll();
						System.out.println(letter.get(i));
						lockObject.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		executorService.execute(() -> {
			final List<String> number = Arrays.asList("1", "2", "3", "4", "5", "6");
			for (int i = 0; i < number.size(); i++) {
				synchronized (lockObject) {
					lockObject.notifyAll();
					System.out.println(number.get(i));
					try {
						lockObject.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		executorService.shutdown();
	}

}
