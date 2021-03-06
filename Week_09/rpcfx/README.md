##  改造自定义 RPC 的程序

### 实现内容
- [x] (1) 尝试将服务端写死查找接口实现类变成泛型和反射；

- [x] (2) 尝试将客户端动态代理改成 AOP，添加异常处理；

- [x] (3) 尝试使用 Netty + HTTP 作为 client 端传输方式

- [ ] (4) 尝试使用压测并分析优化 RPC 性能；

- [x] (5) 尝试使用 Netty + TCP 作为两端传输方式；

- [x] (6) 尝试自定义二进制序列化；

- [ ] (7) 尝试压测改进后的 RPC 并分析优化，有问题欢迎群里讨论；

- [ ] (8) 尝试将 fastjson 改成 xstream；

- [ ] (9) 尝试使用字节码生成方式代替服务端反射。


### 参考资料
- https://www.cnblogs.com/scy251147/p/10721736.html
- https://segmentfault.com/a/1190000020507086
- https://www.cnblogs.com/carl10086/p/6185095.html
- https://github.com/netty/netty/blob/4.1/example/src/main/java/io/netty/example/
