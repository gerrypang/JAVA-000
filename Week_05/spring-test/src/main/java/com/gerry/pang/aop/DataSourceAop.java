package com.gerry.pang.aop;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.gerry.pang.common.annotation.DSKey;
import com.gerry.pang.common.annotation.UseDataSource;
import com.gerry.pang.utils.CustomSpelParser;
import com.gerry.pang.utils.datasouce.DataSourceSwitcher;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

/**
 * 注解切面
 * 
 * 注意：该切面必须要在事务注解@Transactional之前，由于在开始事务之前就需要确定数据源，
 * 所以设置DataSourceAsp的**@Order(Ordered.LOWEST_PRECEDENCE-1)，@Transactional的order是最小值
 * 
 * @author pangguowei
 */
@Aspect
@Component
@Order(Ordered.LOWEST_PRECEDENCE - 1)
public class DataSourceAop implements InitializingBean {

	@Autowired
	private AbstractRoutingDataSource dataSourceSwitcher;

	private List<String> dataSourceKeys = new CopyOnWriteArrayList<>();

	private int keySize = 0;
	
	// 缓存DsKey
	private static Cache<String, Integer> LOCAL_CACHE = CacheBuilder
            .newBuilder()
            .expireAfterWrite(1, TimeUnit.MINUTES)
            .maximumSize(50)
            .build();

	@Override
	@SuppressWarnings("unchecked")
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(dataSourceSwitcher, "dataSourceSwitcher is null");
		Field resolvedDataSources = FieldUtils.getField(dataSourceSwitcher.getClass(), "resolvedDataSources", true);
		Assert.notNull(resolvedDataSources, "resolvedDataSources is null");
		// 获取spring管理的dataSource
		Map<Object, DataSource> dataSourceMap = (Map<Object, DataSource>) resolvedDataSources.get(dataSourceSwitcher);
		// 把key存储到list
		dataSourceMap.forEach((k, v) -> {
			dataSourceKeys.add(String.valueOf(k));
		});
		keySize = dataSourceKeys.size();
		Assert.state(keySize != 0, "dataSource size is 0!");
	}

	@Pointcut("@annotation(com.gerry.pang.common.annotation.UseDataSource)")
	public void useDataSource() {

	}

	@Around("useDataSource() && @annotation(anno)")
	public Object dataSourceSwitcher(ProceedingJoinPoint joinPoint, UseDataSource anno) throws Throwable {
		String dataSourceKey = this.getDsKey(anno, joinPoint);
		String originTag = DataSourceSwitcher.getDataSourceKey();
		DataSourceSwitcher.setDataSourceKey(dataSourceKey);
		try {
			Object result = joinPoint.proceed();
			return result;
		} catch (Throwable e) {
			throw e;
		} finally {
			DataSourceSwitcher.setDataSourceKey(originTag);
		}
	}

	/**
	 * 根据不同方式获取dataSourceKey
	 *
	 * @param anno
	 * @param joinPoint
	 * @return
	 * @author pangguowei
	 */
	private String getDsKey(UseDataSource anno, ProceedingJoinPoint joinPoint) {
		String ds = "";
		String source = anno.value();
		String spel = anno.hashExp();
		// 如果使用hash方式
		if (anno.memberHash()) {
			int i = Math.abs(this.getHashKeyFromMethod(joinPoint).hashCode()) % keySize;
			ds = dataSourceKeys.get(i);
		}
		// 如果使用EL表达式
		else if (!StringUtils.isEmpty(spel)) {
			MethodSignature signature = (MethodSignature) joinPoint.getSignature();
			String dynamicValue = CustomSpelParser.getDynamicValue(signature.getParameterNames(), joinPoint.getArgs(),
					spel);
			int i = Math.abs(dynamicValue.hashCode()) % keySize;
			ds = dataSourceKeys.get(i);
		}
		// 如果指定具体的dataSource Key
		else if (!StringUtils.isEmpty(source)) {
			ds = source;
			if (!isInKeyList(ds)) {
				throw new IllegalArgumentException(String.format("dataSource key %s is not in key List %s", ds, dataSourceKeys));
			}
		}

		if (StringUtils.isEmpty(ds)) {
            throw new IllegalArgumentException("dataSource is empty!");
        }
		return ds;
	}

	private boolean isInKeyList(String dataSource) {
		Assert.hasText(dataSource, "mDataSourceKeys is not init!");
		for (String temp : dataSourceKeys) {
			if (temp.equals(dataSource)) {
				return true;
			}
		}
		return false;
	}
	
	private Object getHashKeyFromMethod(ProceedingJoinPoint joinPoint) {
		MethodSignature signature = MethodSignature.class.cast(joinPoint.getSignature());
		Method method = signature.getMethod();
		// 尝试重缓存中获取key
		Integer positionFromCache = this.getPositionFromCache(method);
		Object[] args = joinPoint.getArgs();
		if (positionFromCache != null) {
			return String.valueOf(args[positionFromCache]);
		}
		Parameter[] parameters = method.getParameters();
		int index = 0;
		for (Parameter parameter : parameters) {
			Annotation[] annotations = parameter.getAnnotations();
			for (Annotation anno : annotations) {
				if (anno instanceof DSKey) {
                    this.putToCache(method, index);
                    return String.valueOf(args[index]);
                }
			}
			index ++;
		}
		
		throw new IllegalArgumentException("can not get field with @DsKey annotation");
	}

	private void putToCache(Method method, int index) {
		LOCAL_CACHE.put(method.toString(), index);
	}

	private Integer getPositionFromCache(Method method) {
		Integer value = LOCAL_CACHE.getIfPresent(method.toString());
		return value;
	}

}
