## 对分布式统一ID生成服务的架构简介

### 包含组件
1. Disruptor 3.x
2. Netty 4.x
3. Guava
4. spring boot 2.x

### 设计思路
基于参考资料课程中的架构，仅实现了简单雏形，其中已实现功能点包括:

1. 业务客户端通过tcp请求，从服务端获取id
2. 服务端预先创建大批量id，存入本地 Guava 缓存
3. 服务端接收到请求后，通过 Disruptor生产者 并发从无锁环形队列中获取 sequence 
4. 根据sequence 从guava中获取唯一key，获取后设置缓存key失效策略 expireAfterAccess(1, TimeUnit.NANOSECONDS)
5. 返回给 Disruptor消费者，并通过 tcp 返回客户端
注意：业务客户端需要设计兜底策略，如果在一定时效内从服务端获取失败时，自己本地通过 Snowflake 生成id

### 待扩展
1. 可以设计水位线，当监控到Guava中预存的id少于50%时，进行自动补充
2. 对本地 Guava缓存 key 进行预先落库，做到可追溯
3. 对不同于业务设计 id 的路由规则
4. 设计业务规则生产具有业务规则的key
![业务id生成规则](https://github.com/gerrypang/JAVA-000/tree/main/Week_07/images/key_rule.png)
5. 考虑服务重启后id如何生成

### 参考资料
- https://coding.imooc.com/learn/list/275.html