-- phpMyAdmin SQL Dump
-- version 4.9.2
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3303
-- Généré le :  mer. 08 jan. 2020 à 22:56
-- Version du serveur :  8.0.18
-- Version de PHP :  7.3.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `projets5_grunivertaires`
--

-- --------------------------------------------------------

--
-- Structure de la table `groupe`
--

DROP TABLE IF EXISTS `groupe`;
CREATE TABLE IF NOT EXISTS `groupe` (
  `idGroupe` int(11) NOT NULL AUTO_INCREMENT,
  `nomGroupe` varchar(255) NOT NULL,
  PRIMARY KEY (`idGroupe`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `groupe`
--

INSERT INTO `groupe` (`idGroupe`, `nomGroupe`) VALUES
(2, 'TDA2'),
(3, 'TDA5'),
(4, 'TDA4'),
(5, 'TDA1'),
(6, 'TDA6'),
(8, 'TDA7');

-- --------------------------------------------------------

--
-- Structure de la table `groupeutilisateur`
--

DROP TABLE IF EXISTS `groupeutilisateur`;
CREATE TABLE IF NOT EXISTS `groupeutilisateur` (
  `idU` int(11) NOT NULL,
  `idGroupe` int(11) NOT NULL,
  PRIMARY KEY (`idU`,`idGroupe`),
  KEY `idGroupe` (`idGroupe`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `groupeutilisateur`
--

INSERT INTO `groupeutilisateur` (`idU`, `idGroupe`) VALUES
(2, 4),
(5, 4),
(2, 5),
(5, 6),
(5, 8);

-- --------------------------------------------------------

--
-- Structure de la table `message`
--

DROP TABLE IF EXISTS `message`;
CREATE TABLE IF NOT EXISTS `message` (
  `idMsg` int(11) NOT NULL AUTO_INCREMENT,
  `contenuMsg` text NOT NULL,
  `date_envoie` datetime NOT NULL,
  `email` varchar(255) NOT NULL,
  `idFil` int(11) NOT NULL,
  `idU` int(11) NOT NULL,
  PRIMARY KEY (`idMsg`),
  KEY `idFil` (`idFil`),
  KEY `idU` (`idU`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `message`
--

INSERT INTO `message` (`idMsg`, `contenuMsg`, `date_envoie`, `email`, `idFil`, `idU`) VALUES
(1, 'Bonjour, \r\nJe signale que y\'a une panne du chauffage dans la B22', '2020-01-06 00:00:00', 'maimouna.bah@univ-tlse3.fr', 1, 1);

-- --------------------------------------------------------

--
-- Structure de la table `ticket`
--

DROP TABLE IF EXISTS `ticket`;
CREATE TABLE IF NOT EXISTS `ticket` (
  `idFil` int(11) NOT NULL AUTO_INCREMENT,
  `titreFil` varchar(255) NOT NULL,
  `date_envoie` datetime NOT NULL,
  `idGroupe` int(11) NOT NULL,
  `idU` int(11) NOT NULL,
  PRIMARY KEY (`idFil`),
  KEY `idGroupe` (`idGroupe`),
  KEY `idU` (`idU`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `ticket`
--

INSERT INTO `ticket` (`idFil`, `titreFil`, `date_envoie`, `idGroupe`, `idU`) VALUES
(1, 'Panne du chauffage', '2020-01-06 00:00:00', 4, 1),
(2, 'panne', '2020-01-08 18:17:43', 2, 1),
(3, 'Bonjour', '2020-01-08 18:31:03', 2, 1),
(4, 'Electricite', '2020-01-08 20:50:56', 2, 2),
(7, 'GJHJHJ', '2020-01-08 21:54:45', 4, 5),
(8, 'TEST', '2020-01-08 21:57:47', 4, 2);

-- --------------------------------------------------------

--
-- Structure de la table `utilisateur`
--

DROP TABLE IF EXISTS `utilisateur`;
CREATE TABLE IF NOT EXISTS `utilisateur` (
  `idU` int(11) NOT NULL AUTO_INCREMENT,
  `nomU` varchar(255) NOT NULL,
  `prenomU` varchar(255) NOT NULL,
  `usernameU` varchar(255) NOT NULL,
  `motDePasse` varchar(255) NOT NULL,
  `roles` varchar(255) NOT NULL,
  PRIMARY KEY (`idU`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `utilisateur`
--

INSERT INTO `utilisateur` (`idU`, `nomU`, `prenomU`, `usernameU`, `motDePasse`, `roles`) VALUES
(1, 'Bah', 'Maimouna', 'mouna2016', 'Mouna2016', 'Etudiante'),
(2, 'Ferrie', 'Nicolas', 'nicola2019', 'Nicola2019', 'Enseignant'),
(3, 'Chu', 'Kewine', 'kewin2019', 'Kewin2019', 'Technicien'),
(5, 'Raynal', 'Mathieu', 'mat', '123456789', 'Administratif');

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `groupeutilisateur`
--
ALTER TABLE `groupeutilisateur`
  ADD CONSTRAINT `groupeutilisateur_ibfk_1` FOREIGN KEY (`idGroupe`) REFERENCES `groupe` (`idGroupe`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  ADD CONSTRAINT `groupeutilisateur_ibfk_2` FOREIGN KEY (`idU`) REFERENCES `utilisateur` (`idU`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Contraintes pour la table `message`
--
ALTER TABLE `message`
  ADD CONSTRAINT `message_ibfk_1` FOREIGN KEY (`idFil`) REFERENCES `ticket` (`idFil`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  ADD CONSTRAINT `message_ibfk_2` FOREIGN KEY (`idU`) REFERENCES `utilisateur` (`idU`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Contraintes pour la table `ticket`
--
ALTER TABLE `ticket`
  ADD CONSTRAINT `ticket_ibfk_1` FOREIGN KEY (`idGroupe`) REFERENCES `groupe` (`idGroupe`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  ADD CONSTRAINT `ticket_ibfk_2` FOREIGN KEY (`idU`) REFERENCES `utilisateur` (`idU`) ON DELETE RESTRICT ON UPDATE RESTRICT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
