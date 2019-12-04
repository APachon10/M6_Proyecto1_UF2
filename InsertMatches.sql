DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `insertMatches`(IN `teamA` VARCHAR(20), IN `GolsA` INT, IN `teamB` VARCHAR(20), IN `GolsB` INT)
    NO SQL
BEGIN 
	insert into matchs(teamA,GoalsA,teamB,GoalsB) values(teamA,GolsA,teamB,GolsB);
END$$
DELIMITER ;