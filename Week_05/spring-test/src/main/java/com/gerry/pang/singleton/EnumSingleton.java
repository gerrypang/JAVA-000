package com.gerry.pang.singleton;

/**
 * 枚举类式单例
 * 
 * 优点： 基于Java语法保证线程安全，不能被能被反射破坏，代码优雅
 * 缺点： 大量使用会造成内存浪费
 */
public enum EnumSingleton {
	;
	
	static {
		data = new Object();
	}
	
	private static Object data;
	
	public static Object getInstance() {
		return data;
	}
}
