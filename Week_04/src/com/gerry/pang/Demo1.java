package com.gerry.pang;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 方法1：使用Callable接口 + 单线程
 * 执行结果截图：https://github.com/gerrypang/JAVA-000/blob/main/Week_04/images/Demo1.png
 * 
 * @author pangguowei
 * @since 2020年11月11日
 */
public class Demo1 {
	public static void main(String[] args) {
		System.out.println("=== Demo1 Main Thread Start! ===");
		long start = System.currentTimeMillis();
		
		Callable<Integer> callable = new CallableThread();
		FutureTask<Integer> task = new FutureTask<>(callable); 
		new Thread(task).start();
		
		// 确保 拿到result 并输出
		try {
			System.out.println("异步计算结果为：" + task.get());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		
		System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
		// 然后退出main线程
		System.out.println("=== Demo1 Main Thread End! ===");
	}

	public static class CallableThread implements Callable<Integer> {
		@Override
		public Integer call() throws Exception {
			return sum();
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
