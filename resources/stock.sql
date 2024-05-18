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

 Date: 18/05/2024 20:44:33
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

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

SET FOREIGN_KEY_CHECKS = 1;
