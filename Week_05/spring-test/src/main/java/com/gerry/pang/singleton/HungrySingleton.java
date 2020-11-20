package com.gerry.pang.singleton;

/**
 * 饿汉式单例
 * 
 * 优点：执行效率高，性能高，没有任何的锁
 * 缺点：大量使用会造成内存浪费，能够被反射破坏
 */
public class HungrySingleton {
	
	private static HungrySingleton singleton = new HungrySingleton();

	private HungrySingleton() {
		super();
	}
	
	public static HungrySingleton getInstance() {
		return singleton;
	}
}
