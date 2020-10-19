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
Classfile /I:/���ʹ�ѧ.��ҵ/JAVA-000/Week_01/src/com/gerry/pang/bytecode/Hello.class
  # ����޸�ʱ��
  Last modified 2020-10-19; size 364 bytes
  # �ļ�md5У����
  MD5 checksum 42f2a8f6155b6a6e908dde09b53ae3f0
  # �����ļ���Դ Hello.java
  Compiled from "Hello.java"
  # ��ǩ��
public class com.gerry.pang.bytecode.Hello
  # jdk�汾52.0����jdk1.8 
  minor version: 0
  major version: 52
  # ACC_PUBLIC ����ʱ�ʶ��public
  # ACC_SUPER ��־������ invokespecial ָ����� super �෽��������
  flags: ACC_PUBLIC, ACC_SUPER
  # ������
Constant pool:
	# #1��������ţ� =���ָ����  Methodref���������ָ��#6�෽��ǩ��Ϊ#16�ķ���
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
  # hello���з���ǩ��
  public com.gerry.pang.bytecode.Hello();
    descriptor: ()V
     # ���ʱ�ʶ 
    flags: ACC_PUBLIC
    Code:
      # ջ���1���ֲ�������1��������θ���1 this
      stack=1, locals=1, args_size=1
      	   # �������ͼ��ص�ջ0
         0: aload_0
         # ���ó�����#1 ʵ����
         1: invokespecial #1                  // Method java/lang/Object."<init>":()V
         4: return
      LineNumberTable:
      	  # 11�� ��Code��0���λ�ÿ�ʼ
        line 11: 0
  
  # ����ǩ��
  public void demo();
    descriptor: ()V
    # ���ʱ�ʶpublic
    flags: ACC_PUBLIC
    Code:
      # ջ���2���ֲ�������7��������θ���1 this
      stack=2, locals=7, args_size=1
         # ������10���ص�ջ
         0: bipush        10
         # int���ʹ�ջ�������ֲ������� 1 slot
         # ��ע�⣬store֮���ָ�����ʵ���ϴ�ջ��ɾ����һ��ֵ
         2: istore_1
         # int���ͽ�����2���ص�ջ
         3: iconst_2
         # int���ʹ�ջ�������ֲ������� 2 slot
         4: istore_2
         # float���ͳ�������#2.1f���ص�ջ 
         5: ldc           #2                  // float 2.1f
         # float���ͳ������ص��ֲ������� 3 slot
         7: fstore_3
         # double����
         8: ldc2_w        #3                  // double 2.1d
         # double���ʹ�ջ��ѹ��ֲ������� 4 slot
        11: dstore        4
        # int���ͳ���1
        13: iconst_1
        # ����ջ��intԪ�أ�����ֲ�������6 slot
        14: istore        6
        # load ָ����Ὣֵ�Ӿֲ�������ѹ�������ջ����������ɾ���ֲ������е� ֵ
        # int���ʹӾֲ�������ѹ��ջ1
        16: iload_1
        # int���ͽ�����2���ص�ջ2
        17: iconst_2
        # int���� �������
        18: irem
        # if �����ڣ�ִ�е�30���
        19: ifne          30
        # int���ʹӾֲ�������ѹ��ջ1
        22: iload_1
        # int���ʹӾֲ�������ѹ��ջ2
        23: iload_2
        # int���� �˷�
        24: imul
        # int����ջѹ��ֲ�������6 slot
        25: iload         6
        # int���ͼӷ�����
        27: iadd
        # int����תfloat����
        28: i2f
        # float���ͳ������ص��ֲ������� 3 slot
        29: fstore_3
        # ��������
        30: return
      LineNumberTable:
        # 13�� ��Code��0���λ�ÿ�ʼ
        line 13: 0
        # 14�� ��Code��5���λ�ÿ�ʼ
        line 14: 5
        # 15�� ��Code��8���λ�ÿ�ʼ
        line 15: 8
        # 16�� ��Code��13���λ�ÿ�ʼ
        line 16: 13
        # 17�� ��Code��16���λ�ÿ�ʼ
        line 17: 16
        # 18�� ��Code��22���λ�ÿ�ʼ
        line 18: 22
        # 20�� ��Code��30���λ�ÿ�ʼ
        line 20: 30
      StackMapTable: number_of_entries = 1
        frame_type = 255 /* full_frame */
          offset_delta = 30
          locals = [ class com/gerry/pang/bytecode/Hello, int, int, float, double, int ]
          stack = []
}
 # Դ�ļ����� Hello.java 
SourceFile: "Hello.java"

```