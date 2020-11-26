package com.gerry.pang.lamda;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSON;

public class StreamDemo {
	public static void main(String[] args) {
		List<Integer> list = Arrays.asList(1, 2, 3, 5, 1, 2, 2, 7, 6, 0);

		Map<Integer, Integer> map = list.parallelStream()
				// key, value, key重复不覆盖，保留前一个， map的实现类
				.collect(Collectors.toMap(a -> a, a -> (a + 1), (a, b) -> a, LinkedHashMap::new));
		print(map);
		Map<Integer, Integer> map2 = list.parallelStream()
				// key, value, key重复不覆盖，保留前一个， map的实现类
				.collect(Collectors.toMap(a -> a, a -> (a + 1), (a, b) -> a, HashMap::new));
		print(map2);
		// Exception in thread "main" java.lang.IllegalStateException: Duplicate key 2
//        Map map3 = list.stream().collect(Collectors.toMap(a -> a, a -> (a + 1)));
//        print(map3);
		
		
		// 计算空字符串
        List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd","", "jkl");
        System.out.println("列表: " +strings);
        
        long count2 = strings.stream().filter(n-> n.isEmpty()).count();
        System.out.println("空字符数量为: " + count2);

		count2 = strings.stream().filter(n -> n.length() == 3).count();
		System.out.println("字符串长度为 3 的数量为: " + count2);
        
        // 删除空字符串
        List<String> filtered2 = strings.stream().filter(n -> !n.isEmpty()).collect(Collectors.toList());
        System.out.println("筛选后的列表: " + filtered2);
        
        // 删除空字符串，并使用逗号把它们合并起来
        String mergedString2 = strings.stream().filter(n -> !n.isEmpty()).collect(Collectors.joining(", "));
        System.out.println("合并字符串: " + mergedString2);
        
        List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);
        // 获取列表元素平方数
		List<Integer> squaresList2 = numbers.stream().map(n -> n * n).distinct().collect(Collectors.toList());
		System.out.println("平方数列表: " + squaresList2);
        
        
        List<Integer> integers = Arrays.asList(1,2,13,4,15,6,17,8,19);
		System.out.println("列表: " + integers);
		
		System.out.println("列表中最大的数 : " + integers.stream().reduce(Integer::max).get());
		
		System.out.println("列表中最小的数 : " + integers.stream().reduce(Integer::min).orElse(-1));
		
		System.out.println("所有数之和 : " + integers.stream().reduce(Integer::sum).get());
		
		System.out.println("平均数 : " + integers.stream().mapToInt(Integer::intValue).average().orElse(0));
		System.out.println("平均数 : " + integers.stream().collect(Collectors.averagingInt(n->n)));
	}

	private static void print(Object obj) {
		System.out.println(JSON.toJSONString(obj));
	}
}
