package com.gerry.pang;

import java.util.concurrent.CompletableFuture;

/**
 * 方法6：使用CompletableFuture
 * 执行结果截图：https://github.com/gerrypang/JAVA-000/blob/main/Week_04/images/Demo6.png
 * 
 * @author pangguowei
 * @since 2020年11月11日
 */
public class Demo6 {

	public static void main(String[] args) {
		System.out.println("=== Demo6 Main Thread Start! ===");
		long start = System.currentTimeMillis();
		Integer result = CompletableFuture.supplyAsync(() -> sum()).join();
		System.out.println("异步计算结果为：" + result);
		System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
		// 然后退出main线程
		System.out.println("=== Demo6 Main Thread End! ===");
	}

	private static int sum() {
		return fibo(36);
	}

	private static int fibo(int a) {
		if (a < 2)
			return 1;
		return fibo(a - 1) + fibo(a - 2);
	}

}
