CREATE DATABASE IF NOT EXISTS mall
DEFAULT CHARACTER SET utf8
DEFAULT COLLATE utf8_general_ci;

use mall;

-- 商品（SPU）
create table tb_goods(
	id bigint not null AUTO_INCREMENT comment '商品id',
	name varchar(64) not null comment '商品名称',
	brand_id bigint not null comment '品牌id', 
	category1_id bigint null comment '1级类别id',  
	category2_id bigint null comment '2级类别id',  
	category3_id bigint null comment '3级类别id',  
	sales int default 0 comment '销量',
	desc_detail varchar(512) comment '详细介绍',
	desc_pack  varchar(512) comment '包装信息',
	desc_service varchar(512) comment '售后服务',
	keywords varchar(255) comment '关键字',
	
	create_time datetime not null comment '创建时间',
	update_time datetime not null comment '更新时间',
	version int not null comment '版本',
	is_deleted bit(1) not null comment '逻辑删除',
	PRIMARY KEY (`id`)
)  ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='商品（SPU）';


-- 品牌
create table tb_goods_brand (
	id bigint not null AUTO_INCREMENT comment '品牌id',
	name varchar(64) not null comment '品牌名称',
 	logo varchar(512) null comment '品牌logo',
	first_letter char(1) null comment '品牌首字母',
	sequence smallint(5) null comment '序号',
	
	create_time datetime not null comment '创建时间',
	update_time datetime not null comment '更新时间',
	version int not null comment '版本',
	is_deleted bit(1) not null comment '逻辑删除',
	PRIMARY KEY (`id`)
)  ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='品牌';


-- 商品类别
create table tb_goods_category (
	id bigint not null AUTO_INCREMENT comment '类别id',
	name varchar(64) not null comment '类别名称',
	parent_id bigint null comment '父类别id',
	child_id bigint null comment '子类别id',
	level smallint(5) not null comment'层级',
	sequence smallint(5) null comment '序号',
	
	create_time datetime not null comment '创建时间',
	update_time datetime not null comment '更新时间',
	version int not null comment '版本',
	is_deleted bit(1) not null comment '逻辑删除',
	PRIMARY KEY (`id`)
)  ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='商品类别';


-- 商品（SKU）
create table tb_sku(
	id bigint not null AUTO_INCREMENT comment 'sku id',
	name varchar(64) not null comment 'sku名称',
	good_id bigint not null comment '商品id', 
	price decimal(10,2) comment '价格',
    promotion_price decimal(10,2) comment '促销价格',
    stock int default 0 comment '库存',
	sales int default 0 comment '销量',
	
	create_time datetime not null comment '创建时间',
	update_time datetime not null comment '更新时间',
	version int not null comment '版本',
	is_deleted bit(1) not null comment '逻辑删除',
	PRIMARY KEY (`id`)
)  ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='商品（SKU）';


-- 商品规格
create table tb_goods_specification(
	id bigint not null AUTO_INCREMENT comment '规格id',
	name varchar(64) not null comment '规格名称',
	good_id bigint not null comment '商品id', 
	
	 
	create_time datetime not null comment '创建时间',
	update_time datetime not null comment '更新时间',
	version int not null comment '版本',
	is_deleted bit(1) not null comment '逻辑删除',
	PRIMARY KEY (`id`)
)  ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='商品规格';


-- 规格选项
create table tb_specification_option(
	id bigint not null AUTO_INCREMENT comment '选项id',
	spec_id bigint not null comment '规格id',
	value varchar(512) not null comment '选项值', 
	
	create_time datetime not null comment '创建时间',
	update_time datetime not null comment '更新时间',
	version int not null comment '版本',
	is_deleted bit(1) not null comment '逻辑删除',
	PRIMARY KEY (`id`)
)  ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='规格选项';


-- sku规格信息
create table tb_sku_specification(
	id bigint not null AUTO_INCREMENT comment '规格id',
	sku_id bigint not null comment '所属规格',
	spec_id bigint not null comment '规格id', 
	option_id bigint not null comment '规格选项id', 
	 
	create_time datetime not null comment '创建时间',
	update_time datetime not null comment '更新时间',
	version int not null comment '版本',
	is_deleted bit(1) not null comment '逻辑删除',
	PRIMARY KEY (`id`)
)  ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='sku规格信息';


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

