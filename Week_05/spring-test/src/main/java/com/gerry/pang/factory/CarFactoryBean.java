package com.gerry.pang.factory;

import org.springframework.beans.factory.FactoryBean;

import com.gerry.pang.domain.Car;

/**
 * 基于springbeanFactory实现的bean工厂类
 * 
 * @author pangguowei
 * @since 2020年11月18日
 */
public class CarFactoryBean implements FactoryBean<Car> {

	@Override
	public Car getObject() throws Exception {
		return Car.builder().name("X5").brand("BMW")
				.weight(Double.valueOf("1.5"))
				.price(Double.valueOf("850000"))
				.build();
	}

	@Override
	public Class<?> getObjectType() {
		return Car.class;
	}

}
