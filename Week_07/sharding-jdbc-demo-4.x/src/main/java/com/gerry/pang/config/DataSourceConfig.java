package com.gerry.pang.config;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.shardingsphere.api.config.masterslave.MasterSlaveRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.KeyGeneratorConfiguration;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.InlineShardingStrategyConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.StandardShardingStrategyConfiguration;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.gerry.pang.algorithm.DemoTableShardingAlgorithm;
import com.google.common.collect.Lists;


@Configuration
public class DataSourceConfig {

	@Bean(name = "masterDataSource")
	@ConfigurationProperties(prefix="spring.datasource.druid.master")
	public DataSource masterDataSource() {
		DataSource dataSource = new DruidDataSourceBuilder().build();
		return dataSource;
	}
	
	@Bean(name = "slaveDataSource")
	@ConfigurationProperties(prefix="spring.datasource.druid.slave")
	public DataSource slaveDataSource() {
		DataSource dataSource = new DruidDataSourceBuilder().build();
		return dataSource;
	}
	
	/**
	 * 创建读写分离数据源
	 * 
	 * @return
	 */
	private Map<String, DataSource> createWRDataSourceMap() {
        final Map<String, DataSource> result = new HashMap<>();
        result.put("demo_ds_master_0", masterDataSource());
        result.put("demo_ds_slave_0", slaveDataSource());
        result.put("demo_ds_slave_1", slaveDataSource());
        return result;
    }
	
	/**
	 * 创建单个数据源
	 * 
	 * @return
	 */
	private Map<String, DataSource> createSingeDataSourceMap() {
		// 单个数据源
		final Map<String, DataSource> dataSourceMap = new HashMap<>();
		dataSourceMap.put("dataSource", masterDataSource());
		return dataSourceMap;
	}
	
	public List<MasterSlaveRuleConfiguration> getMasterSlaveRuleConfigurations() {
		MasterSlaveRuleConfiguration masterSlaveRuleConfig1 = new MasterSlaveRuleConfiguration("ds_0",
				"demo_ds_master_0", Arrays.asList("demo_ds_slave_0", "demo_ds_slave_1"));
		return Lists.newArrayList(masterSlaveRuleConfig1);
	}

	/**
	 * 配置分库 + 分表策略
	 * 
	 * @return
	 * @throws SQLException
	 */
	@Bean(name = "shardingDataSource")
	public DataSource shardingDataSource() throws SQLException {
		ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
		shardingRuleConfig.getTableRuleConfigs().add(userTableRuleConfiguration());
		shardingRuleConfig.getTableRuleConfigs().add(orderTableRuleConfiguration());
		shardingRuleConfig.getTableRuleConfigs().add(orderItemTableRuleConfiguration());
		// 绑定表, t_order表和t_order_item表，均按照order_id分片
		shardingRuleConfig.getBindingTableGroups().add("t_order, t_order_item");
		shardingRuleConfig.setMasterSlaveRuleConfigs(getMasterSlaveRuleConfigurations());
		// 单个数据源
		// DataSource dataSource = ShardingDataSourceFactory.createDataSource(createSingeDataSourceMap(), shardingRuleConfig, new ConcurrentHashMap<>(), new Properties());
		// 创建读写分离数据源 
		DataSource dataSource = ShardingDataSourceFactory.createDataSource(createWRDataSourceMap(), shardingRuleConfig, new Properties());
		// 获取数据源对象
		return dataSource;
	}

	/**
	 * user 表分表规则
	 * 
	 * @return
	 */
	@Bean
	public TableRuleConfiguration userTableRuleConfiguration() {
		TableRuleConfiguration tableRuleConfig = new TableRuleConfiguration("t_user", "ds_0.t_user${0..1}");
		// 标准分片策略
		tableRuleConfig.setTableShardingStrategyConfig(new StandardShardingStrategyConfiguration("user_id", new DemoTableShardingAlgorithm()));
		return tableRuleConfig;
	}
	
	/**
	 * order 表分表规则
	 * 
	 * @return
	 */
	@Bean
	public TableRuleConfiguration orderTableRuleConfiguration() {
		TableRuleConfiguration tableRuleConfig = new TableRuleConfiguration("t_order", "ds_0.t_order${0..1}");
		// 生成主键使用雪花算法
		tableRuleConfig.setKeyGeneratorConfig(new KeyGeneratorConfiguration("SNOWFLAKE", "order_id"));
		// 行表达式分片策略
		// 使用Groovy的表达式，提供对SQL语句中的=和IN的分片操作支持，只支持单分片键。对于简单的分片算法，可以通过简单的配置使用，从而避免繁琐的Java代码开发
		tableRuleConfig.setTableShardingStrategyConfig(new InlineShardingStrategyConfiguration("order_id", "t_order${order_id % 2}"));
		return tableRuleConfig;
	}
	
	/**
	 * order 表分表规则
	 * 
	 * @return
	 */
	@Bean
	public TableRuleConfiguration orderItemTableRuleConfiguration() {
		TableRuleConfiguration tableRuleConfig = new TableRuleConfiguration("t_order_item", "ds_0.t_order_item${0..1}");
		// 生成主键使用雪花算法
		tableRuleConfig.setKeyGeneratorConfig(new KeyGeneratorConfiguration("SNOWFLAKE", "order_item_id"));
		tableRuleConfig.setTableShardingStrategyConfig(new InlineShardingStrategyConfiguration("order_item_id", "t_order_item${order_item_id % 2}"));
		return tableRuleConfig;
	}
	
	/**
	 * 需要手动配置事务管理器
	 * 
	 * @param shardingDataSource
	 * @return
	 */
	@Bean
	public DataSourceTransactionManager transactitonManager(DataSource shardingDataSource) {
		return new DataSourceTransactionManager(shardingDataSource);
	}

	@Bean
	public SqlSessionFactory sqlSessionFactory(DataSource shardingDataSource) throws Exception {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(shardingDataSource);
		bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml"));
		return bean.getObject();
	}
	
	@Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
