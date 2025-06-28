-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le : mar. 07 jan. 2025 à 11:55
-- Version du serveur : 9.1.0
-- Version de PHP : 8.3.14

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `rh`
--

-- --------------------------------------------------------

--
-- Structure de la table `congee`
--

DROP TABLE IF EXISTS `congee`;
CREATE TABLE IF NOT EXISTS `congee` (
                                        `id_conge` int NOT NULL AUTO_INCREMENT,
                                        `id_emp` int NOT NULL,
                                        `date_deb` date NOT NULL,
                                        `date_fin` date NOT NULL,
                                        `conge` int NOT NULL,
                                        PRIMARY KEY (`id_conge`),
    KEY `id_emp` (`id_emp`)
    ) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `employe`
--

DROP TABLE IF EXISTS `employe`;
CREATE TABLE IF NOT EXISTS `employe` (
                                         `id_emp` int NOT NULL AUTO_INCREMENT,
                                         `nom_emp` varchar(30) NOT NULL,
    `prenom_emp` varchar(30) NOT NULL,
    `mail` varchar(30) NOT NULL,
    `date_naissance` date NOT NULL,
    `num_tel` int NOT NULL,
    `salaire` float NOT NULL,
    `departement` varchar(30) NOT NULL,
    `abs` int DEFAULT NULL,
    `conge` int NOT NULL DEFAULT '30',
    `heure_supp` int DEFAULT NULL,
    `password_emp` varchar(20) NOT NULL,
    PRIMARY KEY (`id_emp`),
    UNIQUE KEY `employe_pk` (`id_emp`)
    ) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `employe`
--

INSERT INTO `employe` (`id_emp`, `nom_emp`, `prenom_emp`, `mail`, `date_naissance`, `num_tel`, `salaire`, `departement`, `abs`, `conge`, `heure_supp`, `password_emp`) VALUES
                                                                                                                                                                           (1, 'anass', 'el', 'anaaaaa', '0000-00-00', 0, 0, 'RH', 0, 0, 0, ''),
                                                                                                                                                                           (3, 'hatim', '', '', '0000-00-00', 0, 0, 'RH', 0, 0, 0, '123'),
                                                                                                                                                                           (5, 'amine', 'driouich', 'huhu', '2003-12-15', 678960564, 20000, 'IT', NULL, 30, NULL, '123');

-- --------------------------------------------------------

--
-- Structure de la table `msg`
--

DROP TABLE IF EXISTS `msg`;
CREATE TABLE IF NOT EXISTS `msg` (
                                     `id_msg` int NOT NULL AUTO_INCREMENT,
                                     `id_emp` int NOT NULL,
                                     `message` varchar(50) NOT NULL,
    PRIMARY KEY (`id_msg`),
    KEY `id_emp` (`id_emp`)
    ) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
