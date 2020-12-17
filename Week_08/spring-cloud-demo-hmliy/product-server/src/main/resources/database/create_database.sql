use product_db;

create table tb_product (
   id                   int(11) not null AUTO_INCREMENT,
   product_name         varchar(50) not null,
   product_status 		tinyint(4) not null comment '状态',
   store_num 			INT DEFAULT 0 NOT NULL comment '库存量',
   create_time          datetime comment '创建时间',
   update_time          datetime comment '更新时间',
   is_deleted           bit(1) comment '逻辑删除',
   version              int(11) comment '版本',
   primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 comment 'product表';
