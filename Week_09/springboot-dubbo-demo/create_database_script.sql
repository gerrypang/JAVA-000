CREATE database bank_db_01;

use bank_db_01;
CREATE TABLE tb_frozen_assets (id int NOT NULL AUTO_INCREMENT, user_account int NOT NULL COMMENT '用户账户', assets_type varchar(10) DEFAULT 'CNY' NOT NULL COMMENT '资产类型', amount decimal(10,2) DEFAULT 0.00 NOT NULL COMMENT '金额', asset_status tinyint DEFAULT 0 NOT NULL COMMENT '资产状态', frozen_time datetime NOT NULL COMMENT '冻结时间', unfrozen_time datetime COMMENT '解冻时间', create_time datetime NOT NULL COMMENT '数据创建时间', update_time datetime NOT NULL COMMENT '数据更新时间', version int DEFAULT 0 NOT NULL COMMENT '数据逻辑删除', deleted bit DEFAULT b'0' NOT NULL COMMENT '数据版本', PRIMARY KEY (id)) ENGINE=InnoDB DEFAULT CHARSET=utf8 DEFAULT COLLATE=utf8_general_ci COMMENT='冻结资产表';
CREATE TABLE tb_user_assets (id int NOT NULL AUTO_INCREMENT, username varchar(128) NOT NULL COMMENT '用户名', assets_type varchar(10) DEFAULT 'CNY' NOT NULL COMMENT '资产类型', amount decimal(10,2) DEFAULT 0.00 NOT NULL COMMENT '金额', create_time datetime NOT NULL COMMENT '数据创建时间', update_time datetime NOT NULL COMMENT '数据更新时间', version int DEFAULT 0 NOT NULL COMMENT '数据逻辑删除', deleted bit DEFAULT b'0' NOT NULL COMMENT '数据版本', user_account int NOT NULL, PRIMARY KEY (id)) ENGINE=InnoDB DEFAULT CHARSET=utf8 DEFAULT COLLATE=utf8_general_ci COMMENT='账户表';
INSERT INTO tb_user_assets (id, username, assets_type, amount, create_time, update_time, version, deleted, user_account) VALUES (1, 'zhangsan', 'CNY', 1000.00, '2021-01-04 17:19:35', '2021-01-04 17:19:35', 39, false, 2000105);
INSERT INTO tb_user_assets (id, username, assets_type, amount, create_time, update_time, version, deleted, user_account) VALUES (5, 'zhangsan', 'USD', 1.00, '2021-01-06 09:22:56', '2021-01-06 09:22:56', 0, false, 2000105);



CREATE database bank_db_02;

use bank_db_02;
CREATE TABLE tb_frozen_assets (id int NOT NULL AUTO_INCREMENT, user_account int NOT NULL COMMENT '用户账户', assets_type varchar(10) DEFAULT 'CNY' NOT NULL COMMENT '资产类型', amount decimal(10,2) DEFAULT 0.00 NOT NULL COMMENT '金额', asset_status tinyint DEFAULT 0 NOT NULL COMMENT '资产状态', frozen_time datetime NOT NULL COMMENT '冻结时间', unfrozen_time datetime COMMENT '解冻时间', create_time datetime NOT NULL COMMENT '数据创建时间', update_time datetime NOT NULL COMMENT '数据更新时间', version int DEFAULT 0 NOT NULL COMMENT '数据逻辑删除', deleted bit DEFAULT b'0' NOT NULL COMMENT '数据版本', PRIMARY KEY (id)) ENGINE=InnoDB DEFAULT CHARSET=utf8 DEFAULT COLLATE=utf8_general_ci COMMENT='冻结资产表';
CREATE TABLE tb_user_assets (id int NOT NULL AUTO_INCREMENT, username varchar(128) NOT NULL COMMENT '用户名', assets_type varchar(10) DEFAULT 'CNY' NOT NULL COMMENT '资产类型', amount decimal(10,2) DEFAULT 0.00 NOT NULL COMMENT '金额', create_time datetime NOT NULL COMMENT '数据创建时间', update_time datetime NOT NULL COMMENT '数据更新时间', version int DEFAULT 0 NOT NULL COMMENT '数据逻辑删除', deleted bit DEFAULT b'0' NOT NULL COMMENT '数据版本', user_account int NOT NULL, PRIMARY KEY (id)) ENGINE=InnoDB DEFAULT CHARSET=utf8 DEFAULT COLLATE=utf8_general_ci COMMENT='账户表';
INSERT INTO tb_user_assets (id, username, assets_type, amount, create_time, update_time, version, deleted, user_account) VALUES (1, 'gerry', 'USD', 100.00, '2021-01-04 17:19:35', '2021-01-06 10:24:12', 39, false, 1000101);

