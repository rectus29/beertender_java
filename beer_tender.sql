-- phpMyAdmin SQL Dump
-- version 4.7.9
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le :  ven. 07 sep. 2018 à 15:49
-- Version du serveur :  5.7.21
-- Version de PHP :  7.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `beer_tender`
--

-- --------------------------------------------------------

--
-- Structure de la table `bills`
--

DROP TABLE IF EXISTS `bills`;
CREATE TABLE IF NOT EXISTS `bills` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  `paymentRequest` mediumtext,
  `paymentResponse` mediumtext,
  `paymentToken` mediumtext,
  `state` int(11) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `timeframe_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKk8vs7ac9xknv5xp18pdiehpp1` (`user_id`),
  KEY `FKfsjrhpequ3jr9hrih3w9nf2oo` (`timeframe_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `bills_item`
--

DROP TABLE IF EXISTS `bills_item`;
CREATE TABLE IF NOT EXISTS `bills_item` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  `productPrice` decimal(12,3) DEFAULT NULL,
  `productTax` decimal(12,3) DEFAULT NULL,
  `quantity` bigint(20) DEFAULT NULL,
  `product_id` bigint(20) DEFAULT NULL,
  `referenceOrder_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKguocg4eg48f2v5e54wlc203eh` (`referenceOrder_id`),
  KEY `FK6a60fxr5ii4002ppmwq2g35dj` (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `category`
--

DROP TABLE IF EXISTS `category`;
CREATE TABLE IF NOT EXISTS `category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  `description` mediumtext,
  `name` varchar(255) DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `isRoot` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `category`
--

INSERT INTO `category` (`id`, `created`, `updated`, `description`, `name`, `state`, `isRoot`) VALUES
(1, '2018-06-18 15:39:16', '2018-06-18 15:39:16', 'Bouteille 12x75cl', 'Bouteille 12x75cl', 1, b'1'),
(2, '2018-06-18 15:39:16', '2018-06-18 15:39:16', 'Bouteille 24x33cl', 'Bouteille 24x33cl', 1, b'1'),
(3, '2018-06-19 12:47:07', '2018-06-19 12:47:10', 'Mini-Fût', 'Mini-Fût', 1, b'1'),
(4, '2018-06-19 12:47:43', '2018-06-19 12:47:45', 'Coffret cadeaux', 'Coffret cadeaux', 1, b'1'),
(5, '2018-06-28 09:11:07', '2018-06-28 09:11:09', 'Limonades Artisanales - conditionnement 6x75cl', 'Limonades Artisanales - conditionnement 6x75cl', 1, b'1'),
(6, '2018-06-28 09:11:54', '2018-06-28 09:11:55', 'Verres', 'Verres', 1, b'1');

-- --------------------------------------------------------

--
-- Structure de la table `category_product`
--

DROP TABLE IF EXISTS `category_product`;
CREATE TABLE IF NOT EXISTS `category_product` (
  `categoryList_id` bigint(20) NOT NULL,
  `productList_id` bigint(20) NOT NULL,
  KEY `FK9yrj0lls46mey164t8xoi5x0` (`productList_id`),
  KEY `FKp0srnvhghoqddkdrwj9nlqx97` (`categoryList_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `category_product`
--

INSERT INTO `category_product` (`categoryList_id`, `productList_id`) VALUES
(1, 1),
(3, 1),
(1, 2),
(2, 3),
(2, 4),
(3, 2),
(5, 3),
(4, 4),
(6, 4);

-- --------------------------------------------------------

--
-- Structure de la table `config`
--

DROP TABLE IF EXISTS `config`;
CREATE TABLE IF NOT EXISTS `config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  `ke` longtext NOT NULL,
  `val` longtext,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `config`
--

INSERT INTO `config` (`id`, `created`, `updated`, `ke`, `val`) VALUES
(1, '2018-06-14 15:25:58', '2018-06-14 15:26:07', 'maintenance_mod', '1'),
(2, '2018-08-28 13:33:37', '2018-08-28 13:33:37', 'admin_msg', '');

-- --------------------------------------------------------

--
-- Structure de la table `history`
--

DROP TABLE IF EXISTS `history`;
CREATE TABLE IF NOT EXISTS `history` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  `action` longtext NOT NULL,
  `details` longtext NOT NULL,
  `ip` varchar(255) DEFAULT NULL,
  `object_id` bigint(20) NOT NULL,
  `object_type` longtext NOT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKq4kh99ws9lhtls5i3o73gw30t` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `mailinglist`
--

DROP TABLE IF EXISTS `mailinglist`;
CREATE TABLE IF NOT EXISTS `mailinglist` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `mailinglist_users`
--

DROP TABLE IF EXISTS `mailinglist_users`;
CREATE TABLE IF NOT EXISTS `mailinglist_users` (
  `MailingList_id` bigint(20) NOT NULL,
  `usersList_id` bigint(20) NOT NULL,
  KEY `FK6aqb68fmfkwaqalxx7wvxkc1e` (`usersList_id`),
  KEY `FKg8ctv3nqutc5gyx8s9cfi29qh` (`MailingList_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `news`
--

DROP TABLE IF EXISTS `news`;
CREATE TABLE IF NOT EXISTS `news` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  `publishDate` datetime DEFAULT NULL,
  `shortText` longtext,
  `text` longtext NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKi09n75txtudw1kawj5o7i8xag` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `packaging`
--

DROP TABLE IF EXISTS `packaging`;
CREATE TABLE IF NOT EXISTS `packaging` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `packaging`
--

INSERT INTO `packaging` (`id`, `created`, `updated`, `name`, `state`) VALUES
(1, '2018-09-06 13:18:03', '2018-09-06 13:18:03', 'Bouteille 12x75cl', 1),
(2, '2018-09-06 13:18:03', '2018-09-06 13:18:03', 'Bouteille 24x33cl', 1),
(3, '2018-09-06 13:18:03', '2018-09-06 13:18:03', 'Coffret cadeaux', 1),
(4, '2018-09-06 13:18:03', '2018-09-06 13:18:03', 'Limonades Artisanales - conditionnement 6x75cl', 1),
(5, '2018-09-06 13:18:03', '2018-09-06 13:18:03', 'Mini-Fût', 1),
(6, '2018-09-06 13:18:03', '2018-09-06 13:18:03', 'Verres', 1);

-- --------------------------------------------------------

--
-- Structure de la table `permission`
--

DROP TABLE IF EXISTS `permission`;
CREATE TABLE IF NOT EXISTS `permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  `codeString` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `permission`
--

INSERT INTO `permission` (`id`, `created`, `updated`, `codeString`, `description`) VALUES
(1, '2018-07-06 09:08:34', '2018-07-06 09:08:36', 'admin:access', 'admin:access'),
(2, '2018-08-28 11:29:35', '2018-08-28 11:29:32', 'system:user:edit', 'system:user:edit'),
(3, '2018-08-28 11:30:34', '2018-08-28 11:30:32', 'system:user:delete', 'system:user:delete'),
(4, '2018-08-28 11:30:49', '2018-08-28 11:30:49', 'system:user:runas', 'system:user:runas'),
(5, '2018-09-06 00:00:00', '2018-09-06 00:00:00', 'system:product:edit', 'system:product:edit'),
(6, '2018-09-06 00:00:00', '2018-09-06 00:00:00', 'system:product:delete', 'system:product:delete');

-- --------------------------------------------------------

--
-- Structure de la table `product`
--

DROP TABLE IF EXISTS `product`;
CREATE TABLE IF NOT EXISTS `product` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  `price` decimal(12,3) DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `packaging_id` bigint(20) DEFAULT NULL,
  `productDefinition_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKbbuq7eyl3p15b039dh1r8cswq` (`packaging_id`),
  KEY `FK7xlsyofr56yy5reb7t26ibwtq` (`productDefinition_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `productdefinition`
--

DROP TABLE IF EXISTS `productdefinition`;
CREATE TABLE IF NOT EXISTS `productdefinition` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  `description` mediumtext,
  `imagePath` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `productdefinition`
--

INSERT INTO `productdefinition` (`id`, `created`, `updated`, `description`, `imagePath`, `name`) VALUES
(1, '2018-06-20 12:55:40', '2018-06-20 12:55:42', 'Triple Ambree', NULL, 'triple Ambree'),
(2, '2018-06-28 09:08:08', '2018-06-28 09:08:12', 'Biere Blonde de luxe', NULL, 'Biere Blonde de luxe'),
(3, '2018-06-28 09:08:13', '2018-06-28 09:08:13', 'Blanche qui va bien', NULL, 'Blanche qui va bien'),
(4, '2018-06-28 09:08:14', '2018-06-28 09:08:15', 'Choulette de noël', NULL, 'Choulette de noël'),
(5, '2018-06-28 18:46:18', '2018-06-28 18:46:54', 'Une bière blonde artisanale créée pour commémorer cette bataille et rendre hommage à l’armée britannique.\r\n\r\nBière artisanale fabriquée en France. Contient du malt d’orge. A conserver dans un endroit frais et sec, à l’abri de la lumière.\r\n\r\nL’abus d’alcool est dangereux pour la santé, à consommer avec modération.\r\n\r\nLa consommation de boissons alcoolisées pendant la grossesse, même en faible quantité, peut avoir des conséquences graves sur la santé de l’enfant.', 'img/products/Battle-of-Cambrai-blonde-75cl-500x1000.jpg', 'LA BATTLE OF CAMBRAI 75CL 6% VOL.');

-- --------------------------------------------------------

--
-- Structure de la table `role`
--

DROP TABLE IF EXISTS `role`;
CREATE TABLE IF NOT EXISTS `role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `isAdmin` bit(1) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `weight` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `role`
--

INSERT INTO `role` (`id`, `created`, `updated`, `description`, `isAdmin`, `name`, `weight`) VALUES
(1, '2018-06-14 15:26:50', '2018-06-14 15:26:53', 'admin', b'1', 'admin', 1000),
(2, '2018-08-10 12:35:26', '2018-08-10 12:35:28', 'Utilisateur', b'0', 'user', 10);

-- --------------------------------------------------------

--
-- Structure de la table `role_permission`
--

DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE IF NOT EXISTS `role_permission` (
  `Role_id` bigint(20) NOT NULL,
  `permissions_id` bigint(20) NOT NULL,
  PRIMARY KEY (`Role_id`,`permissions_id`),
  KEY `FKsidab0lpqi82o4o15bwde2c5f` (`permissions_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `role_permission`
--

INSERT INTO `role_permission` (`Role_id`, `permissions_id`) VALUES
(1, 1),
(1, 2),
(1, 3),
(1, 4);

-- --------------------------------------------------------

--
-- Structure de la table `timeframe`
--

DROP TABLE IF EXISTS `timeframe`;
CREATE TABLE IF NOT EXISTS `timeframe` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  `endDate` datetime DEFAULT NULL,
  `startDate` datetime DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `timeframe`
--

INSERT INTO `timeframe` (`id`, `created`, `updated`, `endDate`, `startDate`, `state`, `name`) VALUES
(1, '2018-07-24 15:38:51', '2018-07-24 15:38:55', '2018-09-30 15:39:05', '2018-09-01 15:39:11', 1, 'Septembre 2018'),
(2, '2018-08-10 14:33:25', '2018-08-10 14:33:27', '2018-08-31 14:34:20', '2018-08-01 14:34:23', 0, 'Aout 2018'),
(4, '2018-08-10 14:35:37', '2018-08-10 14:35:35', '2018-07-31 14:35:24', '2018-07-01 14:35:04', 0, 'Juillet 2018');

-- --------------------------------------------------------

--
-- Structure de la table `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `lastLogin` datetime DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `restoreSession` varchar(255) DEFAULT NULL,
  `restoreSessionDate` datetime DEFAULT NULL,
  `salt` varchar(255) DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `userAuthentificationType` varchar(50) NOT NULL,
  `role_id` bigint(20) DEFAULT NULL,
  `uuid` varchar(36) NOT NULL,
  `firstName` varchar(255) DEFAULT NULL,
  `lastName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK4qu1gr772nnf6ve5af002rwya` (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `users`
--

INSERT INTO `users` (`id`, `created`, `updated`, `email`, `lastLogin`, `password`, `restoreSession`, `restoreSessionDate`, `salt`, `state`, `userAuthentificationType`, `role_id`, `uuid`, `firstName`, `lastName`) VALUES
(1, '2015-08-24 00:00:00', '2018-07-13 07:37:20', 'rectus29@gmail.com', '2018-09-04 15:20:00', '2pTblAzn+o+S0sFR46qTR4KVKgVZx7T/lPATHhDTU8c=', NULL, NULL, 'XU5Pp4Lz+mSiUkciqOUXD3XocslsvE/iK0eOze2A0C77WE9idlq9emqQOuO7y2T+arRT84Hku5cpFwGRAqgJy8aCTndMAtzR6QshNyfI61lHu5ec0Msj8121nTt91CYvltCWf3OCid3/8AQg+fNh89QXbSjeZvwRKhLWlb41A3A=', 1, 'EMBED', 1, 'yolo', 'ALexandre', 'Bernard'),
(2, '2018-08-10 14:40:05', '2018-08-10 14:40:05', 'test@yolo.com', NULL, 'UvcQHRaqQAVXnvJ+OrF3UNGgG8uEmxK3RT0s3lYsOIw=', NULL, NULL, 'NpJdQwuK4L/ghzIHo3pS+g==', 1, 'EMBED', 2, '97e8afab-0c75-460d-8629-f0a8f6cc168a', 'test', 'test');

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `bills`
--
ALTER TABLE `bills`
  ADD CONSTRAINT `FKfsjrhpequ3jr9hrih3w9nf2oo` FOREIGN KEY (`timeframe_id`) REFERENCES `timeframe` (`id`),
  ADD CONSTRAINT `FKk8vs7ac9xknv5xp18pdiehpp1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Contraintes pour la table `bills_item`
--
ALTER TABLE `bills_item`
  ADD CONSTRAINT `FK6a60fxr5ii4002ppmwq2g35dj` FOREIGN KEY (`product_id`) REFERENCES `productdefinition` (`id`),
  ADD CONSTRAINT `FKguocg4eg48f2v5e54wlc203eh` FOREIGN KEY (`referenceOrder_id`) REFERENCES `bills` (`id`);

--
-- Contraintes pour la table `category_product`
--
ALTER TABLE `category_product`
  ADD CONSTRAINT `FK9yrj0lls46mey164t8xoi5x0` FOREIGN KEY (`productList_id`) REFERENCES `productdefinition` (`id`),
  ADD CONSTRAINT `FKp0srnvhghoqddkdrwj9nlqx97` FOREIGN KEY (`categoryList_id`) REFERENCES `category` (`id`);

--
-- Contraintes pour la table `history`
--
ALTER TABLE `history`
  ADD CONSTRAINT `FKq4kh99ws9lhtls5i3o73gw30t` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Contraintes pour la table `mailinglist_users`
--
ALTER TABLE `mailinglist_users`
  ADD CONSTRAINT `FK6aqb68fmfkwaqalxx7wvxkc1e` FOREIGN KEY (`usersList_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `FKg8ctv3nqutc5gyx8s9cfi29qh` FOREIGN KEY (`MailingList_id`) REFERENCES `mailinglist` (`id`);

--
-- Contraintes pour la table `news`
--
ALTER TABLE `news`
  ADD CONSTRAINT `FKi09n75txtudw1kawj5o7i8xag` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Contraintes pour la table `product`
--
ALTER TABLE `product`
  ADD CONSTRAINT `FK7xlsyofr56yy5reb7t26ibwtq` FOREIGN KEY (`productDefinition_id`) REFERENCES `productdefinition` (`id`),
  ADD CONSTRAINT `FKbbuq7eyl3p15b039dh1r8cswq` FOREIGN KEY (`packaging_id`) REFERENCES `packaging` (`id`);

--
-- Contraintes pour la table `role_permission`
--
ALTER TABLE `role_permission`
  ADD CONSTRAINT `FKhunhiykw72tqmyd53u4fmt53h` FOREIGN KEY (`Role_id`) REFERENCES `role` (`id`),
  ADD CONSTRAINT `FKsidab0lpqi82o4o15bwde2c5f` FOREIGN KEY (`permissions_id`) REFERENCES `permission` (`id`);

--
-- Contraintes pour la table `users`
--
ALTER TABLE `users`
  ADD CONSTRAINT `FK4qu1gr772nnf6ve5af002rwya` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
