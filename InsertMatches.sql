DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `insertMatches`(IN `teamA` VARCHAR(20), IN `GolsA` INT, IN `teamB` VARCHAR(20), IN `GolsB` INT)
    NO SQL
BEGIN 
	insert into matchs(teamA,GolsA,teamB,GolsB) values(teamA,GolsA,teamB,GolsB);
END$$
DELIMITER ;