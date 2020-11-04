# Week03 作业要求
## 说明
按今天的课程要求，实现一个网关，基础代码可以 fork：https://github.com/kimmking/JavaCourseCodes02nio/nio02 文件夹下，实现以后，代码提交到 Github。 

## 作业提交规范：
1. 作业不要打包 
2. 同学们写在 md 文件里，而不要发 Word, Excel , PDF 等 
3. 代码类作业需提交完整 Java 代码，不能是片段 
4. 作业按课时分目录，仅上传作业相关，笔记分开记录 
5. 画图类作业提交可直接打开的图片或 md，手画的图手机拍照上传后太大，难以查看，推荐画图（推荐 PPT、Keynote） 
6. 提交记录最好要标明明确的含义（比如第几题作业） 

### 2020-10-29(周四) ：
1. （必做）整合你上次作业的 httpclient/okhttp；
2. （选做）使用 netty 实现后端 http 访问（代替上一步骤）

### 2020-10-31(周六)：

1. （必做）实现过滤器。
2. （选做）实现路由。

## 作业实现
- 作业主程序入口：
https://github.com/gerrypang/JAVA-000/blob/main/Week_03/nio02/src/main/java/io/github/kimmking/gateway/NettyServerApplication.java

- 实现简单随机
router ：https://github.com/gerrypang/JAVA-000/blob/main/Week_03/nio02/src/main/java/io/github/kimmking/gateway/router

- 实现filer：
https://github.com/gerrypang/JAVA-000/tree/main/Week_03/nio02/src/main/java/io/github/kimmking/gateway/filter

- 启动结果

![image](https://github.com/gerrypang/JAVA-000/blob/main/Week_03/images/result.png)
 
## 参考资料
1. How It Works 2.0：https://github.com/Netflix/zuul/wiki/How-It-Works-2.0
