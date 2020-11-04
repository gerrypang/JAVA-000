package io.github.kimmking.gateway.support;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public enum GatewagThreadPool {
	;

	static {
		int cores = Runtime.getRuntime().availableProcessors();
		int maxes = cores * 2;
		long keepAliveTime = 1000;
		int queueSize = 2048;
		RejectedExecutionHandler handler = new ThreadPoolExecutor.CallerRunsPolicy();// .DiscardPolicy();
		threadPoolInstance = new ThreadPoolExecutor(cores, maxes, keepAliveTime, TimeUnit.MILLISECONDS,
				new ArrayBlockingQueue<>(queueSize), new NamedThreadFactory("proxyService"), handler);
	}

	private static ExecutorService threadPoolInstance;

	public static ExecutorService getThreadPool() {
		return threadPoolInstance;
	}

	public Future<?> submit(Runnable runnable) {
		return threadPoolInstance.submit(runnable);
	}

	public void execute(Runnable runnable) {
		threadPoolInstance.execute(runnable);
	}

}
