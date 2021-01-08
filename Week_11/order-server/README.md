## 基于 Redis 封装分布式数据操作：

[x] 在 Java 中实现一个简单的分布式锁；

[x] 在 Java 中实现一个分布式计数器，模拟减库存。


### 代码实现
- 分布式锁： [StoreController.java 中 createOrder 接口](/Week_11/order-server/src/main/java/com/gerry/pang/order/controller/StoreController.java)

- 减库存： [StoreController.java 中 payOrder 接口](/Week_11/order-server/src/main/java/com/gerry/pang/order/controller/StoreController.java)


### 调试lua 脚本执行
对于入参的执行可以通过 redis中的monitor命令进行监控，查看key和入参是否符合预期


### 参考资料
- https://time.geekbang.org/column/article/307421
- https://time.geekbang.org/column/article/40743
- https://docs.spring.io/spring-data/redis/docs/2.4.2/reference/html/#scripting
- https://www.cnblogs.com/yulibostu/articles/12517820.html
- https://www.redis.com.cn/commands/monitor.html
