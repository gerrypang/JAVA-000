package com.gerry.pang.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义数据源注解
 * 
 * @author pangguowei
 * @since 2020年12月4日 下午5:47:40
 */
@Target({ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface DSKey {
	String value() default "";
}
