/*
 Navicat Premium Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 80034
 Source Host           : localhost:3306
 Source Schema         : stock

 Target Server Type    : MySQL
 Target Server Version : 80034
 File Encoding         : 65001

 Date: 08/05/2024 20:32:41
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for account
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account`  (
  `userid` int NOT NULL AUTO_INCREMENT,
  `account` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `password` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `email` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`userid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of account
-- ----------------------------
INSERT INTO `account` VALUES (1, '12222', '12222', '11111.com');
INSERT INTO `account` VALUES (2, '22222', '22222', '22222.com');
INSERT INTO `account` VALUES (3, '33333', '33333', '33333.com');
INSERT INTO `account` VALUES (4, 'Account', '123456', 'xxxxx@qq.com');
INSERT INTO `account` VALUES (5, '55555', '55555', '55555.com');
INSERT INTO `account` VALUES (6, '66666', '66666', '2516632593@qq.com');

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission`  (
  `pid` int NOT NULL,
  `pname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '该功能的url',
  PRIMARY KEY (`pid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of permission
-- ----------------------------

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `roleid` int NULL DEFAULT NULL,
  `role` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, 'User');
INSERT INTO `role` VALUES (2, 'Vip');
INSERT INTO `role` VALUES (3, 'StrE');
INSERT INTO `role` VALUES (4, 'AlgE');
INSERT INTO `role` VALUES (5, 'Root');

-- ----------------------------
-- Table structure for roletoaccount
-- ----------------------------
DROP TABLE IF EXISTS `roletoaccount`;
CREATE TABLE `roletoaccount`  (
  `roleid` int NOT NULL,
  `userid` int NULL DEFAULT NULL,
  PRIMARY KEY (`roleid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of roletoaccount
-- ----------------------------
INSERT INTO `roletoaccount` VALUES (1, 1);
INSERT INTO `roletoaccount` VALUES (2, 2);
INSERT INTO `roletoaccount` VALUES (3, 3);
INSERT INTO `roletoaccount` VALUES (4, 4);
INSERT INTO `roletoaccount` VALUES (5, 5);

-- ----------------------------
-- Table structure for roletoper
-- ----------------------------
DROP TABLE IF EXISTS `roletoper`;
CREATE TABLE `roletoper`  (
  `roleid` int NOT NULL,
  `pid` int NULL DEFAULT NULL,
  PRIMARY KEY (`roleid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of roletoper
-- ----------------------------

-- ----------------------------
-- Table structure for stock
-- ----------------------------
DROP TABLE IF EXISTS `stock`;
CREATE TABLE `stock`  (
  `symbol` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `tscode` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `area` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `industry` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `listdate` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`symbol`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of stock
-- ----------------------------
INSERT INTO `stock` VALUES ('000001', '1', '平安银行', '广东', '银行业（IV）', '1991-04-03 00:00:00');
INSERT INTO `stock` VALUES ('000002', '2', '万科A', '广东', '不动产投资开发', '1991-01-29 00:00:00');
INSERT INTO `stock` VALUES ('000003', '3', '中海可转债债券a', '中国', '债券型-混合二级', '2013-02-25 00:00:00');
INSERT INTO `stock` VALUES ('000004', '4', '国华网安', '广东', '计算机 — 软件开发', '1990-12-01 00:00:00');

-- ----------------------------
-- Table structure for stockdata(mongodb)
-- ----------------------------
DROP TABLE IF EXISTS `stockdata(mongodb)`;
CREATE TABLE `stockdata(mongodb)`  (
  `symbol` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `trade_date` datetime NULL DEFAULT NULL,
  `open` float NULL DEFAULT NULL,
  `high` float NULL DEFAULT NULL,
  `low` float NULL DEFAULT NULL,
  `close` float NULL DEFAULT NULL,
  `pre_close` float NULL DEFAULT NULL,
  `change` float NULL DEFAULT NULL,
  `pct_chg` float NULL DEFAULT NULL,
  `vol` float NULL DEFAULT NULL,
  `amount` float NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of stockdata(mongodb)
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `userid` int NOT NULL,
  `nickname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `ifbanned` int NULL DEFAULT NULL COMMENT 'bool类型，表示该用户是否封禁',
  `sex` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `birthday` datetime NULL DEFAULT NULL,
  `signal` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`userid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'nickname1', 0, '男', 'address1', '2024-04-18 18:41:17', 'signal1');
INSERT INTO `user` VALUES (2, 'nickname2', 0, '女', 'address2', '2024-04-18 17:24:39', 'signal2');
INSERT INTO `user` VALUES (3, 'nickname3', 0, '女', 'address3', '2024-04-18 18:42:10', 'signal3');
INSERT INTO `user` VALUES (4, 'nickname4', 0, '男', 'address4', '2021-06-01 00:00:00', 'signal4');
INSERT INTO `user` VALUES (5, 'nickname5', 0, '男', 'address5', '2024-05-08 18:31:43', 'signal5');
INSERT INTO `user` VALUES (6, '6', 0, '男', '6', '2000-01-01 00:00:00', '6');

-- ----------------------------
-- Table structure for usertostock
-- ----------------------------
DROP TABLE IF EXISTS `usertostock`;
CREATE TABLE `usertostock`  (
  `collectionid` int NOT NULL AUTO_INCREMENT,
  `symbol` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '全国统一的股票编码',
  PRIMARY KEY (`collectionid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of usertostock
-- ----------------------------
INSERT INTO `usertostock` VALUES (1, '000001');
INSERT INTO `usertostock` VALUES (3, '000001');

DROP TABLE IF EXISTS `collection`;
CREATE TABLE `collection`  (
    `collectionid` int NOT NULL AUTO_INCREMENT,
    `collectionname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '收藏夹名称',
    `userid` int NULL DEFAULT NULL,
    PRIMARY KEY (`collectionid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

INSERT INTO `collection` (`collectionid`, `collectionname`, `userid`) VALUES (1, '优质股1', 1);
INSERT INTO `collection` (`collectionid`, `collectionname`, `userid`) VALUES (3, '我的股票1', 2);

SET FOREIGN_KEY_CHECKS = 1;
