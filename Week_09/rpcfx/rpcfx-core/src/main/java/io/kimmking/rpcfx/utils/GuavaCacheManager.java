package io.kimmking.rpcfx.utils;

import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;

import io.kimmking.rpcfx.api.RpcfxResponse;

public class GuavaCacheManager {
	
	private static final Log log = LogFactory.getLog(GuavaCacheManager.class);
	
	private static LoadingCache<String, SyncFuture<RpcfxResponse>> futureCache = CacheBuilder.newBuilder()
			.initialCapacity(100)
			.maximumSize(10000)
			.concurrencyLevel(20)
			.expireAfterWrite(10, TimeUnit.SECONDS)
			.removalListener(new RemovalListener<String, SyncFuture<RpcfxResponse>>() {
				@Override
				public void onRemoval(RemovalNotification<String, SyncFuture<RpcfxResponse>> notification) {
					log.info("remove cache key: " + notification.getKey() + ", cause: " + notification.getCause());
				}
			}).build(new CacheLoader<String, SyncFuture<RpcfxResponse>>() {
				@Override
				public SyncFuture<RpcfxResponse> load(String key) throws Exception {
					// 当获取不到key在cache中 不要自动添加
					return null;
				}
			});

	public static LoadingCache<String, SyncFuture<RpcfxResponse>> getFutureCache() {
		return futureCache;
	}
}
