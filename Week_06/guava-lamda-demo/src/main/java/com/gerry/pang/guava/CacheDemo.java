package com.gerry.pang.guava;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.google.common.cache.Weigher;

public class CacheDemo {
	public static void main(String[] args) {
		// 参考链接：https://xie.infoq.cn/article/6bade882abdb2fb076539d2bd
		cache1();
//		cache2();
//		cache3();
	}
	
	private static void cache1() {
		Cache<String, String> cache = CacheBuilder.newBuilder()
				// 缓存移除监听器
				.removalListener(new RemovalListener<String, String>() {
					@Override
					public void onRemoval(RemovalNotification<String, String> notification) {
						System.out.println(notification.getKey()+"_"+notification.getValue()+"_"+notification.getCause());
					}
				})
				//开启统计信息开关
				.recordStats() 
				.build();
		
		cache.put("aaa", "a1");
		cache.put("bbb", "b1");
		cache.put("eee", "e1");
		cache.put("fff", "f1");
		
		System.out.println(cache.getIfPresent("aaa"));
		System.out.println(cache.getIfPresent("ccc"));
		// 显式清除个别缓存项
		cache.invalidate("aaa");
		System.out.println(cache.getIfPresent("aaa"));
		
		// 批量缓存项
		cache.invalidateAll(Arrays.asList("bbb","eee"));
		System.out.println(cache.asMap());
		
		// 清除所有缓存项
		cache.invalidateAll();
		System.out.println(cache.asMap());
		
		// 获取统计信息
		System.out.println(cache.stats());
	}

	public static void cache2() {
		LoadingCache<String, Integer> cahche = CacheBuilder.newBuilder()
				// 最大容量，超过容量就回收最近没有使用的LRU
				.maximumSize(5)
				// Cache中存储的对象如果超过3秒没有被访问就会过期
				.expireAfterAccess(3, TimeUnit.SECONDS)
				// 指定对象被写入到缓存后多久过期
				.expireAfterWrite(3, TimeUnit.SECONDS)
				.build(new CacheLoader<String, Integer>() {
					@Override
					public Integer load(String key) throws Exception {
						Map<String, Integer> database = new HashMap<>();
						database.put("100", 1);
						database.put("103", 2);
						database.put("110", 3);
						System.out.println("load ===> "+ key);
						return database.get(key);
					}
				});

		try {
			cahche.put("aaa", 3);
			System.out.println(cahche.get("aaa")); 
			cahche.put("aaa", 4);
			System.out.println(cahche.get("aaa")); 
			
			
			// get 方法要么返回已经缓存的值，要么使用CacheLoader向缓存原子地加载新值
			System.out.println(cahche.get("100"));
			System.out.println(cahche.get("103"));
			System.out.println(cahche.get("110"));
			
			ConcurrentMap<String, Integer> aa = cahche.asMap();
			System.out.println(aa);
			
			Thread.sleep(3001);
			System.out.println(cahche.getIfPresent("100"));
			System.out.println(cahche.getIfPresent("103"));
			System.out.println(cahche.getIfPresent("110"));

		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static void cache3() {
		LoadingCache<String, Integer> cahche = CacheBuilder.newBuilder()
				// 最大总重，注意回收也是在重量逼近限定值时就进行了，还要知道重量是在缓存创建时计算的
				.maximumWeight(50)
				.weigher(new Weigher<String, Integer>() {
					@Override
					public int weigh(String key, Integer value) {
						int w = value * new Random().nextInt(10);
						System.out.println("key:"+key +"_w:"+w);
						return w;
					}
				})
				// Cache中存储的对象如果超过3秒没有被访问就会过期
				.expireAfterAccess(3, TimeUnit.SECONDS)
				// 指定对象被写入到缓存后多久过期
				.expireAfterWrite(3, TimeUnit.SECONDS)
				.build(new CacheLoader<String, Integer>() {
					@Override
					public Integer load(String key) throws Exception {
						Map<String, Integer> database = new HashMap<>();
						database.put("100", 1);
						database.put("103", 2);
						database.put("110", 3);
						System.out.println("load ===> "+ key);
						return database.get(key);
					}
				});
		
		try {
			cahche.put("aaa", 3);
			System.out.println(cahche.get("aaa")); 
			cahche.put("aaa", 4);
			System.out.println(cahche.get("aaa"));  
			cahche.put("aaa", 5);
			
			// get 方法要么返回已经缓存的值，要么使用CacheLoader向缓存原子地加载新值
			System.out.println(cahche.get("100"));
			System.out.println(cahche.get("103"));
			System.out.println(cahche.get("110"));
			
			
			
			ConcurrentMap<String, Integer> aa = cahche.asMap();
			System.out.println(aa);
			
		} catch (ExecutionException e) {
			e.printStackTrace();
		}  
	}
}
