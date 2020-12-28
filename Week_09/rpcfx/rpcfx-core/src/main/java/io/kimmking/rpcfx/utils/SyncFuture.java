package io.kimmking.rpcfx.utils;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 同步Future工具
 * 
 * 参考https://segmentfault.com/a/1190000020507086
 * @param <T>
 * @author pangguowei
 */
public class SyncFuture<T> implements Future<T> {

	private final CountDownLatch latch = new CountDownLatch(1);
	
	private T response;
	
	private long beginTime = System.currentTimeMillis();
	
	@Override
	public boolean cancel(boolean flag) {
		return false;
	}

	@Override
	public T get() throws InterruptedException, ExecutionException {
		latch.await();
		return response;
	}

	@Override
	public T get(long timeout, TimeUnit timeunit) throws InterruptedException, ExecutionException, TimeoutException {
		if (latch.await(timeout, timeunit)) {
			return response;
		}
		return null;
	}

	@Override
	public boolean isCancelled() {
		return false;
	}

	@Override
	public boolean isDone() {
		return false;
	}

	public void setResponse(T response) {
		this.response = response;
		latch.countDown();
	}
	
	public long getBeginTime() {
		return beginTime;
	}

}
