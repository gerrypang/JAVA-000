# Week09 作业题目

## 2020-12-12（周六）：

1. （选做）实现简单的 Protocol Buffer / Thrift / gRPC(选任一个) 远程调用 demo。
- [实现简单的gRPC远程调用 demo](/Week_09/grpc-demo/README.md)

2. （选做）实现简单的 WebService-Axis2/CXF 远程调用 demo。

3. （必做）改造自定义 RPC 的程序，提交到 GitHub：

    (1) 尝试将服务端写死查找接口实现类变成泛型和反射；

    (2) 尝试将客户端动态代理改成 AOP，添加异常处理；

    (3) 尝试使用 Netty + HTTP 作为 client 端传输方式。


- [改造自定义 RPC 的程序](/Week_09/rpcfx)   先站个坑 还在搞ing


4. （选做☆☆）升级自定义 RPC 的程序：

    (1) 尝试使用压测并分析优化 RPC 性能；

    (2) 尝试使用 Netty + TCP 作为两端传输方式；

    (3) 尝试自定义二进制序列化；

    (4) 尝试压测改进后的 RPC 并分析优化，有问题欢迎群里讨论；

    (5) 尝试将 fastjson 改成 xstream；

    (6) 尝试使用字节码生成方式代替服务端反射。


## 2020-12-13（周日）：

1. （选做）按课程第二部分练习各个技术点的应用。

2. （选做）按 dubbo-samples 项目的各个 demo 学习具体功能使用。

3. （必做）结合 dubbo + hmily，实现一个 TCC 外汇交易处理，代码提交到 GitHub:

    (1) 用户 A 的美元账户和人民币账户都在 A 库，使用 1 美元兑换 7 人民币 ;

    (2) 用户 B 的美元账户和人民币账户都在 B 库，使用 7 人民币兑换 1 美元 ;

    (3) 设计账户表，冻结资产表，实现上述两个本地事务的分布式事务。

- [结合 dubbo + hmily实现一个 TCC 外汇交易处理](/Week_09/springboot-dubbo-demo)  先站个坑 还在搞ing

4. （挑战☆☆）尝试扩展 Dubbo

    (1) 基于上次作业的自定义序列化，实现 Dubbo 的序列化扩展 ;

    (2) 基于上次作业的自定义 RPC，实现 Dubbo 的 RPC 扩展 ;

    (3) 在 Dubbo 的 filter 机制上，实现 REST 权限控制，可参考 dubbox;

    (4) 实现一个自定义 Dubbo 的 Cluster/Loadbalance 扩展，如果一分钟内调用某个服务 / 提供者超过 10 次，则拒绝提供服务直到下一分钟 ;

    (5) 整合 Dubbo + Sentinel，实现限流功能 ;

    (6) 整合 Dubbo 与 Skywalking，实现全链路性能监控。

