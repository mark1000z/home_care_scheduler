CREATE DATABASE  IF NOT EXISTS `home_care_services_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `home_care_services_db`;
-- MySQL dump 10.13  Distrib 8.0.21, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: home_care_services_db
-- ------------------------------------------------------
-- Server version	8.0.23

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `client`
--

DROP TABLE IF EXISTS `client`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `client` (
  `client_id` int NOT NULL,
  `client_name` varchar(100) NOT NULL,
  `client_skill` varchar(45) NOT NULL,
  PRIMARY KEY (`client_id`,`client_name`,`client_skill`),
  UNIQUE KEY `unique_client` (`client_id`,`client_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `client`
--

LOCK TABLES `client` WRITE;
/*!40000 ALTER TABLE `client` DISABLE KEYS */;
INSERT INTO `client` VALUES (1,'山田太郎','1'),(2,'佐々木次郎','2'),(3,'新宿太郎','3');
/*!40000 ALTER TABLE `client` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `client_requests`
--

DROP TABLE IF EXISTS `client_requests`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `client_requests` (
  `id` int NOT NULL AUTO_INCREMENT,
  `client_id` varchar(50) NOT NULL,
  `client_name` varchar(100) NOT NULL,
  `day_of_week` varchar(10) NOT NULL,
  `shift` varchar(10) NOT NULL,
  `client_skill` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_request` (`client_name`,`day_of_week`,`shift`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `client_requests`
--

LOCK TABLES `client_requests` WRITE;
/*!40000 ALTER TABLE `client_requests` DISABLE KEYS */;
INSERT INTO `client_requests` VALUES (20,'1','山田太郎','月曜日','朝','1'),(21,'1','山田太郎','火曜日','朝','1'),(22,'1','山田太郎','水曜日','昼','1'),(23,'1','山田太郎','木曜日','昼','1'),(24,'1','山田太郎','金曜日','夕','1'),(25,'1','山田太郎','土曜日','夕','1'),(26,'1','山田太郎','日曜日','夕','1'),(27,'3','新宿太郎','月曜日','朝','3'),(28,'3','新宿太郎','火曜日','朝','3');
/*!40000 ALTER TABLE `client_requests` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `matching_results`
--

DROP TABLE IF EXISTS `matching_results`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `matching_results` (
  `id` int NOT NULL AUTO_INCREMENT,
  `staff_name` varchar(255) NOT NULL,
  `client_name` varchar(255) NOT NULL,
  `day_of_week` varchar(20) NOT NULL,
  `shift` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_matching` (`staff_name`,`day_of_week`,`shift`),
  UNIQUE KEY `unique_matching_client` (`client_name`,`day_of_week`,`shift`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `matching_results`
--

LOCK TABLES `matching_results` WRITE;
/*!40000 ALTER TABLE `matching_results` DISABLE KEYS */;
/*!40000 ALTER TABLE `matching_results` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `staff`
--

DROP TABLE IF EXISTS `staff`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `staff` (
  `staff_id` int NOT NULL,
  `staff_name` varchar(100) NOT NULL,
  `staff_skill` int NOT NULL,
  PRIMARY KEY (`staff_id`,`staff_name`,`staff_skill`),
  UNIQUE KEY `unique_staff` (`staff_id`,`staff_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `staff`
--

LOCK TABLES `staff` WRITE;
/*!40000 ALTER TABLE `staff` DISABLE KEYS */;
INSERT INTO `staff` VALUES (1,'南野拓実',1),(2,'遠藤航',2),(3,'岡崎真治　',3);
/*!40000 ALTER TABLE `staff` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `staff_schedules`
--

DROP TABLE IF EXISTS `staff_schedules`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `staff_schedules` (
  `id` int NOT NULL AUTO_INCREMENT,
  `staff_id` varchar(50) NOT NULL,
  `staff_name` varchar(100) NOT NULL,
  `day_of_week` varchar(10) NOT NULL,
  `shift` varchar(10) NOT NULL,
  `staff_skill` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_schedule` (`staff_name`,`day_of_week`,`shift`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `staff_schedules`
--

LOCK TABLES `staff_schedules` WRITE;
/*!40000 ALTER TABLE `staff_schedules` DISABLE KEYS */;
INSERT INTO `staff_schedules` VALUES (5,'1','南野拓実','月曜日','夕','1'),(7,'1','南野拓実','日曜日','夕','1'),(9,'1','南野拓実','日曜日','朝','1'),(13,'1','南野拓実','木曜日','夕','1'),(17,'1','南野拓実','土曜日','朝','1'),(29,'1','南野拓実','水曜日','朝','1'),(31,'1','南野拓実','月曜日','昼','1'),(34,'1','南野拓実','火曜日','昼','1'),(36,'1','南野拓実','水曜日','昼','1'),(38,'1','南野拓実','木曜日','昼','1'),(40,'1','南野拓実','月曜日','朝','1'),(42,'1','南野拓実','金曜日','昼','1'),(44,'3','岡崎真治　','月曜日','朝','3'),(45,'3','岡崎真治　','火曜日','朝','3');
/*!40000 ALTER TABLE `staff_schedules` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users_login`
--

DROP TABLE IF EXISTS `users_login`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users_login` (
  `id` int NOT NULL AUTO_INCREMENT,
  `login_id` varchar(45) NOT NULL,
  `login_pass` varchar(70) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users_login`
--

LOCK TABLES `users_login` WRITE;
/*!40000 ALTER TABLE `users_login` DISABLE KEYS */;
INSERT INTO `users_login` VALUES (1,'taro','$2a$08$T/o.pVAO8/Qkxdtimu5xXujv7XOfu3xGBZNxD.S2L7eTXnaFPYSQq'),(2,'yamada','$2a$10$iskRbwwL416IyMUVCd6oluhdn923VmTX0FvvrzVY6TQ3JVdLY6riq');
/*!40000 ALTER TABLE `users_login` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-04-04 16:50:49
