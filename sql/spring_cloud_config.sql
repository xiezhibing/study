/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50716
Source Host           : localhost:3306
Source Database       : spring_cloud_config

Target Server Type    : MYSQL
Target Server Version : 50716
File Encoding         : 65001

Date: 2019-03-06 12:03:11
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for config_info
-- ----------------------------
DROP TABLE IF EXISTS `config_info`;
CREATE TABLE "config_info" (
  "id" int(11) NOT NULL AUTO_INCREMENT,
  "key" varchar(300) DEFAULT NULL,
  "value" varchar(500) DEFAULT NULL,
  "application" varchar(50) DEFAULT NULL,
  "profile" varchar(50) DEFAULT NULL,
  "label" varchar(50) DEFAULT NULL,
  PRIMARY KEY ("id")
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='springcloud配置中心';

-- ----------------------------
-- Records of config_info
-- ----------------------------
INSERT INTO `config_info` VALUES ('1', 'myname', 'john', 'product', 'dev', 'master');
INSERT INTO `config_info` VALUES ('2', 'config.code', '10000', 'config-client', 'dev', 'master');
INSERT INTO `config_info` VALUES ('3', 'config.name', 'john', 'config-client', 'dev', 'master');
INSERT INTO `config_info` VALUES ('4', 'config.version', '1.14', 'config-client', 'dev', 'master');
INSERT INTO `config_info` VALUES ('5', 'server.port', '12129', 'config-client', 'dev', 'master');
