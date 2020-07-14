-- phpMyAdmin SQL Dump
-- version 3.2.4
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Jul 14, 2020 at 03:15 
-- Server version: 5.1.41
-- PHP Version: 5.3.1

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `training_library`
--

-- --------------------------------------------------------

--
-- Table structure for table `author`
--

CREATE TABLE IF NOT EXISTS `author` (
  `id_author` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `surname` varchar(100) NOT NULL,
  `country` varchar(100) NOT NULL,
  PRIMARY KEY (`id_author`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=11 ;

--
-- Dumping data for table `author`
--

INSERT INTO `author` (`id_author`, `name`, `surname`, `country`) VALUES
(1, 'Daniel', 'Keyes', 'America'),
(2, 'Ayn', 'Rand', 'Russia'),
(3, 'Марина ', 'Дяченко', 'Україна'),
(4, 'Сергій', 'Дяченко', 'Україна'),
(6, 'Scott', 'Fitzdgerald', 'America'),
(7, 'Gaston', 'Leroux', 'France'),
(8, 'Іван', 'Франко', 'Україна'),
(9, 'Peter', 'Straub', 'America'),
(10, 'Stephen', 'King', 'America');

-- --------------------------------------------------------

--
-- Table structure for table `book`
--

CREATE TABLE IF NOT EXISTS `book` (
  `id_book` int(11) NOT NULL AUTO_INCREMENT,
  `isbn` varchar(100) CHARACTER SET utf8 NOT NULL,
  `title` varchar(100) CHARACTER SET utf8 NOT NULL,
  `publisher` varchar(100) CHARACTER SET utf8 NOT NULL,
  `availability` varchar(100) CHARACTER SET utf8 NOT NULL,
  PRIMARY KEY (`id_book`),
  UNIQUE KEY `isbn` (`isbn`),
  KEY `title` (`title`)
) ENGINE=InnoDB  DEFAULT CHARSET=ucs2 AUTO_INCREMENT=17 ;

--
-- Dumping data for table `book`
--

INSERT INTO `book` (`id_book`, `isbn`, `title`, `publisher`, `availability`) VALUES
(2, '2345678948732', 'The Minds of Billy Milligan', 'Green Way', 'subscription'),
(5, '1238976345871', 'Atlas shrugged', 'Globus', 'subscription'),
(6, '7648903987263', 'Ритуал', 'Каменяр', 'subscription'),
(7, '5678954678765', ' Anthem', 'New Generation', 'reading room'),
(8, '7685774455667', 'The Phatom of the opera', 'New World', 'reading room'),
(9, '9847654738987', 'The Mystery of the Yellow Room', 'New Sphere', 'reading room'),
(10, '9856789487654', 'Хитрий лис', 'Каменяр', 'subscription'),
(11, '3489678905987', 'Ideal', 'Globus''17', 'reading room'),
(12, '8965898756789', 'The Great Gatsby', 'Great World 12', 'reading room'),
(13, '9098765423456', 'Tender Is the Night', 'Scribners', 'reading room'),
(14, '4598745690987', 'This Side of Paradise', 'Charles Scribner''s Sons', 'reading room'),
(15, '8954670958456', 'The Talisman', 'New Generation', 'subscription'),
(16, '5690567890345', 'Test Title', 'Test Publisher', 'reading room');

-- --------------------------------------------------------

--
-- Table structure for table `book_author`
--

CREATE TABLE IF NOT EXISTS `book_author` (
  `id_book` int(11) NOT NULL,
  `id_author` int(11) NOT NULL,
  PRIMARY KEY (`id_book`,`id_author`),
  KEY `id_author` (`id_author`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `book_author`
--

INSERT INTO `book_author` (`id_book`, `id_author`) VALUES
(2, 1),
(5, 2),
(7, 2),
(11, 2),
(6, 3),
(6, 4),
(12, 6),
(13, 6),
(14, 6),
(8, 7),
(9, 7),
(16, 7),
(10, 8),
(15, 9),
(16, 9),
(15, 10);

-- --------------------------------------------------------

--
-- Table structure for table `book_instance`
--

CREATE TABLE IF NOT EXISTS `book_instance` (
  `id_book_instance` int(11) NOT NULL AUTO_INCREMENT,
  `inventory_number` varchar(100) NOT NULL,
  `status` varchar(100) NOT NULL,
  `id_book` int(11) NOT NULL,
  PRIMARY KEY (`id_book_instance`),
  UNIQUE KEY `inventory_number` (`inventory_number`),
  KEY `id_book` (`id_book`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=67 ;

--
-- Dumping data for table `book_instance`
--

INSERT INTO `book_instance` (`id_book_instance`, `inventory_number`, `status`, `id_book`) VALUES
(1, '5478234587912', 'unavailable', 2),
(2, '1289763478364', 'unavailable', 2),
(6, '9845093487134', 'unavailable', 5),
(7, '5678564534123', 'unavailable', 5),
(8, '8756784567981', 'unavailable', 6),
(9, '7658948576435', 'unavailable', 6),
(10, '1765456789123', 'unavailable', 5),
(11, '0986958475456', 'unavailable', 5),
(12, '8888888888888', 'available', 5),
(13, '3333333333333', 'unavailable', 6),
(14, '7777777777777', 'available', 6),
(17, '8675849857645', 'unavailable', 8),
(18, '5678967876456', 'available', 8),
(19, '1209857689098', 'available', 8),
(20, '6798567984567', 'available', 9),
(21, '8967898345671', 'available', 9),
(22, '2378903456789', 'available', 9),
(23, '5678945678654', 'unavailable', 7),
(24, '9999999999999', 'unavailable', 7),
(25, '7878787878456', 'available', 7),
(26, '8968757433444', 'available', 7),
(27, '1234560900000', 'available', 7),
(28, '0000000000000', 'available', 7),
(29, '0009876543098', 'available', 2),
(30, '3450987654123', 'available', 2),
(31, '1289095876456', 'available', 9),
(32, '2345987645909', 'available', 8),
(33, '3489098765123', 'available', 8),
(34, '8965876543123', 'available', 12),
(35, '5690987444444', 'unavailable', 12),
(36, '3333456789876', 'unavailable', 12),
(37, '0934589876456', 'available', 12),
(38, '3409876655432', 'available', 12),
(39, '8900984756789', 'available', 11),
(40, '1234567890987', 'available', 11),
(41, '3456789012345', 'available', 11),
(42, '6578909876543', 'available', 11),
(43, '1111000099999', 'available', 11),
(44, '3490876458905', 'available', 14),
(45, '1289098745678', 'available', 14),
(46, '6789045678909', 'available', 14),
(47, '1111111111111', 'available', 14),
(49, '7777755555345', 'unavailable', 13),
(50, '7777744438909', 'available', 13),
(51, '3456789009876', 'available', 13),
(53, '5678098765456', 'available', 13),
(54, '0987654321098', 'available', 13),
(55, '8765432111111', 'available', 13),
(56, '9087654321234', 'available', 13),
(57, '3489756458923', 'unavailable', 15),
(58, '0956876543123', 'available', 15),
(59, '6578909876123', 'available', 15),
(60, '3450987654312', 'available', 15),
(61, '6789045678965', 'available', 15),
(62, '2345098745678', 'available', 15),
(63, '0954876523901', 'available', 15),
(64, '4509873456903', 'available', 7),
(65, '2390856789034', 'available', 7),
(66, '5678909854678', 'available', 7);

-- --------------------------------------------------------

--
-- Table structure for table `book_order`
--

CREATE TABLE IF NOT EXISTS `book_order` (
  `id_order` int(11) NOT NULL AUTO_INCREMENT,
  `creation_date` date NOT NULL,
  `fulfilment_date` date DEFAULT NULL,
  `pickup_date` date DEFAULT NULL,
  `return_date` date DEFAULT NULL,
  `actual_return_date` date DEFAULT NULL,
  `id_book_instance` int(11) NOT NULL,
  `id_reader` int(11) NOT NULL,
  `id_librarian` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_order`),
  KEY `id_book_instance` (`id_book_instance`),
  KEY `id_reader` (`id_reader`),
  KEY `id_librarian` (`id_librarian`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=60 ;

--
-- Dumping data for table `book_order`
--

INSERT INTO `book_order` (`id_order`, `creation_date`, `fulfilment_date`, `pickup_date`, `return_date`, `actual_return_date`, `id_book_instance`, `id_reader`, `id_librarian`) VALUES
(37, '2017-07-17', '2017-08-09', NULL, NULL, NULL, 23, 2, 6),
(39, '2017-07-17', NULL, NULL, NULL, NULL, 6, 2, NULL),
(40, '2017-06-16', '2017-06-17', '2017-06-17', '2017-07-17', NULL, 8, 2, NULL),
(41, '2017-06-15', '2017-06-16', '2017-06-16', '2017-07-16', '2017-07-18', 44, 2, NULL),
(42, '2017-06-15', '2017-06-16', '2017-06-16', '2017-07-16', NULL, 1, 11, NULL),
(43, '2017-06-14', '2017-06-15', '2017-06-15', '2017-07-15', NULL, 7, 11, NULL),
(44, '2017-06-16', '2017-06-16', '2017-06-17', '2017-07-17', '2017-07-18', 39, 11, NULL),
(45, '2017-06-15', '2017-06-15', '2017-06-15', '2017-07-15', '2017-07-18', 49, 11, NULL),
(46, '2017-06-15', '2017-06-15', '2017-06-15', '2017-07-15', '2017-07-18', 34, 11, NULL),
(47, '2017-07-17', '2017-07-17', '2017-08-09', '2017-09-09', NULL, 17, 12, NULL),
(48, '2017-07-17', '2017-07-17', NULL, NULL, NULL, 35, 12, NULL),
(49, '2017-07-15', '2017-07-16', '2017-07-17', '2017-08-17', NULL, 9, 12, NULL),
(50, '2017-07-15', '2017-07-16', '2017-07-17', '2017-08-17', NULL, 10, 12, NULL),
(52, '2017-06-11', '2017-06-12', '2017-06-11', '2017-07-11', NULL, 57, 12, NULL),
(53, '2017-07-17', NULL, NULL, NULL, NULL, 13, 13, NULL),
(54, '2017-07-17', NULL, NULL, NULL, NULL, 11, 13, NULL),
(55, '2017-06-10', '2017-06-10', '2017-06-11', '2017-07-11', NULL, 2, 13, NULL),
(56, '2017-06-16', '2017-06-17', '2017-06-17', '2017-07-17', '2017-08-09', 40, 13, NULL),
(57, '2017-06-16', '2017-06-17', '2017-06-17', '2017-07-17', NULL, 24, 13, NULL),
(58, '2017-07-18', '2017-07-18', '2017-08-09', '2017-09-09', NULL, 36, 2, 6),
(59, '2017-07-18', '2017-08-09', '2017-08-09', '2017-09-09', NULL, 49, 2, 6);

-- --------------------------------------------------------

--
-- Table structure for table `librarian`
--

CREATE TABLE IF NOT EXISTS `librarian` (
  `id_librarian` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `surname` varchar(100) NOT NULL,
  `patronymic` varchar(100) NOT NULL,
  PRIMARY KEY (`id_librarian`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=7 ;

--
-- Dumping data for table `librarian`
--

INSERT INTO `librarian` (`id_librarian`, `name`, `surname`, `patronymic`) VALUES
(6, 'Анна', 'Єршак', 'Миколаївна');

-- --------------------------------------------------------

--
-- Table structure for table `reader`
--

CREATE TABLE IF NOT EXISTS `reader` (
  `id_reader` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `surname` varchar(100) NOT NULL,
  `patronymic` varchar(100) NOT NULL,
  `phone` varchar(100) NOT NULL,
  `address` varchar(100) NOT NULL,
  `reader_card_number` varchar(100) NOT NULL,
  PRIMARY KEY (`id_reader`),
  UNIQUE KEY `reader_card_number` (`reader_card_number`),
  UNIQUE KEY `phone` (`phone`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=17 ;

--
-- Dumping data for table `reader`
--

INSERT INTO `reader` (`id_reader`, `name`, `surname`, `patronymic`, `phone`, `address`, `reader_card_number`) VALUES
(2, 'Софія', 'Євшан', 'Андріївна', '+380965094885', 'вул. Сковороди, 12/6', 'KB12345678907'),
(11, 'Viktoriya', 'Pytlyk', 'Andriivna', '+38095805773', 'Andriivska str.,12', 'KB34567890987'),
(12, 'Margarita', 'Tyshkevich', 'Sergiivna', '+380987654332', '12, Shewchenka str.', 'AD68958745'),
(13, 'Anna', 'Golska', 'Sergiivn', '+370985987665', 'Franka str. 12, 7', 'KB98765456'),
(14, 'Marko', 'Krok', 'Olegovich', '+380678098888', 'Svobody str, 12/3', 'KB09876789'),
(16, 'Marjana', 'Lesciv', 'Andriivna', '+380978698554', 'Skovorody str., 2', 'KB67890986');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `id_user` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(100) NOT NULL,
  `password` varchar(64) NOT NULL,
  `role` varchar(100) NOT NULL,
  `salt` binary(16) NOT NULL,
  PRIMARY KEY (`id_user`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `password` (`password`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=17 ;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id_user`, `email`, `password`, `role`, `salt`) VALUES
(2, 'sofiya@gmail.com', '66cf1f2b8a611d6f832af366d508d71ca49ddc65c6d45dc7d6b4fc133cafe30d', 'reader', '|9DS��ٷ��L{.'),
(6, 'anna@gmail.com', '1edef61aae8735e33727fb3df2e147cf1844a1efb25a1953c6e999e9b83837be', 'librarian', '/i�3�_�k:D''���!'),
(11, 'vika@gmail.com', '0424f4ef68e92669c7904b61b6b4e48ede5fb3253edb4c15c38bae613475879c', 'reader', 'lXW��5l�<�3��p5<'),
(12, 'marga@gmail.com', 'f9648b2ecf80a1c6350690da4af8a7f6f1bb3b5230b7784d34c63a1ca9f7d0e6', 'reader', '���xA���q�{b>܋'),
(13, 'golska@gmail.com', '3b43426697b96819ffa2d47acd2cdb3d3793c97d5d7bc95279b4de6e1fcf5ff8', 'reader', '���"�O����'),
(14, 'marko@gmail.com', 'c586d450875538ee42f5b8972eaf942a317e0c5ed28c735dc552c95c5b2974db', 'reader', '�P���^��ݨ���'),
(15, 'petro@gmail.com', '5b0f267cc1762a02398308ff920e35accf6fb9a14e3135c03dca9435182e7109', 'reader', '�9v�<�����K�'),
(16, 'marjana@gmail.com', '7213188a0ef61b9bb4b4e028918910f54078a6eb4c52e18b35fe2215659c3503', 'reader', 'ߩ�0�e�B\r��? �');

--
-- Constraints for dumped tables
--

--
-- Constraints for table `book_author`
--
ALTER TABLE `book_author`
  ADD CONSTRAINT `book_author_ibfk_1` FOREIGN KEY (`id_book`) REFERENCES `book` (`id_book`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `book_author_ibfk_2` FOREIGN KEY (`id_author`) REFERENCES `author` (`id_author`) ON DELETE NO ACTION ON UPDATE CASCADE;

--
-- Constraints for table `book_instance`
--
ALTER TABLE `book_instance`
  ADD CONSTRAINT `book_instance_ibfk_1` FOREIGN KEY (`id_book`) REFERENCES `book` (`id_book`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `book_order`
--
ALTER TABLE `book_order`
  ADD CONSTRAINT `book_order_ibfk_1` FOREIGN KEY (`id_book_instance`) REFERENCES `book_instance` (`id_book_instance`) ON DELETE NO ACTION ON UPDATE CASCADE,
  ADD CONSTRAINT `book_order_ibfk_2` FOREIGN KEY (`id_reader`) REFERENCES `reader` (`id_reader`) ON DELETE NO ACTION ON UPDATE CASCADE,
  ADD CONSTRAINT `book_order_ibfk_3` FOREIGN KEY (`id_librarian`) REFERENCES `librarian` (`id_librarian`) ON DELETE NO ACTION ON UPDATE CASCADE;

--
-- Constraints for table `librarian`
--
ALTER TABLE `librarian`
  ADD CONSTRAINT `librarian_ibfk_1` FOREIGN KEY (`id_librarian`) REFERENCES `users` (`id_user`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `reader`
--
ALTER TABLE `reader`
  ADD CONSTRAINT `reader_ibfk_1` FOREIGN KEY (`id_reader`) REFERENCES `users` (`id_user`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
