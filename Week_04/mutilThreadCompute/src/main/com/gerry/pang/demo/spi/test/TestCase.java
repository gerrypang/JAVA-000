package com.gerry.pang.demo.spi.test;

import java.util.Iterator;
import java.util.ServiceLoader;

import com.gerry.pang.demo.spi.SearchDemoSPI;

public class TestCase {

	public static void main(String[] args) {
		// 参考资料： https://zhuanlan.zhihu.com/p/28909673
		ServiceLoader<SearchDemoSPI> s = ServiceLoader.load(SearchDemoSPI.class);
		Iterator<SearchDemoSPI> iterator = s.iterator();
		while (iterator.hasNext()) {
			SearchDemoSPI search = iterator.next();
			search.searchDoc("hello world");
		}
	}

}
