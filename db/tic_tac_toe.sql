-- phpMyAdmin SQL Dump
-- version 4.9.2
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Jan 06, 2020 at 02:31 PM
-- Server version: 10.4.10-MariaDB
-- PHP Version: 7.3.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `tic_tac_toe`
--

-- --------------------------------------------------------

--
-- Table structure for table `player`
--

CREATE TABLE `player` (
  `id` int(30) NOT NULL,
  `user_name` varchar(20) NOT NULL,
  `first_name` varchar(10) NOT NULL,
  `last_name` varchar(10) NOT NULL,
  `score` int(20) DEFAULT 0,
  `password` varchar(20) NOT NULL,
  `image_url` text DEFAULT NULL,
  `status` int(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `player`
--

INSERT INTO `player` (`id`, `user_name`, `first_name`, `last_name`, `score`, `password`, `image_url`, `status`) VALUES
(24, '\"islam_Ali\"', '\"ali\"', '\"Islam\"', 0, '1425', NULL, -1),
(25, '\"Ahmed_Ali\"', '\"Ahmed\"', '\"Islam\"', 0, '1425', NULL, -1),
(26, '\"ALi\"', '\"ALi\"', '\"Islam\"', 0, '1425', NULL, -1),
(27, '\"Ajmed\"', '\"ALi\"', '\"Islam\"', 0, '1425', NULL, -1),
(28, '\"LLL\"', '\"ALi\"', '\"Islam\"', 0, '1425', NULL, 1),
(29, '\"AAA\"', '\"ALi\"', '\"Islam\"', 0, '1425', NULL, -1),
(30, '\"QQQ\"', '\"ALi\"', '\"Islam\"', 0, '1425', NULL, -1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `player`
--
ALTER TABLE `player`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `user_name` (`user_name`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `player`
--
ALTER TABLE `player`
  MODIFY `id` int(30) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
