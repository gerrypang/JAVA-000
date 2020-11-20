package com.gerry.pang.singleton;

/**
 * 线程安全懒汉式单例
 * 
 * 优点： 性能高，线程安全
 * 缺点： 代码可读性极差，不够优雅。能够被反射破坏
 */
public class LazySingleton {
	
	// volatile 限制指令重排，保证可见性
	private static volatile LazySingleton singleton;
	
	private LazySingleton() {
		super();
	}
	
	public static LazySingleton getInstance() {
		// double check 
		if (null == singleton) { // volatile 关键字保证了可见性
			// 锁定类对象
			synchronized(LazySingleton.class) {
				if (null == singleton) {
					singleton = new LazySingleton();
				}
			}
		}
		
		return singleton;
	}
	
}
