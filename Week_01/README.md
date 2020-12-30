# Week01 作业题目

## 2020年10月15日周四：

1. 选做，自己写一个简单的 Hello.java，里面需要涉及基本类型，四则运行，if 和 for，然后自己分析一下对应的字节码，有问题群里讨论。
- https://github.com/gerrypang/JAVA-000/blob/main/Week_01/src/com/gerry/pang/bytecode/hello.class.md

2. 必做，自定义一个 Classloader，加载一个 Hello.xlass 文件，执行 hello 方法，此文件内容是一个 Hello.class 文件所有字节（x=255-x）处理后的文件。文件群里提供。
- https://github.com/gerrypang/JAVA-000/blob/main/Week_01/src/com/gerry/pang/classloader/CustomClassLoader.java

3. 必做，画一张图，展示 Xmx、Xms、Xmn、Meta、DirectMemory、Xss 这些内存参数的关系。
- https://github.com/gerrypang/JAVA-000/blob/main/Week_01/image/jvm.png
![](https://github.com/gerrypang/JAVA-000/blob/main/Week_01/image/jvm.png)

4. ️选做，检查一下自己维护的业务系统的 JVM 参数配置，用 jstat 和 jstack、jmap 查看一下详情，并且自己独立分析一下大概情况，思考有没有不合理的地方，如何改进。
注意：如果没有线上系统，可以自己 run 一个 web/java 项目。
- https://github.com/gerrypang/JAVA-000/blob/main/Week_01/项目分析.md

## 2020年10月17日周六：
1.  选做，本机使用G1 GC启动一个程序，仿照课上案例分析一下JVM情况


## 挑战题

从Classloader到模块化，动态加载的插件机制。

1. 10-使用自定义Classloader机制，实现xlass的加载：xlass是作业材料。

2. 20-实现xlass打包的xar（类似class文件打包的jar）的加载：xar里是xlass。

3. 30-基于自定义Classloader实现类的动态加载和卸载：需要设计加载和卸载。

4. 30-基于自定义Classloader实现模块化机制：需要设计模块化机制。

5. 30-使用xar作为模块，实现xar动态加载和卸载：综合应用前面的内容。 