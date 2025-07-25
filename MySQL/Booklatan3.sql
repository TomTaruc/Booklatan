-- MySQL dump 10.13  Distrib 8.0.42, for Win64 (x86_64)
--
-- Host: localhost    Database: booklatan
-- ------------------------------------------------------
-- Server version	8.0.42

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
-- Table structure for table `author`
--

DROP TABLE IF EXISTS `author`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `author` (
  `authorID` int NOT NULL AUTO_INCREMENT,
  `name` varchar(500) NOT NULL,
  `bio` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`authorID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `author`
--

LOCK TABLES `author` WRITE;
/*!40000 ALTER TABLE `author` DISABLE KEYS */;
/*!40000 ALTER TABLE `author` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `book`
--

DROP TABLE IF EXISTS `book`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `book` (
  `bookID` int NOT NULL AUTO_INCREMENT,
  `title` varchar(500) NOT NULL,
  `category` varchar(255) NOT NULL,
  `pubDate` date NOT NULL,
  `lang` varchar(255) NOT NULL,
  `_status` enum('loaned','available','not_available','reserved') DEFAULT 'not_available',
  `shelfLocation` varchar(255) DEFAULT NULL,
  `pubID` int NOT NULL,
  `libID` int NOT NULL,
  PRIMARY KEY (`bookID`),
  KEY `pubID` (`pubID`),
  KEY `libID` (`libID`),
  CONSTRAINT `book_ibfk_1` FOREIGN KEY (`pubID`) REFERENCES `publisher` (`pubID`),
  CONSTRAINT `book_ibfk_2` FOREIGN KEY (`libID`) REFERENCES `library` (`libID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `book`
--

LOCK TABLES `book` WRITE;
/*!40000 ALTER TABLE `book` DISABLE KEYS */;
/*!40000 ALTER TABLE `book` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bookauthor`
--

DROP TABLE IF EXISTS `bookauthor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bookauthor` (
  `bookID` int NOT NULL,
  `authorID` int NOT NULL,
  PRIMARY KEY (`bookID`,`authorID`),
  KEY `authorID` (`authorID`),
  CONSTRAINT `bookauthor_ibfk_1` FOREIGN KEY (`bookID`) REFERENCES `book` (`bookID`),
  CONSTRAINT `bookauthor_ibfk_2` FOREIGN KEY (`authorID`) REFERENCES `author` (`authorID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bookauthor`
--

LOCK TABLES `bookauthor` WRITE;
/*!40000 ALTER TABLE `bookauthor` DISABLE KEYS */;
/*!40000 ALTER TABLE `bookauthor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fine`
--

DROP TABLE IF EXISTS `fine`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fine` (
  `fineID` int NOT NULL AUTO_INCREMENT,
  `staffID` int NOT NULL,
  `memberID` int NOT NULL,
  `amount` decimal(50,2) NOT NULL,
  `dateIssued` date NOT NULL,
  `_status` enum('paid','pending') DEFAULT NULL,
  `paid_date` date DEFAULT NULL,
  `description` varchar(500) DEFAULT NULL,
  `due_date` date DEFAULT NULL,
  PRIMARY KEY (`fineID`),
  KEY `memberID` (`memberID`),
  KEY `staffID` (`staffID`),
  CONSTRAINT `fine_ibfk_1` FOREIGN KEY (`memberID`) REFERENCES `member` (`memberID`),
  CONSTRAINT `fine_ibfk_2` FOREIGN KEY (`staffID`) REFERENCES `staff` (`staffID`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fine`
--

LOCK TABLES `fine` WRITE;
/*!40000 ALTER TABLE `fine` DISABLE KEYS */;
INSERT INTO `fine` VALUES (1,1001,1,10000.00,'2025-07-22','paid','2025-07-23','Damaged','2025-07-22'),(4,1001,1,1000.00,'2025-07-22','pending',NULL,'Broken Recond','2025-07-22'),(5,1001,4,1000.00,'2025-07-23','paid','2025-07-23','Overdue book','2025-08-25'),(6,1001,5,10000.00,'2025-07-23','paid','2025-07-23','Damaged Book','2026-07-24'),(7,1001,1,10000.00,'2025-07-23','pending',NULL,'Damaged','2025-07-23');
/*!40000 ALTER TABLE `fine` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `finemember`
--

DROP TABLE IF EXISTS `finemember`;
/*!50001 DROP VIEW IF EXISTS `finemember`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `finemember` AS SELECT 
 1 AS `fineID`,
 1 AS `staffID`,
 1 AS `memberID`,
 1 AS `amount`,
 1 AS `dateIssued`,
 1 AS `_status`,
 1 AS `paid_date`,
 1 AS `description`,
 1 AS `due_date`,
 1 AS `name`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `library`
--

DROP TABLE IF EXISTS `library`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `library` (
  `libID` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `address` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `phone` varchar(255) NOT NULL,
  PRIMARY KEY (`libID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `library`
--

LOCK TABLES `library` WRITE;
/*!40000 ALTER TABLE `library` DISABLE KEYS */;
/*!40000 ALTER TABLE `library` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `loan`
--

DROP TABLE IF EXISTS `loan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `loan` (
  `loanID` int NOT NULL AUTO_INCREMENT,
  `issueDate` date NOT NULL,
  `dueDate` date NOT NULL,
  `returnDate` date DEFAULT NULL,
  `memberID` int NOT NULL,
  PRIMARY KEY (`loanID`),
  KEY `memberID` (`memberID`),
  CONSTRAINT `loan_ibfk_1` FOREIGN KEY (`memberID`) REFERENCES `member` (`memberID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `loan`
--

LOCK TABLES `loan` WRITE;
/*!40000 ALTER TABLE `loan` DISABLE KEYS */;
/*!40000 ALTER TABLE `loan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `loanbook`
--

DROP TABLE IF EXISTS `loanbook`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `loanbook` (
  `loanID` int NOT NULL,
  `bookID` int NOT NULL,
  PRIMARY KEY (`loanID`,`bookID`),
  KEY `bookID` (`bookID`),
  CONSTRAINT `loanbook_ibfk_1` FOREIGN KEY (`bookID`) REFERENCES `book` (`bookID`),
  CONSTRAINT `loanbook_ibfk_2` FOREIGN KEY (`loanID`) REFERENCES `loan` (`loanID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `loanbook`
--

LOCK TABLES `loanbook` WRITE;
/*!40000 ALTER TABLE `loanbook` DISABLE KEYS */;
/*!40000 ALTER TABLE `loanbook` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `member`
--

DROP TABLE IF EXISTS `member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `member` (
  `memberID` int NOT NULL AUTO_INCREMENT,
  `userID` int NOT NULL,
  `name` varchar(255) NOT NULL,
  `phone` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `address` varchar(255) NOT NULL,
  `dateJoined` date NOT NULL,
  `_status` enum('active','suspended','deactivated') DEFAULT 'active',
  PRIMARY KEY (`memberID`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `uq_userID` (`userID`),
  CONSTRAINT `member_ibfk_1` FOREIGN KEY (`userID`) REFERENCES `users` (`userID`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `member`
--

LOCK TABLES `member` WRITE;
/*!40000 ALTER TABLE `member` DISABLE KEYS */;
INSERT INTO `member` VALUES (1,1,'Kaiser Lycan','09763026873','kai@gmail.com','Malabon City','2025-07-13','active'),(2,2,'Alice Love','09783093920','Mill@yahoo.com','Hospital City','2025-07-13','active'),(4,4,'Dinel Christian P. Robles','09893042932','dcpr@yahoo.com','Taguig City','2025-07-13','active'),(5,5,'Khryzna Advincula','09782030430','krhyz@mapua.com','Manila City','2025-07-13','active'),(8,8,'Login','09915050156','dinel232@gmail.com','hehehe','2025-07-14','active'),(9,9,'Alyssa','09382462738','aly@gmail.com','hihiihi','2025-08-03','active'),(10,10,'Joseph Rey Panti','09128300949','jrpanti@gmail.com','Home Address','2025-07-20','active');
/*!40000 ALTER TABLE `member` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `memberuser`
--

DROP TABLE IF EXISTS `memberuser`;
/*!50001 DROP VIEW IF EXISTS `memberuser`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `memberuser` AS SELECT 
 1 AS `userID`,
 1 AS `memberID`,
 1 AS `name`,
 1 AS `phone`,
 1 AS `email`,
 1 AS `address`,
 1 AS `dateJoined`,
 1 AS `_status`,
 1 AS `userName`,
 1 AS `password`,
 1 AS `type`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `payments`
--

DROP TABLE IF EXISTS `payments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payments` (
  `paymentID` int NOT NULL AUTO_INCREMENT,
  `fineID` int NOT NULL,
  `amountPaid` decimal(50,2) NOT NULL DEFAULT '0.00',
  `paymentChange` decimal(50,2) NOT NULL DEFAULT '0.00',
  `datePaid` date DEFAULT NULL,
  PRIMARY KEY (`paymentID`),
  KEY `fineID` (`fineID`),
  CONSTRAINT `payments_ibfk_1` FOREIGN KEY (`fineID`) REFERENCES `fine` (`fineID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payments`
--

LOCK TABLES `payments` WRITE;
/*!40000 ALTER TABLE `payments` DISABLE KEYS */;
/*!40000 ALTER TABLE `payments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `publisher`
--

DROP TABLE IF EXISTS `publisher`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `publisher` (
  `pubID` int NOT NULL AUTO_INCREMENT,
  `name` varchar(500) NOT NULL,
  `address` varchar(255) NOT NULL,
  `phone` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  PRIMARY KEY (`pubID`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `publisher`
--

LOCK TABLES `publisher` WRITE;
/*!40000 ALTER TABLE `publisher` DISABLE KEYS */;
/*!40000 ALTER TABLE `publisher` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reservation`
--

DROP TABLE IF EXISTS `reservation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reservation` (
  `resID` int NOT NULL AUTO_INCREMENT,
  `memberID` int NOT NULL,
  `dateReserved` date NOT NULL,
  `_status` enum('pending','cancelled','claimed') DEFAULT NULL,
  PRIMARY KEY (`resID`),
  KEY `memberID` (`memberID`),
  CONSTRAINT `reservation_ibfk_1` FOREIGN KEY (`memberID`) REFERENCES `member` (`memberID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reservation`
--

LOCK TABLES `reservation` WRITE;
/*!40000 ALTER TABLE `reservation` DISABLE KEYS */;
/*!40000 ALTER TABLE `reservation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reservationbook`
--

DROP TABLE IF EXISTS `reservationbook`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reservationbook` (
  `resID` int NOT NULL,
  `bookID` int NOT NULL,
  PRIMARY KEY (`resID`,`bookID`),
  KEY `bookID` (`bookID`),
  CONSTRAINT `reservationbook_ibfk_1` FOREIGN KEY (`bookID`) REFERENCES `book` (`bookID`),
  CONSTRAINT `reservationbook_ibfk_2` FOREIGN KEY (`resID`) REFERENCES `reservation` (`resID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reservationbook`
--

LOCK TABLES `reservationbook` WRITE;
/*!40000 ALTER TABLE `reservationbook` DISABLE KEYS */;
/*!40000 ALTER TABLE `reservationbook` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `staff`
--

DROP TABLE IF EXISTS `staff`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `staff` (
  `staffID` int NOT NULL AUTO_INCREMENT,
  `userID` int NOT NULL,
  `name` varchar(255) NOT NULL,
  `phone` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `dateHired` date NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`staffID`),
  UNIQUE KEY `email` (`email`),
  KEY `userID` (`userID`),
  CONSTRAINT `staff_ibfk_1` FOREIGN KEY (`userID`) REFERENCES `users` (`userID`)
) ENGINE=InnoDB AUTO_INCREMENT=1003 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `staff`
--

LOCK TABLES `staff` WRITE;
/*!40000 ALTER TABLE `staff` DISABLE KEYS */;
INSERT INTO `staff` VALUES (1001,1,'Librarian One','09123456789','librarian1@example.com','2023-01-15','123 Library St'),(1002,11,'Kaiser','09763026873','kl@gmail.com','2025-07-23','Malabon City');
/*!40000 ALTER TABLE `staff` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `staffuser`
--

DROP TABLE IF EXISTS `staffuser`;
/*!50001 DROP VIEW IF EXISTS `staffuser`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `staffuser` AS SELECT 
 1 AS `userID`,
 1 AS `staffID`,
 1 AS `name`,
 1 AS `phone`,
 1 AS `email`,
 1 AS `dateHired`,
 1 AS `address`,
 1 AS `userName`,
 1 AS `password`,
 1 AS `type`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `userID` int NOT NULL AUTO_INCREMENT,
  `userName` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL DEFAULT 'user1234',
  `type` enum('member','admin','librarian') NOT NULL,
  PRIMARY KEY (`userID`),
  UNIQUE KEY `userName` (`userName`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'KaiserLycan','KaiserLycan@081505','librarian'),(2,'AliceMill','AlMiller1245@','member'),(4,'LegionLover','HelloPo_@134','member'),(5,'Loriz','=lorizkaBA?123','member'),(8,'Lasd','Ulais12443@','member'),(9,'Tommers','Tomtomtom06!','librarian'),(10,'JrPanti','KaiserLycan@081505','member'),(11,'KaiserLycan1','KaiserLycan@081505','admin');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Final view structure for view `finemember`
--

/*!50001 DROP VIEW IF EXISTS `finemember`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `finemember` AS select `fine`.`fineID` AS `fineID`,`fine`.`staffID` AS `staffID`,`fine`.`memberID` AS `memberID`,`fine`.`amount` AS `amount`,`fine`.`dateIssued` AS `dateIssued`,`fine`.`_status` AS `_status`,`fine`.`paid_date` AS `paid_date`,`fine`.`description` AS `description`,`fine`.`due_date` AS `due_date`,`member`.`name` AS `name` from (`member` join `fine` on((`fine`.`memberID` = `member`.`memberID`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `memberuser`
--

/*!50001 DROP VIEW IF EXISTS `memberuser`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `memberuser` AS select `member`.`userID` AS `userID`,`member`.`memberID` AS `memberID`,`member`.`name` AS `name`,`member`.`phone` AS `phone`,`member`.`email` AS `email`,`member`.`address` AS `address`,`member`.`dateJoined` AS `dateJoined`,`member`.`_status` AS `_status`,`users`.`userName` AS `userName`,`users`.`password` AS `password`,`users`.`type` AS `type` from (`member` join `users` on((`member`.`userID` = `users`.`userID`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `staffuser`
--

/*!50001 DROP VIEW IF EXISTS `staffuser`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `staffuser` AS select `staff`.`userID` AS `userID`,`staff`.`staffID` AS `staffID`,`staff`.`name` AS `name`,`staff`.`phone` AS `phone`,`staff`.`email` AS `email`,`staff`.`dateHired` AS `dateHired`,`staff`.`address` AS `address`,`users`.`userName` AS `userName`,`users`.`password` AS `password`,`users`.`type` AS `type` from (`staff` join `users` on((`staff`.`userID` = `users`.`userID`))) */;
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

-- Dump completed on 2025-07-23 11:35:58
