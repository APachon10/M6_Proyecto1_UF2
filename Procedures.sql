

CREATE OR REPLACE PROCEDURE GenerarTablas() AS 
    BEGIN
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
            drawn INTEGER,
            points INTEGER 
        );       
        CREATE TABLE IF NOT EXISTS Matchs(
            match_id INTEGER PRIMARY KEY AUTO_INCREMENT,
            teamA VARCHAR(20),
            GoalsA INTEGER,
            teamB VARCHAR(20),
            GoalsB INTEGER
        );
    END;
