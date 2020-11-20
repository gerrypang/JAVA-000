package com.gerry.pang.singleton;

/**
 * 内部静态类式单例
 * 
 * 优点： 基于JVM语法保证线程安全
 * 缺点： 代码可读性极差，不够优雅。能被反射破坏
 */
public class InnerStaticCLassSingleton {
	
	private InnerStaticCLassSingleton() {
		super();
	}

	public static InnerStaticCLassSingleton getInstance() {
		return InnerHolder.INSTANCE;
	}

	/**
	 * JVM在类的初始化阶段（即在Class被加载后，且被线程使用之前），会执行类的初始化。
	 * 在执行类的初始化期间，JVM会去获取一个锁。这个锁可以同步多个线程对同一个类的初始化。
	 */
	private static class InnerHolder {
		private static final InnerStaticCLassSingleton INSTANCE = new InnerStaticCLassSingleton();
	}
}
