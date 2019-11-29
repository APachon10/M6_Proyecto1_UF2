DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `insertTeams`(IN `nom_Equip` VARCHAR(20))
    NO SQL
BEGIN 
insert into Teams(nom_equip) values(nom_equip);
END$$
DELIMITER ;