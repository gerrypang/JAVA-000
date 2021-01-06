## 结合 dubbo + hmily，实现一个 TCC 外汇交易处理，题目要求如下
1. 用户 A 的美元账户和人民币账户都在 A 库，使用 1 美元兑换 7 人民币 ;
2. 用户 B 的美元账户和人民币账户都在 B 库，使用 7 人民币兑换 1 美元 ;
3. 设计账户表，冻结资产表，实现上述两个本地事务的分布式事务。


### 数据库表结构设计
- [表结构设计文档](/Week_09/springboot-dubbo-demo/table.xlsx)

账户表结构
![tb_user_assets.png](https://github.com/gerrypang/JAVA-000/blob/main/Week_09/images/tb_user_assets.png)

冻结资产表结构
![tb_frozen_assets.png](https://github.com/gerrypang/JAVA-000/blob/main/Week_09/images/tb_frozen_assets.png)

- [建库脚本](/Week_09/springboot-dubbo-demo/create_database_script.sql)

### 测试结果

- 正向测试用例
通过rest接口，尝试从A库下a账户（有1000CNY，1USD）向B库下b账户（100USD）进行外汇交易，通过 a使用7 CNY 向b购汇 1USD

预期: a 有993CNY 和 2USD; b 有7CNY 和 99USD

测试结果：

![postman 请求截图](https://github.com/gerrypang/JAVA-000/blob/main/Week_09/images/postman.png)

![a账户结果](https://github.com/gerrypang/JAVA-000/blob/main/Week_09/images/a_assests.png)

![b账户结果](https://github.com/gerrypang/JAVA-000/blob/main/Week_09/images/b_assests.png)

- 异常测试用例
通过rest接口，尝试从A库下a账户（有993CNY，2USD）向B库下b账户（有7CNY，99USD）进行外汇交易，通过 a使用7 CNY 向b购汇 1USD，但是购汇过程出现异常

预期: a和b保持原有账户中金额，a 有 993CNY 和 2USD; b 有 7CNY 和 99USD

测试结果：

![postman 请求截图](https://github.com/gerrypang/JAVA-000/blob/main/Week_09/images/postman-error.png)

![a账户结果](https://github.com/gerrypang/JAVA-000/blob/main/Week_09/images/a_assests_error.png)

![b账户结果](https://github.com/gerrypang/JAVA-000/blob/main/Week_09/images/b_assests_error.png)

### 参考资料
- https://dromara.org/website/zh-cn/docs/hmily/user-dubbo.html
- https://github.com/dromara/hmily/blob/master/hmily-demo/hmily-demo-dubbo/