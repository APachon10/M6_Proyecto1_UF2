DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `InsertClassification`(IN `team` VARCHAR(20), IN `wins` INT, IN `loses` INT, IN `draws` INT, IN `points` INT)
    NO SQL
BEGIN
	insert into  classification (team,wins,loses,draws,points) 
    values(team,wins,loses,draws,points);
END$$
DELIMITER ;