## 按自己设计的表结构，插入 100万订单模拟数据，测试不同方式的插入效率

考虑到电脑性能问题，我降低的测试的数量为10w

### 环境信息
- windows 10 64位 i7-7600U 2.80GHz，4盒，16G
- My-8.0.18SQLSQL
- 操作单表，自增主键，15个字段，无索引

### 操作方式
#### 1、使用存储过程循环单条Insert方式
``` SQL
-- 使用存储过程创建模拟100000数据，在命令行模式下，切换到要操作的库，执行如下批量创建数据
delimiter ;;
create procedure idata()
begin
  declare i int;
  set i=1;
  while(i<=100000)do
    INSERT INTO tb_goods (id, name, brand_id, category1_id, category2_id, category3_id, sales, desc_detail, desc_pack, desc_service, keywords, create_time, update_time, version, is_deleted) VALUES (i, 'iphone x', i, i, null, null, 1000, 'iphone x 详细介绍', 'iphone x 包装信息', 'iphone x 售后服务', 'iphone,apple', '2020-11-24 12:56:00', '2020-11-24 12:56:00', 1, false);
    set i=i+1;
  end while;
end;;
delimiter ;
call idata();
```
时长：Query OK, 1 row affected (2 hours 31 min 11.64 sec)
缺陷：太慢了

#### 2、create table select 方式
``` SQL
create table tb_goods_bak select * from tb_goods;
```
时长：Query OK, 100000 rows affected (6.14 sec)
缺陷：创建一张新表，只有表结构相同，没有索引，数据文件会比原表要大一些

#### 3、insert into ... select 方法
``` SQL
create table tb_goods_app like tb_goods;
insert into tb_goods_app select * from tb_goods;
```
时长：Query OK, 100000 rows affected (5.20 sec)
缺陷：可以控制对源表的扫描行数和加锁范围很小的。缺陷会对原表加锁，此外必须先创建insert的表

#### 4、source 方式
``` SQL
mysqldump -uroot -p --default-character-set=utf8 mall tb_goods --result-file=tb_goods.sql

use mall;
source tb_goods.sql
```
时长：约10s
缺陷：导出命令注意使用--result-file，不然导出数据为utf16，此外导出语句中会锁表，导出sql语句其实是insert into values(),()...();形式

#### 5、insert into values(),()...() 方式
时长：约10s
缺陷：SQL的长度会限制数据条数。

#### 6、load 方式;
```SQL
use mall;
select * from tb_goods into outfile 'D:/tb_goods.csv';
load data infile 'D:/tb_goods.csv' into table tb_goods;
```
时长：Query OK, 100000 rows affected (4.57 sec)
缺陷：会限制导出的路径，可以修改my.ini中参数secure-file-priv=''为不限制路径


### 参考资料：
- mysqldump命令参数说明 https://dev.mysql.com/doc/refman/5.7/en/mysqldump.html
- load data命令参数说明 https://dev.mysql.com/doc/refman/5.7/en/load-data.html