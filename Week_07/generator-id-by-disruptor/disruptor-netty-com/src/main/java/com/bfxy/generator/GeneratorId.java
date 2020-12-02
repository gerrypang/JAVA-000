package com.bfxy.generator;

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;

public class GeneratorId {

	private final static int MAX_CAPACITY = 1024 * 1024;
	private final static int INITIAL_CAPACITY = 1024;
	private final static int EXPRIRE_TIME = 60;

	public final static LoadingCache<String, Integer> CAHCHE = CacheBuilder.newBuilder()
			// 初始容量
			.initialCapacity(INITIAL_CAPACITY)
			// 最大容量，超过容量就回收最近没有使用的LRU
			.maximumSize(MAX_CAPACITY)
			// 指定对象被写入到缓存后多久过期
//			.expireAfterWrite(EXPRIRE_TIME, TimeUnit.MINUTES)
			.expireAfterAccess(1, TimeUnit.NANOSECONDS)
			// 缓存移除监听器
			.removalListener(new RemovalListener<String, Integer>() {
				public void onRemoval(RemovalNotification<String, Integer> removal) {
					System.out.println("[" + removal.getKey() + ":" + removal.getValue() + "] is evicted! "+ System.currentTimeMillis());
				}
			}).build(new CacheLoader<String, Integer>() {
				@Override
				public Integer load(String key) throws Exception {
					Integer value = new Random().nextInt(100);
					System.out.println("load ===> " + key + ", " + value);
					return value;
				}
			});

	public void generator() {
		for (int i = 0; i < 100; i++) {
			CAHCHE.put("key_" + i, i);
		}
	}
	
	public static void main(String[] args) {
		GeneratorId a = new GeneratorId();
		a.generator();
		
		System.out.println();
		try {
			GeneratorId.CAHCHE.get("123");
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println();
	}
}
