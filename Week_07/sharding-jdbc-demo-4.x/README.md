## shardingsphere-jdbc 4.x DEMO 

### 使用组件：
- Spring Boot 
- Lombok
- Druid
- Sharding-JDBC 4.x
- MyBatis
- MySQL

### 主要功能：
1. 读写分离
2. 分库分表
3. 强制路由
4. 分布式主键-雪花算法

### 3.x 升级改造点
1. ShardingDataSourceFactory.createDataSource 少了一个参数
2. TableRuleConfiguration 关闭了logicTable、actualDataNodes属性的setter方法，改为通过构造参数配置属性


### 参考资料：
https://shardingsphere.apache.org/document/legacy/4.x/document/cn/features/
