use order_db;

create table tb_order (
   id                   int(11) not null AUTO_INCREMENT,
   order_status 				tinyint(4) not null comment '状态',
   user_id  int(11) not null,
   create_time          datetime comment '创建时间',
   update_time          datetime comment '更新时间',
   is_deleted           bit(1) comment '逻辑删除',
   version              int(11) comment '版本',
   primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 comment 'order表';


create table tb_product (
   id                   int(11) not null AUTO_INCREMENT,
   product_name         varchar(50) not null,
   product_status 		tinyint(4) not null comment '状态',
   store_num 			INT( 10 ) unsigned DEFAULT 0 NOT NULL comment '库存量',
   create_time          datetime comment '创建时间',
   update_time          datetime comment '更新时间',
   is_deleted           bit(1) comment '逻辑删除',
   version              int(11) comment '版本',
   primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 comment 'product表';


create table tb_order_product_relation (
   id                   int(11) not null AUTO_INCREMENT,
   order_id 	int(11) not null ,
   product_id 	int(11) not null ,
   create_time datetime comment '创建时间',
   update_time datetime comment '更新时间',
   is_deleted           bit(1) comment '逻辑删除',
   version              int(11) comment '版本',
   primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 comment 'order product 关系表';


INSERT INTO tb_order (id, order_status, user_id, create_time, update_time, is_deleted, version) VALUES (33, 1, 44, '2020-12-16 18:13:23', '2020-12-16 18:13:23', false, 0);
INSERT INTO tb_order (id, order_status, user_id, create_time, update_time, is_deleted, version) VALUES (34, 1, 44, '2020-12-16 18:14:34', '2020-12-16 18:14:34', false, 0);
INSERT INTO tb_order (id, order_status, user_id, create_time, update_time, is_deleted, version) VALUES (35, 1, 44, '2020-12-16 18:16:58', '2020-12-16 18:16:58', false, 0);
INSERT INTO tb_order (id, order_status, user_id, create_time, update_time, is_deleted, version) VALUES (36, 1, 44, '2020-12-16 18:19:17', '2020-12-16 18:19:17', false, 0);
INSERT INTO tb_order (id, order_status, user_id, create_time, update_time, is_deleted, version) VALUES (37, 1, 44, '2020-12-16 18:19:51', '2020-12-16 18:19:51', false, 0);
INSERT INTO tb_order (id, order_status, user_id, create_time, update_time, is_deleted, version) VALUES (38, 1, 44, '2020-12-17 10:03:15', '2020-12-17 10:03:15', false, 0);
INSERT INTO tb_order (id, order_status, user_id, create_time, update_time, is_deleted, version) VALUES (39, 1, 44, '2020-12-17 10:26:07', '2020-12-17 10:26:07', false, 0);
INSERT INTO tb_order (id, order_status, user_id, create_time, update_time, is_deleted, version) VALUES (40, 1, 44, '2020-12-17 10:33:14', '2020-12-17 10:33:14', false, 0);
INSERT INTO tb_order (id, order_status, user_id, create_time, update_time, is_deleted, version) VALUES (41, 1, 44, '2020-12-17 10:38:00', '2020-12-17 10:38:00', false, 0);
INSERT INTO tb_order (id, order_status, user_id, create_time, update_time, is_deleted, version) VALUES (42, 1, 44, '2021-01-07 10:10:09', '2021-01-07 10:10:09', false, 0);
INSERT INTO tb_order (id, order_status, user_id, create_time, update_time, is_deleted, version) VALUES (43, 1, 44, '2021-01-07 10:10:10', '2021-01-07 10:10:10', false, 0);
INSERT INTO tb_order_product_relation (id, order_id, product_id, create_time, update_time, is_deleted, version, num) VALUES (31, 1, 123, '2020-12-16 18:13:23', '2020-12-16 18:13:23', false, 0, 1);
INSERT INTO tb_order_product_relation (id, order_id, product_id, create_time, update_time, is_deleted, version, num) VALUES (32, 1, 123, '2020-12-16 18:14:34', '2020-12-16 18:14:34', false, 0, 1);
INSERT INTO tb_order_product_relation (id, order_id, product_id, create_time, update_time, is_deleted, version, num) VALUES (33, 1, 123, '2020-12-16 18:16:58', '2020-12-16 18:16:58', false, 0, 1);
INSERT INTO tb_order_product_relation (id, order_id, product_id, create_time, update_time, is_deleted, version, num) VALUES (34, 1, 123, '2020-12-16 18:19:17', '2020-12-16 18:19:17', false, 0, 1);
INSERT INTO tb_order_product_relation (id, order_id, product_id, create_time, update_time, is_deleted, version, num) VALUES (35, 1, 123, '2020-12-16 18:19:51', '2020-12-16 18:19:51', false, 0, 1);
INSERT INTO tb_order_product_relation (id, order_id, product_id, create_time, update_time, is_deleted, version, num) VALUES (36, 1, 123, '2020-12-17 10:03:16', '2020-12-17 10:03:16', false, 0, 1);
INSERT INTO tb_order_product_relation (id, order_id, product_id, create_time, update_time, is_deleted, version, num) VALUES (37, 1, 123, '2020-12-17 10:26:07', '2020-12-17 10:26:07', false, 0, 1);
INSERT INTO tb_order_product_relation (id, order_id, product_id, create_time, update_time, is_deleted, version, num) VALUES (38, 1, 123, '2020-12-17 10:33:14', '2020-12-17 10:33:14', false, 0, 1);
INSERT INTO tb_order_product_relation (id, order_id, product_id, create_time, update_time, is_deleted, version, num) VALUES (39, 1, 123, '2020-12-17 10:38:00', '2020-12-17 10:38:00', false, 0, 1);
INSERT INTO tb_product (id, product_name, product_status, store_num, create_time, update_time, is_deleted, version) VALUES (123, 'iphone 11', 1, 5, '2020-10-11 00:12:00', '2020-12-17 10:38:00', false, 15);
