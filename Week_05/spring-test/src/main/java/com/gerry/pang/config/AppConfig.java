package com.gerry.pang.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.gerry.pang.domain.Car;
import com.gerry.pang.domain.Person;

@Configuration
public class AppConfig {

	@Bean
	public Person chris() {
		return Person.builder().name("chris").age(18).gender("male").address("beijing").build();
	}
	
	@Bean
	public Person dougLea() {
		return Person.builder().name("Doug Lea").age(60).gender("male").address("U.S.A").build();
	}

	@Bean
	public Car ghost() {
		return Car.builder().name("Ghost").weight(Double.valueOf("1.8")).price(Double.valueOf("3050000")).brand("Rolls-Royce")
				.build();
	}
}
