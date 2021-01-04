### 结合 dubbo + hmily，实现一个 TCC 外汇交易处理，题目要求如下
1. 用户 A 的美元账户和人民币账户都在 A 库，使用 1 美元兑换 7 人民币 ;
2. 用户 B 的美元账户和人民币账户都在 B 库，使用 7 人民币兑换 1 美元 ;
3. 设计账户表，冻结资产表，实现上述两个本地事务的分布式事务。


### 数据库表结构设计
- 表结构设计

- 建库脚本



### 参考资料：
- https://dromara.org/website/zh-cn/docs/hmily/user-dubbo.html
- https://github.com/dromara/hmily/blob/master/hmily-demo/hmily-demo-dubbo/