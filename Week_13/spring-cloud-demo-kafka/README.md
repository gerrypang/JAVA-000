# kafka 实战练习

## （必做）搭建一个 3 节点 Kafka 集群，测试功能和性能；实现 spring kafka 下对 kafka 集群的操作

### kafka 集群3 节点配置
- [集群配置](/Week13/kafka-conf)

### spring kafka 下对 kafka 集群的操作
- [spring kakfa config](/Week_13/spring-cloud-demo-kafka/order-server/src/main/java/com/gerry/pang/config/KafkaConfig.java)
- [consumer 实现类](/Week_13/spring-cloud-demo-kafka/order-server/src/main/java/com/gerry/pang/mq/kafka/consumer/ConsumerImpl.java)
- [producer 实现类](/Week_13/spring-cloud-demo-kafka/order-server/src/main/java/com/gerry/pang/mq/kafka/producer/ProducerImpl.java)

## （挑战☆☆☆）Kafka 金融领域实战

在证券或者外汇、数字货币类金融核心交易系统里，对于订单的处理，大概可以分为收单、定序、撮合、清算等步骤。其中我们一般可以用 mq 来实现订单定序，然后将订单发送给撮合模块。

- 收单：请实现一个订单的 rest 接口，能够接收一个订单 Order 对象；
- 定序：将 Order 对象写入到 kafka 集群的 order.usd2cny 队列，要求数据有序并且不丢失；
- 撮合：模拟撮合程序（不需要实现撮合逻辑），从 kafka 获取 order 数据，并打印订单信息，要求可重放, 顺序消费, 消息仅处理一次。

### 服务
- discover-server 使用 spring cloud eureka 作为注册中心 
- gateway-server 使用 spring cloud gateway 作为网关
- order-server 订单服务
- product-server 库存服务


### 参考资料
- https://zookeeper.apache.org/doc/current/zookeeperStarted.html
- http://kafka.apache.org/documentation/#configuration
- https://www.cnblogs.com/silent2012/p/10045557.html
- https://docs.spring.io/spring-kafka/docs/current/reference/html/#preface