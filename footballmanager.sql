-- phpMyAdmin SQL Dump
-- version 4.9.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Temps de generació: 11-12-2019 a les 19:15:06
-- Versió del servidor: 10.4.8-MariaDB
-- Versió de PHP: 7.3.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de dades: `footballmanager`
--

DELIMITER $$
--
-- Procediments
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `altertableteams` ()  NO SQL
alter table teams
add quality Integer$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `CreateTables` ()  NO SQL
BEGIN
        -- Creamos las Tablas --
        CREATE TABLE IF NOT EXISTS Teams(
            ID_equip integer PRIMARY KEY AUTO_INCREMENT,
            nom_equip VARCHAR(20)
        );   
        CREATE TABLE IF NOT EXISTS Players( 
            ID_jugador INTEGER PRIMARY KEY AUTO_INCREMENT,
            nom_jugador VARCHAR(20),
			posicio VARCHAR(20),
            ID_equip INTEGER,
            nom_equip VARCHAR(20),
            FOREIGN KEY (ID_equip) REFERENCES TEAMS (ID_equip)
        );
        CREATE TABLE IF NOT EXISTS Classification(
            position_id INTEGER PRIMARY KEY AUTO_INCREMENT,
            team VARCHAR(20),
            wins INTEGER,
            loses INTEGER,
            draws INTEGER,
            points INTEGER 
        );       
        CREATE TABLE IF NOT EXISTS Matchs(
            match_id INTEGER PRIMARY KEY AUTO_INCREMENT,
            teamA VARCHAR(20),
            GoalsA INTEGER,
            teamB VARCHAR(20),
            GoalsB INTEGER
        );
    END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `InsertClassification` (IN `team` VARCHAR(20), IN `wins` INT, IN `loses` INT, IN `draws` INT, IN `points` INT)  NO SQL
BEGIN
	insert into  classification (team,wins,loses,draws,points) 
    values(team,wins,loses,draws,points);
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `insertMatches` (IN `teamA` VARCHAR(20), IN `GolsA` INT, IN `teamB` VARCHAR(20), IN `GolsB` INT)  NO SQL
BEGIN 
	insert into matchs(teamA,GoalsA,teamB,GoalsB) values(teamA,GolsA,teamB,GolsB);
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `insertPlayers` (IN `nom_jugador` VARCHAR(20), IN `posicio` VARCHAR(20), IN `ID_equip` INT, IN `nom_equip` VARCHAR(20))  NO SQL
BEGIN
	insert into Players(nom_jugador,posicio,ID_equip,nom_equip) values(nom_jugador,posicio,ID_equip,nom_equip);
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `insertTeams` (IN `nom_Equip` VARCHAR(20))  NO SQL
BEGIN 
insert into Teams(nom_equip) values(nom_equip);
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Estructura de la taula `classification`
--

CREATE TABLE `classification` (
  `position_id` int(11) NOT NULL,
  `team` varchar(20) DEFAULT NULL,
  `wins` int(11) DEFAULT NULL,
  `loses` int(11) DEFAULT NULL,
  `draws` int(11) DEFAULT NULL,
  `points` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Bolcament de dades per a la taula `classification`
--

INSERT INTO `classification` (`position_id`, `team`, `wins`, `loses`, `draws`, `points`) VALUES
(19, 'FcBarcelona', 1, 0, 0, 3),
(20, 'ElPozo_Murcia', 0, 1, 0, 0),
(21, 'Movistar_Inter', 0, 0, 0, 0),
(22, 'Cartagena', 1, 0, 0, 3),
(23, 'Zaragoza', 0, 0, 1, 1),
(24, 'Santa Coloma', 0, 0, 1, 1);

-- --------------------------------------------------------

--
-- Estructura de la taula `matchs`
--

CREATE TABLE `matchs` (
  `match_id` int(11) NOT NULL,
  `teamA` varchar(20) DEFAULT NULL,
  `GoalsA` int(11) DEFAULT NULL,
  `teamB` varchar(20) DEFAULT NULL,
  `GoalsB` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Bolcament de dades per a la taula `matchs`
--

INSERT INTO `matchs` (`match_id`, `teamA`, `GoalsA`, `teamB`, `GoalsB`) VALUES
(10, 'FcBarcelona', 5, 'Movistar_Inter', 4),
(11, 'Zaragoza', 1, 'Santa Coloma', 1),
(12, 'ElPozo_Murcia', 0, 'Cartagena', 4);

-- --------------------------------------------------------

--
-- Estructura de la taula `players`
--

CREATE TABLE `players` (
  `ID_jugador` int(11) NOT NULL,
  `nom_jugador` varchar(20) DEFAULT NULL,
  `posicio` varchar(20) DEFAULT NULL,
  `ID_equip` int(11) DEFAULT NULL,
  `nom_equip` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Bolcament de dades per a la taula `players`
--

INSERT INTO `players` (`ID_jugador`, `nom_jugador`, `posicio`, `ID_equip`, `nom_equip`) VALUES
(88, 'Alex Lluch Romeu', 'Portero', 19, 'FcBarcelona'),
(89, 'Jesus Nazaret', 'Cierre', 19, 'FcBarcelona'),
(90, 'Adolfo ', 'Ala', 19, 'FcBarcelona'),
(91, 'Leandro Rodrigues', 'Pivot', 19, 'FcBarcelona'),
(92, 'Mario Rivillos', 'Ala', 19, 'FcBarcelona'),
(93, 'Carlos Eduardo', 'Portero', 20, 'ElPozo Murcia'),
(94, 'Gines Gabarron', 'Cierre', 20, 'ElPozo Murcia'),
(95, 'Antonio Fernando', 'Ala', 20, 'ElPozo Murcia'),
(96, 'Alejandro Yepes', 'Pivot', 20, 'ElPozo Murcia'),
(97, 'Miguel', 'Ala', 20, 'ElPozo Murcia'),
(98, 'Alejandro Gonsalez', 'Portero', 21, 'Movistar Inter'),
(99, 'Marlon Oliveira', 'Cierre', 21, 'Movistar Inter'),
(100, 'Fabricio Bastesini', 'Ala', 21, 'Movistar Inter'),
(101, 'Francisco Humberto', 'Pivot', 21, 'Movistar Inter'),
(102, 'Adrian Alonso', 'Ala', 21, 'Movistar Inter'),
(103, 'Alejandro Rivera', 'Portero', 22, 'Cartagena'),
(104, 'Marlon Rivallos', 'Cierre', 22, 'Cartagena'),
(105, 'Carlos Martines', 'Ala', 22, 'Cartagena'),
(106, 'Marc Morales', 'Pivot', 22, 'Cartagena'),
(107, 'Adrian Sanchez', 'Ala', 22, 'Cartagena'),
(108, 'Alejandro Oliviera', 'Portero', 23, 'Zaragoza'),
(109, 'Carlos Rivallos', 'Cierre', 23, 'Zaragoza'),
(110, 'Marc Martines', 'Ala', 23, 'Zaragoza'),
(111, 'Adrian Morales', 'Pivot', 23, 'Zaragoza'),
(112, 'Sean Sanches', 'Ala', 23, 'Zaragoza'),
(113, 'Carlos Oliviera', 'Portero', 24, 'Santa Coloma'),
(114, 'Marcos Rivallos', 'Cierre', 24, 'Santa Coloma'),
(115, 'Marcelo Martines', 'Ala', 24, 'Santa Coloma'),
(116, 'Adolf Morales', 'Pivot', 24, 'Santa Coloma');

-- --------------------------------------------------------

--
-- Estructura de la taula `teams`
--

CREATE TABLE `teams` (
  `ID_equip` int(11) NOT NULL,
  `nom_equip` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Bolcament de dades per a la taula `teams`
--

INSERT INTO `teams` (`ID_equip`, `nom_equip`) VALUES
(19, 'FcBarcelona'),
(20, 'ElPozo_Murcia'),
(21, 'Movistar_Inter'),
(22, 'Cartagena'),
(23, 'Zaragoza'),
(24, 'Santa Coloma');

--
-- Índexs per a les taules bolcades
--

--
-- Índexs per a la taula `classification`
--
ALTER TABLE `classification`
  ADD PRIMARY KEY (`position_id`);

--
-- Índexs per a la taula `matchs`
--
ALTER TABLE `matchs`
  ADD PRIMARY KEY (`match_id`);

--
-- Índexs per a la taula `players`
--
ALTER TABLE `players`
  ADD PRIMARY KEY (`ID_jugador`),
  ADD KEY `ID_equip` (`ID_equip`);

--
-- Índexs per a la taula `teams`
--
ALTER TABLE `teams`
  ADD PRIMARY KEY (`ID_equip`);

--
-- AUTO_INCREMENT per les taules bolcades
--

--
-- AUTO_INCREMENT per la taula `classification`
--
ALTER TABLE `classification`
  MODIFY `position_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;

--
-- AUTO_INCREMENT per la taula `matchs`
--
ALTER TABLE `matchs`
  MODIFY `match_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT per la taula `players`
--
ALTER TABLE `players`
  MODIFY `ID_jugador` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=117;

--
-- AUTO_INCREMENT per la taula `teams`
--
ALTER TABLE `teams`
  MODIFY `ID_equip` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;

--
-- Restriccions per a les taules bolcades
--

--
-- Restriccions per a la taula `players`
--
ALTER TABLE `players`
  ADD CONSTRAINT `players_ibfk_1` FOREIGN KEY (`ID_equip`) REFERENCES `teams` (`ID_equip`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
