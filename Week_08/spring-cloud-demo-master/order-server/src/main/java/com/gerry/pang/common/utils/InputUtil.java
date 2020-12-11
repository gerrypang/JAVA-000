package com.gerry.pang.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.lang3.StringUtils;

public class InputUtil {

	private static final BufferedReader KEYBOARD_INPUT = new BufferedReader(new InputStreamReader(System.in));

	private InputUtil() {
	}

	public static String getString(String prompt) {
		boolean flag = true;
		String str = null;
		while (flag) {
			System.out.print(prompt);
			try {
				str = KEYBOARD_INPUT.readLine();
				if (StringUtils.isBlank(str)) {
					System.out.println("输入的内容为空");
				} else {
					flag = false;
				}
			} catch (IOException e) {
				System.err.println("输入异常" + e.getMessage());
			}
		}
		return str;
	}

}
