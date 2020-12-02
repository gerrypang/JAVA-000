## shardingsphere-jdbc 3.x DEMO 

这个是我在去年春节前没有什么事的时候自己学习，做的小demo，没想到成了今天的作业，在看完老师的作业要求后我本想直接改个版本号就可以解决问题了，但是发现没有这么简单，
其中出现了不少问题，这里简单记录一下，稍后我还会创建一个shardingsphere-jdbc 4.x DEMO。
1. maven仓库中的没有deploy 5.x包
2. 在github的readme 中写的maven链接到 maven中央仓库的artifactId是错误的，因改为shardingsphere-jdbc-core
```xml
<dependency>
    <groupId>org.apache.shardingsphere</groupId>
    <artifactId>shardingsphere-jdbc</artifactId>
    <version>4.1.1</version>
</dependency>
```
3. 从3.x到4.x中包名发生的变化之前是io.shardingsphere，现在改为了org.apache.shardingsphere，需要重新倒入类
4. 3.x中的一些类在4.x中已经找不到了，不知道是不是改了名字，还是已经删除，具体还没有细看

### 使用组件：
- Spring Boot 
- Lombok
- Druid
- Sharding-JDBC
- MyBatis
- MySQL

### 主要功能：
1. 读写分离
2. 分库分表
3. 强制路由
4. 分布式主键-雪花算法
5. 分布式事务-待完成


### 参考资料：
https://shardingsphere.apache.org/document/current/cn/features/sharding/other-features/key-generator/
