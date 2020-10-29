# GC总结

## 问题说明
1. 使用 GCLogAnalysis.java 自己演练一遍串行 / 并行 / CMS / G1 的案例。
2. 使用压测工具（wrk 或 sb），演练 gateway-server-0.0.1-SNAPSHOT.jar 示例。

根据上述自己对于 1 和 2 的演示，写一段对于不同 GC 的总结，提交到 Github

## 环境参数说明
- JDK版本：1.8.0_231
- 电脑配置：i7-7600U 2.80GHz，4盒，16G

## GCLogAnalysis 实验数据总结
![gc-data-table.png](https://github.com/gerrypang/JAVA-000/blob/main/Week_02/images/gc-data-table.png)
![jvm-xmx-xms.png](https://github.com/gerrypang/JAVA-000/blob/main/Week_02/images/jvm-xmx-xms.png)

1. 此程序不管是用什么垃圾收集器，基本上128M情况都无法满足正常使用，都会导致内存溢出OOM问题
2. 如果想要减少full gc问题，不管使用什么垃圾收集器，最大的对空间至少需要512M
3. 如果想要减少yang gc问题，不管使用什么垃圾收集器，最大的对空间至少需要1024M
4. 此程序如果想提升吞吐量，降低gc频率则需要-Xms1024m -Xmx1024m及以上配置
5. 如果UseG1GC则至少需要256M以上的内存才不会报00M问题
6. 初始化和最大可以配置为相同 -Xms2048m -Xmx2048m，避免运行时自动扩容，官方文档中也是这样建议的
7. 有一个诡异的现象如果使用UseConcMarkSweepGC，到了1024M及之后无论怎么扩大初始化和最大堆空间，年轻代都只会分配306688K（300M）

## gateway-server 实验数据总结
![gateway-data.png](https://github.com/gerrypang/JAVA-000/blob/main/Week_02/images/gateway-data.png)
执行 `sb -u http://127.0.0.1:8088/api/hello -c 20 -N 60` 对四种主流不同的垃圾收集器进行压测。
1. 从压测结果中我们可以看出，串行GC和并行在4G情况下，并没有明显的性能提升，反而下降了。
2. 在所有种类的GC中，我们可以看串行出平均最大响应时间要明显比其他GC要低
3. 对于CMS和G1两种GC在扩大堆内存空间后各指标均有所提升

有一点疑问，老师让我们用压测这个意义是什么？从用要发现什么问题，可能我对压测不太了解，没有发现什么更重要的问题，忘老师指点。

## GC 类型

### 主要的垃圾收集器类型有
1. 串行 GC：-XX:+UseSerialGC
2. 并行 GC：-XX:+UseParallelGC 
3. CMS GC: -XX:+UseConcMarkSweepGC
4. G1 GC: ‐XX:+UseG1GC

### 不同版本JDK默认垃圾收集器
- JDK8：-XX:+UseParallelGC 
- JDK9：‐XX:+UseG1GC
- 默认参数查询方法： java -XX:+PrintFlagsInitial
- 不指定JVM堆大小时默认为内存的1/4

## GC日志分析

### 串行GC/并行GC日志解读
``` shell
2020-10-27T21:49:14.235+0800: [Full GC (Ergonomics) [PSYoungGen: 8526K->0K(29184K)] [ParOldGen: 72498K->73714K(87552K)] 81025K->73714K(116736K), [Metaspace: 2704K->2704K(1056768K)], 0.0221761 secs] [Times: user=0.03 sys=0.00, real=0.02 secs]
``` 
![GC-log.png](https://github.com/gerrypang/JAVA-000/blob/main/Week_02/images/GC-log.png)

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

``` shell
2020-10-28T10:10:31.342+0800: [GC pause (G1 Humongous Allocation) (young) (initial-mark), 0.0071045 secs]
   [Parallel Time: 6.4 ms, GC Workers: 4]
      [GC Worker Start (ms): Min: 816.1, Avg: 816.1, Max: 816.1, Diff: 0.0]
      [Ext Root Scanning (ms): Min: 0.1, Avg: 0.1, Max: 0.1, Diff: 0.0, Sum: 0.5]
      [Update RS (ms): Min: 0.2, Avg: 0.2, Max: 0.2, Diff: 0.0, Sum: 0.8]
         [Processed Buffers: Min: 1, Avg: 3.0, Max: 4, Diff: 3, Sum: 12]
      [Scan RS (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.1]
      [Code Root Scanning (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
      [Object Copy (ms): Min: 5.8, Avg: 5.9, Max: 6.0, Diff: 0.1, Sum: 23.6]
      [Termination (ms): Min: 0.0, Avg: 0.1, Max: 0.2, Diff: 0.2, Sum: 0.4]
         [Termination Attempts: Min: 1, Avg: 1.0, Max: 1, Diff: 0, Sum: 4]
      [GC Worker Other (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
      [GC Worker Total (ms): Min: 6.3, Avg: 6.4, Max: 6.4, Diff: 0.1, Sum: 25.5]
      [GC Worker End (ms): Min: 822.5, Avg: 822.5, Max: 822.5, Diff: 0.1]
   [Code Root Fixup: 0.0 ms]
   [Code Root Purge: 0.0 ms]
   [Clear CT: 0.1 ms]
   [Other: 0.6 ms]
      [Choose CSet: 0.0 ms]
      [Ref Proc: 0.2 ms]
      [Ref Enq: 0.0 ms]
      [Redirty Cards: 0.1 ms]
      [Humongous Register: 0.1 ms]
      [Humongous Reclaim: 0.1 ms]
      [Free CSet: 0.1 ms]
   [Eden: 105.0M(125.0M)->0.0B(142.0M) Survivors: 16.0M->18.0M Heap: 307.2M(512.0M)->214.5M(512.0M)]
 [Times: user=0.05 sys=0.02, real=0.01 secs] 
2020-10-28T10:10:31.349+0800: [GC concurrent-root-region-scan-start]
2020-10-28T10:10:31.349+0800: [GC concurrent-root-region-scan-end, 0.0001775 secs]
2020-10-28T10:10:31.349+0800: [GC concurrent-mark-start]
2020-10-28T10:10:31.353+0800: [GC concurrent-mark-end, 0.0026642 secs]
2020-10-28T10:10:31.353+0800: [GC remark 2020-10-28T10:10:31.353+0800: [Finalize Marking, 0.0000721 secs] 2020-10-28T10:10:31.353+0800: [GC ref-proc, 0.0000608 secs] 2020-10-28T10:10:31.353+0800: [Unloading, 0.0004506 secs], 0.0014068 secs]
 [Times: user=0.00 sys=0.00, real=0.00 secs] 
2020-10-28T10:10:31.356+0800: [GC cleanup 219M->217M(512M), 0.0004672 secs]
 [Times: user=0.00 sys=0.00, real=0.00 secs] 
2020-10-28T10:10:31.356+0800: [GC concurrent-cleanup-start]
2020-10-28T10:10:31.356+0800: [GC concurrent-cleanup-end, 0.0000230 secs]
CMS垃圾收集器包括5个阶段，其中包括初始标记（会发生STW）、根分区扫描、并发标记、重写标记（会发生STW）、清除（会发生STW）、并发清理
```
- “[GC pause (G1 Humongous Allocation) (young) (initial-mark), 0.0071045 secs]” 表示新生代垃圾收集，
- “[GC concurrent-root-region-scan-start]” 表示根分区扫描开始
- “[GC concurrent-root-region-scan-end, 0.0001775 secs]” 表示根分区扫描结束
- “[GC concurrent-mark-start]” 表示并发标记阶段开始
- “[GC concurrent-mark-end, 0.0026642 secs]” 表示并发标记阶段结束
- “[GC remark 2020-10-28T10:10:31.353+0800: [Finalize Marking, 0.0000721 secs]” 表示重新标记阶段
- “[GC cleanup 219M->217M(512M), 0.0004672 secs]” 表示清理阶段
- “[GC concurrent-cleanup-start]” 表示并发清理阶段启动
- “[GC concurrent-cleanup-end, 0.0000230 secs]” 表示并发清理阶段结束


## 参考资料：
1. 官网Jvm jdk7参数说明：https://www.oracle.com/java/technologies/javase/vmoptions-jsp.html
2. 官网Jvm jdk8参数说明：https://docs.oracle.com/javase/8/docs/technotes/tools/windows/java.html
3. Red hat 介绍G1：https://www.redhat.com/en/blog/part-1-introduction-g1-garbage-collector
4. Red hat 介绍G1 log：https://www.redhat.com/en/blog/collecting-and-reading-g1-garbage-collector-logs-part-2
