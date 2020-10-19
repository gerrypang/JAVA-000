Hello.java 
============
``` java
public class Hello {
	public void demo() {
		int a = 10, b = 2;
		float c = 2.1f;
		double d = 2.1;
		char m = 1;
		if (a % 2 == 0) {
			c = a * b + m;
		}
	}
}
```

javap -v Hello.class
============
``` java
Classfile /I:/极客大学.作业/JAVA-000/Week_01/src/com/gerry/pang/bytecode/Hello.class
  # 最近修改时间
  Last modified 2020-10-19; size 364 bytes
  # 文件md5校验码
  MD5 checksum 42f2a8f6155b6a6e908dde09b53ae3f0
  # 编译文件来源 Hello.java
  Compiled from "Hello.java"
  # 类签名
public class com.gerry.pang.bytecode.Hello
  # jdk版本52.0：即jdk1.8 
  minor version: 0
  major version: 52
  # ACC_PUBLIC 类访问标识，public
  # ACC_SUPER 标志来修正 invokespecial 指令调用 super 类方法的问题
  flags: ACC_PUBLIC, ACC_SUPER
  # 常量池
Constant pool:
	# #1：常量编号， =：分割符，  Methodref：这个常量指向#6类方法签名为#16的方法
   #1 = Methodref          #6.#16         // java/lang/Object."<init>":()V
   #2 = Float              2.1f
   #3 = Double             2.1d
   #5 = Class              #17            // com/gerry/pang/bytecode/Hello
   #6 = Class              #18            // java/lang/Object
   #7 = Utf8               <init>
   #8 = Utf8               ()V
   #9 = Utf8               Code
  #10 = Utf8               LineNumberTable
  #11 = Utf8               demo
  #12 = Utf8               StackMapTable
  #13 = Class              #17            // com/gerry/pang/bytecode/Hello
  #14 = Utf8               SourceFile
  #15 = Utf8               Hello.java
  #16 = NameAndType        #7:#8          // "<init>":()V
  #17 = Utf8               com/gerry/pang/bytecode/Hello
  #18 = Utf8               java/lang/Object
{
  # hello类中方法签名
  public com.gerry.pang.bytecode.Hello();
    descriptor: ()V
     # 访问标识 
    flags: ACC_PUBLIC
    Code:
      # 栈深度1，局部变量表1，方法入参个数1 this
      stack=1, locals=1, args_size=1
      	   # 引用类型加载到栈0
         0: aload_0
         # 调用常量池#1 实例化
         1: invokespecial #1                  // Method java/lang/Object."<init>":()V
         4: return
      LineNumberTable:
      	  # 11行 从Code中0编号位置开始
        line 11: 0
  
  # 方法签名
  public void demo();
    descriptor: ()V
    # 访问标识public
    flags: ACC_PUBLIC
    Code:
      # 栈深度2，局部变量表7，方法入参个数1 this
      stack=2, locals=7, args_size=1
         # 将常量10加载到栈
         0: bipush        10
         # int类型从栈弹出到局部变量表 1 slot
         # 请注意，store之类的指令调用实际上从栈顶删除了一个值
         2: istore_1
         # int类型将常量2加载到栈
         3: iconst_2
         # int类型从栈弹出到局部变量表 2 slot
         4: istore_2
         # float类型常量表中#2.1f加载到栈 
         5: ldc           #2                  // float 2.1f
         # float类型常量加载到局部变量表 3 slot
         7: fstore_3
         # double类型
         8: ldc2_w        #3                  // double 2.1d
         # double类型从栈顶压如局部变量表 4 slot
        11: dstore        4
        # int类型常量1
        13: iconst_1
        # 弹出栈顶int元素，存入局部变量表6 slot
        14: istore        6
        # load 指令则会将值从局部变量表压入操作数栈，但并不会删除局部变量中的 值
        # int类型从局部变量表压入栈1
        16: iload_1
        # int类型将常量2加载到栈2
        17: iconst_2
        # int类型 求余操作
        18: irem
        # if 不等于，执行到30编号
        19: ifne          30
        # int类型从局部变量表压入栈1
        22: iload_1
        # int类型从局部变量表压入栈2
        23: iload_2
        # int类型 乘法
        24: imul
        # int类型栈压如局部变量表6 slot
        25: iload         6
        # int类型加法操作
        27: iadd
        # int类型转float类型
        28: i2f
        # float类型常量加载到局部变量表 3 slot
        29: fstore_3
        # 方法返回
        30: return
      LineNumberTable:
        # 13行 从Code中0编号位置开始
        line 13: 0
        # 14行 从Code中5编号位置开始
        line 14: 5
        # 15行 从Code中8编号位置开始
        line 15: 8
        # 16行 从Code中13编号位置开始
        line 16: 13
        # 17行 从Code中16编号位置开始
        line 17: 16
        # 18行 从Code中22编号位置开始
        line 18: 22
        # 20行 从Code中30编号位置开始
        line 20: 30
      StackMapTable: number_of_entries = 1
        frame_type = 255 /* full_frame */
          offset_delta = 30
          locals = [ class com/gerry/pang/bytecode/Hello, int, int, float, double, int ]
          stack = []
}
 # 源文件名称 Hello.java 
SourceFile: "Hello.java"

```