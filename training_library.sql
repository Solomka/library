-- phpMyAdmin SQL Dump
-- version 3.2.4
-- http://www.phpmyadmin.net
--
-- Хост: localhost
-- Время создания: Июл 16 2017 г., 16:34
-- Версия сервера: 5.1.41
-- Версия PHP: 5.3.1

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- База данных: `training_library`
--

-- --------------------------------------------------------

--
-- Структура таблицы `author`
--

CREATE TABLE IF NOT EXISTS `author` (
  `id_author` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `surname` varchar(100) NOT NULL,
  `country` varchar(100) NOT NULL,
  PRIMARY KEY (`id_author`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=9 ;

--
-- Структура таблицы `book`
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
) ENGINE=InnoDB  DEFAULT CHARSET=ucs2 AUTO_INCREMENT=12 ;

--
-- Структура таблицы `book_author`
--

CREATE TABLE IF NOT EXISTS `book_author` (
  `id_book` int(11) NOT NULL,
  `id_author` int(11) NOT NULL,
  PRIMARY KEY (`id_book`,`id_author`),
  KEY `id_author` (`id_author`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Структура таблицы `book_instance`
--

CREATE TABLE IF NOT EXISTS `book_instance` (
  `id_book_instance` int(11) NOT NULL AUTO_INCREMENT,
  `inventory_number` varchar(100) NOT NULL,
  `status` varchar(100) NOT NULL,
  `id_book` int(11) NOT NULL,
  PRIMARY KEY (`id_book_instance`),
  UNIQUE KEY `inventory_number` (`inventory_number`),
  KEY `id_book` (`id_book`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=29 ;

--
-- Структура таблицы `book_order`
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
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=34 ;

--
-- Структура таблицы `librarian`
--

CREATE TABLE IF NOT EXISTS `librarian` (
  `id_librarian` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `surname` varchar(100) NOT NULL,
  `patronymic` varchar(100) NOT NULL,
  PRIMARY KEY (`id_librarian`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=7 ;

--
-- Структура таблицы `reader`
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
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=12 ;

--
-- Структура таблицы `users`
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
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=12 ;

--
-- Ограничения внешнего ключа сохраненных таблиц
--

--
-- Ограничения внешнего ключа таблицы `book_author`
--
ALTER TABLE `book_author`
  ADD CONSTRAINT `book_author_ibfk_1` FOREIGN KEY (`id_book`) REFERENCES `book` (`id_book`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `book_author_ibfk_2` FOREIGN KEY (`id_author`) REFERENCES `author` (`id_author`) ON DELETE NO ACTION ON UPDATE CASCADE;

--
-- Ограничения внешнего ключа таблицы `book_instance`
--
ALTER TABLE `book_instance`
  ADD CONSTRAINT `book_instance_ibfk_1` FOREIGN KEY (`id_book`) REFERENCES `book` (`id_book`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ограничения внешнего ключа таблицы `book_order`
--
ALTER TABLE `book_order`
  ADD CONSTRAINT `book_order_ibfk_1` FOREIGN KEY (`id_book_instance`) REFERENCES `book_instance` (`id_book_instance`) ON DELETE NO ACTION ON UPDATE CASCADE,
  ADD CONSTRAINT `book_order_ibfk_2` FOREIGN KEY (`id_reader`) REFERENCES `reader` (`id_reader`) ON DELETE NO ACTION ON UPDATE CASCADE,
  ADD CONSTRAINT `book_order_ibfk_3` FOREIGN KEY (`id_librarian`) REFERENCES `librarian` (`id_librarian`) ON DELETE NO ACTION ON UPDATE CASCADE;

--
-- Ограничения внешнего ключа таблицы `librarian`
--
ALTER TABLE `librarian`
  ADD CONSTRAINT `librarian_ibfk_1` FOREIGN KEY (`id_librarian`) REFERENCES `users` (`id_user`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ограничения внешнего ключа таблицы `reader`
--
ALTER TABLE `reader`
  ADD CONSTRAINT `reader_ibfk_1` FOREIGN KEY (`id_reader`) REFERENCES `users` (`id_user`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
