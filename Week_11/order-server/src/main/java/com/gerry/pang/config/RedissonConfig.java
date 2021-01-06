package com.gerry.pang.config;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Redisson Config
 */
@Configuration
public class RedissonConfig {
	
	private final static String COMMON_URL_FORMAT = "redis://%s";
	
	@Autowired
	private RedisProperties redisProperties;

	@Bean(destroyMethod = "shutdown")
	public RedissonClient redissonClient() {
		List<String> allNode = redisProperties.getCluster().getNodes();
		String[] nodeAddress = new String[allNode.size()];
		
		for (int i = 0; i < allNode.size(); i++) {
			nodeAddress[i] = String.format(COMMON_URL_FORMAT, allNode.get(i));
		}
		Config config = new Config();
		if (StringUtils.isNotBlank(redisProperties.getPassword())) {
			config.useClusterServers()
				// 设置1秒钟ping一次来维持连接
				.setPingConnectionInterval(2000)
				// 集群状态扫描间隔时间，单位是毫秒
				.setScanInterval(2000)
				.setRetryInterval(1000)
				// 可以用"rediss://"来启用SSL连接
				.addNodeAddress(nodeAddress)
				// 密码
				.setPassword(redisProperties.getPassword());
		} else {
			config.useClusterServers()
				// 设置1秒钟ping一次来维持连接
				.setPingConnectionInterval(2000)
				// 集群状态扫描间隔时间，单位是毫秒
				.setScanInterval(2000)
				.setRetryInterval(1000)
				.addNodeAddress(nodeAddress);
		}
		
		RedissonClient redisson = Redisson.create(config);
		return redisson;

	}
}
