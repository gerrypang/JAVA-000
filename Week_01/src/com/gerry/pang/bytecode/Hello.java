package com.gerry.pang.bytecode;
/**
 * <p>自定Hello.java</p>
 * 
 * Week01-题目1：<br/>
 * 自己写一个简单的 Hello.java，里面需要涉及基本类型，四则运行，if 和 for，然后自己分析一下对应的字节码，有问题群里讨论。
 * 
 * @author pangguowei
 * @since 2020年10月19日 下午4:40:59
 */
public class Hello {
	public void demo() {
		int a = 10, b = 2;
		float c = 2.1f;
		double d = 2.1;
		char m = 1;
		if (a % 2 == 0) {
			c = a * b + m;
		}
	}
}
