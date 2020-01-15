-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               10.4.6-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Version:             10.2.0.5599
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for tic_tac_toe
CREATE DATABASE IF NOT EXISTS `tic_tac_toe` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `tic_tac_toe`;

-- Dumping structure for table tic_tac_toe.player
CREATE TABLE IF NOT EXISTS `player` (
  `id` int(30) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(20) NOT NULL,
  `first_name` varchar(10) NOT NULL,
  `last_name` varchar(10) NOT NULL,
  `score` int(20) DEFAULT 0,
  `password` varchar(20) NOT NULL,
  `image_url` text DEFAULT NULL,
  `status` int(1) NOT NULL DEFAULT 1,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_name` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;

-- Dumping data for table tic_tac_toe.player: ~3 rows (approximately)
/*!40000 ALTER TABLE `player` DISABLE KEYS */;
INSERT INTO `player` (`id`, `user_name`, `first_name`, `last_name`, `score`, `password`, `image_url`, `status`) VALUES
	(1, '"lll"', '"ll"', '"lll"', 2, '"ll"', NULL, 1),
	(2, 'oo', '"fiio"', '"ioi"', 1, '"oo"', NULL, 1),
	(3, 'aaa', 'aaa', 'aaa', 1, 'aaa', NULL, 1),
	(4, '"ahmed"', '"islam"', '"ahmed"', 0, '123', NULL, 1);
/*!40000 ALTER TABLE `player` ENABLE KEYS */;

-- Dumping structure for table tic_tac_toe.saved_game
CREATE TABLE IF NOT EXISTS `saved_game` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `host_id` int(11) NOT NULL,
  `guest_id` int(11) NOT NULL DEFAULT -1,
  `game_board` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

-- Dumping data for table tic_tac_toe.saved_game: ~2 rows (approximately)
/*!40000 ALTER TABLE `saved_game` DISABLE KEYS */;
INSERT INTO `saved_game` (`id`, `host_id`, `guest_id`, `game_board`) VALUES
	(5, 1, -1, '"---------"'),
	(6, 1, -1, '"---------"'),
	(7, 1, -1, '"---------"');
/*!40000 ALTER TABLE `saved_game` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
