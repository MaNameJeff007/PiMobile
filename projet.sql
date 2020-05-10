-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le :  Dim 10 mai 2020 à 17:31
-- Version du serveur :  5.7.26
-- Version de PHP :  7.2.18

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `projet`
--

-- --------------------------------------------------------

--
-- Structure de la table `absences`
--

DROP TABLE IF EXISTS `absences`;
CREATE TABLE IF NOT EXISTS `absences` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `enseignant_id` int(11) DEFAULT NULL,
  `eleve_id` int(11) DEFAULT NULL,
  `dateabs` date DEFAULT NULL,
  `justification` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `heuredebut` int(11) NOT NULL,
  `heurefin` int(11) NOT NULL,
  `etat` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_F9C0EFFFE455FCC0` (`enseignant_id`),
  KEY `IDX_F9C0EFFFA6CC7B2` (`eleve_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `activity`
--

DROP TABLE IF EXISTS `activity`;
CREATE TABLE IF NOT EXISTS `activity` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `NomClub` varchar(11) COLLATE utf8_unicode_ci DEFAULT NULL,
  `nomActivite` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `typeActivite` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `vote` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_AC74095AA76ED395` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Déchargement des données de la table `activity`
--

INSERT INTO `activity` (`id`, `user_id`, `NomClub`, `nomActivite`, `typeActivite`, `vote`) VALUES
(15, 1, 'isims', 'rootSPACE', 'Club Artistique', 0),
(17, 1, 'mohsen', 'rootSPACE', 'Club Sportif', 0),
(18, 1, 'wiwhi<', 'sqd', 'Club Sportif', 0),
(19, 1, 'isims', 'nouv activite', 'Activite Sportif', 0),
(20, 1, 'mohsen', 'mlk', 'Activite scientifique', 0),
(21, 1, 'rootSPACE', 'mohsen', 'Activite Sportif', 0),
(22, 1, 'mohsen', 'nom act', 'Activite scientifique', 0),
(23, 1, 'ENACTUS', 'calcul', 'Activite scientifique', 0);

-- --------------------------------------------------------

--
-- Structure de la table `attestation`
--

DROP TABLE IF EXISTS `attestation`;
CREATE TABLE IF NOT EXISTS `attestation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parent` int(11) DEFAULT NULL,
  `date` datetime NOT NULL,
  `etat` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `enfant` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_326EC63F3D8E604F` (`parent`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `bulletin`
--

DROP TABLE IF EXISTS `bulletin`;
CREATE TABLE IF NOT EXISTS `bulletin` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `eleve` int(11) NOT NULL,
  `trimestre` int(11) NOT NULL,
  `classe` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `moyenne` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=65 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Déchargement des données de la table `bulletin`
--

INSERT INTO `bulletin` (`id`, `eleve`, `trimestre`, `classe`, `moyenne`) VALUES
(1, 12, 1, '1', 2.6),
(26, 8, 1, '1', 6),
(27, 8, 2, '1', 8),
(28, 8, 3, '1', 14),
(32, 12, 3, '1', 2.4),
(35, 12, 2, '1', 14),
(36, 10, 2, '2', 10.3),
(57, 7, 1, '1', 14),
(58, 7, 2, '1', 10.3),
(59, 18, 3, '1', 2.4),
(60, 1, 1, '1', 14),
(61, 9, 2, '1', 10.3),
(62, 20, 3, '1', 2.4),
(63, 8, 2, '1', 14.5),
(64, 11, 1, '1A21', NULL);

-- --------------------------------------------------------

--
-- Structure de la table `classe`
--

DROP TABLE IF EXISTS `classe`;
CREATE TABLE IF NOT EXISTS `classe` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `niveau` int(11) NOT NULL,
  `capacite` int(11) NOT NULL,
  `libelle` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UNIQ_8F87BF96A4D60759` (`libelle`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Déchargement des données de la table `classe`
--

INSERT INTO `classe` (`id`, `niveau`, `capacite`, `libelle`) VALUES
(1, 3, 20, '3A11'),
(2, 1, 52, '1A2'),
(3, 1, 30, '1A3'),
(4, 2, 30, '2A6'),
(5, 5, 40, '5A2'),
(6, 1, 36, '1A21'),
(7, 2, 20, '3a20'),
(9, 5, 20, '3a40'),
(10, 2, 20, '3a10'),
(11, 4, 22, '4a2');

-- --------------------------------------------------------

--
-- Structure de la table `club`
--

DROP TABLE IF EXISTS `club`;
CREATE TABLE IF NOT EXISTS `club` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `nomclub` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `nom_image` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_B8EE3872A76ED395` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Déchargement des données de la table `club`
--

INSERT INTO `club` (`id`, `user_id`, `nomclub`, `nom_image`) VALUES
(58, 1, 'IEEE', '20180710_231024.jpg'),
(59, 7, 'AISEC', '20180710_231024.jpg'),
(64, 7, 'ESPRO', 'Photo ID MohamedTurki.png'),
(65, 1, 'ENACTUS', 'Photo ID MohamedTurki.png'),
(66, 1, 'ROTARACT', '_c__dangerous_love_by_luupon_dcylwav.png'),
(67, 1, 'FUTURA', '0BCwEoQ-code-geass-wallpaper.jpg');

-- --------------------------------------------------------

--
-- Structure de la table `coeff`
--

DROP TABLE IF EXISTS `coeff`;
CREATE TABLE IF NOT EXISTS `coeff` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `niveau_id` int(11) DEFAULT NULL,
  `matiere_id` int(11) DEFAULT NULL,
  `valeur` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_A29F1C52B3E9C81` (`niveau_id`),
  KEY `IDX_A29F1C52F46CD258` (`matiere_id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Déchargement des données de la table `coeff`
--

INSERT INTO `coeff` (`id`, `niveau_id`, `matiere_id`, `valeur`) VALUES
(1, 1, 2, 4),
(2, 2, 3, 4),
(9, 1, 1, 6),
(10, 5, 3, 2),
(12, 2, 1, 4),
(23, 5, 4, 1);

-- --------------------------------------------------------

--
-- Structure de la table `commentaire`
--

DROP TABLE IF EXISTS `commentaire`;
CREATE TABLE IF NOT EXISTS `commentaire` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sujet` int(11) DEFAULT NULL,
  `createur` int(11) NOT NULL,
  `texte` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `date` datetime NOT NULL,
  `score` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_67F068BC2E13599D` (`sujet`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Déchargement des données de la table `commentaire`
--

INSERT INTO `commentaire` (`id`, `sujet`, `createur`, `texte`, `date`, `score`) VALUES
(2, 11, 2, 'a correction des cahiers, les classes à plusieurs niveaux, différencier sa pédagogie', '2020-04-29 00:00:00', 5);

-- --------------------------------------------------------

--
-- Structure de la table `course`
--

DROP TABLE IF EXISTS `course`;
CREATE TABLE IF NOT EXISTS `course` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `nom` varchar(250) COLLATE utf8_unicode_ci NOT NULL,
  `description` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `contenu` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `type_intelligence` int(11) NOT NULL,
  `niveau` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_169E6FB9A76ED395` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Déchargement des données de la table `course`
--

INSERT INTO `course` (`id`, `user_id`, `nom`, `description`, `contenu`, `type_intelligence`, `niveau`) VALUES
(3, NULL, 'symfony', 'symfony2', 'mohamedturki-5e56fe66655df.pdf', 1, 1),
(4, 1, 'jjj', 'jjjj', 'mohamedturki-5e5700e682d85.pdf', 1, 4),
(8, NULL, 'Mathématique', 'cours math CM1', 'validationdesdonnees-5e4dad5398b6c.pdf', 1, 2),
(9, NULL, 'Mathématique', 'cours math CM1', 'fichierdevoirsmathscm12018-5e4dae943d547.pdf', 3, 2),
(10, NULL, 'calcul', 'cours math CM1', 'gecc81omecc81triecm12018blog-5e4dba0552c8e.pdf', 1, 1),
(11, NULL, 'Mathématique', 'cours math CM1', 'fichierdevoirsmathscm12018-5e4e62cd93c15.pdf', 2, 1),
(12, NULL, 'validat', 'val', 'validationdesdonnees-5e56e4527522d.pdf', 2, 3),
(13, NULL, 'français', 'fr', 'frt1-5e5777df23749.pdf', 1, 2),
(14, NULL, 'français', 'fr', 'frt2-5e5777fcc5fde.pdf', 2, 1),
(15, NULL, 'français', 'fr', 'frt3-5e57781e71081.pdf', 3, 1),
(16, NULL, 'français', 'ff', 'mohamedturki-5e57830b8d929.pdf', 1, 5);

-- --------------------------------------------------------

--
-- Structure de la table `evenement`
--

DROP TABLE IF EXISTS `evenement`;
CREATE TABLE IF NOT EXISTS `evenement` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `club_id` int(11) DEFAULT NULL,
  `NomClub` varchar(11) COLLATE utf8_unicode_ci NOT NULL,
  `nom_evenement` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `nom_image` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `heure_debut` datetime NOT NULL,
  `heure_fin` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_B26681E61190A32` (`club_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Déchargement des données de la table `evenement`
--

INSERT INTO `evenement` (`id`, `club_id`, `NomClub`, `nom_evenement`, `nom_image`, `heure_debut`, `heure_fin`) VALUES
(1, 58, 'ROtARACT', 'new event1', NULL, '2020-04-01 00:00:00', '2020-04-09 00:00:00'),
(2, 59, 'AISEC', 'hackathon', NULL, '2020-04-18 00:00:00', '2020-04-18 00:00:00');

-- --------------------------------------------------------

--
-- Structure de la table `exercice`
--

DROP TABLE IF EXISTS `exercice`;
CREATE TABLE IF NOT EXISTS `exercice` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `course_id` int(11) DEFAULT NULL,
  `question` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `reponse` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `score` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `option1` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `option2` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `option3` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_E418C74D591CC992` (`course_id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Déchargement des données de la table `exercice`
--

INSERT INTO `exercice` (`id`, `course_id`, `question`, `reponse`, `score`, `option1`, `option2`, `option3`) VALUES
(2, 4, 'pourquoi la terre est ronde?', 'gravité', '1', '0', '0', '0'),
(3, 4, 'pourquoi la mer est bleu?', 'reflection', '0', '0', '0', '0'),
(4, 4, 'pourquoi la vie?', '42', '3', '0', '0', '0'),
(5, 4, 'Ou est ce que se trouve Bizerte?', 'Nord', '1', '0', '0', '0'),
(6, 3, 'Ou est ce que se trouve Nabeul?', 'nord est', '3', '0', '0', '0'),
(7, 3, 'Ou est ce que se trouve Gabes?', 'sud', '1', '0', '0', '0'),
(8, 3, 'Ou est ce que se trouve Sidi Bouzid?', 'ouest', '1', '0', '0', '0'),
(9, 8, ' quel le capital du maroc?', 'Rabat', ' 1', '0', '0', '0'),
(10, 8, 'quel le capital de la France?', 'Paris', '1', '0', '0', '0'),
(11, 8, 'quel le capital de l\'italie?', 'Rome', '1', '0', '0', '0'),
(12, 8, 'quel le capital de l\'allemagne ?', 'Berlin', '1', '0', '0', '0'),
(13, 9, '  quel est le resultat de 4/2?', '  2', '  1', '0', '0', '0'),
(14, 9, 'quel est le resultat de 10*4?', '40', '1', '0', '0', '0'),
(15, 3, 'quel est le resultat de 14*4?', '56', '1', '0', '0', '0'),
(16, 9, 'quel est le resultat de 14*4?', '56', '1', '0', '0', '0'),
(17, 9, 'quel est le resultat de 14*4?', '56', '1', '0', '0', '0'),
(18, 9, 'quel est le resultat de 35*1?', '35', '1', '0', '0', '0'),
(19, 10, 'Le verbe etre avec tu:', 'es', '2', '0', '0', '0'),
(20, 10, 'Le verbe etre avec il:', 'est', '1', '0', '0', '0'),
(21, 10, 'Le verbe etre avec nous:', 'sommes', '1', '0', '0', '0'),
(22, 10, 'Le verbe etre avec vous:', 'etes', '1', '0', '0', '0'),
(24, 4, 'Moyenne d age à ESPRIT?', ' 25', '  1', '0', '0', '0');

-- --------------------------------------------------------

--
-- Structure de la table `likes`
--

DROP TABLE IF EXISTS `likes`;
CREATE TABLE IF NOT EXISTS `likes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sujet` int(11) DEFAULT NULL,
  `commentaire` int(11) DEFAULT NULL,
  `createur` int(11) NOT NULL,
  `type` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_49CA4E7D2E13599D` (`sujet`),
  KEY `IDX_49CA4E7D67F068BC` (`commentaire`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Déchargement des données de la table `likes`
--

INSERT INTO `likes` (`id`, `sujet`, `commentaire`, `createur`, `type`) VALUES
(3, NULL, 2, 5, 'commentaire');

-- --------------------------------------------------------

--
-- Structure de la table `matiere`
--

DROP TABLE IF EXISTS `matiere`;
CREATE TABLE IF NOT EXISTS `matiere` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `nbH` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UNIQ_9014574A6C6E55B5` (`nom`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Déchargement des données de la table `matiere`
--

INSERT INTO `matiere` (`id`, `nom`, `nbH`) VALUES
(1, 'pidev', 99),
(2, 'francais', 40),
(3, 'Anglais', 12),
(4, 'Physique', 45),
(6, 'Sciences Expérimentales', 3),
(7, 'Mathematiques', 20);

-- --------------------------------------------------------

--
-- Structure de la table `message`
--

DROP TABLE IF EXISTS `message`;
CREATE TABLE IF NOT EXISTS `message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `thread_id` int(11) DEFAULT NULL,
  `sender_id` int(11) DEFAULT NULL,
  `body` longtext COLLATE utf8_unicode_ci NOT NULL,
  `created_at` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_B6BD307FE2904019` (`thread_id`),
  KEY `IDX_B6BD307FF624B39D` (`sender_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `message_metadata`
--

DROP TABLE IF EXISTS `message_metadata`;
CREATE TABLE IF NOT EXISTS `message_metadata` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `message_id` int(11) DEFAULT NULL,
  `participant_id` int(11) DEFAULT NULL,
  `is_read` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_4632F005537A1329` (`message_id`),
  KEY `IDX_4632F0059D1C3019` (`participant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `moyennes`
--

DROP TABLE IF EXISTS `moyennes`;
CREATE TABLE IF NOT EXISTS `moyennes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `eleve_id` int(11) DEFAULT NULL,
  `matiere` int(11) DEFAULT NULL,
  `trimestre` int(11) NOT NULL,
  `moyenne` double NOT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_66FB2B45A6CC7B2` (`eleve_id`),
  KEY `IDX_66FB2B459014574A` (`matiere`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Déchargement des données de la table `moyennes`
--

INSERT INTO `moyennes` (`id`, `eleve_id`, `matiere`, `trimestre`, `moyenne`) VALUES
(1, 8, 1, 1, 7.1),
(2, 8, 2, 1, 1);

-- --------------------------------------------------------

--
-- Structure de la table `moyennesgenerales`
--

DROP TABLE IF EXISTS `moyennesgenerales`;
CREATE TABLE IF NOT EXISTS `moyennesgenerales` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `eleve` int(11) NOT NULL,
  `moyG` float NOT NULL,
  `classe` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `moyennesgenerales`
--

INSERT INTO `moyennesgenerales` (`id`, `eleve`, `moyG`, `classe`) VALUES
(12, 12, 6.33333, 1);

-- --------------------------------------------------------

--
-- Structure de la table `notes`
--

DROP TABLE IF EXISTS `notes`;
CREATE TABLE IF NOT EXISTS `notes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `enseignant_id` int(11) DEFAULT NULL,
  `eleve_id` int(11) DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL,
  `matiere` int(11) DEFAULT NULL,
  `type` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `valeur` double NOT NULL,
  `id_trimestre` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_11BA68CE455FCC0` (`enseignant_id`),
  KEY `IDX_11BA68CA6CC7B2` (`eleve_id`),
  KEY `IDX_11BA68C727ACA70` (`parent_id`),
  KEY `IDX_11BA68C9014574A` (`matiere`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Déchargement des données de la table `notes`
--

INSERT INTO `notes` (`id`, `enseignant_id`, `eleve_id`, `parent_id`, `matiere`, `type`, `valeur`, `id_trimestre`) VALUES
(1, 7, 8, NULL, 1, 'CC', 13, 1),
(2, 7, 8, NULL, 2, 'CC', 5, 1),
(3, 7, 8, NULL, 1, 'Devoir de controle', 15, 1),
(4, 7, 8, NULL, 1, 'CC', 14, 2),
(5, 1, 5, NULL, 3, 'CC', 14, 1);

-- --------------------------------------------------------

--
-- Structure de la table `notification`
--

DROP TABLE IF EXISTS `notification`;
CREATE TABLE IF NOT EXISTS `notification` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ens` int(11) DEFAULT NULL,
  `title` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `description` longtext COLLATE utf8_unicode_ci NOT NULL,
  `icon` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `route` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `route_parameters` longtext COLLATE utf8_unicode_ci COMMENT '(DC2Type:array)',
  `notification_date` datetime NOT NULL,
  `seen` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Déchargement des données de la table `notification`
--

INSERT INTO `notification` (`id`, `ens`, `title`, `description`, `icon`, `route`, `route_parameters`, `notification_date`, `seen`) VALUES
(1, NULL, 'Nouveau Club', ' A été crée le', NULL, '#', 'N;', '2020-02-26 18:42:59', 1),
(2, NULL, 'Nouvelle Evenement', ' A été crée', NULL, '#', 'N;', '2020-02-26 18:47:15', 1),
(3, 1, 'Une nouvelle seance à été ajouté à votre emploi', 'lundi', NULL, 'emploi_En', 'a:1:{s:2:\"id\";i:1;}', '2020-02-26 19:11:32', 1),
(4, NULL, 'Nouveau Club', ' A été crée le', NULL, '#', 'N;', '2020-02-26 21:09:22', 1),
(5, NULL, 'Nouveau Club', ' A été crée le', NULL, '#', 'N;', '2020-02-27 02:49:03', 1),
(6, NULL, 'Nouvelle Evenement', ' A été crée', NULL, '#', 'N;', '2020-02-27 02:50:06', 1),
(7, NULL, 'Nouveau Club', ' A été crée le', NULL, '#', 'N;', '2020-02-27 08:33:12', 1),
(8, 1, 'Une nouvelle seance à été ajouté à votre emploi', 'mercredi', NULL, 'emploi_En', 'a:1:{s:2:\"id\";i:1;}', '2020-02-27 09:00:17', 0);

-- --------------------------------------------------------

--
-- Structure de la table `participantevent`
--

DROP TABLE IF EXISTS `participantevent`;
CREATE TABLE IF NOT EXISTS `participantevent` (
  `evenement_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`evenement_id`,`user_id`),
  KEY `IDX_73FE4786FD02F13` (`evenement_id`),
  KEY `IDX_73FE4786A76ED395` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Déchargement des données de la table `participantevent`
--

INSERT INTO `participantevent` (`evenement_id`, `user_id`) VALUES
(1, 3),
(1, 5);

-- --------------------------------------------------------

--
-- Structure de la table `permutation`
--

DROP TABLE IF EXISTS `permutation`;
CREATE TABLE IF NOT EXISTS `permutation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `classe_s` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `raison` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `etat` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `date` datetime NOT NULL,
  `eleve_id` int(11) DEFAULT NULL,
  `parent` int(11) DEFAULT NULL,
  `enfant` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_565560E7A6CC7B2` (`eleve_id`),
  KEY `IDX_565560E73D8E604F` (`parent`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Déchargement des données de la table `permutation`
--

INSERT INTO `permutation` (`id`, `classe_s`, `raison`, `etat`, `date`, `eleve_id`, `parent`, `enfant`) VALUES
(1, '3A5', 'fdsfsdf', 'non traitee', '2020-02-27 08:58:45', 8, 6, 'yassine ghadhoune');

-- --------------------------------------------------------

--
-- Structure de la table `reclamation`
--

DROP TABLE IF EXISTS `reclamation`;
CREATE TABLE IF NOT EXISTS `reclamation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parent` int(11) DEFAULT NULL,
  `note` int(11) DEFAULT NULL,
  `date` datetime NOT NULL,
  `etat` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `details` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_CE6064043D8E604F` (`parent`),
  KEY `IDX_CE606404CFBDFA14` (`note`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Déchargement des données de la table `reclamation`
--

INSERT INTO `reclamation` (`id`, `parent`, `note`, `date`, `etat`, `details`) VALUES
(2, 6, 3, '2020-02-27 08:58:36', 'non traitee', '');

-- --------------------------------------------------------

--
-- Structure de la table `salle`
--

DROP TABLE IF EXISTS `salle`;
CREATE TABLE IF NOT EXISTS `salle` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `libelle` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UNIQ_4E977E5CA4D60759` (`libelle`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Déchargement des données de la table `salle`
--

INSERT INTO `salle` (`id`, `libelle`) VALUES
(1, 'salle 1'),
(3, 'salle 2');

-- --------------------------------------------------------

--
-- Structure de la table `sanctions`
--

DROP TABLE IF EXISTS `sanctions`;
CREATE TABLE IF NOT EXISTS `sanctions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `enseignant_id` int(11) DEFAULT NULL,
  `eleve_id` int(11) DEFAULT NULL,
  `date_sanction` date DEFAULT NULL,
  `raisonsanction` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `punition` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `etat` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_5D0A15E3E455FCC0` (`enseignant_id`),
  KEY `IDX_5D0A15E3A6CC7B2` (`eleve_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Déchargement des données de la table `sanctions`
--

INSERT INTO `sanctions` (`id`, `enseignant_id`, `eleve_id`, `date_sanction`, `raisonsanction`, `punition`, `etat`) VALUES
(1, 1, 5, '2020-04-19', 'Retard', 'Observation', 0),
(2, 1, 6, '2020-04-17', 'Travail non fait', 'Avertissement', 0);

-- --------------------------------------------------------

--
-- Structure de la table `score`
--

DROP TABLE IF EXISTS `score`;
CREATE TABLE IF NOT EXISTS `score` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `score` int(11) NOT NULL,
  `date` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_32993751A76ED395` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Déchargement des données de la table `score`
--

INSERT INTO `score` (`id`, `user_id`, `score`, `date`) VALUES
(1, 2, 7, '2020-02-18 22:49:44'),
(2, 2, 4, '2020-02-18 22:56:37'),
(3, 2, 5, '2020-02-18 22:57:41'),
(4, 2, 7, '2020-02-18 23:11:56'),
(5, 2, 7, '2020-02-18 23:13:00'),
(6, 2, 7, '2020-02-18 23:13:45'),
(7, 2, 7, '2020-02-18 23:14:17'),
(8, 2, 7, '2020-02-18 23:16:51'),
(9, 2, 7, '2020-02-18 23:18:18'),
(10, 2, 7, '2020-02-18 23:19:01'),
(11, 2, 4, '2020-02-19 12:26:28'),
(12, 2, 4, '2020-02-19 12:29:06'),
(13, 2, 4, '2020-02-19 12:29:21'),
(14, 2, 4, '2020-02-19 12:29:34'),
(15, 2, 2, '2020-02-19 14:52:40'),
(16, 2, 0, '2020-02-19 14:53:48'),
(17, 2, 0, '2020-02-19 14:54:21'),
(18, 2, 0, '2020-02-19 14:55:14'),
(19, 2, 0, '2020-02-19 15:05:09'),
(20, 2, 0, '2020-02-19 15:06:55'),
(21, 2, 0, '2020-02-19 15:07:01'),
(22, 1, 0, '2020-02-19 23:11:13'),
(23, 3, 0, '2020-02-19 23:16:43'),
(24, 3, 7, '2020-02-19 23:28:54'),
(25, 2, 7, '2020-02-19 23:37:53'),
(26, 2, 3, '2020-02-20 08:55:10'),
(27, 2, 3, '2020-02-20 09:39:37'),
(28, 2, 0, '2020-02-20 10:46:25'),
(29, 2, 4, '2020-02-20 10:48:19'),
(30, 3, 1, '2020-02-26 21:29:51'),
(31, 3, 1, '2020-02-26 21:30:46'),
(32, 3, 5, '2020-02-26 21:31:38'),
(33, 4, 2, '2020-02-26 23:30:43'),
(34, 4, 5, '2020-02-26 23:31:27'),
(35, 4, 3, '2020-02-26 23:36:49'),
(36, 5, 3, '2020-02-27 08:49:43');

-- --------------------------------------------------------

--
-- Structure de la table `seance`
--

DROP TABLE IF EXISTS `seance`;
CREATE TABLE IF NOT EXISTS `seance` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `enseignant_id` int(11) DEFAULT NULL,
  `classe_id` int(11) DEFAULT NULL,
  `salle_id` int(11) DEFAULT NULL,
  `matiere_id` int(11) DEFAULT NULL,
  `jour` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `hdeb` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `hfin` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_DF7DFD0EE455FCC0` (`enseignant_id`),
  KEY `IDX_DF7DFD0E8F5EA509` (`classe_id`),
  KEY `IDX_DF7DFD0EDC304035` (`salle_id`),
  KEY `IDX_DF7DFD0EF46CD258` (`matiere_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Déchargement des données de la table `seance`
--

INSERT INTO `seance` (`id`, `enseignant_id`, `classe_id`, `salle_id`, `matiere_id`, `jour`, `hdeb`, `hfin`) VALUES
(1, 1, 1, 1, 1, 'lundi', '08:00:00', '10:00:00'),
(2, 1, 2, 1, 1, 'mercredi', '12:00:00', '13:00:00'),
(5, 7, 4, 3, 2, 'jeudi', '10:15:00', '12:00:00');

-- --------------------------------------------------------

--
-- Structure de la table `signaler`
--

DROP TABLE IF EXISTS `signaler`;
CREATE TABLE IF NOT EXISTS `signaler` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sujet` int(11) DEFAULT NULL,
  `commentaire` int(11) DEFAULT NULL,
  `nombre` int(11) NOT NULL,
  `type` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_EF69B322E13599D` (`sujet`),
  KEY `IDX_EF69B3267F068BC` (`commentaire`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `sujet`
--

DROP TABLE IF EXISTS `sujet`;
CREATE TABLE IF NOT EXISTS `sujet` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `createur` int(11) NOT NULL,
  `titre` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `description` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `date` datetime DEFAULT NULL,
  `score` int(11) NOT NULL,
  `vues` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Déchargement des données de la table `sujet`
--

INSERT INTO `sujet` (`id`, `createur`, `titre`, `description`, `date`, `score`, `vues`) VALUES
(11, 3, 'Les domaines d\'activité', 'Organiser, préparer', '2020-04-21 00:00:00', 54, 125),
(17, 5, 'conseil', 'le conseil sera le 15 mai', '2020-04-18 00:49:08', 0, 0);

-- --------------------------------------------------------

--
-- Structure de la table `test`
--

DROP TABLE IF EXISTS `test`;
CREATE TABLE IF NOT EXISTS `test` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `question` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `reponse` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `type_intell` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Déchargement des données de la table `test`
--

INSERT INTO `test` (`id`, `question`, `reponse`, `type_intell`) VALUES
(2, 'quand je veux me rappeler d\'un roman je:', 'je me rappele du titre', 3),
(3, 'quand je veux me rappeler d\'un film je:', 'entend son titre', 1),
(4, 'quand je veux me rappeler d\'un anime je:', 'visualize le protagoniste', 2),
(5, 'Si je veux mémoriser une personne:', 'je mémorise son nom', 3),
(6, 'On me présente quelqu\'un', 'je mémorise son visage', 2),
(7, 'la nouvelle voisine nous rend visite:', 'je mémorise sa voix', 1),
(9, 'why is it ?', '45', 2),
(10, 'where is the sun?', 'in the center', 1),
(11, 'what is FBI?', 'Federal Bureau of investigation', 2),
(12, 'where is Tunisia', 'North Africa', 1),
(13, 'when did tunisia become independent?', '1956', 2),
(14, 'when did Japan Invade China?', '1937', 1),
(15, 'what color is the sky?', 'Blue', 1),
(16, 'where does sushi come from?', 'Japan', 1),
(17, 'where is the moon', 'in the sky', 1),
(18, 'what is the sun?', 'a star', 1),
(19, 'what is the moon?', 'a sattelite', 1),
(20, 'what is 5*5?', '25', 2),
(21, 'when did WWI end?', '1919', 2),
(22, ' when did WWII end?', ' 1945', 2);

-- --------------------------------------------------------

--
-- Structure de la table `thread`
--

DROP TABLE IF EXISTS `thread`;
CREATE TABLE IF NOT EXISTS `thread` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_by_id` int(11) DEFAULT NULL,
  `subject` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `created_at` datetime NOT NULL,
  `is_spam` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_31204C83B03A8386` (`created_by_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `thread_metadata`
--

DROP TABLE IF EXISTS `thread_metadata`;
CREATE TABLE IF NOT EXISTS `thread_metadata` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `thread_id` int(11) DEFAULT NULL,
  `participant_id` int(11) DEFAULT NULL,
  `is_deleted` tinyint(1) NOT NULL,
  `last_participant_message_date` datetime DEFAULT NULL,
  `last_message_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_40A577C8E2904019` (`thread_id`),
  KEY `IDX_40A577C89D1C3019` (`participant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parent_id` int(11) DEFAULT NULL,
  `classeeleve_id` int(11) DEFAULT NULL,
  `classeenseignant_id` int(11) DEFAULT NULL,
  `username` varchar(180) COLLATE utf8_unicode_ci NOT NULL,
  `username_canonical` varchar(180) COLLATE utf8_unicode_ci NOT NULL,
  `email` varchar(180) COLLATE utf8_unicode_ci NOT NULL,
  `email_canonical` varchar(180) COLLATE utf8_unicode_ci NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  `salt` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `password` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `last_login` datetime DEFAULT NULL,
  `confirmation_token` varchar(180) COLLATE utf8_unicode_ci DEFAULT NULL,
  `password_requested_at` datetime DEFAULT NULL,
  `roles` longtext COLLATE utf8_unicode_ci NOT NULL COMMENT '(DC2Type:array)',
  `nom` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `prenom` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `date_embauche` date DEFAULT NULL,
  `date_inscription` date DEFAULT NULL,
  `type_intelligence` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UNIQ_8D93D64992FC23A8` (`username_canonical`),
  UNIQUE KEY `UNIQ_8D93D649A0D96FBF` (`email_canonical`),
  UNIQUE KEY `UNIQ_8D93D649C05FB297` (`confirmation_token`),
  KEY `IDX_8D93D649727ACA70` (`parent_id`),
  KEY `IDX_8D93D6495BD46AE` (`classeeleve_id`),
  KEY `IDX_8D93D6491F7314D5` (`classeenseignant_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Déchargement des données de la table `user`
--

INSERT INTO `user` (`id`, `parent_id`, `classeeleve_id`, `classeenseignant_id`, `username`, `username_canonical`, `email`, `email_canonical`, `enabled`, `salt`, `password`, `last_login`, `confirmation_token`, `password_requested_at`, `roles`, `nom`, `prenom`, `date_embauche`, `date_inscription`, `type_intelligence`) VALUES
(1, NULL, NULL, 10, 'mohamed', 'borchani', 'mohamedyassin.ghadhoune@esprit.tn', 'mohamedyassin.ghadhoune@esprit.tn', 1, NULL, '$2y$13$DS6WpNdYKUiXrqIw3aAe1uEbRQpu/U7SSFNhW7FDpMTCCfdvuj1Om', '2020-02-27 03:42:54', NULL, NULL, 'a:1:{i:0;s:15:\"ROLE_ENSEIGNANT\";}', 'mohamed', 'borchani', '2020-02-26', '2020-02-26', 0),
(2, NULL, 2, NULL, 'ahmed', 'ahmed', 'ahmed1234@gmail.com', 'ahmed1234@gmail.com', 1, NULL, '$2y$13$DS6WpNdYKUiXrqIw3aAe1uEbRQpu/U7SSFNhW7FDpMTCCfdvuj1Om', '2020-02-27 03:43:09', NULL, NULL, 'a:1:{i:0;s:10:\"ROLE_ADMIN\";}', 'ahmed', 'turki', '2020-02-26', '2020-02-26', 0),
(3, NULL, 2, NULL, 'eleve', 'eleve', 'eleve.eleve@eleve.eleve', 'eleve.eleve@eleve.eleve', 1, NULL, '$2y$13$DS6WpNdYKUiXrqIw3aAe1uEbRQpu/U7SSFNhW7FDpMTCCfdvuj1Om', '2020-02-26 19:01:21', NULL, NULL, 'a:1:{i:0;s:10:\"ROLE_ELEVE\";}', 'yassine', 'ghadhoune', '2020-02-26', '2020-02-26', 0),
(4, NULL, 6, NULL, 'cc', 'cc', 'cc@cc.com', 'cc@cc.com', 1, NULL, '$2y$13$DS6WpNdYKUiXrqIw3aAe1uEbRQpu/U7SSFNhW7FDpMTCCfdvuj1Om', '2020-04-29 13:42:39', NULL, NULL, 'a:1:{i:0;s:10:\"ROLE_ELEVE\";}', 'wafa', 'haoues', '2020-02-26', '2020-02-26', 0),
(5, 9, 10, NULL, 'aa', 'aa', 'aa@aa.com', 'aa@aa.com', 1, NULL, '$2y$13$DS6WpNdYKUiXrqIw3aAe1uEbRQpu/U7SSFNhW7FDpMTCCfdvuj1Om', '2020-04-29 13:42:25', NULL, NULL, 'a:1:{i:0;s:10:\"ROLE_ELEVE\";}', 'olfa', 'Bennouri', '2020-02-26', '2020-02-26', 0),
(6, NULL, 10, NULL, 'bb', 'bb', 'bb@bb.com', 'bb@bb.com', 1, NULL, '$2y$13$DS6WpNdYKUiXrqIw3aAe1uEbRQpu/U7SSFNhW7FDpMTCCfdvuj1Om', '2020-04-09 22:55:46', NULL, NULL, 'a:1:{i:0;s:10:\"ROLE_ELEVE\";}', 'hamadi', 'tabben', '2020-02-26', '2020-02-26', 0),
(7, NULL, NULL, 1, 'mohamedhh', 'mohamedhh', 'mohamed12345@gmail.com', 'mohamed12345@gmail.com', 1, NULL, '$2y$13$DS6WpNdYKUiXrqIw3aAe1uEbRQpu/U7SSFNhW7FDpMTCCfdvuj1Om', '2020-02-27 08:26:41', NULL, NULL, 'a:1:{i:0;s:15:\"ROLE_ENSEIGNANT\";}', 'mohamed', 'turki', '2020-02-27', '2020-02-27', 0),
(8, 9, 1, NULL, 'test', 'test', 'test20@gmail.com', 'test20@gmail.com', 1, NULL, '$2y$13$DS6WpNdYKUiXrqIw3aAe1uEbRQpu/U7SSFNhW7FDpMTCCfdvuj1Om', '2020-02-27 08:09:30', NULL, NULL, 'a:1:{i:0;s:10:\"ROLE_ELEVE\";}', 'ibrahim', 'bennouri', '2020-02-27', '2020-02-27', 0),
(9, NULL, NULL, NULL, 'dd', 'dd', 'dd@dd.com', 'dd@dd.com', 1, NULL, '$2y$13$DS6WpNdYKUiXrqIw3aAe1uEbRQpu/U7SSFNhW7FDpMTCCfdvuj1Om', '2020-04-29 13:43:29', NULL, NULL, 'a:1:{i:0;s:11:\"ROLE_PARENT\";}', 'dalanda', 'ben mustpha', '2020-02-27', '2020-02-27', 0),
(11, 9, 6, NULL, 'kek', 'kek', 'aaa.aaaa@aaaa.kk', 'aaa.aaaa@aaaa.kk', 1, NULL, '$2y$13$DS6WpNdYKUiXrqIw3aAe1uEbRQpu/U7SSFNhW7FDpMTCCfdvuj1Om', '2020-02-26 12:11:29', NULL, NULL, 'a:1:{i:0;s:10:\"ROLE_ELEVE\";}', 'haoues', 'wafa', NULL, NULL, 1),
(12, NULL, 1, NULL, 'wafa', 'wafa', 'wafa@w.com', 'wafa@w.com', 1, NULL, '$2y$13$DS6WpNdYKUiXrqIw3aAe1uEbRQpu/U7SSFNhW7FDpMTCCfdvuj1Om', '2020-02-26 12:34:19', NULL, NULL, 'a:1:{i:0;s:10:\"ROLE_ELEVE\";}', 'wafa', 'amri', NULL, NULL, 2),
(13, NULL, 7, NULL, 'aaaa', 'aaaa', 'aaa@aaaa.kkk', 'aaa@aaaa.kkk', 1, NULL, '$2y$13$DS6WpNdYKUiXrqIw3aAe1uEbRQpu/U7SSFNhW7FDpMTCCfdvuj1Om', '2020-02-13 08:41:16', NULL, NULL, 'a:1:{i:0;s:10:\"ROLE_ELEVE\";}', 'wafa', 'takwa', NULL, NULL, 1),
(14, NULL, NULL, NULL, '000erh', '000erh', 'mm@mm.com', 'mm@mm.com', 1, NULL, '$2a$05$OJJgfcoIsECZY50xHIbUD.3aUlBwwcWmLVqrbwElmXYVQcyoQ1E8e', '2020-04-18 00:00:00', NULL, NULL, 'a:1:{i:0;s:10:\"ROLE_ROLE_PARENT\";}', 'agea', 'faezfaz', '2020-04-18', '2020-04-18', 0);

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `absences`
--
ALTER TABLE `absences`
  ADD CONSTRAINT `FK_F9C0EFFFA6CC7B2` FOREIGN KEY (`eleve_id`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `FK_F9C0EFFFE455FCC0` FOREIGN KEY (`enseignant_id`) REFERENCES `user` (`id`);

--
-- Contraintes pour la table `activity`
--
ALTER TABLE `activity`
  ADD CONSTRAINT `FK_AC74095AA76ED395` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

--
-- Contraintes pour la table `attestation`
--
ALTER TABLE `attestation`
  ADD CONSTRAINT `FK_326EC63F3D8E604F` FOREIGN KEY (`parent`) REFERENCES `user` (`id`);

--
-- Contraintes pour la table `club`
--
ALTER TABLE `club`
  ADD CONSTRAINT `FK_B8EE3872A76ED395` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

--
-- Contraintes pour la table `coeff`
--
ALTER TABLE `coeff`
  ADD CONSTRAINT `FK_A29F1C52B3E9C81` FOREIGN KEY (`niveau_id`) REFERENCES `classe` (`id`),
  ADD CONSTRAINT `FK_A29F1C52F46CD258` FOREIGN KEY (`matiere_id`) REFERENCES `matiere` (`id`);

--
-- Contraintes pour la table `commentaire`
--
ALTER TABLE `commentaire`
  ADD CONSTRAINT `FK_67F068BC2E13599D` FOREIGN KEY (`sujet`) REFERENCES `sujet` (`id`);

--
-- Contraintes pour la table `course`
--
ALTER TABLE `course`
  ADD CONSTRAINT `FK_169E6FB9A76ED395` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

--
-- Contraintes pour la table `evenement`
--
ALTER TABLE `evenement`
  ADD CONSTRAINT `FK_B26681E61190A32` FOREIGN KEY (`club_id`) REFERENCES `club` (`id`) ON DELETE CASCADE;

--
-- Contraintes pour la table `exercice`
--
ALTER TABLE `exercice`
  ADD CONSTRAINT `FK_E418C74D591CC992` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`);

--
-- Contraintes pour la table `likes`
--
ALTER TABLE `likes`
  ADD CONSTRAINT `FK_49CA4E7D2E13599D` FOREIGN KEY (`sujet`) REFERENCES `sujet` (`id`),
  ADD CONSTRAINT `FK_49CA4E7D67F068BC` FOREIGN KEY (`commentaire`) REFERENCES `commentaire` (`id`);

--
-- Contraintes pour la table `message`
--
ALTER TABLE `message`
  ADD CONSTRAINT `FK_B6BD307FE2904019` FOREIGN KEY (`thread_id`) REFERENCES `thread` (`id`),
  ADD CONSTRAINT `FK_B6BD307FF624B39D` FOREIGN KEY (`sender_id`) REFERENCES `user` (`id`);

--
-- Contraintes pour la table `message_metadata`
--
ALTER TABLE `message_metadata`
  ADD CONSTRAINT `FK_4632F005537A1329` FOREIGN KEY (`message_id`) REFERENCES `message` (`id`),
  ADD CONSTRAINT `FK_4632F0059D1C3019` FOREIGN KEY (`participant_id`) REFERENCES `user` (`id`);

--
-- Contraintes pour la table `moyennes`
--
ALTER TABLE `moyennes`
  ADD CONSTRAINT `FK_66FB2B459014574A` FOREIGN KEY (`matiere`) REFERENCES `matiere` (`id`),
  ADD CONSTRAINT `FK_66FB2B45A6CC7B2` FOREIGN KEY (`eleve_id`) REFERENCES `user` (`id`);

--
-- Contraintes pour la table `notes`
--
ALTER TABLE `notes`
  ADD CONSTRAINT `FK_11BA68C727ACA70` FOREIGN KEY (`parent_id`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `FK_11BA68C9014574A` FOREIGN KEY (`matiere`) REFERENCES `matiere` (`id`),
  ADD CONSTRAINT `FK_11BA68CA6CC7B2` FOREIGN KEY (`eleve_id`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `FK_11BA68CE455FCC0` FOREIGN KEY (`enseignant_id`) REFERENCES `user` (`id`);

--
-- Contraintes pour la table `participantevent`
--
ALTER TABLE `participantevent`
  ADD CONSTRAINT `FK_73FE4786A76ED395` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `FK_73FE4786FD02F13` FOREIGN KEY (`evenement_id`) REFERENCES `evenement` (`id`) ON DELETE CASCADE;

--
-- Contraintes pour la table `permutation`
--
ALTER TABLE `permutation`
  ADD CONSTRAINT `FK_565560E73D8E604F` FOREIGN KEY (`parent`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `FK_565560E7A6CC7B2` FOREIGN KEY (`eleve_id`) REFERENCES `user` (`id`);

--
-- Contraintes pour la table `reclamation`
--
ALTER TABLE `reclamation`
  ADD CONSTRAINT `FK_CE6064043D8E604F` FOREIGN KEY (`parent`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `FK_CE606404CFBDFA14` FOREIGN KEY (`note`) REFERENCES `notes` (`id`);

--
-- Contraintes pour la table `sanctions`
--
ALTER TABLE `sanctions`
  ADD CONSTRAINT `FK_5D0A15E3A6CC7B2` FOREIGN KEY (`eleve_id`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `FK_5D0A15E3E455FCC0` FOREIGN KEY (`enseignant_id`) REFERENCES `user` (`id`);

--
-- Contraintes pour la table `score`
--
ALTER TABLE `score`
  ADD CONSTRAINT `FK_32993751A76ED395` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

--
-- Contraintes pour la table `seance`
--
ALTER TABLE `seance`
  ADD CONSTRAINT `FK_DF7DFD0E8F5EA509` FOREIGN KEY (`classe_id`) REFERENCES `classe` (`id`),
  ADD CONSTRAINT `FK_DF7DFD0EDC304035` FOREIGN KEY (`salle_id`) REFERENCES `salle` (`id`),
  ADD CONSTRAINT `FK_DF7DFD0EE455FCC0` FOREIGN KEY (`enseignant_id`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `FK_DF7DFD0EF46CD258` FOREIGN KEY (`matiere_id`) REFERENCES `matiere` (`id`);

--
-- Contraintes pour la table `signaler`
--
ALTER TABLE `signaler`
  ADD CONSTRAINT `FK_EF69B322E13599D` FOREIGN KEY (`sujet`) REFERENCES `sujet` (`id`),
  ADD CONSTRAINT `FK_EF69B3267F068BC` FOREIGN KEY (`commentaire`) REFERENCES `commentaire` (`id`);

--
-- Contraintes pour la table `thread`
--
ALTER TABLE `thread`
  ADD CONSTRAINT `FK_31204C83B03A8386` FOREIGN KEY (`created_by_id`) REFERENCES `user` (`id`);

--
-- Contraintes pour la table `thread_metadata`
--
ALTER TABLE `thread_metadata`
  ADD CONSTRAINT `FK_40A577C89D1C3019` FOREIGN KEY (`participant_id`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `FK_40A577C8E2904019` FOREIGN KEY (`thread_id`) REFERENCES `thread` (`id`);

--
-- Contraintes pour la table `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `FK_8D93D6491F7314D5` FOREIGN KEY (`classeenseignant_id`) REFERENCES `classe` (`id`),
  ADD CONSTRAINT `FK_8D93D6495BD46AE` FOREIGN KEY (`classeeleve_id`) REFERENCES `classe` (`id`),
  ADD CONSTRAINT `FK_8D93D649727ACA70` FOREIGN KEY (`parent_id`) REFERENCES `user` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
