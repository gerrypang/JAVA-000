create database spring_cloud_order;

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `express`
-- ----------------------------
DROP TABLE IF EXISTS `express`;
CREATE TABLE `express` (
  `id` tinyint(1) unsigned NOT NULL AUTO_INCREMENT COMMENT '����ID',
  `name` varchar(50) NOT NULL COMMENT '��˾����',
  `kuaidi_no` varchar(100) DEFAULT NULL COMMENT '���100���',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '״̬0Ϊ����1Ϊ�ر�',
  `create_time` int(11) DEFAULT NULL COMMENT '����ʱ��',
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='��ݹ�˾';

-- ----------------------------
--  Table structure for `order_goods`
-- ----------------------------
DROP TABLE IF EXISTS `order_goods`;
CREATE TABLE `order_goods` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `goods_id` int(11) DEFAULT NULL COMMENT '��Ʒid',
  `goods_price_id` int(11) DEFAULT NULL COMMENT '��Ʒ���id  (һ�����һ���۸�)',
  `pay_status` tinyint(1) DEFAULT '0' COMMENT '0δ֧��  1��֧��',
  `pay_money` decimal(11,2) DEFAULT '0.00' COMMENT '����֧�����',
  `money` decimal(11,2) DEFAULT '0.00' COMMENT '���֧�����',
  `point` int(11) DEFAULT '0' COMMENT 'ʹ�õֿۻ��ֽ��',
  `fh_status` tinyint(1) DEFAULT '0' COMMENT '0δ����  1���ַ��� 2ȫ������',
  `order_status` tinyint(1) DEFAULT '0' COMMENT '����״̬  0��֧�� 1���ջ� 2��ȷ��  3�����  4��ȡ��',
  `sh_name` varchar(20) DEFAULT NULL COMMENT '�ջ�������',
  `sh_phone` varchar(20) DEFAULT NULL COMMENT '�ջ��˵绰',
  `sh_address` varchar(80) DEFAULT NULL COMMENT '�ջ���ַ',
  `create_time` int(11) DEFAULT '0' COMMENT '����������ʱ��',
  `update_time` int(11) DEFAULT '0' COMMENT '����ʱ��',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `order_goods_detail`
-- ----------------------------
DROP TABLE IF EXISTS `order_goods_detail`;
CREATE TABLE `order_goods_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `goods_id` int(11) DEFAULT NULL COMMENT '��Ʒid',
  `goods_price_id` int(11) DEFAULT NULL COMMENT '��Ʒ���id  (һ�����һ���۸�)',
  `order_goods_id` int(11) DEFAULT NULL COMMENT '��������id',
  `express_id` int(11) DEFAULT NULL COMMENT '������˾��id',
  `wuliu_order_no` varchar(30) DEFAULT NULL COMMENT '�������',
  `pay_money` decimal(11,2) DEFAULT '0.00' COMMENT '����֧�����',
  `money` decimal(11,2) DEFAULT '0.00' COMMENT '���֧�����',
  `point` int(11) DEFAULT '0' COMMENT 'ʹ�õֿۻ��ֽ��',
  `tk_status` tinyint(1) DEFAULT '0' COMMENT '�˿�״̬ 0 δ�����˿� 1�����˿�  2���������˿� 3�����˿�',
  `fh_status` tinyint(1) DEFAULT '0' COMMENT '0δ����  1���ַ��� 2ȫ������',
  `create_time` int(11) DEFAULT '0' COMMENT '����������ʱ��',
  `update_time` int(11) DEFAULT '0' COMMENT '����ʱ��',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `order_pay`
-- ----------------------------
DROP TABLE IF EXISTS `order_pay`;
CREATE TABLE `order_pay` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` int(11) DEFAULT '0' COMMENT '֧������ 0΢�� 1֧���� 2���п�',
  `tab` varchar(20) DEFAULT NULL COMMENT '��Ӧ��',
  `tab_id` int(11) DEFAULT '0' COMMENT '��id',
  `tab_order_no` varchar(22) DEFAULT NULL COMMENT '��Ӧ�����������',
  `three_order_no` varchar(22) DEFAULT NULL COMMENT '֧�����Ķ������',
  `money` decimal(11,2) DEFAULT '0.00' COMMENT '֧�����',
  `pay_status` tinyint(1) DEFAULT '0' COMMENT '0δ֧�� 1��֧��',
  `notice_data` text COMMENT '֪ͨ����',
  `create_time` int(11) DEFAULT NULL COMMENT '����ʱ��',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `order_refund`
-- ----------------------------
DROP TABLE IF EXISTS `order_refund`;
CREATE TABLE `order_refund` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_pay_id` int(11) DEFAULT '0' COMMENT '����֧����id',
  `money` decimal(11,2) DEFAULT '0.00' COMMENT '�˿���',
  `status` tinyint(1) DEFAULT '0' COMMENT '0�����˿� 1���˿�',
  `create_time` int(11) DEFAULT NULL COMMENT '����ʱ��',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `user_bill`
-- ----------------------------
DROP TABLE IF EXISTS `user_bill`;
CREATE TABLE `user_bill` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '�û�id',
  `order_no` varchar(50) DEFAULT '' COMMENT '�������',
  `tab` varchar(20) NOT NULL COMMENT '��Ӧ����',
  `tab_id` int(11) NOT NULL DEFAULT '0' COMMENT '��Ӧ��id',
  `type` int(4) NOT NULL DEFAULT '0' COMMENT 'ҵ������',
  `before_money` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '֮ǰ���',
  `money` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '�仯���',
  `show_money` varchar(30) DEFAULT NULL COMMENT '��ʾ���',
  `after_money` decimal(11,2) DEFAULT '0.00' COMMENT '֮����',
  `fee` decimal(11,2) DEFAULT '0.00' COMMENT '������',
  `note` varchar(100) DEFAULT '' COMMENT '��ע',
  `create_time` int(10) NOT NULL DEFAULT '0' COMMENT '����ʱ��',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;