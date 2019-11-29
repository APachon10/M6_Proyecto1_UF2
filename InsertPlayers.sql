DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `insertPlayers`(IN `nom_jugador` VARCHAR(20), IN `posicio` VARCHAR(20), IN `ID_equip` INT, IN `nom_equip` VARCHAR(20))
    NO SQL
BEGIN
	insert into Players(nom_jugador,posicio,ID_equip,nom_equip) values(nom_jugador,posicio,ID_equip,nom_equip);
END$$
DELIMITER ;