package GestionComandos;

import java.sql.Connection;
import java.sql.DriverManager;

import Clases.Equipo;
import Clases.Jugador;
import Interfaces.ParametrosConexion;

public class Comandos implements ParametrosConexion{
	public Connection conexion (String url,String user,String password) {
		Connection conn = null;
		
		try {
			conn = DriverManager.getConnection(ParametrosConexion.url,ParametrosConexion.user,ParametrosConexion.pass);
			if(conn== null) {
				Comandos c = new Comandos();
				c.crearTablas_SQL();
				System.out.println("Hola");
			}
			System.out.println("Conectado con exito ");
		} catch (Exception e) {
			System.out.println("Error ");
			System.out.println("==========");
			e.printStackTrace();
		}
		return conn;
	}
	public void crearTablas_SQL() {
		//Comando Creacion Tabla Teams
		String teams_table = "CREATE TABLE IF NOT EXIST TEAMS (ID_equip integer PRIMARY KEY AUTO_INCREMENT, "
				+ "nom_equip VARCHAR(20) ";
		//Comando Creacion Tabla Jugadores 
		String players_table = "CREATE TABLE IF NOT EXIST PLAYERS((ID _jugador INTEGER PRIMARY KEY AUTO_INCREMENT, "
				+ "nom_jugador VARCHAR(20) , "
				+ "posició VARCHAR(20), "
				+ "ID_equip INTEGER FOREIGN KEY (ID_equip) REFERENCES TEAMS (ID_equip), "
				+ "nom_equip VARCHAR(20) )";
	}
	public void insert(Object obj ) {
		String insert = "";
		if(obj instanceof Jugador) {
			System.out.println(obj);
		}else if(obj instanceof Equipo) {
			System.out.println(obj);
		}
	}
	public static void main(String[] args) {
		Comandos c = new Comandos();
		c.conexion(ParametrosConexion.url, ParametrosConexion.user, ParametrosConexion.pass);
	}

}
