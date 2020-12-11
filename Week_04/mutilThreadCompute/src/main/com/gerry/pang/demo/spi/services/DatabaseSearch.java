package com.gerry.pang.demo.spi.services;

import java.util.List;

import com.gerry.pang.demo.spi.SearchDemoSPI;

public class DatabaseSearch implements SearchDemoSPI {

	@Override
	public List<String> searchDoc(String keyword) {
		System.out.println("数据搜索 " + keyword);
		return null;
	}

}
