package com.gerry.pang.classloader;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

/**
 * <p>自定义ClassLoader</p>
 * 
 * Week01-题目2：<br/>
 * 自定义一个 Classloader，加载一个 Hello.xlass 文件，执行 hello 方法， 此文件内容是一个 Hello.class
 * 文件所有字节（x=255-x）处理后的文件。文件群里提供。
 * 
 * @author pangguowei
 * @since 2020年10月19日 下午4:40:59
 */
public class CustomClassLoader extends ClassLoader {

	@Override
	public Class<?> loadClass(String name) throws ClassNotFoundException {
		System.out.println("==> class name:" + name);
		if (name == null || name.trim().length() == 0) {
			throw new IllegalArgumentException("name is null");
		}
		try (InputStream resourceAsStream = getClass().getResourceAsStream(name);
				ByteArrayOutputStream outStream = new ByteArrayOutputStream();) {
			if (resourceAsStream == null) {
				/*
				 * 注意：这里不能抛出异常 throw new ClassNotFoundException(name + " file not found");
				 * 因为加载Hello.xlass时会触发其包含Object类加载，当前类的System、PrintStream类加载
				 * 当前classloader处理不了，要保证这些类委托给父类加载器
				 */
				return super.loadClass(name);
			}
			int data = 0;
			// ByteArrayOutputStream outStream = new ByteArrayOutputStream();
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
		 * 运行结果：
		 * ==> class name:Hello.xlass
		 * ==> class name:java.lang.Object
		 * ===============
		 * ==> class name:java.lang.System
		 * ==> class name:java.io.PrintStream
		 * Hello, classLoader!
		 */
	}
}
