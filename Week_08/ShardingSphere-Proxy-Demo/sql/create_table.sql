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
