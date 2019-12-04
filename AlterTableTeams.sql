DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `altertableteams`()
    NO SQL
alter table teams
add quality Integer$$
DELIMITER ;