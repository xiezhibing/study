/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50716
Source Host           : localhost:3306
Source Database       : spring_cloud_demo

Target Server Type    : MYSQL
Target Server Version : 50716
File Encoding         : 65001

Date: 2019-03-06 12:09:43
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for fund_info
-- ----------------------------
DROP TABLE IF EXISTS `fund_info`;
CREATE TABLE "fund_info" (
  "id" varchar(20) DEFAULT NULL,
  "user_id" varchar(20) DEFAULT '' COMMENT '用户ID',
  "total_amount" decimal(15,2) DEFAULT '0.00' COMMENT '总金额',
  "available_amount" decimal(15,2) DEFAULT '0.00' COMMENT '可用金额',
  "frozen_amount" decimal(15,2) DEFAULT '0.00' COMMENT '冻结金额',
  "create_time" datetime DEFAULT NULL COMMENT '创建时间',
  "creater_id" varchar(20) DEFAULT NULL COMMENT '创建人',
  "modify_time" datetime DEFAULT NULL COMMENT '更新时间',
  "modifier_id" varchar(20) DEFAULT NULL COMMENT '修改人',
  "dr" int(1) DEFAULT '1' COMMENT '使用标识: 1 可用, 0 禁用'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='资金';

-- ----------------------------
-- Records of fund_info
-- ----------------------------
INSERT INTO `fund_info` VALUES ('150317809904123904', '150323107427516416', '10000.00', '10000.00', '0.00', '2019-02-19 19:09:18', null, '2019-02-19 19:09:18', null, '1');

-- ----------------------------
-- Table structure for order_info
-- ----------------------------
DROP TABLE IF EXISTS `order_info`;
CREATE TABLE "order_info" (
  "id" varchar(20) NOT NULL,
  "user_id" varchar(20) DEFAULT '',
  "user_name" varchar(50) DEFAULT '',
  "amount" decimal(10,0) DEFAULT NULL,
  "status" varchar(10) DEFAULT NULL COMMENT '状态',
  "create_time" datetime DEFAULT NULL COMMENT '创建时间',
  "creater_id" varchar(20) DEFAULT NULL COMMENT '创建人',
  "modify_time" datetime DEFAULT NULL COMMENT '更新时间',
  "modifier_id" varchar(20) DEFAULT NULL COMMENT '修改人',
  "dr" int(1) DEFAULT '1' COMMENT '使用标识: 1 可用, 0 禁用',
  PRIMARY KEY ("id")
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of order_info
-- ----------------------------

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE "user_info" (
  "id" varchar(20) NOT NULL COMMENT '主键ID',
  "code" varchar(50) DEFAULT NULL,
  "name" varchar(50) DEFAULT NULL,
  "status" varchar(255) DEFAULT NULL,
  "phone" varchar(20) DEFAULT NULL,
  "password" varchar(64) DEFAULT NULL,
  "sex" varchar(10) DEFAULT NULL COMMENT '性别: [0: 男, 1:女]',
  "create_time" datetime DEFAULT NULL COMMENT '创建时间',
  "creater_id" varchar(20) DEFAULT NULL COMMENT '创建人',
  "modify_time" datetime DEFAULT NULL COMMENT '更新时间',
  "modifier_id" varchar(20) DEFAULT NULL COMMENT '修改人',
  "dr" int(1) DEFAULT '1' COMMENT '使用标识: 1 可用, 0 禁用',
  PRIMARY KEY ("id")
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT INTO `user_info` VALUES ('150323107427516416', '10001', '张三', '1', '18734141111', '111111', 'M', '2019-02-19 19:30:21', null, '2019-02-19 19:30:21', null, '1');
INSERT INTO `user_info` VALUES ('150323148162596864', '10002', '李四', '1', '18734141111', '111111', 'M', '2019-02-19 19:30:31', null, '2019-02-19 19:30:31', null, '1');
INSERT INTO `user_info` VALUES ('150323413955641344', '10003', '张三丰', '1', '18734141111', '111111', 'M', '2019-02-19 19:31:34', null, '2019-02-19 19:31:34', null, '1');
INSERT INTO `user_info` VALUES ('150323467072307200', '10004', '李世民', '1', '18734141115', '111111', 'M', '2019-02-19 19:31:47', null, '2019-02-19 19:31:47', null, '1');
INSERT INTO `user_info` VALUES ('150663934838308864', '10004', '李世民', '1', '18734141115', '111111', 'M', '2019-02-20 18:04:41', null, '2019-02-20 18:04:41', null, '1');
INSERT INTO `user_info` VALUES ('150677964596445184', null, null, null, null, null, null, '2019-02-20 19:00:26', null, '2019-02-20 19:00:26', null, '1');
