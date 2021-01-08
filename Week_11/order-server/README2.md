## 基于 Redis 的 PubSub 实现订单异步处理

### 代码实现
- [消息发送 OrderServiceImpl.publishingOrderMessage()](/order-server/src/main/java/com/gerry/pang/order/service/impl/OrderServiceImpl.java)
- [配置消息订阅](/order-server/src/main/java/com/gerry/pang/config/RedisTemplateConfig.java)
- [消息监听处理类](/order-server/src/main/java/com/gerry/pang/order/listenter/PayOrderRedisListener.java)


### 参考资料
- https://docs.spring.io/spring-data/redis/docs/2.4.2/reference/html/#pubsub
- https://blog.csdn.net/yichen0429/article/details/106757359/