package com.gerry.pang.algorithm;

import java.util.Collection;

import io.shardingsphere.api.algorithm.sharding.PreciseShardingValue;
import io.shardingsphere.api.algorithm.sharding.standard.PreciseShardingAlgorithm;

public class DemoTableShardingAlgorithm implements PreciseShardingAlgorithm<Integer> {

	@Override
	public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Integer> shardingValue) {
		for (String each : availableTargetNames) {
			if (each.endsWith(shardingValue.getValue() % 2 + "")) {
				return each;
			}
		}
		throw new IllegalArgumentException();
	}

}
