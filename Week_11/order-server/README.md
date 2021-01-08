## 基于 Redis 封装分布式数据操作：

[x] 在 Java 中实现一个简单的分布式锁；
/order-server/src/main/java/com/gerry/pang/order/controller/StoreController.java 中 createOrder 接口

[x] 在 Java 中实现一个分布式计数器，模拟减库存。
x/order-server/src/main/java/com/gerry/pang/order/controller/StoreController.java 中 payOrder 接口


### 调试lua 脚本执行
对于入参的执行可以通过 redis中的monitor命令进行监控，查看key和入参是否符合预期
https://www.redis.com.cn/commands/monitor.html


### 参考资料
https://time.geekbang.org/column/article/307421
https://time.geekbang.org/column/article/40743
https://www.cnblogs.com/yulibostu/articles/12517820.html
https://www.redis.com.cn/commands/monitor.html