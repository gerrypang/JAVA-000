# GC总结
## GC 类型
主要的GC类型有
串行GC：-XX:+UseSerialGC
并行GC：
-XX:+UseConcMarkSweepGC

‐XX:+UseSerialGC
-XX:+UseParallelGC
‐XX:+UseConcMarkSweepGC
‐XX:+UseG1GC


## GC日志分析
``` shell
2020-10-27T21:49:14.235+0800: [Full GC (Ergonomics) [PSYoungGen: 8526K->0K(29184K)] [ParOldGen: 72498K->73714K(87552K)] 81025K->73714K(116736K), [Metaspace: 2704K->2704K(1056768K)], 0.0221761 secs] [Times: user=0.03 sys=0.00, real=0.02 secs]
``` 




## 