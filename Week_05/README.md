# Week05 作业题目

## 2020-11-12（周四）：

1. （必做）写代码实现 Spring Bean 的装配，方式越多越好（XML、Annotation 都可以）, 提交到 Github。

共实现了6种方法，分别为：

- [通过 XmlBeanFactory 方式](/Week_05/spring-test/src/test/java/com/gerry/pang/loadbean/ByXmlBeanFactoryTest.java)
- [通过 DefaultListableBeanFactory 方式](/Week_05/spring-test/src/test/java/com/gerry/pang/loadbean/ByDefaultListableBeanFactoryTest.java)
- [通过 ClassPathXmlApplicationContext 方式](/Week_05/spring-test/src/test/java/com/gerry/pang/loadbean/ByClassPathXmlApplicationContextTest.java.java)
- [通过 AnnotationConfigApplicationContext 方式](/Week_05/spring-test/src/test/java/com/gerry/pang/loadbean/ByAnnotationConfigApplicationContextTest.java)
- [通过 SpringBootApplication 方式](/Week_05/spring-test/src/test/java/com/gerry/pang/loadbean/BySpringBootApplicationTest.java)
- [通过 SpringBootTest 方式](/Week_05/spring-test/src/test/java/com/gerry/pang/loadbean/BySpringBootTestTest.java)

2. （选做）使 Java 里的动态代理，实现一个简单的 AOP。


3. （选做）实现一个 Spring XML 自定义配置，配置一组 Bean，例如：Student/Klass/School。

通过 ClassPathXmlApplicationContext 对 beanFactoryDemo.xml 中的 Student对象进行配置，设置了 klass 和 school 属性

https://github.com/gerrypang/JAVA-000/blob/main/Week_05/spring-test/src/test/java/com/gerry/pang/getbean/GetBeanFromXML.java

4. （选做，会添加到高手附加题）

- （挑战）讲网关的 frontend/backend/filter/router 线程池都改造成 Spring 配置方式；
- （挑战）基于 AOP 改造 Netty 网关，filter 和 router 使用 AOP 方式实现；
- （中级挑战）基于前述改造，将网关请求前后端分离，中级使用 JMS 传递消息；
- （中级挑战）尝试使用 ByteBuddy 实现一个简单的基于类的 AOP；
- （超级挑战）尝试使用 ByteBuddy 与 Instrument 实现一个简单 JavaAgent 实现无侵入下的 AOP。

## 2020-11-14（周六）：

1. （必做）给前面课程提供的 Student/Klass/School 实现自动配置和 Starter。

核心操作：

- [spring.factories 添加自定义stater配置类路径 ](/Week_05/spring-test/src/main/resources/META-INF/spring.factories)
- [基于@Configuration和@Condition注解 创建自定义configuration 类](/Week_05/spring-test/src/main/java/com/gerry/pang/config/MySpringTestAutoConfiguration.java)
- [自定义 stater 开关注解](/Week_05/spring-test/src/main/java/com/gerry/pang/config/EnableMySpringTestConfiguration.java)
- [properties 配置类](/Week_05/spring-test/src/main/java/com/gerry/pang/)
- [meta-data.json](/Week_05/spring-test/src/main/resources/META-INF/spring-test-metadata.json)
- [properties配置文件](/Week_05/spring-test/src/main/resources/application.properties)

测试方法：

- 创建一个controller，在通过get请求获取stater中自动创建的默认student bean对象，启动Springboot，执行请求获取bean结果如下图所示:

![获取bean结果](https://github.com/gerrypang/JAVA-000/tree/main/Week_05/images/getDemoSudent.png)


2. （必做）研究一下 JDBC 接口和数据库连接池，掌握它们的设计和用法：

- 使用 JDBC 原生接口，实现数据库的增删改查操作。
- 使用事务，PrepareStatement 方式，批处理方式，改进上述操作。
- 配置 Hikari 连接池，改进上述操作。提交代码到 Github。

3.（选做）总结一下，单例的各种写法，比较它们的优劣。

4.（选做）maven/spring 的 profile 机制，都有什么用法？

5.（选做）总结 Hibernate 与 MyBatis 的各方面异同点。

6.（选做）学习 MyBatis-generator 的用法和原理，学会自定义 TypeHandler 处理复杂类型。

## 附加题（可以后面上完数据库的课再考虑做）：

1. (挑战) 基于 AOP 和自定义注解，实现 @MyCache(60) 对于指定方法返回值缓存 60 秒。

2. (挑战) 自定义实现一个数据库连接池，并整合 Hibernate/Mybatis/Spring/SpringBoot。

3. (挑战) 基于 MyBatis 实现一个简单的分库分表 + 读写分离 + 分布式 ID 生成方案。

