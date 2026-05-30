-- MySQL dump 10.13  Distrib 8.4.8, for Linux (x86_64)
--
-- Host: localhost    Database: bibliotheque_db
-- ------------------------------------------------------
-- Server version	8.4.8-0ubuntu0.25.10.1

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
-- Table structure for table `bibliothecaires`
--

DROP TABLE IF EXISTS `bibliothecaires`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bibliothecaires` (
  `matricule` varchar(255) DEFAULT NULL,
  `service` varchar(255) DEFAULT NULL,
  `id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FKhldgcp6t5ou6rqdadnij92vlw` FOREIGN KEY (`id`) REFERENCES `utilisateurs` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bibliothecaires`
--

LOCK TABLES `bibliothecaires` WRITE;
/*!40000 ALTER TABLE `bibliothecaires` DISABLE KEYS */;
INSERT INTO `bibliothecaires` VALUES ('BIB-UNZ-001',NULL,5),('BIB005','12-15',11);
/*!40000 ALTER TABLE `bibliothecaires` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categories` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nom` varchar(255) NOT NULL,
  `rayon` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` VALUES (1,'Informatique',NULL,'Livres informatique et programmation'),(2,'Mathématiques',NULL,'Algèbre, analyse, statistiques'),(3,'Physique',NULL,'Mécanique, thermodynamique, optique'),(4,'Droit',NULL,'Droit civil, pénal et administratif'),(9,'Dev','A1','Personnel'),(10,'Maths','B2','cool');
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `emprunts`
--

DROP TABLE IF EXISTS `emprunts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `emprunts` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `date_emprunt` date DEFAULT NULL,
  `date_retour_prevue` date DEFAULT NULL,
  `date_retour_reelle` date DEFAULT NULL,
  `statut` enum('EN_COURS','RETOURNE','EN_RETARD') DEFAULT NULL,
  `etudiant_id` bigint NOT NULL,
  `ouvrage_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK8uhpnrx384nr71hev3l77tp6i` (`etudiant_id`),
  KEY `FK52bkr4etcopay2lhcaltsr1o6` (`ouvrage_id`),
  CONSTRAINT `FK52bkr4etcopay2lhcaltsr1o6` FOREIGN KEY (`ouvrage_id`) REFERENCES `ouvrages` (`id`),
  CONSTRAINT `FK8uhpnrx384nr71hev3l77tp6i` FOREIGN KEY (`etudiant_id`) REFERENCES `etudiants` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `emprunts`
--

LOCK TABLES `emprunts` WRITE;
/*!40000 ALTER TABLE `emprunts` DISABLE KEYS */;
INSERT INTO `emprunts` VALUES (15,'2026-05-29','2026-06-12',NULL,'EN_COURS',2,1),(16,'2026-05-29','2026-06-12','2026-05-29','RETOURNE',2,2),(17,'2026-05-29','2026-06-12','2026-05-29','RETOURNE',2,4),(18,'2026-05-29','2026-06-12','2026-05-29','RETOURNE',10,1),(19,'2026-05-29','2026-06-12','2026-05-29','RETOURNE',10,14),(20,'2026-05-29','2026-06-12','2026-05-29','RETOURNE',10,14),(21,'2026-05-29','2026-06-12','2026-05-29','RETOURNE',10,2),(22,'2026-05-29','2026-06-12','2026-05-29','RETOURNE',10,2);
/*!40000 ALTER TABLE `emprunts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `etudiants`
--

DROP TABLE IF EXISTS `etudiants`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `etudiants` (
  `filiere` varchar(255) DEFAULT NULL,
  `numero_etudiant` varchar(255) DEFAULT NULL,
  `id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FKmcq8u7ayw6c7wxgwgq3ds0mtl` FOREIGN KEY (`id`) REFERENCES `utilisateurs` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `etudiants`
--

LOCK TABLES `etudiants` WRITE;
/*!40000 ALTER TABLE `etudiants` DISABLE KEYS */;
INSERT INTO `etudiants` VALUES ('Informatique','N200456789012',2),(NULL,'N02582620251',10),(NULL,'N02568920231',12);
/*!40000 ALTER TABLE `etudiants` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ouvrages`
--

DROP TABLE IF EXISTS `ouvrages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ouvrages` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `annee_publication` int NOT NULL,
  `auteur` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `exemplaires_disponibles` int NOT NULL,
  `isbn` varchar(255) DEFAULT NULL,
  `nombre_exemplaires` int NOT NULL,
  `titre` varchar(255) NOT NULL,
  `categorie_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_al61tb5e85ahe9yxer497ex2b` (`isbn`),
  KEY `FKsfnxjit69es069q39d5xmiim5` (`categorie_id`),
  CONSTRAINT `FKsfnxjit69es069q39d5xmiim5` FOREIGN KEY (`categorie_id`) REFERENCES `categories` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ouvrages`
--

LOCK TABLES `ouvrages` WRITE;
/*!40000 ALTER TABLE `ouvrages` DISABLE KEYS */;
INSERT INTO `ouvrages` VALUES (1,2023,'Pierre Dupont',NULL,6,'978-2100823456',7,'Introduction à Java',1),(2,2022,'Jean Kouadio',NULL,4,'978-2100823457',4,'Algorithmique et Structures de Données',1),(4,2021,'Paul Kaboré',NULL,7,'978-2100823455',7,'Analyse Mathématique L1',2),(6,2022,'Aimé Ouédraogo',NULL,5,'978-2100823461',5,'Introduction au Droit Civil',4),(14,2021,'Sophie Sawadogo',NULL,4,'978-2100823460',4,'Statistiques et Probabilités',2);
/*!40000 ALTER TABLE `ouvrages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reservations`
--

DROP TABLE IF EXISTS `reservations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reservations` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `date_expiration` date DEFAULT NULL,
  `date_reservation` date DEFAULT NULL,
  `statut` enum('EN_ATTENTE','DISPONIBLE','ANNULEE','EXPIREE') DEFAULT NULL,
  `etudiant_id` bigint NOT NULL,
  `ouvrage_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKon7pq5g06e2l7nju8vc3ske4e` (`etudiant_id`),
  KEY `FKi40q3dvosqq3knndubpgr5hg9` (`ouvrage_id`),
  CONSTRAINT `FKi40q3dvosqq3knndubpgr5hg9` FOREIGN KEY (`ouvrage_id`) REFERENCES `ouvrages` (`id`),
  CONSTRAINT `FKon7pq5g06e2l7nju8vc3ske4e` FOREIGN KEY (`etudiant_id`) REFERENCES `etudiants` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reservations`
--

LOCK TABLES `reservations` WRITE;
/*!40000 ALTER TABLE `reservations` DISABLE KEYS */;
INSERT INTO `reservations` VALUES (8,'2026-06-05','2026-05-29','ANNULEE',10,1),(9,'2026-06-05','2026-05-29','ANNULEE',2,1),(10,'2026-06-05','2026-05-29','ANNULEE',10,2),(11,'2026-06-05','2026-05-29','ANNULEE',10,4),(12,'2026-06-05','2026-05-29','ANNULEE',10,14),(13,'2026-06-05','2026-05-29','ANNULEE',10,2),(14,'2026-06-05','2026-05-29','ANNULEE',10,4),(15,'2026-06-05','2026-05-29','ANNULEE',10,1),(16,'2026-06-05','2026-05-29','ANNULEE',10,2),(17,'2026-06-05','2026-05-29','EN_ATTENTE',10,1);
/*!40000 ALTER TABLE `reservations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `utilisateurs`
--

DROP TABLE IF EXISTS `utilisateurs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `utilisateurs` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `date_inscription` datetime(6) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `mot_de_passe` varchar(255) NOT NULL,
  `nom` varchar(255) NOT NULL,
  `prenom` varchar(255) NOT NULL,
  `role` enum('ETUDIANT','BIBLIOTHECAIRE','ADMINISTRATEUR') DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_6ldvumu3hqvnmmxy1b6lsxwqy` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `utilisateurs`
--

LOCK TABLES `utilisateurs` WRITE;
/*!40000 ALTER TABLE `utilisateurs` DISABLE KEYS */;
INSERT INTO `utilisateurs` VALUES (1,'2026-05-25 21:13:42.000000','admin@unz.bf','$2a$10$/BLSPl.sKaxOBLwKFEi7du1UcAwU5tuzluR8EHnxSIRAIqnY2KoHG','UNZ','Admin','ADMINISTRATEUR'),(2,'2026-05-25 21:13:43.006438','fatima.kabore@unz.bf','$2a$10$/BLSPl.sKaxOBLwKFEi7du1UcAwU5tuzluR8EHnxSIRAIqnY2KoHG','Kaboré','Fatima','ETUDIANT'),(5,'2026-05-25 21:13:55.605497','sali.traore@unz.bf','$2a$10$/BLSPl.sKaxOBLwKFEi7du1UcAwU5tuzluR8EHnxSIRAIqnY2KoHG','Traoré','Sali','BIBLIOTHECAIRE'),(10,'2026-05-29 06:40:35.230529','fatima.kabore1@unz.bf','$2a$10$BqCd70g0YeZ69lDcuHZGmOv1C90MAE4rEevaTP2soOP/3IFN9bf/i','Fatimata','KABORE','ETUDIANT'),(11,'2026-05-29 14:01:01.990342','surm@gmail.com','$2a$10$U0S78iOZXCLtN416.rTR8.akynncDdoE9IPyqSIHItypDML0BJIxm','BANAO','Moumouni','BIBLIOTHECAIRE'),(12,'2026-05-29 14:02:14.068663','000dd0d@gmail.com','$2a$10$RzMPzRW8vzY6raYxbqbVxOdcHin/ERaIY0zNXrjKI04xedHzR8JdG','KO','DA','ETUDIANT');
/*!40000 ALTER TABLE `utilisateurs` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-05-30 17:28:30
