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

 Date: 17/06/2024 18:12:23
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
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of account
-- ----------------------------
INSERT INTO `account` VALUES (1, 'Root1', 'deng20030806', 'dengenze2003@hotmail.com');
INSERT INTO `account` VALUES (2, 'User1', 'deng20030806', '3346583698@qq.com');
INSERT INTO `account` VALUES (3, 'Vip1', 'deng20030806', '3346583698@qq.com');
INSERT INTO `account` VALUES (4, 'AlgE1', 'deng20030806', '3346583698@qq.com');
INSERT INTO `account` VALUES (5, 'StrE1', 'deng20030806', '3346583698@qq.com');

-- ----------------------------
-- Table structure for alg
-- ----------------------------
DROP TABLE IF EXISTS `alg`;
CREATE TABLE `alg`  (
  `algid` int NOT NULL AUTO_INCREMENT,
  `account` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `algname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `alggrade` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `ifpass` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `algdate` date NULL DEFAULT NULL,
  `introduction` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`algid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of alg
-- ----------------------------
INSERT INTO `alg` VALUES (1, '内部算法1', 'DSFormer', 'User', 'YES', '2024-06-03', '内部算法1');
INSERT INTO `alg` VALUES (2, '内部算法2', 'itransformer', 'User', 'NO', '2024-06-02', '内部算法2');
INSERT INTO `alg` VALUES (3, '内部算法3', 'patchTST', 'Vip', 'YES', '2024-06-01', '内部算法3');
INSERT INTO `alg` VALUES (4, 'Root1', 'MyAlg', 'Vip', 'NO', '2024-06-04', 'Root1');

-- ----------------------------
-- Table structure for collection
-- ----------------------------
DROP TABLE IF EXISTS `collection`;
CREATE TABLE `collection`  (
  `collectionid` int NOT NULL AUTO_INCREMENT,
  `collectionname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '收藏夹名称',
  `userid` int NULL DEFAULT NULL,
  PRIMARY KEY (`collectionid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of collection
-- ----------------------------
INSERT INTO `collection` VALUES (1, '优质股1', 1);
INSERT INTO `collection` VALUES (2, '好股票', 2);
INSERT INTO `collection` VALUES (3, '我的股票', 2);
INSERT INTO `collection` VALUES (4, '不错', 2);
INSERT INTO `collection` VALUES (5, '优质股2', 1);
INSERT INTO `collection` VALUES (6, '优质股1', 3);
INSERT INTO `collection` VALUES (7, '好股票', 1);

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission`  (
  `pid` int NOT NULL,
  `pname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '该功能的url',
  PRIMARY KEY (`pid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

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
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

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
  `userid` int NOT NULL,
  PRIMARY KEY (`userid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of roletoaccount
-- ----------------------------
INSERT INTO `roletoaccount` VALUES (2, 1);
INSERT INTO `roletoaccount` VALUES (3, 2);
INSERT INTO `roletoaccount` VALUES (5, 3);
INSERT INTO `roletoaccount` VALUES (4, 4);
INSERT INTO `roletoaccount` VALUES (1, 5);

-- ----------------------------
-- Table structure for roletoper
-- ----------------------------
DROP TABLE IF EXISTS `roletoper`;
CREATE TABLE `roletoper`  (
  `roleid` int NOT NULL,
  `pid` int NULL DEFAULT NULL,
  PRIMARY KEY (`roleid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

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
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of stock
-- ----------------------------
INSERT INTO `stock` VALUES ('000001', '000001.SZ', '平安银行', '深圳', '银行', '1991-04-03 00:00:00');
INSERT INTO `stock` VALUES ('000002', '000002.SZ', '万科A', '深圳', '全国地产', '1991-01-29 00:00:00');
INSERT INTO `stock` VALUES ('000004', '000004.SZ', '国华网安', '深圳', '软件服务', '1991-01-14 00:00:00');
INSERT INTO `stock` VALUES ('000006', '000006.SZ', '深振业A', '深圳', '区域地产', '1992-04-27 00:00:00');
INSERT INTO `stock` VALUES ('000007', '000007.SZ', '*ST全新', '深圳', '其他商业', '1992-04-13 00:00:00');
INSERT INTO `stock` VALUES ('000008', '000008.SZ', '神州高铁', '北京', '运输设备', '1992-05-07 00:00:00');
INSERT INTO `stock` VALUES ('000009', '000009.SZ', '中国宝安', '深圳', '电气设备', '1991-06-25 00:00:00');
INSERT INTO `stock` VALUES ('000010', '000010.SZ', '美丽生态', '深圳', '建筑工程', '1995-10-27 00:00:00');
INSERT INTO `stock` VALUES ('000011', '000011.SZ', '深物业A', '深圳', '房产服务', '1992-03-30 00:00:00');
INSERT INTO `stock` VALUES ('000012', '000012.SZ', '南玻A', '深圳', '玻璃', '1992-02-28 00:00:00');
INSERT INTO `stock` VALUES ('000014', '000014.SZ', '沙河股份', '深圳', '全国地产', '1992-06-02 00:00:00');
INSERT INTO `stock` VALUES ('000016', '000016.SZ', '深康佳A', '深圳', '家用电器', '1992-03-27 00:00:00');
INSERT INTO `stock` VALUES ('000017', '000017.SZ', '深中华A', '深圳', '服饰', '1992-03-31 00:00:00');
INSERT INTO `stock` VALUES ('000019', '000019.SZ', '深粮控股', '深圳', '农业综合', '1992-10-12 00:00:00');
INSERT INTO `stock` VALUES ('000020', '000020.SZ', '深华发A', '深圳', '元器件', '1992-04-28 00:00:00');
INSERT INTO `stock` VALUES ('000021', '000021.SZ', '深科技', '深圳', '元器件', '1994-02-02 00:00:00');
INSERT INTO `stock` VALUES ('000023', '000023.SZ', '*ST深天', '深圳', '水泥', '1993-04-29 00:00:00');
INSERT INTO `stock` VALUES ('000025', '000025.SZ', '特力A', '深圳', '汽车服务', '1993-06-21 00:00:00');
INSERT INTO `stock` VALUES ('000026', '000026.SZ', '飞亚达', '深圳', '服饰', '1993-06-03 00:00:00');
INSERT INTO `stock` VALUES ('000027', '000027.SZ', '深圳能源', '深圳', '火力发电', '1993-09-03 00:00:00');
INSERT INTO `stock` VALUES ('000028', '000028.SZ', '国药一致', '深圳', '医药商业', '1993-08-09 00:00:00');
INSERT INTO `stock` VALUES ('000029', '000029.SZ', '深深房A', '深圳', '区域地产', '1993-09-15 00:00:00');
INSERT INTO `stock` VALUES ('000030', '000030.SZ', '富奥股份', '吉林', '汽车配件', '1993-09-29 00:00:00');
INSERT INTO `stock` VALUES ('000031', '000031.SZ', '大悦城', '深圳', '全国地产', '1993-10-08 00:00:00');
INSERT INTO `stock` VALUES ('000032', '000032.SZ', '深桑达A', '深圳', '建筑工程', '1993-10-28 00:00:00');
INSERT INTO `stock` VALUES ('000034', '000034.SZ', '神州数码', '深圳', '软件服务', '1994-05-09 00:00:00');
INSERT INTO `stock` VALUES ('000035', '000035.SZ', '中国天楹', '江苏', '环境保护', '1994-04-08 00:00:00');
INSERT INTO `stock` VALUES ('000036', '000036.SZ', '华联控股', '深圳', '全国地产', '1994-06-17 00:00:00');
INSERT INTO `stock` VALUES ('000037', '000037.SZ', '深南电A', '深圳', '火力发电', '1994-07-01 00:00:00');
INSERT INTO `stock` VALUES ('000039', '000039.SZ', '中集集团', '深圳', '运输设备', '1994-04-08 00:00:00');
INSERT INTO `stock` VALUES ('000040', '000040.SZ', '东旭蓝天', '深圳', '新型电力', '1994-08-08 00:00:00');
INSERT INTO `stock` VALUES ('000042', '000042.SZ', '中洲控股', '深圳', '全国地产', '1994-09-21 00:00:00');
INSERT INTO `stock` VALUES ('000045', '000045.SZ', '深纺织A', '深圳', '元器件', '1994-08-15 00:00:00');
INSERT INTO `stock` VALUES ('000048', '000048.SZ', '京基智农', '深圳', '区域地产', '1994-11-01 00:00:00');
INSERT INTO `stock` VALUES ('000049', '000049.SZ', '德赛电池', '深圳', '电气设备', '1995-03-20 00:00:00');
INSERT INTO `stock` VALUES ('000050', '000050.SZ', '深天马A', '深圳', '元器件', '1995-03-15 00:00:00');
INSERT INTO `stock` VALUES ('000055', '000055.SZ', '方大集团', '深圳', '其他建材', '1996-04-15 00:00:00');
INSERT INTO `stock` VALUES ('000056', '000056.SZ', '皇庭国际', '深圳', '其他商业', '1996-07-08 00:00:00');
INSERT INTO `stock` VALUES ('000058', '000058.SZ', '深赛格', '深圳', '商品城', '1996-12-26 00:00:00');
INSERT INTO `stock` VALUES ('000059', '000059.SZ', '华锦股份', '辽宁', '石油加工', '1997-01-30 00:00:00');
INSERT INTO `stock` VALUES ('000060', '000060.SZ', '中金岭南', '深圳', '铅锌', '1997-01-23 00:00:00');
INSERT INTO `stock` VALUES ('000061', '000061.SZ', '农产品', '深圳', '农业综合', '1997-01-10 00:00:00');
INSERT INTO `stock` VALUES ('000062', '000062.SZ', '深圳华强', '深圳', '元器件', '1997-01-30 00:00:00');
INSERT INTO `stock` VALUES ('000063', '000063.SZ', '中兴通讯', '深圳', '通信设备', '1997-11-18 00:00:00');
INSERT INTO `stock` VALUES ('000065', '000065.SZ', '北方国际', '北京', '建筑工程', '1998-06-05 00:00:00');
INSERT INTO `stock` VALUES ('000066', '000066.SZ', '中国长城', '深圳', 'IT设备', '1997-06-26 00:00:00');
INSERT INTO `stock` VALUES ('000068', '000068.SZ', '华控赛格', '深圳', '环境保护', '1997-06-11 00:00:00');
INSERT INTO `stock` VALUES ('000069', '000069.SZ', '华侨城A', '深圳', '全国地产', '1997-09-10 00:00:00');
INSERT INTO `stock` VALUES ('000070', '000070.SZ', 'ST特信', '深圳', '通信设备', '2000-05-11 00:00:00');
INSERT INTO `stock` VALUES ('000078', '000078.SZ', '海王生物', '深圳', '医药商业', '1998-12-18 00:00:00');
INSERT INTO `stock` VALUES ('000088', '000088.SZ', '盐田港', '深圳', '港口', '1997-07-28 00:00:00');
INSERT INTO `stock` VALUES ('000089', '000089.SZ', '深圳机场', '深圳', '机场', '1998-04-20 00:00:00');
INSERT INTO `stock` VALUES ('000090', '000090.SZ', '天健集团', '深圳', '建筑工程', '1999-07-21 00:00:00');
INSERT INTO `stock` VALUES ('000096', '000096.SZ', '广聚能源', '深圳', '石油贸易', '2000-07-24 00:00:00');
INSERT INTO `stock` VALUES ('000099', '000099.SZ', '中信海直', '深圳', '空运', '2000-07-31 00:00:00');
INSERT INTO `stock` VALUES ('000100', '000100.SZ', 'TCL科技', '广东', '元器件', '2004-01-30 00:00:00');
INSERT INTO `stock` VALUES ('000151', '000151.SZ', '中成股份', '北京', '商贸代理', '2000-09-06 00:00:00');
INSERT INTO `stock` VALUES ('000153', '000153.SZ', '丰原药业', '安徽', '化学制药', '2000-09-20 00:00:00');
INSERT INTO `stock` VALUES ('000155', '000155.SZ', '川能动力', '四川', '新型电力', '2000-09-26 00:00:00');
INSERT INTO `stock` VALUES ('000156', '000156.SZ', '华数传媒', '浙江', '影视音像', '2000-09-06 00:00:00');
INSERT INTO `stock` VALUES ('000157', '000157.SZ', '中联重科', '湖南', '工程机械', '2000-10-12 00:00:00');
INSERT INTO `stock` VALUES ('000158', '000158.SZ', '常山北明', '河北', '软件服务', '2000-07-24 00:00:00');
INSERT INTO `stock` VALUES ('000159', '000159.SZ', '国际实业', '新疆', '石油贸易', '2000-09-26 00:00:00');
INSERT INTO `stock` VALUES ('000166', '000166.SZ', '申万宏源', '新疆', '证券', '2015-01-26 00:00:00');
INSERT INTO `stock` VALUES ('000301', '000301.SZ', '东方盛虹', '江苏', '化纤', '2000-05-29 00:00:00');
INSERT INTO `stock` VALUES ('000333', '000333.SZ', '美的集团', '广东', '家用电器', '2013-09-18 00:00:00');
INSERT INTO `stock` VALUES ('000338', '000338.SZ', '潍柴动力', '山东', '汽车配件', '2007-04-30 00:00:00');
INSERT INTO `stock` VALUES ('000400', '000400.SZ', '许继电气', '河南', '电气设备', '1997-04-18 00:00:00');
INSERT INTO `stock` VALUES ('000401', '000401.SZ', '冀东水泥', '河北', '水泥', '1996-06-14 00:00:00');
INSERT INTO `stock` VALUES ('000402', '000402.SZ', '金融街', '北京', '全国地产', '1996-06-26 00:00:00');
INSERT INTO `stock` VALUES ('000403', '000403.SZ', '派林生物', '山西', '生物制药', '1996-06-28 00:00:00');
INSERT INTO `stock` VALUES ('000404', '000404.SZ', '长虹华意', '江西', '家用电器', '1996-06-19 00:00:00');
INSERT INTO `stock` VALUES ('000407', '000407.SZ', '胜利股份', '山东', '供气供热', '1996-07-03 00:00:00');
INSERT INTO `stock` VALUES ('000408', '000408.SZ', '藏格矿业', '青海', '农药化肥', '1996-06-28 00:00:00');
INSERT INTO `stock` VALUES ('000409', '000409.SZ', '云鼎科技', '山东', '软件服务', '1996-06-27 00:00:00');
INSERT INTO `stock` VALUES ('000410', '000410.SZ', '沈阳机床', '辽宁', '机床制造', '1996-07-18 00:00:00');
INSERT INTO `stock` VALUES ('000411', '000411.SZ', '英特集团', '浙江', '医药商业', '1996-07-16 00:00:00');
INSERT INTO `stock` VALUES ('000413', '000413.SZ', '东旭光电', '河北', '元器件', '1996-09-25 00:00:00');
INSERT INTO `stock` VALUES ('000415', '000415.SZ', '渤海租赁', '新疆', '多元金融', '1996-07-16 00:00:00');
INSERT INTO `stock` VALUES ('000416', '000416.SZ', '*ST民控', '山东', '多元金融', '1996-07-19 00:00:00');
INSERT INTO `stock` VALUES ('000417', '000417.SZ', '合肥百货', '安徽', '百货', '1996-08-12 00:00:00');
INSERT INTO `stock` VALUES ('000419', '000419.SZ', '通程控股', '湖南', '百货', '1996-08-16 00:00:00');
INSERT INTO `stock` VALUES ('000420', '000420.SZ', '吉林化纤', '吉林', '化纤', '1996-08-02 00:00:00');
INSERT INTO `stock` VALUES ('000421', '000421.SZ', '南京公用', '江苏', '供气供热', '1996-08-06 00:00:00');
INSERT INTO `stock` VALUES ('000422', '000422.SZ', '湖北宜化', '湖北', '农药化肥', '1996-08-15 00:00:00');
INSERT INTO `stock` VALUES ('000423', '000423.SZ', '东阿阿胶', '山东', '中成药', '1996-07-29 00:00:00');
INSERT INTO `stock` VALUES ('000425', '000425.SZ', '徐工机械', '江苏', '工程机械', '1996-08-28 00:00:00');
INSERT INTO `stock` VALUES ('000426', '000426.SZ', '兴业银锡', '内蒙', '铅锌', '1996-08-28 00:00:00');
INSERT INTO `stock` VALUES ('000428', '000428.SZ', '华天酒店', '湖南', '酒店餐饮', '1996-08-08 00:00:00');
INSERT INTO `stock` VALUES ('000429', '000429.SZ', '粤高速A', '广东', '路桥', '1998-02-20 00:00:00');
INSERT INTO `stock` VALUES ('000430', '000430.SZ', '张家界', '湖南', '旅游景点', '1996-08-29 00:00:00');
INSERT INTO `stock` VALUES ('000488', '000488.SZ', '晨鸣纸业', '山东', '造纸', '2000-11-20 00:00:00');
INSERT INTO `stock` VALUES ('000498', '000498.SZ', '山东路桥', '山东', '建筑工程', '1997-06-09 00:00:00');
INSERT INTO `stock` VALUES ('000501', '000501.SZ', '武商集团', '湖北', '百货', '1992-11-20 00:00:00');
INSERT INTO `stock` VALUES ('000503', '000503.SZ', '国新健康', '山东', '软件服务', '1992-11-30 00:00:00');
INSERT INTO `stock` VALUES ('000504', '000504.SZ', '南华生物', '湖南', '医疗保健', '1992-12-08 00:00:00');
INSERT INTO `stock` VALUES ('000505', '000505.SZ', '京粮控股', '海南', '食品', '1992-12-21 00:00:00');
INSERT INTO `stock` VALUES ('000506', '000506.SZ', '*ST中润', '山东', '黄金', '1993-03-12 00:00:00');
INSERT INTO `stock` VALUES ('000507', '000507.SZ', '珠海港', '广东', '港口', '1993-03-26 00:00:00');
INSERT INTO `stock` VALUES ('000509', '000509.SZ', '华塑控股', '四川', '元器件', '1993-05-07 00:00:00');
INSERT INTO `stock` VALUES ('000510', '000510.SZ', '新金路', '四川', '化工原料', '1993-05-07 00:00:00');

-- ----------------------------
-- Table structure for str
-- ----------------------------
DROP TABLE IF EXISTS `str`;
CREATE TABLE `str`  (
  `strid` int NOT NULL AUTO_INCREMENT,
  `account` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `strname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `strgrade` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `ifpass` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `strdate` date NULL DEFAULT NULL,
  `introduction` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`strid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of str
-- ----------------------------
INSERT INTO `str` VALUES (1, '内部策略1', 'alpha', 'Vip', 'NO', '2024-06-04', '内部策略1');
INSERT INTO `str` VALUES (2, '内部策略2', 'mean', 'Vip', 'YES', '2024-06-18', '内部策略2');
INSERT INTO `str` VALUES (3, '内部策略3', 'mva', 'User', 'YES', '2024-06-17', '内部策略3');
INSERT INTO `str` VALUES (5, 'Root1', 'MyMean', 'Vip', 'NO', '2024-06-05', 'Root1');

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
  `birthday` date NULL DEFAULT NULL,
  `signal` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`userid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, '第一小组', 0, '女', 'szu', '2020-06-08', 'signal1');
INSERT INTO `user` VALUES (2, 'nickname2', 0, '女', 'address2', '2024-04-18', 'signal2');
INSERT INTO `user` VALUES (3, 'nickname3', 0, '女', 'address3', '2024-04-18', 'signal3');
INSERT INTO `user` VALUES (4, 'nickname4', 0, '男', 'address4', '2021-06-01', 'signal4');
INSERT INTO `user` VALUES (5, 'nickname5', 0, '男', 'address5', '2024-05-08', 'signal5');

-- ----------------------------
-- Table structure for usertostock
-- ----------------------------
DROP TABLE IF EXISTS `usertostock`;
CREATE TABLE `usertostock`  (
  `collectionid` int NOT NULL,
  `symbol` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '全国统一的股票编码'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of usertostock
-- ----------------------------
INSERT INTO `usertostock` VALUES (1, '000002');
INSERT INTO `usertostock` VALUES (1, '000001');
INSERT INTO `usertostock` VALUES (2, '000004');
INSERT INTO `usertostock` VALUES (1, '000016');
INSERT INTO `usertostock` VALUES (1, '000017');
INSERT INTO `usertostock` VALUES (1, '000004');
INSERT INTO `usertostock` VALUES (2, '000006');
INSERT INTO `usertostock` VALUES (2, '000017');
INSERT INTO `usertostock` VALUES (2, '000016');
INSERT INTO `usertostock` VALUES (3, '000016');
INSERT INTO `usertostock` VALUES (3, '000017');
INSERT INTO `usertostock` VALUES (4, '000016');
INSERT INTO `usertostock` VALUES (4, '000017');
INSERT INTO `usertostock` VALUES (5, '000016');
INSERT INTO `usertostock` VALUES (6, '000016');
INSERT INTO `usertostock` VALUES (7, '000016');
INSERT INTO `usertostock` VALUES (7, '000017');
INSERT INTO `usertostock` VALUES (1, '000045');
INSERT INTO `usertostock` VALUES (2, '000045');

SET FOREIGN_KEY_CHECKS = 1;
