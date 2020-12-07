package com.gerry.pang.utils;

import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

public class CustomSpelParser {
	
	public static String getDynamicValue(String[] parameterNames, Object[] args, String key) {
		// 创建SpEL表达式解析器对象 
		ExpressionParser praser = new SpelExpressionParser();
		// 创建表达式计算上下文
		StandardEvaluationContext context = new StandardEvaluationContext();
		for (int i = 0; i < parameterNames.length; i++) {
			context.setVariable(parameterNames[i], args[i]);
		}
		return praser.parseExpression(key).getValue(context, String.class);
	}
	
//	public static void main(String[] args) {
//		String dynamicValue = CustomSpelParser.getDynamicValue(new String[]{"a", "b"}, new Object[]{1, 2}, "#a+'_'+#b");
//        System.out.println(dynamicValue);
//	}
}
