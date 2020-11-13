# Week02 作业题目

## 2020年10月22日周四：

1. 使用 GCLogAnalysis.java 自己演练一遍串行 / 并行 / CMS / G1 的案例。

2. 使用压测工具（wrk 或 sb），演练 gateway-server-0.0.1-SNAPSHOT.jar 示例。

3. （选做）如果自己本地有可以运行的项目，可以按照 2 的方式进行演练。

4. （必做）根据上述自己对于 1 和 2 的演示，写一段对于不同 GC 的总结，提交到 Github。
- https://github.com/gerrypang/JAVA-000/blob/main/Week_02/GC%E6%80%BB%E7%BB%93.md

## 2020年10月24日周六：

1. （选做）运行课上的例子，以及 Netty 的例子，分析相关现象。

2. （必做）写一段代码，使用 HttpClient 或 OkHttp 访问 http://localhost:8801 ，代码提交到 Github。
- https://github.com/gerrypang/JAVA-000/blob/main/Week_02/src/main/java/com/gerry/pang/demo/RestDemo.java

## 心得

## 疑问
1. 在复习的过程中，对于JDBC破坏双亲加载这块不太理解，查阅了一些资料但是理解的不够透彻，希望老师能够补充一下这块内容
2. 对于老师说的G1 GC 能否在展开讲一下里面的原理


## 挑战题

实现一个http 文件服务器和一个ftp文件服务器。

1. 10-实现文件列表展示：http直接网页展示列表即可。ftp支持cd、ls命令。

2. 20-实现文件上传下载：http上传不需要支持multi-part，直接post文件内容即可。ftp只需要支持主动模式或被动模式的一种。

3. 30-支持断点续传：http下载需要实现range，上传需要自己设计服务器端的分片方式并记录。ftp需要实现retr，stor，rest命令。

4. 30-实现多线程文件上传下载：基于断点续传，需考虑客户端分片方式，多线程调度。

5. 30-实现爬虫爬取前面实现的服务器上所有文件：需要考虑html解析，记录多个文件的传输进度，位置等。