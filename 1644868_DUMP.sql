-- MySQL dump 10.13  Distrib 5.5.38, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: d1644868
-- ------------------------------------------------------
-- Server version	5.5.38-0ubuntu0.14.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `ADMIN`
--

DROP TABLE IF EXISTS `ADMIN`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ADMIN` (
  `ADMIN_ID` varchar(13) NOT NULL,
  `ADMIN_NAME` varchar(30) NOT NULL,
  `ADMIN_SURNAME` varchar(30) NOT NULL,
  `ADMIN_USERNAME` varchar(30) NOT NULL,
  `ADMIN_PASSWORD` varchar(50) NOT NULL,
  `ADMIN_SALT` varchar(30) NOT NULL,
  PRIMARY KEY (`ADMIN_ID`),
  UNIQUE KEY `ADMIN_ID` (`ADMIN_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ADMIN`
--

LOCK TABLES `ADMIN` WRITE;
/*!40000 ALTER TABLE `ADMIN` DISABLE KEYS */;
INSERT INTO `ADMIN` VALUES ('9604247612483','Bill','Bezos','admin','ac21f9440f77c9527445a469d52370b4','sJca2gqr');
/*!40000 ALTER TABLE `ADMIN` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CUSTOMER`
--

DROP TABLE IF EXISTS `CUSTOMER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CUSTOMER` (
  `CUSTOMER_ID` varchar(13) NOT NULL,
  `CUSTOMER_NAME` varchar(30) NOT NULL,
  `CUSTOMER_SURNAME` varchar(30) NOT NULL,
  `CUSTOMER_EMAIL` varchar(30) NOT NULL,
  `CUSTOMER_USERNAME` varchar(30) NOT NULL,
  `CUSTOMER_PASSWORD` varchar(50) NOT NULL,
  `CUSTOMER_DOCTOR` varchar(13) DEFAULT NULL,
  `CUSTOMER_VERIFYDOCTOR` varchar(5) NOT NULL,
  `CUSTOMER_SALT` varchar(30) NOT NULL,
  PRIMARY KEY (`CUSTOMER_ID`),
  KEY `CUSTOMER_DOCTOR` (`CUSTOMER_DOCTOR`),
  CONSTRAINT `CUSTOMER_ibfk_1` FOREIGN KEY (`CUSTOMER_DOCTOR`) REFERENCES `DOCTOR` (`DOCTOR_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CUSTOMER`
--

LOCK TABLES `CUSTOMER` WRITE;
/*!40000 ALTER TABLE `CUSTOMER` DISABLE KEYS */;
INSERT INTO `CUSTOMER` VALUES ('5509107584321','Vin','Diesl','vin34@yahoo.com','driver','98389371924866c1dc040259440467c5','1599018734652','false','31V44XPQ'),('5607187553678','Cyril','Walters','cyril7@gmail.com','freedom','16bf5bbf55df1d609c279be0fecbb831','3456217645678','false','S5rUvRe5'),('6410269845712','Ashley','Young','worst@gmail.com','terrible','e3390d38b489a4772476759a54901d30',NULL,'false','PJYR1ANa'),('6501126785943','Clint','Eastwood','east@gmail.com','shooter','e92588ff7e3de4f09b4f98b575d3dacb','1254769834027','false','WIxbWCPM'),('6503157685343','Steven','Spielberg','spielberg@hotmail.com','legend','49aaaaa5ad3d3677ff1746ca36c01871','1209564387432','true','WLSqCllR'),('6707187584243','James','Hynamin','jamesh12@hotmail.net','scienceman','bfded6fa1c12b7197de6cd42dd7f72fa','9811215463123','false','udt4HVJa'),('7405121547863','kyle','John','Kyle@gmail.com','Kyle1','f5b93506b0c8e74db509eaf0183e1393','1599018734652','false','3DGyAbN7'),('7408247847589','Joe','Dirt','joedirt@gmai.com','jeppe','5411f821da104d5825eb5d3349dbb521',NULL,'false','V9JL309c'),('7608196547832','Adam','Savage','adams65@hotmail.net','buster','3f6ae975921a08e4926446642756a61a','9811215463123','true','0EKBNNM6'),('7611127584342','George','Lucas','georgeluc@gmail.com','emperor','79630d49f3ee64190fad3729293b4ca4','7634652134980','false','A8FLkfrz'),('7612111588063','Cade','Trump','ctrumo@yahoo.com','alien66','9a0ce14eb2d0e09ecd7e7753bcd645c5','7410235491234','true','HDCV9SJj'),('7708197584932','Jackie','Cham','jc12@yahoo.com','culture','3fc13e27ec03bbf7b75f717a5f2068d3','1254769834027','true','3ZsBjYEI'),('7806095988081','Luke','Skywalker','luke56@yahoo.com','skywalker5','7e8182514488d537dcc8d30d929d8fc4','7410235491234','true','OyVVvEvJ'),('7806223276538','Jeff','Goldblum','jeffgold@gmail.com','jurassic','2b8b04599011252041dac1ed707b1a03',NULL,'false','7YtusszK'),('7901015111088','Pravesh','Ranchod','g@h.com','pravesh','f65162cdbc9148b9f33edbf6d448f00b','1234567890','false','5JgsQenz'),('8005247649857','Barry','Allen','ba@gmail.com','the','79737edfc09b969005bc178f6db94f01',NULL,'false','mIcefD9h'),('8006207846578','Eliza','Cohen','elihen@hotmail.com','ash','c199e1294d568a30465274cb8803ff6d',NULL,'false','sX8L7yVL'),('8010126352841','Anatoly','Knitsiev','anatknit@yahoo.com','mother','eb2b0d49bfd743676774c5e19e72183c','7410235491234','true','uTk1YRLZ'),('8210307248715','Nat','Romanoff','natro@gmail.com','avenger','315932c6ffbdf75e681ea5aa2e54ca68',NULL,'false','nMKu5UE1'),('8401267458758','Edward','Kenway','ek@yahoo.com','pirate','3230144b558d621b1fe857e681bf5bc6','7410235491234','true','qYju56Yy'),('8406147594137','Dennis','Menace','denthemenace@hotmail.com','mad','10cfa263c713969c6d09d3d28d907554','7410235491234','true','jTOnbKdG'),('8407117859421','Tony','Stark','tstark@gmail.com','iron','50d3cb3eb9d08931aa0ebb101e802402',NULL,'false','l4ApeFdR'),('8509127458713','Seth','Rogen','sethrogen@gmail.com','cabbage','fa4fddd11ff8409ab8207ef8141e1bea','7410235491234','true','uqRkJY8a'),('8704127847589','Anakin','Skywalker','anakywalker@gmail.com','darth','a6d3b4ed2b4cbc9baecb2c059b9fe1ee','7410235491234','true','3MzUO6ed'),('8704127854785','Ezio','Auditore','ea@gmail.com','assassins','e70b8b3524afb25aca999e12190e5219',NULL,'false','8Nm9DSFD'),('8708043614784','Connor','Kenway','conken@yahoo.com','eagle','892b0c99190fa6a6979e3a0a65d6e080',NULL,'false','kt4F11Lj'),('8803047687443','Dwayne','Johnson','rock@gmail.com','rock','453902ea00495ecbf798505c1acf86f1','1599018734652','true','zcBZFXYQ'),('8809306584932','David','Belle','davidb@hotmail.net','runner','88e076b2b719a8a81e5e5aa7760a7f67','3456217645678','true','JpExhyql'),('8812047856321','Benjamin','Button','Ben@gmail.com','Benji','22f26084a7596e7411828ed55be6257f','1234567890','false','5kWUfZhF'),('8909317584321','Han','Solo','solo77@gmail.com','falcon','6cf31a874fe974dd30611eeb8ae82281','7634652134980','true','XC3ShyRr'),('9106057547895','Paul','Pogba','pp@gmail.com','bad','dde9dd379711ccae500ff272a8c2449a',NULL,'false','mzlLyeGs'),('9811165977083','Josh','Pickle','mack@gmail.com','josh12','428cca3ca120f346fe8a864658b42be8','7410235491234','true','qjm1IKzb'),('9901546372895','James','Cameron','jc@gmail.com','director','86a1b3f59ea37e5f5172c6508364ea74','1209564387432','false','naW5SJja'),('9904301578625','Jeff','Bob','jeff@gmail.com','MyNameJeff','3438801442ab83e129245c852f68e3e2','1254769834027','false','J6oguZ6P'),('9904305162087','Ryan','Alexander','ryan@gmail.com','Awesome,','6d5fab7795fb9de838cd64075d5b12ea','7410235491234','false','A1uVIdgI'),('9911175877063','Mike','Forest','mikemc@gmail.com','mike99','62119686be4587b0e48cdb2953168141','7410235491234','true','zOxTnaC4');
/*!40000 ALTER TABLE `CUSTOMER` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `DOCTOR`
--

DROP TABLE IF EXISTS `DOCTOR`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `DOCTOR` (
  `DOCTOR_ID` varchar(13) NOT NULL,
  `DOCTOR_NAME` varchar(30) NOT NULL,
  `DOCTOR_SURNAME` varchar(30) NOT NULL,
  `DOCTOR_PHONENUMBER` varchar(10) NOT NULL,
  `DOCTOR_EMAIL` varchar(30) NOT NULL,
  `DOCTOR_ADDRESS` varchar(40) NOT NULL,
  `DOCTOR_WORKHOURS` varchar(15) NOT NULL,
  `DOCTOR_USERNAME` varchar(30) NOT NULL,
  `DOCTOR_PASSWORD` varchar(50) NOT NULL,
  `DOCTOR_SALT` varchar(30) NOT NULL,
  PRIMARY KEY (`DOCTOR_ID`),
  UNIQUE KEY `DOCTOR_ID` (`DOCTOR_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `DOCTOR`
--

LOCK TABLES `DOCTOR` WRITE;
/*!40000 ALTER TABLE `DOCTOR` DISABLE KEYS */;
INSERT INTO `DOCTOR` VALUES ('1209564387432','Ritesh','Paul','9637894563','ritesh7@gmail.com','76 Penguin Avenue, Johannesburg','8:00-18:00','bach','ac02d71be657cf321ad87c1ada40b31b','nQk8MQkW'),('1234567890','Gregory','House','3214567987','ghouse@hosp.com','42 Medication Lane, New York','7:30-17:15','limpMaster','1d422e1df0c43f75c5bd0b280e7bb48e','PMA8p64E'),('1254769834027','Bruce','Norris','7893694545','norris45@gmail.com','23 Round Rose Street, San Francisco ','6:00-17:00','roundhouse','a9c9b391d6ebd6529d162af9e7fdf1a7','7L2EYA2H'),('1599018734652','Yusuf','Tazin','0213697458','tazin99@yahoo.com','89 Town Drive, Venice','9:00-18:00','mentor','f7050accacd9c886bd9b254007d80022','uJHUc04N'),('3456217645678','Steve','Klein','0851472356','klein@hotmail.com','27 Mountain Road, Pretoria','8:00-17:00','vision','bf11944dd1d00597b34efb1d28e29d09','FUceOf2v'),('7410235491234','Hannibal','Lector','0114259872','hanlector4@gmail.com','8 Ridge Road, Kensington','08:00-17:00','hanLector4','a5e96326104e4ff8564ac80243819cc4','R0QHz0gL'),('7634652134980','Chuck','Lee','033123685','chuckl23@yahoo.net','45 Buffalo Drive, Dallas.','7:30-18:45','master','5d72095eebd7757bd42bc54fde4b0463','Gv2PIdrP'),('9811215463123','Edward','Thatch','0843331413','edThatch@gmail.com','33 Ocean Avenue, Johannesburg ','8:00-16:30','captain','9b65f3b9eafe709b80d992c0c73b1510','8QEiAyPa');
/*!40000 ALTER TABLE `DOCTOR` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ORDERS`
--

DROP TABLE IF EXISTS `ORDERS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ORDERS` (
  `ORDER_NUMBER` int(11) NOT NULL AUTO_INCREMENT,
  `ORDER_CUSTOMER` varchar(13) NOT NULL,
  `ORDER_PHARMACEUTICALS` varchar(500) NOT NULL,
  `ORDER_PRICE` double NOT NULL,
  `ORDER_DATEORDERED` date NOT NULL,
  `ORDER_DATECOMPLETED` date DEFAULT NULL,
  PRIMARY KEY (`ORDER_NUMBER`),
  KEY `ORDER_CUSTOMER` (`ORDER_CUSTOMER`),
  CONSTRAINT `ORDERS_ibfk_1` FOREIGN KEY (`ORDER_CUSTOMER`) REFERENCES `CUSTOMER` (`CUSTOMER_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ORDERS`
--

LOCK TABLES `ORDERS` WRITE;
/*!40000 ALTER TABLE `ORDERS` DISABLE KEYS */;
INSERT INTO `ORDERS` VALUES (35,'8401267458758','3*Adco-Wormex Sd,2*Akrinor',366.54,'2019-05-19',NULL),(36,'8401267458758','3*Alcophyllex',18.54,'2019-05-19',NULL),(37,'8401267458758','2*Brufen 200',54.34,'2019-05-19',NULL),(38,'8401267458758','2*Brufen 200',54.34,'2019-05-19',NULL),(39,'8401267458758','1*Dilinct',53.22,'2019-05-19',NULL),(40,'8401267458758','1*Dilinct',53.22,'2019-05-19',NULL),(41,'8401267458758','1*Panado Plus',71.65,'2019-05-19',NULL),(42,'8401267458758','1*Panado Plus',71.65,'2019-05-19',NULL),(43,'8401267458758','2*Dilinct,1*Pankreoflat',402.62,'2019-05-20',NULL),(46,'7806095988081','1*Mucolator',44.57,'2019-05-20',NULL),(47,'7806095988081','1*Mucolator',44.57,'2019-05-20',NULL),(48,'9811165977083','1*Mucolator',44.57,'2019-05-20',NULL),(49,'9904305162087','2*Adco-Wormex Sd',16.28,'2019-05-15',NULL),(50,'8401267458758','1*Allergex Non Drowsy Syrup,1*FullHealth,2*Mango Kush,1*Faverin 100',430,'2019-05-15',NULL),(51,'7901015111088','1*Allergex Non Drowsy Syrup,1*FullHealth,2*Mango Kush,1*Faverin 100,3*Alcophyllex',448.54,'2019-05-15',NULL);
/*!40000 ALTER TABLE `ORDERS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `PHARMACEUTICAL`
--

DROP TABLE IF EXISTS `PHARMACEUTICAL`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PHARMACEUTICAL` (
  `PHARMACEUTICAL_ID` varchar(20) NOT NULL,
  `PHARMACEUTICAL_NAME` varchar(40) NOT NULL,
  `PHARMACEUTICAL_SCHEDULE` int(1) NOT NULL,
  `PHARMACEUTICAL_COSTPRICE` double NOT NULL,
  `PHARMACEUTICAL_MARKUP` double NOT NULL,
  `PHARMACEUTICAL_DISCOUNT` double NOT NULL,
  `PHARMACEUTICAL_AVAILABLEQUANTITY` int(3) NOT NULL,
  `PHARMACEUTICAL_SELLINGPRICE` double NOT NULL,
  `PHARMACEUTICAL_MANUFACTURER` varchar(30) NOT NULL,
  PRIMARY KEY (`PHARMACEUTICAL_ID`),
  UNIQUE KEY `PHARMACEUTICAL_ID` (`PHARMACEUTICAL_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PHARMACEUTICAL`
--

LOCK TABLES `PHARMACEUTICAL` WRITE;
/*!40000 ALTER TABLE `PHARMACEUTICAL` DISABLE KEYS */;
INSERT INTO `PHARMACEUTICAL` VALUES ('123/45','Mango Kush',1,50,0,0,46,50,'GreenSmokeRoom INC'),('33/1.2/0354','Faverin 100',5,249.07,0,0,26,249.07,'Abbott Laboratories SA Pty Ltd'),('34/3.3/0178','Mucolator',2,44.57,0,0,22,44.57,'Abbott Laboratories SA Pty Ltd'),('34/7.1.3/0495','Nebilet 5Mg',3,196.3,0,0,28,196.3,'Adcock Ingram Limited'),('35/15.4/0076','Zaditen',2,123.2,0,0,5,123.2,'Adcock Ingram Limited'),('35/2.8/0046','Gen-Payne Capsules',2,41.53,0,0,27,41.53,'Adcock Ingram Limited'),('35/2.8/0070','Panado Plus',2,71.65,0,0,41,71.65,'Adcock Ingram Limited'),('35/7.1/0372','Teveten 600',3,224.74,0,0,28,224.74,'Abbott Laboratories SA Pty Ltd'),('36/5.7.1/0008','Allergex Non Drowsy Syrup',1,50.94,0,0,146,50.94,'Adcock Ingram Limited'),('37/12/0008','Adco-Wormex Sd',2,8.14,0,0,26,8.14,'Adcock Ingram Limited'),('37/5.8/0552','Corenza Cold and Flu Syrup',2,17.71,0,0,49,17.71,'Adcock Ingram Limited'),('41/5.7.1/0134','Fexaway 180',2,144.51,0,0,29,144.51,'Adcock Ingram Limited'),('42/08','FullHealth',5,23.24,3.67,2.33,78,29.99,'Wellness Inc.'),('43/1.2/0578','Venlafaxine XR 75 ADCO',5,181.62,0,0,30,181.62,'Adcock Ingram Limited'),('43/7.1.3/0804','Hytenza 50',3,70.59,0,0,30,70.59,'Abex Pharmaceutica (Pty) Ltd'),('46/2.6.5/0674','Ripside 1',5,174.84,0,0,30,174.84,'Abex Pharmaceutica (Pty) Ltd'),('46/5.7.1/0004','Dehrin Solution',2,114.65,0,0,119,114.65,'Abex Pharmaceutica (Pty) Ltd'),('46/7.1.3/0232','Abecard 16',3,110.34,0,0,30,110.34,'Abex Pharmaceutica (Pty) Ltd'),('48/11.4.3/0437','Nexiflux 40',4,263.78,0,0,28,263.78,'Abex Pharmaceutica (Pty) Ltd'),('999/11','NZT-48',1,50,10,0,500,55,'Limitless Pharmaceuticals '),('A/3.1/727','Brufen 200',1,27.17,0,0,20,27.17,'Abbott Laboratories SA Pty Ltd'),('A38/4/0430','Ubistesin',4,229.91,0,0,50,229.91,'3M South Africa (Pty) Ltd'),('A39/2.6/0072','Stresam',5,190.45,0,0,60,190.45,'Adcock Ingram Limited'),('A40/21.2/0464','Diamin-500',3,35.73,0,0,100,35.73,'Adcock Ingram Limited'),('B/2.9/1251','Sabax Pethidine 50mg',6,33.49,0,0,10,33.49,'Adcock Ingram Limited'),('C/11.10/59','Pankreoflat',1,296.18,0,0,99,296.18,'Abbott Laboratories SA Pty Ltd'),('C661 (OM)','Corenza C',2,43.08,0,0,10,43.08,'Adcock Ingram Limited'),('E/11.3/954','Nobese No 1',6,23.54,0,0,15,23.54,'Adcock Ingram Limited'),('G/18.1/94','Burinex 1 mg',3,133.78,0,0,30,133.78,'Adcock Ingram Limited'),('G1115 (OM)','Dilinct',2,53.22,0,0,198,53.22,'Adcock Ingram Limited'),('G1796 (OM)','Calasthetic',2,30.4,0,0,34,30.4,'Adcock Ingram Limited'),('G2904 (OM)','Akrinor',1,171.06,0,0,28,171.06,'Adcock Ingram Limited'),('G668 (OM)','Allergex',2,37.41,0,0,97,37.41,'Adcock Ingram Limited'),('G720 (OM)','Alcophyllex',2,6.18,0,0,94,6.18,'Adcock Ingram Limited'),('G721 (OM)','Alcophyllin',1,5.32,0,0,98,5.32,'Adcock Ingram Limited'),('H/16.3/123','Cepacol Plus Original Lozenges',1,33.45,0,0,16,33.45,'Adcock Ingram Limited'),('H1474 (OM)','Covancaine',2,37.35,0,0,20,37.35,'Adcock Ingram Limited'),('H1536 (OM)','Neopan',4,13.58,0,0,15,13.58,'Adcock Ingram Limited'),('H990 (OM)','Achromide',4,79.43,0,0,500,79.43,'Adcock Ingram Limited'),('N/1.2/180','Lantanon',5,319.73,0,0,40,319.73,'Adcock Ingram Limited'),('Q/11.2/165','Colofac 135mg',2,48.72,0,0,20,48.72,'Abbott Laboratories SA Pty Ltd'),('R/3.1/71','Brufen',3,223.09,0,0,100,223.09,'Abbott Laboratories SA Pty Ltd'),('T/2.8/243','Myprodol Tablets',3,69.8,0,0,30,69.8,'Adcock Ingram Limited'),('U/4/104','Xylestesin-A',4,189.1,0,0,50,189.1,'3M South Africa (Pty) Ltd');
/*!40000 ALTER TABLE `PHARMACEUTICAL` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `PRESCRIPTIONS`
--

DROP TABLE IF EXISTS `PRESCRIPTIONS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PRESCRIPTIONS` (
  `PRESCRIPTION_NUMBER` int(11) NOT NULL AUTO_INCREMENT,
  `PRESCRIPTION_CUSTOMER` varchar(13) NOT NULL,
  `PRESCRIPTION_PHARMACEUTICALS` varchar(500) NOT NULL,
  `PRESCRIPTION_PRICE` double NOT NULL,
  `PRESCRIPTIONS_REPEATSLEFT` int(1) NOT NULL,
  PRIMARY KEY (`PRESCRIPTION_NUMBER`),
  KEY `PRESCRIPTION_CUSTOMER` (`PRESCRIPTION_CUSTOMER`),
  CONSTRAINT `PRESCRIPTIONS_ibfk_1` FOREIGN KEY (`PRESCRIPTION_CUSTOMER`) REFERENCES `CUSTOMER` (`CUSTOMER_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PRESCRIPTIONS`
--

LOCK TABLES `PRESCRIPTIONS` WRITE;
/*!40000 ALTER TABLE `PRESCRIPTIONS` DISABLE KEYS */;
INSERT INTO `PRESCRIPTIONS` VALUES (1,'8401267458758','1*Allergex Non Drowsy Syrup,1*FullHealth',80.92999999999999,4),(5,'9811165977083','3*Allergex Non Drowsy Syrup,1*FullHealth',182.81,6),(6,'8509127458713','3*Allergex Non Drowsy Syrup,1*FullHealth',182.81,2),(7,'7612111588063','1*Mucolator',44.57,1),(13,'9911175877063','2*Faverin 100,1*Nebilet 5Mg',694.44,1),(17,'9911175877063','1*Mucolator',44.57,1),(19,'8401267458758','2*Mango Kush,1*Faverin 100',349.07,2),(20,'7806095988081','1*Panado Plus',71.65,13);
/*!40000 ALTER TABLE `PRESCRIPTIONS` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-05-26 13:49:56
