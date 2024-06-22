-- MySQL dump 10.13  Distrib 8.0.36, for Linux (x86_64)
--
-- Host: localhost    Database: stock
-- ------------------------------------------------------
-- Server version	8.0.36-0ubuntu0.20.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `account` (
  `userid` int NOT NULL AUTO_INCREMENT,
  `account` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `password` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `email` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`userid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account`
--

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` VALUES (1,'Root1','deng20030806','dengenze2003@hotmail.com'),(2,'User1','deng20030806','3346583698@qq.com'),(4,'AlgE1','deng20030806','3346583698@qq.com'),(5,'StrE1','deng20030806','3346583698@qq.com'),(9,'123','123','1398391185@qq.com');
/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `alg`
--

DROP TABLE IF EXISTS `alg`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `alg` (
  `algid` int NOT NULL AUTO_INCREMENT,
  `account` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `algname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `alggrade` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `ifpass` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `algdate` date DEFAULT NULL,
  `introduction` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  PRIMARY KEY (`algid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `alg`
--

LOCK TABLES `alg` WRITE;
/*!40000 ALTER TABLE `alg` DISABLE KEYS */;
INSERT INTO `alg` VALUES (2,'内部策略','itransformer','User','NO','2024-06-02','简介'),(3,'内部策略','patchTST','Vip','YES','2024-06-01',NULL),(4,'Root1','MyAlg','Vip','NO','2024-06-04',NULL),(8,'Root1','TVA_block','Vip','NO','2024-06-20',''),(9,'AlgE1','1','User','NO','2024-06-20',''),(10,'Root1','11','Vip','NO','2024-06-20','');
/*!40000 ALTER TABLE `alg` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `collection`
--

DROP TABLE IF EXISTS `collection`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `collection` (
  `collectionid` int NOT NULL AUTO_INCREMENT,
  `collectionname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '收藏夹名称',
  `userid` int DEFAULT NULL,
  PRIMARY KEY (`collectionid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `collection`
--

LOCK TABLES `collection` WRITE;
/*!40000 ALTER TABLE `collection` DISABLE KEYS */;
INSERT INTO `collection` VALUES (1,'优质股1',1),(2,'好股票',2),(3,'我的股票',2),(4,'不错',2),(5,'优质股2',1);
/*!40000 ALTER TABLE `collection` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `permission`
--

DROP TABLE IF EXISTS `permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `permission` (
  `pid` int NOT NULL,
  `pname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '该功能的url',
  PRIMARY KEY (`pid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permission`
--

LOCK TABLES `permission` WRITE;
/*!40000 ALTER TABLE `permission` DISABLE KEYS */;
/*!40000 ALTER TABLE `permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `roleid` int DEFAULT NULL,
  `role` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'User'),(2,'Vip'),(3,'StrE'),(4,'AlgE'),(5,'Root');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roletoaccount`
--

DROP TABLE IF EXISTS `roletoaccount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roletoaccount` (
  `roleid` int NOT NULL,
  `userid` int DEFAULT NULL,
  PRIMARY KEY (`roleid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roletoaccount`
--

LOCK TABLES `roletoaccount` WRITE;
/*!40000 ALTER TABLE `roletoaccount` DISABLE KEYS */;
INSERT INTO `roletoaccount` VALUES (1,2),(3,5),(4,4),(5,1);
/*!40000 ALTER TABLE `roletoaccount` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roletoper`
--

DROP TABLE IF EXISTS `roletoper`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roletoper` (
  `roleid` int NOT NULL,
  `pid` int DEFAULT NULL,
  PRIMARY KEY (`roleid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roletoper`
--

LOCK TABLES `roletoper` WRITE;
/*!40000 ALTER TABLE `roletoper` DISABLE KEYS */;
/*!40000 ALTER TABLE `roletoper` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stock`
--

DROP TABLE IF EXISTS `stock`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `stock` (
  `symbol` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `tscode` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `area` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `industry` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `listdate` datetime DEFAULT NULL,
  PRIMARY KEY (`symbol`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stock`
--

LOCK TABLES `stock` WRITE;
/*!40000 ALTER TABLE `stock` DISABLE KEYS */;
INSERT INTO `stock` VALUES ('000001','000001.SZ','平安银行','深圳','银行','1991-04-03 00:00:00'),('000002','000002.SZ','万科A','深圳','全国地产','1991-01-29 00:00:00'),('000004','000004.SZ','国华网安','深圳','软件服务','1991-01-14 00:00:00'),('000006','000006.SZ','深振业A','深圳','区域地产','1992-04-27 00:00:00'),('000007','000007.SZ','*ST全新','深圳','其他商业','1992-04-13 00:00:00'),('000008','000008.SZ','神州高铁','北京','运输设备','1992-05-07 00:00:00'),('000009','000009.SZ','中国宝安','深圳','电气设备','1991-06-25 00:00:00'),('000010','000010.SZ','美丽生态','深圳','建筑工程','1995-10-27 00:00:00'),('000011','000011.SZ','深物业A','深圳','房产服务','1992-03-30 00:00:00'),('000012','000012.SZ','南玻A','深圳','玻璃','1992-02-28 00:00:00'),('000014','000014.SZ','沙河股份','深圳','全国地产','1992-06-02 00:00:00'),('000016','000016.SZ','深康佳A','深圳','家用电器','1992-03-27 00:00:00'),('000017','000017.SZ','深中华A','深圳','服饰','1992-03-31 00:00:00'),('000019','000019.SZ','深粮控股','深圳','农业综合','1992-10-12 00:00:00'),('000020','000020.SZ','深华发A','深圳','元器件','1992-04-28 00:00:00'),('000021','000021.SZ','深科技','深圳','元器件','1994-02-02 00:00:00'),('000023','000023.SZ','*ST深天','深圳','水泥','1993-04-29 00:00:00'),('000025','000025.SZ','特力A','深圳','汽车服务','1993-06-21 00:00:00'),('000026','000026.SZ','飞亚达','深圳','服饰','1993-06-03 00:00:00'),('000027','000027.SZ','深圳能源','深圳','火力发电','1993-09-03 00:00:00'),('000028','000028.SZ','国药一致','深圳','医药商业','1993-08-09 00:00:00'),('000029','000029.SZ','深深房A','深圳','区域地产','1993-09-15 00:00:00'),('000030','000030.SZ','富奥股份','吉林','汽车配件','1993-09-29 00:00:00'),('000031','000031.SZ','大悦城','深圳','全国地产','1993-10-08 00:00:00'),('000032','000032.SZ','深桑达A','深圳','建筑工程','1993-10-28 00:00:00'),('000034','000034.SZ','神州数码','深圳','软件服务','1994-05-09 00:00:00'),('000035','000035.SZ','中国天楹','江苏','环境保护','1994-04-08 00:00:00'),('000036','000036.SZ','华联控股','深圳','全国地产','1994-06-17 00:00:00'),('000037','000037.SZ','深南电A','深圳','火力发电','1994-07-01 00:00:00'),('000039','000039.SZ','中集集团','深圳','运输设备','1994-04-08 00:00:00'),('000040','000040.SZ','东旭蓝天','深圳','新型电力','1994-08-08 00:00:00'),('000042','000042.SZ','中洲控股','深圳','全国地产','1994-09-21 00:00:00'),('000045','000045.SZ','深纺织A','深圳','元器件','1994-08-15 00:00:00'),('000048','000048.SZ','京基智农','深圳','区域地产','1994-11-01 00:00:00'),('000049','000049.SZ','德赛电池','深圳','电气设备','1995-03-20 00:00:00'),('000050','000050.SZ','深天马A','深圳','元器件','1995-03-15 00:00:00'),('000055','000055.SZ','方大集团','深圳','其他建材','1996-04-15 00:00:00'),('000056','000056.SZ','皇庭国际','深圳','其他商业','1996-07-08 00:00:00'),('000058','000058.SZ','深赛格','深圳','商品城','1996-12-26 00:00:00'),('000059','000059.SZ','华锦股份','辽宁','石油加工','1997-01-30 00:00:00'),('000060','000060.SZ','中金岭南','深圳','铅锌','1997-01-23 00:00:00'),('000061','000061.SZ','农产品','深圳','农业综合','1997-01-10 00:00:00'),('000062','000062.SZ','深圳华强','深圳','元器件','1997-01-30 00:00:00'),('000063','000063.SZ','中兴通讯','深圳','通信设备','1997-11-18 00:00:00'),('000065','000065.SZ','北方国际','北京','建筑工程','1998-06-05 00:00:00'),('000066','000066.SZ','中国长城','深圳','IT设备','1997-06-26 00:00:00'),('000068','000068.SZ','华控赛格','深圳','环境保护','1997-06-11 00:00:00'),('000069','000069.SZ','华侨城A','深圳','全国地产','1997-09-10 00:00:00'),('000070','000070.SZ','ST特信','深圳','通信设备','2000-05-11 00:00:00'),('000078','000078.SZ','海王生物','深圳','医药商业','1998-12-18 00:00:00'),('000088','000088.SZ','盐田港','深圳','港口','1997-07-28 00:00:00'),('000089','000089.SZ','深圳机场','深圳','机场','1998-04-20 00:00:00'),('000090','000090.SZ','天健集团','深圳','建筑工程','1999-07-21 00:00:00'),('000096','000096.SZ','广聚能源','深圳','石油贸易','2000-07-24 00:00:00'),('000099','000099.SZ','中信海直','深圳','空运','2000-07-31 00:00:00'),('000100','000100.SZ','TCL科技','广东','元器件','2004-01-30 00:00:00'),('000151','000151.SZ','中成股份','北京','商贸代理','2000-09-06 00:00:00'),('000153','000153.SZ','丰原药业','安徽','化学制药','2000-09-20 00:00:00'),('000155','000155.SZ','川能动力','四川','新型电力','2000-09-26 00:00:00'),('000156','000156.SZ','华数传媒','浙江','影视音像','2000-09-06 00:00:00'),('000157','000157.SZ','中联重科','湖南','工程机械','2000-10-12 00:00:00'),('000158','000158.SZ','常山北明','河北','软件服务','2000-07-24 00:00:00'),('000159','000159.SZ','国际实业','新疆','石油贸易','2000-09-26 00:00:00'),('000166','000166.SZ','申万宏源','新疆','证券','2015-01-26 00:00:00'),('000301','000301.SZ','东方盛虹','江苏','化纤','2000-05-29 00:00:00'),('000333','000333.SZ','美的集团','广东','家用电器','2013-09-18 00:00:00'),('000338','000338.SZ','潍柴动力','山东','汽车配件','2007-04-30 00:00:00'),('000400','000400.SZ','许继电气','河南','电气设备','1997-04-18 00:00:00'),('000401','000401.SZ','冀东水泥','河北','水泥','1996-06-14 00:00:00'),('000402','000402.SZ','金融街','北京','全国地产','1996-06-26 00:00:00'),('000403','000403.SZ','派林生物','山西','生物制药','1996-06-28 00:00:00'),('000404','000404.SZ','长虹华意','江西','家用电器','1996-06-19 00:00:00'),('000407','000407.SZ','胜利股份','山东','供气供热','1996-07-03 00:00:00'),('000408','000408.SZ','藏格矿业','青海','农药化肥','1996-06-28 00:00:00'),('000409','000409.SZ','云鼎科技','山东','软件服务','1996-06-27 00:00:00'),('000410','000410.SZ','沈阳机床','辽宁','机床制造','1996-07-18 00:00:00'),('000411','000411.SZ','英特集团','浙江','医药商业','1996-07-16 00:00:00'),('000413','000413.SZ','东旭光电','河北','元器件','1996-09-25 00:00:00'),('000415','000415.SZ','渤海租赁','新疆','多元金融','1996-07-16 00:00:00'),('000416','000416.SZ','*ST民控','山东','多元金融','1996-07-19 00:00:00'),('000417','000417.SZ','合肥百货','安徽','百货','1996-08-12 00:00:00'),('000419','000419.SZ','通程控股','湖南','百货','1996-08-16 00:00:00'),('000420','000420.SZ','吉林化纤','吉林','化纤','1996-08-02 00:00:00'),('000421','000421.SZ','南京公用','江苏','供气供热','1996-08-06 00:00:00'),('000422','000422.SZ','湖北宜化','湖北','农药化肥','1996-08-15 00:00:00'),('000423','000423.SZ','东阿阿胶','山东','中成药','1996-07-29 00:00:00'),('000425','000425.SZ','徐工机械','江苏','工程机械','1996-08-28 00:00:00'),('000426','000426.SZ','兴业银锡','内蒙','铅锌','1996-08-28 00:00:00'),('000428','000428.SZ','华天酒店','湖南','酒店餐饮','1996-08-08 00:00:00'),('000429','000429.SZ','粤高速A','广东','路桥','1998-02-20 00:00:00'),('000430','000430.SZ','张家界','湖南','旅游景点','1996-08-29 00:00:00'),('000488','000488.SZ','晨鸣纸业','山东','造纸','2000-11-20 00:00:00'),('000498','000498.SZ','山东路桥','山东','建筑工程','1997-06-09 00:00:00'),('000501','000501.SZ','武商集团','湖北','百货','1992-11-20 00:00:00'),('000503','000503.SZ','国新健康','山东','软件服务','1992-11-30 00:00:00'),('000504','000504.SZ','南华生物','湖南','医疗保健','1992-12-08 00:00:00'),('000505','000505.SZ','京粮控股','海南','食品','1992-12-21 00:00:00'),('000506','000506.SZ','*ST中润','山东','黄金','1993-03-12 00:00:00'),('000507','000507.SZ','珠海港','广东','港口','1993-03-26 00:00:00'),('000509','000509.SZ','华塑控股','四川','元器件','1993-05-07 00:00:00'),('000510','000510.SZ','新金路','四川','化工原料','1993-05-07 00:00:00');
/*!40000 ALTER TABLE `stock` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `str`
--

DROP TABLE IF EXISTS `str`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `str` (
  `strid` int NOT NULL AUTO_INCREMENT,
  `account` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `strname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `strgrade` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `ifpass` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `strdate` date DEFAULT NULL,
  `introduction` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  PRIMARY KEY (`strid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `str`
--

LOCK TABLES `str` WRITE;
/*!40000 ALTER TABLE `str` DISABLE KEYS */;
INSERT INTO `str` VALUES (1,'内部策略','银行股轮动策略','Vip','YES','2024-06-04','简介'),(2,'内部策略','Dual_Thrust策略','User','YES','2024-06-18','简介'),(3,'内部策略','股指期货跨期套利策略','Vip','NO','2024-06-17',''),(5,'Root1','双均线策略','User','NO','2024-06-05',''),(8,'Root1','多因子策略','User','NO','2024-06-20',''),(9,'Root1','低估价值选股策略','Vip','NO','2024-06-20','简介'),(10,'Root1','测试1','User','NO','2024-06-20','简介');
/*!40000 ALTER TABLE `str` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `userid` int NOT NULL,
  `nickname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `ifbanned` int DEFAULT NULL COMMENT 'bool类型，表示该用户是否封禁',
  `sex` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `signal` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  PRIMARY KEY (`userid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'nickname1',0,'男','address1','2024-04-18','signal1'),(2,'nickname2',0,'女','address2','2024-04-18','signal2'),(4,'nickname4',0,'男','address4','2021-06-01','signal4'),(5,'nickname5',0,'男','address5','2024-05-08','signal5'),(9,'用户1718885915506',0,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usertostock`
--

DROP TABLE IF EXISTS `usertostock`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usertostock` (
  `collectionid` int NOT NULL,
  `symbol` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '全国统一的股票编码'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usertostock`
--

LOCK TABLES `usertostock` WRITE;
/*!40000 ALTER TABLE `usertostock` DISABLE KEYS */;
INSERT INTO `usertostock` VALUES (1,'000002'),(1,'000001'),(2,'000004'),(1,'000016'),(1,'000017'),(1,'000004'),(2,'000006'),(2,'000017'),(2,'000016'),(3,'000016'),(3,'000017'),(4,'000016'),(4,'000017'),(5,'000016'),(7,'000016'),(7,'000017'),(2,'000045'),(5,'000001');
/*!40000 ALTER TABLE `usertostock` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-06-22 13:18:21
