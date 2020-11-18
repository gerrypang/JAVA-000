package com.gerry.pang.loadbean;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.gerry.pang.domain.Car;
import com.gerry.pang.domain.Person;

@SpringBootTest
public class BySpringBootTestTest {
	
	@Autowired
	private Car ghost;

	@Autowired
	private Person dougLea;
	
	@Test
	public void testLoadBean() {
		/**
		 * 通过 @SpringBootTest 获取 bean
		 * 
		 * 其主要是通过spring boot test包，在启动spring boot时 内部自动加载容器，并通过@Autowired 自动注入bean
		 */
		assertEquals("Doug Lea", dougLea.getName());
		assertEquals("Ghost", ghost.getName());
		
	}

}
