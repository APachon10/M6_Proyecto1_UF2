package GestionComandos;

import java.sql.Connection;
import java.sql.DriverManager;
import Interfaces.ParametrosConexion;

public class Comandos implements ParametrosConexion{
	public Connection conexion (String url,String user,String password) {
		Connection conn = null;
		
		try {
			conn = DriverManager.getConnection(ParametrosConexion.url,ParametrosConexion.user,ParametrosConexion.pass);
			System.out.println("Conectado con exito ");
		} catch (Exception e) {
			System.out.println("Error ");
			System.out.println("==========");
			e.printStackTrace();
		}
		return conn;
	}
	public void crearTablas() {
		//Comando Creacion Tabla Teams
		String teams_table = "CREATE TABLE IF NOT EXIST TEAMS (ID_equip integer PRIMARY KEY AUTO_INCREMENT, "
				+ "nom_equip VARCHAR(20) ";
		//Comando Creacion Tabla Jugadores 
		String players_table = "CREATE TABLE IF NOT EXIST PLAYERS((ID _jugador INTEGER PRIMARY KEY AUTO_INCREMENT, "
				+ "nom_jugador VARCHAR(20) , "
				+ "posici� VARCHAR(20), "
				+ "ID_equip INTEGER FOREIGN KEY (ID_equip) REFERENCES TEAMS (ID_equip), nom_equip)";
	}
}
