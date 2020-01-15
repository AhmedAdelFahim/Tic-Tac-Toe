-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 15, 2020 at 12:24 PM
-- Server version: 10.1.38-MariaDB
-- PHP Version: 7.3.4

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
  `score` int(20) DEFAULT '0',
  `password` varchar(20) NOT NULL,
  `image_url` text,
  `status` int(1) NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `player`
--

INSERT INTO `player` (`id`, `user_name`, `first_name`, `last_name`, `score`, `password`, `image_url`, `status`) VALUES
(24, '\"islam_Ali\"', '\"ali\"', '\"Islam\"', 0, '1425', NULL, -1),
(25, '\"Ahmed_Ali\"', '\"Ahmed\"', '\"Islam\"', 0, '1425', NULL, 1),
(26, '\"ALi\"', '\"ALi\"', '\"Islam\"', 0, '1425', NULL, -1),
(27, '\"Ajmed\"', '\"ALi\"', '\"Islam\"', 0, '1425', NULL, -1),
(28, '\"LLL\"', '\"ALi\"', '\"Islam\"', 7, '1425', NULL, 1),
(29, '\"AAA\"', '\"ALi\"', '\"Islam\"', 4, '1425', NULL, 1),
(30, '\"QQQ\"', '\"ALi\"', '\"Islam\"', 0, '1425', NULL, 1),
(31, '\"asd\"', '\"aaa\"', '\"aaa\"', 0, '123456', NULL, 1),
(32, '\"ad\"', '\"ada\"', '\"ad\"', 0, '123', NULL, 1),
(33, '\"xxx\"', '\"aaa\"', '\"ddd\"', 0, '111', NULL, 1),
(37, '\"xxxa\"', '\"aaaa\"', '\"ddda\"', 0, '111', NULL, 1),
(38, '\"xxxsdd\"', '\"aaasd\"', '\"dddsd\"', 0, '111', NULL, 1),
(39, '\"xxxzx\"', '\"aaazxxz\"', '\"dddzx\"', 0, '111', NULL, 1),
(40, '\"dsffs\"', '\"aaaa\"', '\"ddda\"', 0, '111', NULL, 1),
(41, '\"xxxfd\"', '\"aaadff\"', '\"ddd\"', 0, '\"111df\"', NULL, 1),
(42, '\"xxxxxs\"', '\"aaa\"', '\"ddd\"', 0, '456789', NULL, 1),
(44, '\"xxxknkk\"', '\"aaa\"', '\"ddd\"', 0, '111', NULL, 1),
(45, '\"xxxtftftf\"', '\"aaa\"', '\"ddd\"', 0, '111', NULL, 1),
(46, '\"qwqwq\"', '\"aaaq\"', '\"ddd\"', 0, '12', NULL, 1);

-- --------------------------------------------------------

--
-- Table structure for table `saved_game`
--

CREATE TABLE `saved_game` (
  `id` int(11) NOT NULL,
  `host_id` int(11) NOT NULL,
  `guest_id` int(11) NOT NULL,
  `game_board` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `saved_game`
--

INSERT INTO `saved_game` (`id`, `host_id`, `guest_id`, `game_board`) VALUES
(1, 29, -1, '\"XX-----OO\"'),
(2, 29, -1, '\"---------\"'),
(3, 29, -1, '\"----X----\"'),
(4, 29, -1, '\"-X-XXOOO-\"');

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
-- Indexes for table `saved_game`
--
ALTER TABLE `saved_game`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `player`
--
ALTER TABLE `player`
  MODIFY `id` int(30) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=47;

--
-- AUTO_INCREMENT for table `saved_game`
--
ALTER TABLE `saved_game`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
