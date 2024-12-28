-- MySQL dump 10.13  Distrib 8.0.40, for Linux (x86_64)
--
-- Host: localhost    Database: ByteCity
-- ------------------------------------------------------
-- Server version	8.0.40-0ubuntu0.24.04.1

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
-- Table structure for table `Cart`
--

DROP TABLE IF EXISTS `Cart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Cart` (
  `idUser` int NOT NULL,
  `idProduct` int NOT NULL,
  `quantity` smallint NOT NULL,
  PRIMARY KEY (`idUser`,`idProduct`),
  KEY `idProduct_idx` (`idProduct`),
  CONSTRAINT `idProductCart` FOREIGN KEY (`idProduct`) REFERENCES `Product` (`idProduct`) ON DELETE CASCADE,
  CONSTRAINT `idUserCart` FOREIGN KEY (`idUser`) REFERENCES `User` (`idUser`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Cart`
--

LOCK TABLES `Cart` WRITE;
/*!40000 ALTER TABLE `Cart` DISABLE KEYS */;
/*!40000 ALTER TABLE `Cart` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CaseFormFactor`
--

DROP TABLE IF EXISTS `CaseFormFactor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `CaseFormFactor` (
  `idPCCase` int NOT NULL,
  `formFactor` varchar(10) NOT NULL COMMENT 'Форм-фактор(-ы)',
  PRIMARY KEY (`idPCCase`,`formFactor`),
  CONSTRAINT `idCaseFormFactor` FOREIGN KEY (`idPCCase`) REFERENCES `PCCase` (`idPCCase`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CaseFormFactor`
--

LOCK TABLES `CaseFormFactor` WRITE;
/*!40000 ALTER TABLE `CaseFormFactor` DISABLE KEYS */;
INSERT INTO `CaseFormFactor` VALUES (124,'ATX'),(124,'Micro-ATX'),(125,'ATX'),(125,'Micro-ATX'),(126,'ATX'),(126,'Micro-ATX'),(127,'ATX'),(127,'Micro-ATX');
/*!40000 ALTER TABLE `CaseFormFactor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Cooling`
--

DROP TABLE IF EXISTS `Cooling`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Cooling` (
  `idCooling` int NOT NULL,
  `coolingType` varchar(10) DEFAULT NULL COMMENT 'Охлаждение',
  `TDP` smallint DEFAULT NULL COMMENT 'TDP',
  `connectType` char(4) DEFAULT NULL COMMENT 'Тип подключения',
  `qtyFan` tinyint DEFAULT NULL COMMENT 'Количество вентиляторов',
  `diametrFan` smallint DEFAULT NULL COMMENT 'Диаметр вентилатора(-ов)',
  `depth` smallint DEFAULT NULL COMMENT 'Глубина',
  `length` smallint DEFAULT NULL COMMENT 'Длина',
  `width` smallint DEFAULT NULL COMMENT 'Ширина',
  `minSpeed` smallint DEFAULT NULL COMMENT 'Минимальная скорость',
  `maxSpeed` smallint DEFAULT NULL COMMENT 'Максимальная скорость',
  `color` varchar(10) DEFAULT NULL COMMENT 'Цвет',
  PRIMARY KEY (`idCooling`),
  CONSTRAINT `idProductCooling` FOREIGN KEY (`idCooling`) REFERENCES `Product` (`idProduct`) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Cooling`
--

LOCK TABLES `Cooling` WRITE;
/*!40000 ALTER TABLE `Cooling` DISABLE KEYS */;
INSERT INTO `Cooling` VALUES (116,'Liquid',250,'PWM',2,120,30,315,143,500,2000,'Black'),(117,'Air',250,'PWM',2,135,163,136,123,400,1500,'Black'),(118,'Liquid',300,'PWM',3,120,27,392,120,500,2000,'Black'),(119,'Air',180,'PWM',1,120,25,157,129,500,1500,'Black');
/*!40000 ALTER TABLE `Cooling` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CoolingSockets`
--

DROP TABLE IF EXISTS `CoolingSockets`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `CoolingSockets` (
  `idCooling` int NOT NULL,
  `socket` varchar(7) NOT NULL COMMENT 'Сокет(-ы)',
  PRIMARY KEY (`idCooling`,`socket`),
  CONSTRAINT `idCoolingSocket` FOREIGN KEY (`idCooling`) REFERENCES `Cooling` (`idCooling`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CoolingSockets`
--

LOCK TABLES `CoolingSockets` WRITE;
/*!40000 ALTER TABLE `CoolingSockets` DISABLE KEYS */;
INSERT INTO `CoolingSockets` VALUES (116,'AM4'),(116,'LGA1200'),(117,'AM4'),(117,'LGA1151'),(118,'AM4'),(118,'LGA1200'),(119,'AM4'),(119,'LGA1151');
/*!40000 ALTER TABLE `CoolingSockets` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Discount`
--

DROP TABLE IF EXISTS `Discount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Discount` (
  `idDiscount` int NOT NULL AUTO_INCREMENT,
  `value` double NOT NULL,
  PRIMARY KEY (`idDiscount`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Discount`
--

LOCK TABLES `Discount` WRITE;
/*!40000 ALTER TABLE `Discount` DISABLE KEYS */;
/*!40000 ALTER TABLE `Discount` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Favourite`
--

DROP TABLE IF EXISTS `Favourite`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Favourite` (
  `idUser` int NOT NULL,
  `idProduct` int NOT NULL,
  PRIMARY KEY (`idUser`,`idProduct`),
  KEY `idProductFavourite_idx` (`idProduct`),
  CONSTRAINT `idProductFavourite` FOREIGN KEY (`idProduct`) REFERENCES `Product` (`idProduct`) ON DELETE CASCADE,
  CONSTRAINT `idUserFavourite` FOREIGN KEY (`idUser`) REFERENCES `User` (`idUser`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Favourite`
--

LOCK TABLES `Favourite` WRITE;
/*!40000 ALTER TABLE `Favourite` DISABLE KEYS */;
INSERT INTO `Favourite` VALUES (7,149);
/*!40000 ALTER TABLE `Favourite` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Hdd`
--

DROP TABLE IF EXISTS `Hdd`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Hdd` (
  `idHdd` int NOT NULL,
  `interface` varchar(5) DEFAULT NULL COMMENT 'Интерфейс',
  `volume` mediumint DEFAULT NULL COMMENT 'Объем',
  `formFactor` varchar(3) DEFAULT NULL COMMENT 'Форм-фактор',
  `thickness` float DEFAULT NULL COMMENT 'Толщина',
  `spindleSpeed` smallint DEFAULT NULL COMMENT 'Скорость вращения шпинделя',
  `seqReadSpeed` mediumint DEFAULT NULL COMMENT 'Скорость последовательного чтения',
  `seqWriteSpeed` mediumint DEFAULT NULL COMMENT 'Скорость последовательной записи',
  PRIMARY KEY (`idHdd`),
  CONSTRAINT `idProductHdd` FOREIGN KEY (`idHdd`) REFERENCES `Product` (`idProduct`) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Hdd`
--

LOCK TABLES `Hdd` WRITE;
/*!40000 ALTER TABLE `Hdd` DISABLE KEYS */;
INSERT INTO `Hdd` VALUES (120,'SATA',1000,'3.5',26.1,7200,150,150),(121,'SATA',2000,'3.5',26.1,7200,210,210),(122,'SATA',1000,'3.5',26.1,7200,150,150),(123,'SATA',4000,'3.5',26.1,7200,240,240);
/*!40000 ALTER TABLE `Hdd` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Motherboard`
--

DROP TABLE IF EXISTS `Motherboard`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Motherboard` (
  `idMotherboard` int NOT NULL,
  `formFactor` varchar(10) DEFAULT NULL COMMENT 'Форм-фактор',
  `chipset` varchar(4) DEFAULT NULL COMMENT 'Чипсет',
  `socket` varchar(7) DEFAULT NULL COMMENT 'Сокет',
  `maxRamFreq` smallint DEFAULT NULL COMMENT 'Максимальная частота ОЗУ',
  `maxRamCap` varchar(5) DEFAULT NULL COMMENT 'Максимальная объем памяти',
  `typeRam` varchar(4) DEFAULT NULL COMMENT 'Тип памяти',
  `qtyRam` tinyint DEFAULT NULL COMMENT 'Количество слотов памяти',
  `color` varchar(10) DEFAULT NULL COMMENT 'Цвет',
  PRIMARY KEY (`idMotherboard`),
  CONSTRAINT `idProductMotherboard` FOREIGN KEY (`idMotherboard`) REFERENCES `Product` (`idProduct`) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Motherboard`
--

LOCK TABLES `Motherboard` WRITE;
/*!40000 ALTER TABLE `Motherboard` DISABLE KEYS */;
INSERT INTO `Motherboard` VALUES (108,'ATX','Z590','LGA1200',5333,'128GB','DDR4',4,'Black'),(109,'ATX','B550','AM4',4400,'128GB','DDR4',4,'Black'),(110,'Micro-ATX','B450','AM4',3533,'64GB','DDR4',4,'Black'),(111,'ATX','Z490','LGA1200',4800,'128GB','DDR4',4,'Black');
/*!40000 ALTER TABLE `Motherboard` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `OrderDetails`
--

DROP TABLE IF EXISTS `OrderDetails`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `OrderDetails` (
  `idOrder` int NOT NULL AUTO_INCREMENT,
  `registrationDate` date NOT NULL,
  `status` enum('Checked out','On its way','Delivered','Received') NOT NULL,
  PRIMARY KEY (`idOrder`)
) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `OrderDetails`
--

LOCK TABLES `OrderDetails` WRITE;
/*!40000 ALTER TABLE `OrderDetails` DISABLE KEYS */;
INSERT INTO `OrderDetails` VALUES (63,'2024-12-26','Received');
/*!40000 ALTER TABLE `OrderDetails` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `OrderProduct`
--

DROP TABLE IF EXISTS `OrderProduct`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `OrderProduct` (
  `idOrder` int NOT NULL,
  `idProduct` int NOT NULL,
  `quantity` int NOT NULL,
  `price` double NOT NULL,
  PRIMARY KEY (`idOrder`,`idProduct`),
  KEY `idProduct_idx` (`idProduct`),
  CONSTRAINT `idOrderconnectProduct` FOREIGN KEY (`idOrder`) REFERENCES `OrderDetails` (`idOrder`) ON DELETE RESTRICT,
  CONSTRAINT `idProductconnectOrder` FOREIGN KEY (`idProduct`) REFERENCES `Product` (`idProduct`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `OrderProduct`
--

LOCK TABLES `OrderProduct` WRITE;
/*!40000 ALTER TABLE `OrderProduct` DISABLE KEYS */;
INSERT INTO `OrderProduct` VALUES (63,149,2,1499.99);
/*!40000 ALTER TABLE `OrderProduct` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `PC`
--

DROP TABLE IF EXISTS `PC`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `PC` (
  `idPC` int NOT NULL,
  `idMotherboard` int NOT NULL COMMENT 'Материнская плата',
  `idProcessor` int NOT NULL COMMENT 'Процессор',
  `idRam` int NOT NULL COMMENT 'Оперативная память',
  `idVideocard` int DEFAULT NULL COMMENT 'Видеокарта',
  `idSsd` int DEFAULT NULL COMMENT 'SSD',
  `idHdd` int DEFAULT NULL COMMENT 'HDD',
  `idCooling` int NOT NULL COMMENT 'Охлаждение',
  `idCase` int NOT NULL COMMENT 'Корпус',
  `idPowerUnit` int NOT NULL COMMENT 'Блок питания',
  PRIMARY KEY (`idPC`),
  KEY `idMotherboardPC_idx` (`idMotherboard`),
  KEY `idProcessorPC_idx` (`idProcessor`),
  KEY `idRamPC_idx` (`idRam`),
  KEY `idVideocardPC_idx` (`idVideocard`),
  KEY `idSsdPC_idx` (`idSsd`),
  KEY `idHdd_idx` (`idHdd`),
  KEY `idCoolingPC_idx` (`idCooling`),
  KEY `idCasePC_idx` (`idCase`),
  KEY `idPowerUnitPC_idx` (`idPowerUnit`),
  CONSTRAINT `idCasePC` FOREIGN KEY (`idCase`) REFERENCES `PCCase` (`idPCCase`) ON DELETE RESTRICT,
  CONSTRAINT `idCoolingPC` FOREIGN KEY (`idCooling`) REFERENCES `Cooling` (`idCooling`) ON DELETE RESTRICT,
  CONSTRAINT `idHdd` FOREIGN KEY (`idHdd`) REFERENCES `Hdd` (`idHdd`) ON DELETE RESTRICT,
  CONSTRAINT `idMotherboardPC` FOREIGN KEY (`idMotherboard`) REFERENCES `Motherboard` (`idMotherboard`) ON DELETE RESTRICT,
  CONSTRAINT `idPowerUnitPC` FOREIGN KEY (`idPowerUnit`) REFERENCES `PowerUnit` (`idPowerUnit`) ON DELETE RESTRICT,
  CONSTRAINT `idProcessorPC` FOREIGN KEY (`idProcessor`) REFERENCES `Processor` (`idProcessor`) ON DELETE RESTRICT,
  CONSTRAINT `idProductPC` FOREIGN KEY (`idPC`) REFERENCES `Product` (`idProduct`) ON DELETE RESTRICT,
  CONSTRAINT `idRamPC` FOREIGN KEY (`idRam`) REFERENCES `Ram` (`idRam`) ON DELETE RESTRICT,
  CONSTRAINT `idSsdPC` FOREIGN KEY (`idSsd`) REFERENCES `Ssd` (`idSsd`) ON DELETE RESTRICT,
  CONSTRAINT `idVideocardPC` FOREIGN KEY (`idVideocard`) REFERENCES `Videocard` (`idVideocard`) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PC`
--

LOCK TABLES `PC` WRITE;
/*!40000 ALTER TABLE `PC` DISABLE KEYS */;
INSERT INTO `PC` VALUES (148,108,104,112,144,140,120,118,125,136),(149,109,105,113,145,141,121,119,126,137),(150,110,106,114,146,142,122,116,127,138),(151,111,107,115,147,143,123,117,124,139);
/*!40000 ALTER TABLE `PC` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `PCCase`
--

DROP TABLE IF EXISTS `PCCase`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `PCCase` (
  `idPCCase` int NOT NULL,
  `depth` smallint DEFAULT NULL COMMENT 'Глубина',
  `length` smallint DEFAULT NULL COMMENT 'Длина',
  `width` smallint DEFAULT NULL COMMENT 'Ширина',
  `weight` float DEFAULT NULL COMMENT 'Вес',
  `maxLengthVideocard` float DEFAULT NULL COMMENT 'Максимальная длина видеокарты',
  `type` varchar(14) DEFAULT NULL COMMENT 'Тип корпуса',
  `powerUnitLocation` varchar(14) DEFAULT NULL COMMENT 'Расположение блока питания',
  `color` varchar(10) DEFAULT NULL COMMENT 'Цвет',
  PRIMARY KEY (`idPCCase`),
  CONSTRAINT `idProductCase` FOREIGN KEY (`idPCCase`) REFERENCES `Product` (`idProduct`) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PCCase`
--

LOCK TABLES `PCCase` WRITE;
/*!40000 ALTER TABLE `PCCase` DISABLE KEYS */;
INSERT INTO `PCCase` VALUES (124,428,210,460,6.8,381,'Mid Tower','Bottom','Black'),(125,453,230,466,7.8,400,'Mid Tower','Bottom','Black'),(126,470,220,465,7.5,435,'Mid Tower','Bottom','Black'),(127,450,232,443,7.7,369,'Mid Tower','Bottom','Black');
/*!40000 ALTER TABLE `PCCase` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Phone`
--

DROP TABLE IF EXISTS `Phone`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Phone` (
  `idPhone` int NOT NULL,
  `OS` varchar(7) DEFAULT NULL COMMENT 'ОС',
  `color` varchar(20) DEFAULT NULL,
  `cameraQty` tinyint DEFAULT NULL COMMENT 'Количество камер',
  `cameraMain` mediumint DEFAULT NULL COMMENT 'Основная камера',
  `cameraFront` mediumint DEFAULT NULL COMMENT 'Фронтальная камера',
  `screenSize` float DEFAULT NULL COMMENT 'Размер экрана',
  `screenResolution` varchar(9) DEFAULT NULL COMMENT 'Разрешение экрана',
  `screenTechnology` varchar(10) DEFAULT NULL COMMENT 'Технология экрана',
  `screenRefreshRate` mediumint DEFAULT NULL COMMENT 'Частота обновления экрана',
  `processor` varchar(35) DEFAULT NULL COMMENT 'Процессор',
  `ramQty` tinyint DEFAULT NULL COMMENT 'Количество ОЗУ',
  `romQty` mediumint DEFAULT NULL COMMENT 'Толщина встроенной памяти',
  `thickness` float DEFAULT NULL COMMENT 'Толщина',
  `length` float DEFAULT NULL COMMENT 'Длина',
  `width` float DEFAULT NULL COMMENT 'Ширина',
  `weight` float DEFAULT NULL COMMENT 'Вес',
  `chargingPower` mediumint DEFAULT NULL COMMENT 'Мощность зарядки',
  `protection` char(4) DEFAULT NULL COMMENT 'Защита',
  `batteryCapacity` mediumint DEFAULT NULL COMMENT 'Емкость аккумулятора',
  PRIMARY KEY (`idPhone`),
  CONSTRAINT `idPhoneProduct` FOREIGN KEY (`idPhone`) REFERENCES `Product` (`idProduct`) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Phone`
--

LOCK TABLES `Phone` WRITE;
/*!40000 ALTER TABLE `Phone` DISABLE KEYS */;
INSERT INTO `Phone` VALUES (132,'iOS','Midnight',3,48,12,6.1,'2532x1170','OLED',120,'A15 Bionic',6,128,7.65,146.7,71.5,174,20,'IP68',3095),(133,'Android','Phantom Black',4,108,40,6.8,'3200x1440','AMOLED 2X',120,'Exynos 2100',12,128,8.9,165.1,75.6,228,25,'IP68',5000),(134,'Android','Stormy Black',3,50,11,6.7,'3120x1440','OLED',120,'Google Tensor',12,128,8.9,163.9,75.9,210,23,'IP68',5000),(135,'Android','Ceramic White',3,50,20,6.81,'3200x1440','AMOLED',120,'Snapdragon 888',12,256,8.38,164.3,74.6,234,67,'IP68',5000);
/*!40000 ALTER TABLE `Phone` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `PowerUnit`
--

DROP TABLE IF EXISTS `PowerUnit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `PowerUnit` (
  `idPowerUnit` int NOT NULL,
  `formFactor` varchar(10) DEFAULT NULL COMMENT 'Форм-фактор',
  `power` smallint DEFAULT NULL COMMENT 'Мощность',
  `certificate80PLUS` varchar(8) DEFAULT NULL COMMENT 'Сертификат 80 PLUS',
  `load+12V` smallint DEFAULT NULL COMMENT 'Комбинированная нагрузка по +12V',
  `PCle6+2pin` tinyint DEFAULT NULL COMMENT 'PCle 6+2 pin',
  `PCle6pin` tinyint DEFAULT NULL COMMENT 'PCle 6 pin',
  `PCle8pin` tinyint DEFAULT NULL COMMENT 'PCle 8 pin',
  `SATA` tinyint DEFAULT NULL COMMENT 'SATA',
  `color` varchar(10) DEFAULT NULL COMMENT 'Цвет',
  PRIMARY KEY (`idPowerUnit`),
  CONSTRAINT `idProductPowerUnit` FOREIGN KEY (`idPowerUnit`) REFERENCES `Product` (`idProduct`) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PowerUnit`
--

LOCK TABLES `PowerUnit` WRITE;
/*!40000 ALTER TABLE `PowerUnit` DISABLE KEYS */;
INSERT INTO `PowerUnit` VALUES (136,'ATX',850,'Gold',840,4,0,2,10,'Black'),(137,'ATX',750,'Bronze',744,4,0,2,8,'Black'),(138,'ATX',750,'Gold',744,4,0,2,10,'Black'),(139,'ATX',750,'Gold',744,4,0,2,8,'Black');
/*!40000 ALTER TABLE `PowerUnit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Processor`
--

DROP TABLE IF EXISTS `Processor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Processor` (
  `idProcessor` int NOT NULL,
  `socket` varchar(7) DEFAULT NULL COMMENT 'Сокет',
  `coreQty` mediumint DEFAULT NULL COMMENT 'Количество ядер',
  `threadQty` mediumint DEFAULT NULL COMMENT 'Количество потоков',
  `baseClockFreq` float DEFAULT NULL COMMENT 'Базовая тактовая частота',
  `maxClockFreq` float DEFAULT NULL COMMENT 'Максимальная частота',
  `L2` float DEFAULT NULL COMMENT 'Кэш L2',
  `L3` float DEFAULT NULL COMMENT 'Кэш L3',
  `typeDelivery` char(3) DEFAULT NULL COMMENT 'Тип подставки',
  `graphics` varchar(22) DEFAULT NULL COMMENT 'Встроенная графика',
  PRIMARY KEY (`idProcessor`),
  CONSTRAINT `idProductProcessor` FOREIGN KEY (`idProcessor`) REFERENCES `Product` (`idProduct`) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Processor`
--

LOCK TABLES `Processor` WRITE;
/*!40000 ALTER TABLE `Processor` DISABLE KEYS */;
INSERT INTO `Processor` VALUES (104,'LGA1200',8,16,3.6,5.2,2,16,'BOX','Intel UHD 750'),(105,'AM4',12,24,3.7,4.8,6,64,'OEM',NULL),(106,'LGA1200',8,16,3.8,5.1,2,16,'BOX','Intel UHD 750'),(107,'AM4',8,16,3.8,4.7,4,32,'OEM',NULL);
/*!40000 ALTER TABLE `Processor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Product`
--

DROP TABLE IF EXISTS `Product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Product` (
  `idProduct` int NOT NULL AUTO_INCREMENT,
  `brand` varchar(25) NOT NULL COMMENT 'Бренд',
  `model` varchar(45) NOT NULL COMMENT 'Модель',
  `type` enum('Processor','Motherboard','Ram','Videocard','Hdd','Ssd','Cooling','PCCase','PowerUnit','Phone','PC') NOT NULL COMMENT 'Категория',
  `releaseDate` date NOT NULL COMMENT 'Дата выхода',
  `price` double NOT NULL COMMENT 'Цена',
  `inStock` smallint NOT NULL COMMENT 'Количество',
  `imageProduct` varchar(200) DEFAULT NULL,
  `idDiscount` int DEFAULT NULL,
  PRIMARY KEY (`idProduct`),
  KEY `FK_Product_Discount` (`idDiscount`),
  CONSTRAINT `FK_Product_Discount` FOREIGN KEY (`idDiscount`) REFERENCES `Discount` (`idDiscount`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=152 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Product`
--

LOCK TABLES `Product` WRITE;
/*!40000 ALTER TABLE `Product` DISABLE KEYS */;
INSERT INTO `Product` VALUES (104,'Intel','Core i9-13900K','Processor','2023-09-01',599.99,50,'https://256bit.by/upload/resize_cache/iblock/636/450_450_140cd750bba9870f18aada2478b24840a/p01xztjtd6igwnty91joc9ei70bnuff6.jpeg',NULL),(105,'AMD','Ryzen 9 5900X','Processor','2021-01-12',449.99,30,'https://256bit.by/upload/resize_cache/iblock/72d/450_450_140cd750bba9870f18aada2478b24840a/36fanf7kq0shem93x1t5cn3o26s2kdvh.jpeg',NULL),(106,'Intel','Core i7-13700K','Processor','2023-06-15',399.99,45,'https://256bit.by/upload/resize_cache/iblock/dee/450_450_140cd750bba9870f18aada2478b24840a/b6iqsfgm1seulc3d7jjvlasxsmkgqean.jpeg',NULL),(107,'AMD','Ryzen 7 5800X','Processor','2021-03-15',349.99,20,'https://256bit.by/upload/resize_cache/iblock/72d/450_450_140cd750bba9870f18aada2478b24840a/36fanf7kq0shem93x1t5cn3o26s2kdvh.jpeg',NULL),(108,'ASUS','ROG Strix Z590-E Gaming','Motherboard','2023-03-01',299.99,20,'https://256bit.by/upload/resize_cache/iblock/7f9/450_450_140cd750bba9870f18aada2478b24840a/wf1hu716ln3yc4ydw2hg1r8umg5f91e7.jpeg',NULL),(109,'MSI','MPG B550 Gaming Edge WiFi','Motherboard','2023-04-15',199.99,30,'https://256bit.by/upload/resize_cache/iblock/551/450_450_140cd750bba9870f18aada2478b24840a/vdrngf4oqyx1pm8t60cy558kjcfi4zp4.jpeg',NULL),(110,'ASRock','B450M Steel Legend','Motherboard','2023-02-20',99.99,25,'https://256bit.by/upload/resize_cache/iblock/f5a/450_450_140cd750bba9870f18aada2478b24840a/c7785r8qap98sbhzngagzyxggen5j30s.jpeg',NULL),(111,'MSI','Z490-A PRO','Motherboard','2023-07-10',149.99,35,'https://256bit.by/upload/resize_cache/iblock/d02/450_450_140cd750bba9870f18aada2478b24840a/zaqvmyebpr1mybo1e5asvnhgdu2p1pf8.jpeg',NULL),(112,'Corsair','Vengeance LPX 16GB','Ram','2023-01-15',89.99,50,'https://256bit.by/upload/resize_cache/iblock/137/450_450_140cd750bba9870f18aada2478b24840a/te3bfrhmpbqbgaty45exbp83v8egj85k.jpeg',NULL),(113,'G.Skill','Ripjaws V 32GB','Ram','2023-02-10',159.99,40,'https://256bit.by/upload/resize_cache/iblock/1dc/450_450_140cd750bba9870f18aada2478b24840a/t8g1xbo45ikawqupag6g9mp3jlx6xz8s.jpeg',NULL),(114,'Kingston','HyperX Fury 16GB','Ram','2023-03-05',79.99,60,'https://256bit.by/upload/resize_cache/iblock/1de/450_450_140cd750bba9870f18aada2478b24840a/poo5y9c3wg3nku735y4nwwypmxhlllp4.jpeg',NULL),(115,'Crucial','Ballistix 16GB','Ram','2023-04-01',84.99,55,'https://256bit.by/upload/resize_cache/iblock/832/450_450_140cd750bba9870f18aada2478b24840a/z4o6j3obarcvddtbevx6qhijql35cr4j.jpeg',NULL),(116,'NZXT','Kraken X53','Cooling','2023-04-15',159.99,20,'https://256bit.by/upload/resize_cache/iblock/ce6/450_450_140cd750bba9870f18aada2478b24840a/49j4e4cl02e33aaq3lgele6l5763wiw2.jpeg',NULL),(117,'be quiet!','Dark Rock Pro 4','Cooling','2023-05-01',89.99,35,'https://256bit.by/upload/resize_cache/iblock/f88/450_450_140cd750bba9870f18aada2478b24840a/arcsgavdubofny3y52bk00wvchqqrp2p.jpeg',NULL),(118,'Arctic','Liquid Freezer III 360','Cooling','2023-07-05',49.99,40,'https://256bit.by/upload/resize_cache/iblock/973/450_450_140cd750bba9870f18aada2478b24840a/pbhuw03kh1n9am0r8wwvr321fmjct37c.jpg',NULL),(119,'Deepcool','Gammaxx 400 V2','Cooling','2023-08-20',29.99,50,'https://256bit.by/upload/resize_cache/iblock/853/450_450_140cd750bba9870f18aada2478b24840a/xsdzd2u2fxw4dme1cl8567t22w5sm9e1.jpg',NULL),(120,'Western Digital','WD Blue 1TB','Hdd','2023-01-15',49.99,100,'https://256bit.by/upload/resize_cache/iblock/d2e/450_450_140cd750bba9870f18aada2478b24840a/mjvhtixqckyeqluy65gfsibmh2llc7b8.jpg',NULL),(121,'Seagate','Barracuda 2TB','Hdd','2023-02-10',79.99,80,'https://256bit.by/upload/resize_cache/iblock/834/450_450_140cd750bba9870f18aada2478b24840a/vjk4yavj1a6ojdon113whbu2lhtke4aw.jpeg',NULL),(122,'Toshiba','P300 1TB','Hdd','2023-03-05',45.99,60,'https://256bit.by/upload/resize_cache/iblock/658/450_450_140cd750bba9870f18aada2478b24840a/gn2uv32mut3jadv7eq1bqxgcoxvvzbcn.jpg',NULL),(123,'Toshiba','X300 4TB','Hdd','2023-07-10',119.99,45,'https://256bit.by/upload/resize_cache/iblock/287/450_450_140cd750bba9870f18aada2478b24840a/20kg9yjhqd4v7s197hu0z36kkh9fgwz3.jpeg',NULL),(124,'NZXT','H510','PCCase','2023-03-01',69.99,25,'https://256bit.by/upload/resize_cache/iblock/8c9/450_450_140cd750bba9870f18aada2478b24840a/p88arti3vspw8ojbsghgdob37tkxyp2s.jpeg',NULL),(125,'Corsair','iCUE 4000D RGB','PCCase','2023-05-10',129.99,20,'https://256bit.by/upload/resize_cache/iblock/796/450_450_140cd750bba9870f18aada2478b24840a/yl8pcvjk3bczcdt0nsuo2njbhttpdov3.jpeg',NULL),(126,'Phanteks','Eclipse G500A','PCCase','2023-07-20',79.99,18,'https://256bit.by/upload/resize_cache/iblock/d60/450_450_140cd750bba9870f18aada2478b24840a/2t2kfkfp3jqp1298mbwr3xta3wh0ccbp.jpeg',NULL),(127,'Be Quiet!','Pure Base 500DX','PCCase','2023-10-15',109.99,20,'https://256bit.by/upload/resize_cache/iblock/fef/450_450_140cd750bba9870f18aada2478b24840a/oktf9ragcas0gcg80lmhz252gty8hiyo.jpeg',NULL),(132,'Apple','iPhone 14','Phone','2023-09-20',999.99,50,'https://img.5element.by/import/images/ut/goods/good_d9c88da3-803a-11ed-bb97-0050560120e8/iphone-14-128gb-midnight-telefon-gsm-apple-mpuf3hn-a-1_600.jpg',NULL),(133,'Samsung','Galaxy S21 Ultra','Phone','2023-01-29',849.99,40,'https://img.5element.by/import/images/ut/goods/good_7bdd4945-5682-11eb-bb92-0050560120e8/sm-g998b-chern-fantom-128gb-telefon-gsm-samsung-galaxy-s21-ultra-1_600.jpg',NULL),(134,'Google','Pixel 7 Pro','Phone','2023-05-17',899.99,30,'https://c.dns-shop.ru/thumb/st4/fit/500/500/a45236027ed9d7bcc6f5725bc63eb250/ff9f338724b9e863fb84bfc50d54b74146e1b315a174e9110e5ab879b1a3cc78.jpg.webp',NULL),(135,'Xiaomi','Mi 11 Ultra','Phone','2023-04-05',749.99,25,'https://xistore.by/upload/resize/element/62678/char/f0e/ad6344a4997d6242aba9c0deac6b980b_490_490_85.webp',NULL),(136,'Corsair','RM850e','PowerUnit','2023-01-15',139.99,40,'https://256bit.by/upload/resize_cache/iblock/592/450_450_140cd750bba9870f18aada2478b24840a/dr0wz5tphh82h3bdcg0br4rnbfmw25gs.jpeg',NULL),(137,'Chieftec','Proton','PowerUnit','2023-02-10',129.99,35,'https://256bit.by/upload/resize_cache/iblock/65d/450_450_140cd750bba9870f18aada2478b24840a/y7vh8fgjxo4ql9ym0zrzij5vx02128es.jpeg',NULL),(138,'Be Quiet!','Straight Power 11 750W','PowerUnit','2023-06-15',159.99,20,'https://256bit.by/upload/resize_cache/iblock/0f2/450_450_140cd750bba9870f18aada2478b24840a/yr0loy192izc61ixtrwm9m963e0o5v7y.jpeg',NULL),(139,'Gigabyte','Aorus P750W','PowerUnit','2023-08-05',109.99,50,'https://256bit.by/upload/resize_cache/iblock/edc/450_450_140cd750bba9870f18aada2478b24840a/0fy86bsttmxlmcvcq2e04caqkjp4mr5z.jpeg',NULL),(140,'Samsung','970 EVO Plus 1TB','Ssd','2023-01-15',149.99,50,'https://256bit.by/upload/resize_cache/iblock/bc4/450_450_140cd750bba9870f18aada2478b24840a/7oo2jgbcbug95v8ihy9vg59vz3ork4p8.jpeg',NULL),(141,'Western Digital','WD Blue SN550 1TB','Ssd','2023-02-10',129.99,45,'https://256bit.by/upload/resize_cache/iblock/24a/450_450_140cd750bba9870f18aada2478b24840a/a4mx00oukpjlgs9qr8i5qiapaud94thn.jpeg',NULL),(142,'Kingston','A2000 1TB','Ssd','2023-04-01',119.99,55,'https://256bit.by/upload/resize_cache/iblock/e0b/450_450_140cd750bba9870f18aada2478b24840a/0heu30965o9kfdplff29pwsoles5lfmh.jpeg',NULL),(143,'Seagate','FireCuda 520 1TB','Ssd','2023-05-20',139.99,40,'https://256bit.by/upload/resize_cache/iblock/2be/450_450_140cd750bba9870f18aada2478b24840a/5ddv99ab54f4hqpw7nyo80ckvnu391f5.jpeg',NULL),(144,'PowerColor','Radeon RX 6800 XT','Videocard','2023-02-15',649.99,25,'https://256bit.by/upload/resize_cache/iblock/697/450_450_140cd750bba9870f18aada2478b24840a/72rlwfsnk01i66fg3hau3kx2f4djb33u.jpg',NULL),(145,'MSI','GeForce RTX 3060 Gaming X 12G','Videocard','2023-03-12',399.99,30,'https://256bit.by/upload/resize_cache/iblock/4bc/450_450_140cd750bba9870f18aada2478b24840a/gwp001mld8r47pkl1j8m47hyskj3wzgp.jpg',NULL),(146,'Sapphire','Radeon RX 6700 XT OC','Videocard','2023-04-20',479.99,22,'https://256bit.by/upload/resize_cache/iblock/b4e/450_450_140cd750bba9870f18aada2478b24840a/cpjfgdy51phgwhocb626wg0htggkm8r1.jpg',NULL),(147,'Gigabyte','GeForce RTX 3050 Windforce OC','Videocard','2023-05-18',499.99,28,'https://256bit.by/upload/resize_cache/iblock/80f/450_450_140cd750bba9870f18aada2478b24840a/ozoeptpozu1ma1djv70yyu57ox7cswfk.jpg',NULL),(148,'TechMakers','PowerBox GX','PC','2023-02-01',1299.99,15,'https://256bit.by/upload/resize_cache/iblock/796/450_450_140cd750bba9870f18aada2478b24840a/yl8pcvjk3bczcdt0nsuo2njbhttpdov3.jpeg',NULL),(149,'ByteCity','One','PC','2023-04-15',1499.99,18,'https://256bit.by/upload/resize_cache/iblock/8c9/450_450_140cd750bba9870f18aada2478b24840a/p88arti3vspw8ojbsghgdob37tkxyp2s.jpeg',NULL),(150,'MegaByte Systems','Ultra Performance','PC','2023-05-25',1099.99,25,'https://256bit.by/upload/resize_cache/iblock/d60/450_450_140cd750bba9870f18aada2478b24840a/2t2kfkfp3jqp1298mbwr3xta3wh0ccbp.jpeg',NULL),(151,'Quantum Computers','Extreme Build','PC','2023-08-08',1699.99,14,'https://256bit.by/upload/resize_cache/iblock/fef/450_450_140cd750bba9870f18aada2478b24840a/oktf9ragcas0gcg80lmhz252gty8hiyo.jpeg',NULL);
/*!40000 ALTER TABLE `Product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Ram`
--

DROP TABLE IF EXISTS `Ram`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Ram` (
  `idRam` int NOT NULL,
  `type` varchar(4) DEFAULT NULL COMMENT 'Тип памяти',
  `totalVolume` varchar(5) DEFAULT NULL COMMENT 'Общий объем',
  `freq` smallint DEFAULT NULL COMMENT 'Частота',
  `qty` tinyint DEFAULT NULL COMMENT 'Количество модулей',
  `color` varchar(10) DEFAULT NULL COMMENT 'Цвет',
  PRIMARY KEY (`idRam`),
  CONSTRAINT `idProductRam` FOREIGN KEY (`idRam`) REFERENCES `Product` (`idProduct`) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Ram`
--

LOCK TABLES `Ram` WRITE;
/*!40000 ALTER TABLE `Ram` DISABLE KEYS */;
INSERT INTO `Ram` VALUES (112,'DDR4','16GB',3200,2,'Black'),(113,'DDR4','32GB',3600,2,'Black'),(114,'DDR4','16GB',3200,2,'Black'),(115,'DDR4','16GB',3600,2,'Black');
/*!40000 ALTER TABLE `Ram` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Review`
--

DROP TABLE IF EXISTS `Review`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Review` (
  `idProduct` int NOT NULL,
  `idUser` int NOT NULL,
  `rating` float NOT NULL,
  `review` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`idProduct`,`idUser`),
  KEY `idProduct_idx` (`idProduct`),
  KEY `idUser_idx` (`idUser`),
  CONSTRAINT `idProductReview` FOREIGN KEY (`idProduct`) REFERENCES `Product` (`idProduct`) ON DELETE CASCADE,
  CONSTRAINT `idUserReview` FOREIGN KEY (`idUser`) REFERENCES `User` (`idUser`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Review`
--

LOCK TABLES `Review` WRITE;
/*!40000 ALTER TABLE `Review` DISABLE KEYS */;
INSERT INTO `Review` VALUES (149,7,3,'I like it! But too expensive)');
/*!40000 ALTER TABLE `Review` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Ssd`
--

DROP TABLE IF EXISTS `Ssd`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Ssd` (
  `idSsd` int NOT NULL,
  `interface` varchar(4) DEFAULT NULL COMMENT 'Интерфейс',
  `volume` smallint DEFAULT NULL COMMENT 'Объем',
  `formFactor` varchar(5) DEFAULT NULL COMMENT 'Форм-фактор',
  `mtbf` int DEFAULT NULL COMMENT 'Время наработки на отказ',
  `cooling` char(4) DEFAULT NULL COMMENT 'Охлаждение',
  `seqReadSpeed` mediumint DEFAULT NULL COMMENT 'Скорость последовательного чтения',
  `seqWriteSpeed` mediumint DEFAULT NULL COMMENT 'Скорость последовательной записи',
  `averageRandomReadSpeed` mediumint DEFAULT NULL COMMENT 'Средння скорость случайного чтения',
  `averageRandomWriteSpeed` mediumint DEFAULT NULL COMMENT 'Средння скорость случайной записи',
  PRIMARY KEY (`idSsd`),
  CONSTRAINT `idProductSsd` FOREIGN KEY (`idSsd`) REFERENCES `Product` (`idProduct`) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Ssd`
--

LOCK TABLES `Ssd` WRITE;
/*!40000 ALTER TABLE `Ssd` DISABLE KEYS */;
INSERT INTO `Ssd` VALUES (140,'NVMe',1000,'M.2',1500000,'None',3500,3300,500000,480000),(141,'NVMe',1000,'M.2',1700000,'None',2400,1950,410000,405000),(142,'NVMe',1000,'M.2',2000000,'None',2200,2000,350000,310000),(143,'NVMe',1000,'M.2',1800000,'None',5000,4400,760000,710000);
/*!40000 ALTER TABLE `Ssd` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `User`
--

DROP TABLE IF EXISTS `User`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `User` (
  `idUser` int NOT NULL AUTO_INCREMENT,
  `login` varchar(20) NOT NULL,
  `password` char(64) NOT NULL,
  `email` varchar(40) NOT NULL,
  `contactNumber` char(13) NOT NULL,
  PRIMARY KEY (`idUser`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `User`
--

LOCK TABLES `User` WRITE;
/*!40000 ALTER TABLE `User` DISABLE KEYS */;
INSERT INTO `User` VALUES (6,'F0r41n','17756315ebd47b7110359fc7b168179bf6f2df3646fcc888bc8aa05c78b38ac1','dovi@gmail.com','+375292391658'),(7,'Zakhar','ef797c8118f02dfb649607dd5d3f8c7623048c9c063d532cc95c5ed7a898a64f','dovi1@gmail.com','+375201231231');
/*!40000 ALTER TABLE `User` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `UserOrder`
--

DROP TABLE IF EXISTS `UserOrder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `UserOrder` (
  `idOrder` int NOT NULL,
  `idUser` int NOT NULL,
  PRIMARY KEY (`idOrder`,`idUser`),
  KEY `idUser_idx` (`idUser`),
  CONSTRAINT `idOrderConnectUser` FOREIGN KEY (`idOrder`) REFERENCES `OrderDetails` (`idOrder`) ON DELETE RESTRICT,
  CONSTRAINT `idUserConnectOrder` FOREIGN KEY (`idUser`) REFERENCES `User` (`idUser`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `UserOrder`
--

LOCK TABLES `UserOrder` WRITE;
/*!40000 ALTER TABLE `UserOrder` DISABLE KEYS */;
INSERT INTO `UserOrder` VALUES (63,7);
/*!40000 ALTER TABLE `UserOrder` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Videocard`
--

DROP TABLE IF EXISTS `Videocard`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Videocard` (
  `idVideocard` int NOT NULL,
  `numStreamProc` smallint DEFAULT NULL COMMENT 'Количество потоковых процессоров',
  `numRtProc` smallint DEFAULT NULL COMMENT 'Количество RT-ядер',
  `baseFreq` smallint DEFAULT NULL COMMENT 'Базовая частота графического прроцессора',
  `maxFreq` smallint DEFAULT NULL COMMENT 'Максимальная частота графического прроцессора',
  `memType` varchar(7) DEFAULT NULL COMMENT 'Тип видеопамяти',
  `memQty` smallint DEFAULT NULL COMMENT 'Количество памяти',
  `memFreq` mediumint DEFAULT NULL COMMENT 'Частота памяти',
  `memBusWidth` smallint DEFAULT NULL COMMENT 'Ширина шины памяти',
  `energyCons` smallint DEFAULT NULL COMMENT 'Энергопотребление',
  `powerConn` varchar(8) DEFAULT NULL,
  `thickness` float DEFAULT NULL COMMENT 'Толщина',
  `length` float DEFAULT NULL COMMENT 'Длина',
  `width` float DEFAULT NULL COMMENT 'Ширина',
  `color` varchar(10) DEFAULT NULL COMMENT 'Цвет',
  PRIMARY KEY (`idVideocard`),
  CONSTRAINT `idProductVideocard` FOREIGN KEY (`idVideocard`) REFERENCES `Product` (`idProduct`) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Videocard`
--

LOCK TABLES `Videocard` WRITE;
/*!40000 ALTER TABLE `Videocard` DISABLE KEYS */;
INSERT INTO `Videocard` VALUES (144,4608,72,1825,2250,'GDDR6',16,16000,256,300,'8-pin x2',2,267,120,'Red'),(145,3584,48,1320,1777,'GDDR6',12,15000,192,170,'8-pin x1',2,285,112,'Black'),(146,2560,40,2321,2581,'GDDR6',12,16000,192,230,'8-pin x1',2,267,120,'Black'),(147,2560,40,1552,1777,'GDDR6',8,14000,128,130,'8-pin x1',2,282,140,'Black');
/*!40000 ALTER TABLE `Videocard` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `comments`
--

DROP TABLE IF EXISTS `comments`;
/*!50001 DROP VIEW IF EXISTS `comments`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `comments` AS SELECT 
 1 AS `column_name`,
 1 AS `column_comment`,
 1 AS `table_name`*/;
SET character_set_client = @saved_cs_client;

--
-- Final view structure for view `comments`
--

/*!50001 DROP VIEW IF EXISTS `comments`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `comments` AS select `information_schema`.`columns`.`COLUMN_NAME` AS `column_name`,`information_schema`.`columns`.`COLUMN_COMMENT` AS `column_comment`,`information_schema`.`columns`.`TABLE_NAME` AS `table_name` from `information_schema`.`COLUMNS` `columns` where (`information_schema`.`columns`.`TABLE_SCHEMA` = 'ByteCity') */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-12-26 14:35:37
