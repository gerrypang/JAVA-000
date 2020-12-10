## 分库分表DEMO
设计对前面的订单表数据进行水平分库分表，拆分 2 个库，每个库 16 张表。并在新结构在演示常见的增删改查操作。代码、sql 和配置文件


### 思路
基于  apache-shardingsphere-proxy，其中配置数据库的分库和分表规则，然后执行创建表SQL和插入数据SQL, shardingsphere-proxy 基于
已经定义好的规则自动在库中创建分表和插入数据到不同的表中

### 环境准备
1. 基于mysql-5.7.31版本，准备两个mysql数据库
2. 下载 apache-shardingsphere-proxy-5.0.0-alpha

### 操作步骤 
1. 在已经准备好的两个库中创建库mall_00， mall_01

- ![mall_00 数库连接](https://github.com/gerrypang/JAVA-000/tree/main/Week_08/images/mall-00-connection.png)
- ![mall_01 数库连接](https://github.com/gerrypang/JAVA-000/tree/main/Week_08/images/mall-01-connection.png)

2. 修改apache-shardingsphere-proxy配置文件，主要修改两个配置文件，[server.yaml](/JAVA-000/Week_08/ShardingSphere-Proxy-Demo/config/server.yaml)、[config-sharding.yaml](/JAVA-000/Week_08/ShardingSphere-Proxy-Demo/config/config-sharding.yaml)


- server.yaml：主要配置proxy 服务端相关信息 
- config-sharding.yaml：主要配置自定义的分库和分表规则


3. 通过客户端创建 shardingsphere-proxy 连接

proxy 它相当对两个库的代理，在上面执行SQL会在解析后根据规则到相对应的库上执行，执行成功后获取结果返回proxy进行汇总展示


- ![shardingsphere-proxy 连接](https://github.com/gerrypang/JAVA-000/tree/main/Week_08/images/sharding-connection.png)

4. 在 shardingsphere-proxy 客户端，[执行创建表SQL](/JAVA-000/Week_08/ShardingSphere-Proxy-Demo/sql/create_table.sql)

执行成功后proxy 会根据规则，在两个mall库中创建 16张 tb_goods 表和16 张tb _goods_brand 表 

5. 在 proxy 中执行[插入](/JAVA-000/Week_08/ShardingSphere-Proxy-Demo/sql/tb_goods-insert-demo.sql)、修改、和查询 SQL操作

- ![tb_goods 全表查询](https://github.com/gerrypang/JAVA-000/tree/main/Week_08/images/sharding-tb_goods-data.png)
- ![tb_goods 根据分库/分表条件查询](https://github.com/gerrypang/JAVA-000/tree/main/Week_08/images/sharding-tb_goods-data-one.png) 
- ![tb_goods 根据分库/分表条件修改](https://github.com/gerrypang/JAVA-000/tree/main/Week_08/images/sharding-tb_goods-data-update-one.png) 

