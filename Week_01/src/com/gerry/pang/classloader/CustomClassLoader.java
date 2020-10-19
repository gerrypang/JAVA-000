package com.gerry.pang.classloader;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

/**
 * <p>�Զ���ClassLoader</p>
 * 
 * Week01-��Ŀ2��<br/>
 * �Զ���һ�� Classloader������һ�� Hello.xlass �ļ���ִ�� hello ������ ���ļ�������һ�� Hello.class
 * �ļ������ֽڣ�x=255-x���������ļ����ļ�Ⱥ���ṩ��
 * 
 * @author pangguowei
 * @since 2020��10��19�� ����4:40:59
 */
public class CustomClassLoader extends ClassLoader {

	@Override
	public Class<?> loadClass(String name) throws ClassNotFoundException {
		System.out.println("==> class name:" + name);
		if (name == null || name.trim().length() == 0) {
			throw new IllegalArgumentException("name is null");
		}
		try (InputStream resourceAsStream = getClass().getResourceAsStream(name)) {
			if (resourceAsStream == null) {
				/*
				 * ע�⣺���ﲻ���׳��쳣 throw new ClassNotFoundException(name + " file not found");
				 * ��Ϊ����Hello.xlassʱ�ᴥ�������Object����أ���ǰ���System��PrintStream�����
				 * ��ǰclassloader�����ˣ�Ҫ��֤��Щ��ί�и����������
				 */
				return super.loadClass(name);
			}
			int data = 0;
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			while ((data = resourceAsStream.read()) != -1) {
				outStream.write(255 - data);
			}
			name = name.replace(".xlass", "");
			return defineClass(name, outStream.toByteArray(), 0, outStream.toByteArray().length);
		} catch (IOException e) {
			throw new ClassNotFoundException();
		}
	}

	public static void main(String[] args) throws Exception {
		ClassLoader helloClassLoader = new CustomClassLoader();
		Class<?> aClass = helloClassLoader.loadClass("Hello.xlass");
		Method hello = aClass.getMethod("hello");
		hello.setAccessible(true);
		System.out.println("===============");
		hello.invoke(aClass.newInstance());
		/**
		 * ���н����
		 * ==> class name:Hello.xlass
		 * ==> class name:java.lang.Object
		 * ===============
		 * ==> class name:java.lang.System
		 * ==> class name:java.io.PrintStream
		 * Hello, classLoader!
		 */
	}
}
