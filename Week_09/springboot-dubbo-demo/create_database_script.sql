CREATE database bank_db_01;

use bank_db_01;
CREATE TABLE tb_user_assets (
    id INT NOT NULL AUTO_INCREMENT,
    username VARCHAR(128) NOT NULL comment '用户名',
    assets_type VARCHAR(10) DEFAULT 'CNY' NOT NULL comment '资产类型',
    amount DECIMAL(10, 2) DEFAULT 0 NOT NULL comment '金额',
    create_time DATETIME NOT NULL comment '数据创建时间',
    update_time DATETIME NOT NULL comment '数据更新时间',
    version INT DEFAULT 0 NOT NULL comment '数据逻辑删除',
    deleted BIT(1) DEFAULT 0 NOT NULL comment '数据版本',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8 COMMENT = '账户表';
insert into tb_user_assets(username, assets_type, amount, create_time, update_time, version, deleted) values('zhangsan', 'CNY', 100.00, now(), now(), 0, 0);

CREATE TABLE tb_frozen_assets (
    id INT NOT NULL AUTO_INCREMENT,
    user_id VARCHAR(128) NOT NULL comment '用户ID',
    assets_type VARCHAR(10) DEFAULT 'CNY' NOT NULL comment '资产类型',
    amount DECIMAL(10, 2) DEFAULT 0 NOT NULL comment '金额',
    asset_status TINYINT DEFAULT 0 NOT NULL comment '资产状态',
    frozen_time DATETIME NOT NULL comment '冻结时间',
    unfrozen_time DATETIME NOT NULL comment '解冻时间',
    create_time DATETIME NOT NULL comment '数据创建时间',
    update_time DATETIME NOT NULL comment '数据更新时间',
    version INT DEFAULT 0 NOT NULL comment '数据逻辑删除',
    deleted BIT(1) DEFAULT 0 NOT NULL comment '数据版本',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8 COMMENT = '冻结资产表';


CREATE database bank_db_02;

use bank_db_02;
CREATE TABLE tb_user_assets (
    id INT NOT NULL AUTO_INCREMENT,
    username VARCHAR(128) NOT NULL comment '用户名',
    assets_type VARCHAR(10) DEFAULT 'CNY' NOT NULL comment '资产类型',
    amount DECIMAL(10, 2) DEFAULT 0 NOT NULL comment '金额',
    create_time DATETIME NOT NULL comment '数据创建时间',
    update_time DATETIME NOT NULL comment '数据更新时间',
    version INT DEFAULT 0 NOT NULL comment '数据逻辑删除',
    deleted BIT(1) DEFAULT 0 NOT NULL comment '数据版本',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8 COMMENT = '账户表';

insert into tb_user_assets(username, assets_type, amount, create_time, update_time, version, deleted) values('gerry', 'USD', 10.00, now(), now(), 0, 0);

CREATE TABLE tb_frozen_assets (
    id INT NOT NULL AUTO_INCREMENT,
    user_id VARCHAR(128) NOT NULL comment '用户ID',
    assets_type VARCHAR(10) DEFAULT 'CNY' NOT NULL comment '资产类型',
    amount DECIMAL(10, 2) DEFAULT 0 NOT NULL comment '金额',
    asset_status TINYINT DEFAULT 0 NOT NULL comment '资产状态',
    frozen_time DATETIME NOT NULL comment '冻结时间',
    unfrozen_time DATETIME NOT NULL comment '解冻时间',
    create_time DATETIME NOT NULL comment '数据创建时间',
    update_time DATETIME NOT NULL comment '数据更新时间',
    version INT DEFAULT 0 NOT NULL comment '数据逻辑删除',
    deleted BIT(1) DEFAULT 0 NOT NULL comment '数据版本',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8 COMMENT = '冻结资产表';
