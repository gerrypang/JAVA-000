package com.gerry.pang.getbean;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SuppressWarnings("resource")
public class GetBeanFromXML {

	@Test
	public void getBean() {
		ApplicationContext appContext = new ClassPathXmlApplicationContext("beanFactoryDemo.xml");
		
		System.out.println("===> get bean form xml ");
		System.out.println(appContext.getBean("studentGerry"));
		
		/**
		 * 打印結果：
		 * ===> get bean form xml
		 * Student(id=000001, name=gerry, age=20, gender=male, address=Beijing, school=School(id=000001, Name=清华附中, code=S001), klass=Klass(id=000001, name=高一三班, code=K1001))
		 */
	}
}
