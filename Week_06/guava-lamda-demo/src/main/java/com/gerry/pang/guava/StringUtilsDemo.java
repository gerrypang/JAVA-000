package com.gerry.pang.guava;

import java.util.Arrays;
import java.util.List;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;

public class StringUtilsDemo {

	public static void main(String[] args) {
		joniner();

		splitter();
	}

	public static void joniner() {
		List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, null, 6);
		String demo = Joiner.on(",").skipNulls().join(list);
		System.out.println(demo);
	}

	public static void splitter() {
		String hello = "the ,quick, , brown         , fox,              jumps, over, the, lazy, little dog.";
		Iterable<String> demo = Splitter.on(",").trimResults().omitEmptyStrings().split(hello);
		System.out.println(demo);
		List<String> demo1 = Splitter.on(",").trimResults().omitEmptyStrings().splitToList(hello);
		System.out.println(demo1);
		List<String> demo2 = Splitter.on(",").trimResults().omitEmptyStrings().splitToList(hello);
		System.out.println(demo2);
		// [the ,quick, , brown , fox, jumps, over, the, lazy, little dog.]
	}

	
}
