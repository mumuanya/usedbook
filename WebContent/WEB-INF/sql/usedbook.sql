/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50719
Source Host           : localhost:3306
Source Database       : usedbook

Target Server Type    : MYSQL
Target Server Version : 50719
File Encoding         : 65001

Date: 2018-04-14 18:03:42
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for book
-- ----------------------------
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `degree` enum('0','5','6','7','8','9') NOT NULL DEFAULT '0' COMMENT '新旧程度',
  `creatime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `price` double NOT NULL,
  `userid` int(11) NOT NULL,
  `district` enum('DuJiangyan','ChengDu','YaAn') NOT NULL,
  `img1` varchar(255) DEFAULT NULL COMMENT '图片1地址',
  `img2` varchar(255) DEFAULT NULL COMMENT '地址2',
  `img3` varchar(255) DEFAULT NULL COMMENT '地址3',
  PRIMARY KEY (`id`),
  KEY `fk_book_user` (`userid`),
  CONSTRAINT `fk_book_user` FOREIGN KEY (`userid`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COMMENT='用户发布的图书保存表，其中有用户的id';

-- ----------------------------
-- Records of book
-- ----------------------------
INSERT INTO `book` VALUES ('1', '数据结构', '8', '2018-04-14 10:13:34', '32.5', '3', 'YaAn', 'test.jpg', 'test.jpg', 'test.jpg');
INSERT INTO `book` VALUES ('2', 'C语言', '9', '2018-04-14 10:14:15', '18', '1', 'ChengDu', 'test.jpg', 'test.jpg', 'test.jpg');
INSERT INTO `book` VALUES ('3', '离散数学', '0', '2018-04-14 16:49:13', '20', '1', 'YaAn', 'test.jpg', null, null);
INSERT INTO `book` VALUES ('4', '概率论', '9', '2018-04-14 10:15:01', '20', '2', 'ChengDu', 'test.jpg', 'test.jpg', 'test.jpg');
INSERT INTO `book` VALUES ('5', '计算机组成原理', '9', '2018-04-14 12:22:36', '15', '1', 'ChengDu', 'test.jpg', 'test.jpg', 'test.jpg');
INSERT INTO `book` VALUES ('6', '计算机导论', '0', '2018-04-14 16:37:25', '40.5', '1', 'YaAn', 'test.jpg', null, null);
INSERT INTO `book` VALUES ('7', '计算机图形学', '0', '2018-04-14 16:38:24', '70', '1', 'DuJiangyan', null, null, null);

-- ----------------------------
-- Table structure for favor
-- ----------------------------
DROP TABLE IF EXISTS `favor`;
CREATE TABLE `favor` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `userid` int(11) DEFAULT NULL COMMENT '用户号',
  `bookid` int(11) DEFAULT NULL COMMENT '书号',
  `creatime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `fk_favor_user` (`userid`),
  KEY `fk_favor_book` (`bookid`),
  CONSTRAINT `fk_favor_book` FOREIGN KEY (`bookid`) REFERENCES `book` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_favor_user` FOREIGN KEY (`userid`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COMMENT='用户收藏的书本，其中外键userid 和 bookid';

-- ----------------------------
-- Records of favor
-- ----------------------------
INSERT INTO `favor` VALUES ('3', '1', '5', '2018-04-14 15:18:21');
INSERT INTO `favor` VALUES ('4', '1', '1', null);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `neckname` varchar(255) DEFAULT '' COMMENT '昵称',
  `mail` varchar(255) NOT NULL COMMENT '用户邮箱',
  `pic` varchar(255) DEFAULT NULL COMMENT '头像图片地址',
  `password` varchar(255) NOT NULL DEFAULT '123456' COMMENT '密码',
  `token` varchar(255) NOT NULL DEFAULT '123456' COMMENT '用户鉴权令牌',
  `phone` varchar(255) DEFAULT NULL COMMENT '电话',
  `addr` varchar(255) DEFAULT NULL COMMENT '地址',
  `district` enum('ChengDu','DuJiangyan','YaAn') NOT NULL DEFAULT 'YaAn' COMMENT '校区',
  `creatime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `role` int(1) NOT NULL DEFAULT '0' COMMENT '角色状态，1为管理员',
  PRIMARY KEY (`id`),
  UNIQUE KEY `mail_unique` (`mail`) USING BTREE COMMENT 'mail为独特的'
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COMMENT='用户表，存有用户的多种信息';

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'gay铠', 'xxxxxxx', null, '123456', '123456', null, '攀枝花', 'YaAn', '2018-04-14 10:10:50', '1');
INSERT INTO `user` VALUES ('2', '阮坤', 'qkmc@outlook.com', null, '123456', 'e10adc3949ba59abbe56e057f20f883e', null, '九寨沟', 'YaAn', '2018-04-14 10:11:07', '0');
INSERT INTO `user` VALUES ('3', '聂娇', 'qkmc2@outlook.com', null, '123456', 'e10adc3949ba59abbe56e057f20f8832', null, '松潘', 'YaAn', '2018-04-14 10:13:07', '0');
INSERT INTO `user` VALUES ('4', '小笨笨', '314445437@qq.com', null, '123456', '6d28a88047edb426c3d4aec4214cf6ca', null, null, 'ChengDu', '2018-04-14 17:56:14', '0');
INSERT INTO `user` VALUES ('5', '小傻傻', '1537854187@qq.com', 'test.jpg', '123456', '3610eac4b342e702534bf0199dc3a752', '18783551223', '九寨沟', 'YaAn', '2018-04-14 17:57:04', '0');
