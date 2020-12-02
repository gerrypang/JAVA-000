package com.gerry.pang.challenge.xlassloader;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MyXlassLoader extends ClassLoader {
	public final static String suffix=".xlass";
	
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
}
