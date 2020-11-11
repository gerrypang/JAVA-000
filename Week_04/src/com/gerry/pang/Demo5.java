package com.gerry.pang;

/**
 * 方法5：使用Runnable接口 + obj.wait
 * 执行结果截图：https://github.com/gerrypang/JAVA-000/blob/main/Week_04/images/Demo5.png
 * 
 * @author pangguowei
 * @since 2020年11月11日
 */
public class Demo5 {
	private static Integer result = new Integer(-1);

	public static void main(String[] args) {
		System.out.println("=== Demo5 Main Thread Start! ===");
		long start = System.currentTimeMillis();
		Object obj = new Object();
		new Thread(new RunnableThread(obj)).start();;

		synchronized (obj) {
			try {
				// 确保 拿到result 并输出
				obj.wait();
				System.out.println("异步计算结果为：" + result);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
		// 然后退出main线程
		System.out.println("=== Demo5 Main Thread End! ===");
	}

	public static class RunnableThread implements Runnable {
		private Object obj = null;

		public RunnableThread(Object obj) {
			super();
			this.obj = obj;
		}

		@Override
		public void run() {
			try {
				synchronized (obj) {
					result = this.sum();
					obj.notifyAll();
				}
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
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
