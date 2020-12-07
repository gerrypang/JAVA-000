# Week07 

## 2020-11-27 作业题目（周五）：

1. （选做）用今天课上学习的知识，分析自己系统的 SQL 和表结构
2. （必做）按自己设计的表结构，插入 100 万订单模拟数据，测试不同方式的插入效率

- [不同方式的插入效率测试总结](/Week_07/insert_big_data.md)

3. （选做）按自己设计的表结构，插入 1000 万订单模拟数据，测试不同方式的插入效
4. （选做）使用不同的索引或组合，测试不同方式查询效率
5. （选做）调整测试数据，使得数据尽量均匀，模拟 1 年时间内的交易，计算一年的销售报表：销售总额，订单数，客单价，每月销售量，前十的商品等等（可以自己设计更多指标）
6. （选做）尝试自己做一个 ID 生成器（可以模拟 Seq 或 Snowflake）

- [disruptor 分布式id生成 ](/Week_07/generator-id-by-disruptor/README.md)

7. （选做）尝试实现或改造一个非精确分页的程序

## 2020-11-28（周六）：
1. （选做）配置一遍异步复制，半同步复制、组复制
2. （必做）读写分离 - 动态切换数据源版本 1.0

- [在第5周作业的基础上进行修改为动态数据源](/Week_05/spring-test/src/main/java/com/gerry/pang/controller/DataHikariController.java) 

3. （必做）读写分离 - 数据库框架版本 2.0

- [sharding-jdbc-3.x demo](/Week_07/sharding-jdbc-demo/README.md)
- [sharding-jdbc-4.x demo](/Week_07/sharding-jdbc-demo-4.x/README.md)

4. （选做）读写分离 - 数据库中间件版本 3.0
5. （选做）配置 MHA，模拟 master 宕机
6. （选做）配置 MGR，模拟 master 宕机
7. （选做）配置 Orchestrator，模拟 master 宕机，演练 UI 调整拓扑结构

## 参考資料
态切换数据源版本 1.0参考资料：
- https://www.cnblogs.com/fishpro/p/spring-boot-study-dynamicdb.html
- https://blog.csdn.net/laojiaqi/article/details/78964862
- https://github.com/lywhlao/multiDS