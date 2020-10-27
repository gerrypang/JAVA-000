# GC总结
## GC 类型

### 主要的垃圾收集器类型有
1. 串行 GC：-XX:+UseSerialGC
2. 并行 GC：-XX:+UseParallelGC 
3. CMS GC: -XX:+UseConcMarkSweepGC
4. G1 GC: ‐XX:+UseG1GC

### 不同版本JDK默认垃圾收集器
- JDK8：-XX:+UseParallelGC 
- JDK9：‐XX:+UseG1GC

## GC日志分析

### 串行GC/并行GC日志解读
``` shell
2020-10-27T21:49:14.235+0800: [Full GC (Ergonomics) [PSYoungGen: 8526K->0K(29184K)] [ParOldGen: 72498K->73714K(87552K)] 81025K->73714K(116736K), [Metaspace: 2704K->2704K(1056768K)], 0.0221761 secs] [Times: user=0.03 sys=0.00, real=0.02 secs]
``` 
- “2020-10-27T21:49:14.235+0800:” 表示这次GC发时间
- “[GC (xxxx)”和“[Full GC (xxxx)” 表示这次垃圾收集的停顿类型，小括号(xxxx)表示触发GC的原因
- “[PSYoungGen”、“[ParOldGen”、“[Metaspace” 表示GC发生的区域
- 后面方括号内部的“8526K->0K(29184K)” 表示 GC前该内存区域已使用容量-＞GC后该内存区域已使用容量（该内存区域总容量）。
- 在方括号之外的“81025K->73714K(116736K)” 表示GC前Java堆已使用容量-＞GC后Java堆已使用容量（Java堆总容量）
- 0.0221761 secs] 表示GC总用时多少秒
- [Times: user=0.03 sys=0.00, real=0.02 secs] 其中user表示用户线程占用的时间，sys表示系统内核占用的时间，real表示实际占用的时间 


### UseConcMarkSweepGC日志解读
``` shell
2020-10-27T22:43:48.590+0800: [GC (CMS Initial Mark) [1 CMS-initial-mark: 157665K(189372K)] 175173K(346620K), 0.0026260 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
2020-10-27T22:43:48.596+0800: [CMS-concurrent-mark-start]
2020-10-27T22:43:48.606+0800: [CMS-concurrent-mark: 0.005/0.005 secs] [Times: user=0.03 sys=0.00, real=0.01 secs]
2020-10-27T22:43:48.606+0800: [CMS-concurrent-preclean-start]
2020-10-27T22:43:48.612+0800: [CMS-concurrent-preclean: 0.001/0.001 secs] [Times: user=0.00 sys=0.00, real=0.01 secs]
2020-10-27T22:43:48.614+0800: [CMS-concurrent-abortable-preclean-start]
2020-10-27T22:43:48.663+0800: [GC (Allocation Failure) 2020-10-27T22:43:48.663+0800: [ParNew2020-10-27T22:43:48.719+0800: [CMS-concurrent-abortable-preclean: 0.001/0.102 secs] [Times: user=0.22 sys=0.05, real=0.11 secs]
: 157248K->17472K(157248K), 0.1212269 secs] 314913K->222175K(362492K), 0.1219737 secs] [Times: user=0.27 sys=0.09, real=0.12 secs]
2020-10-27T22:43:48.799+0800: [GC (CMS Final Remark) [YG occupancy: 20924 K (157248 K)]2020-10-27T22:43:48.800+0800: [Rescan (parallel) , 0.0012167 secs]2020-10-27T22:43:48.801+0800: [weak refs processing, 0.0129014 secs]2020-10-27T22:43:48.814+0800: [class unloading, 0.0009801 secs]2020-10-27T22:43:48.815+0800: [scrub symbol table, 0.0018824 secs]2020-10-27T22:43:48.817+0800: [scrub string table, 0.0140317 secs][1 CMS-remark: 204703K(205244K)] 225628K(362492K), 0.0324291 secs] [Times: user=0.00 sys=0.00, real=0.03 secs]
2020-10-27T22:43:48.848+0800: [CMS-concurrent-sweep-start]
2020-10-27T22:43:48.855+0800: [CMS-concurrent-sweep: 0.002/0.002 secs] [Times: user=0.01 sys=0.00, real=0.02 secs]
2020-10-27T22:43:48.871+0800: [CMS-concurrent-reset-start]
2020-10-27T22:43:48.878+0800: [CMS-concurrent-reset: 0.002/0.002 secs] [Times: user=0.01 sys=0.00, real=0.02 secs]
```
CMS垃圾收集器包括四个阶段，其中包括初始标记（会发生STW）、并发标记、重写标记（会发生STW）、并发清除
- “[GC (CMS Initial Mark)” 初始化标记阶段
- “[CMS-concurrent-mark-start]” 并发标记开始
- “[CMS-concurrent-mark: 0.005/0.005 secs]” 并发标记执行完成
- “[CMS-concurrent-preclean-start]” 并发预清理阶段开始
- “[CMS-concurrent-preclean: 0.001/0.001 secs] ” 并发预清理阶段完成
- “[CMS-concurrent-abortable-preclean-start]” 并发可中止的预清理阶段
- “[GC (CMS Final Remark)” 重写标记
- “[CMS-concurrent-sweep-start]” 并发清除开始
- “[CMS-concurrent-sweep: 0.002/0.002 secs]” 并发清除完成