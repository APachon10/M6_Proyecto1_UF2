package GestionComandos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

import Clases.Equipo;
import Clases.Jugador;
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
	public void crearTablas_SQL() {
		Connection conn = null;
		//Comando Creacion Tabla Teams
		String teams_table = "CREATE TABLE TEAMS (ID_equip integer PRIMARY KEY AUTO_INCREMENT,nom_equip VARCHAR(20))";
		PreparedStatement pst = null;
		try {
			conn = DriverManager.getConnection(ParametrosConexion.url,ParametrosConexion.user,ParametrosConexion.pass);
			pst = conn.prepareStatement(teams_table);
			pst.executeUpdate();
		} catch (Exception e) {
			System.out.println("Error");
			System.out.println("=================");
			e.printStackTrace();
		}
		//Comando Creacion Tabla Jugadores 
		String players_table = "CREATE TABLE PLAYERS(ID_jugador INTEGER PRIMARY KEY AUTO_INCREMENT, "
				+ "nom_jugador VARCHAR(20),"
				+ "posició VARCHAR(20), "
				+ "ID_equip INTEGER,"
				+ "nom_equip VARCHAR(20),"
				+ "FOREIGN KEY (ID_equip) REFERENCES TEAMS (ID_equip))";
		PreparedStatement pst2 = null;
		try {
			conn = DriverManager.getConnection(ParametrosConexion.url,ParametrosConexion.user,ParametrosConexion.pass);
			pst2 = conn.prepareStatement(players_table);
			pst2.executeUpdate();
		} catch (Exception e) {
			System.out.println("Error");
			System.out.println("=================");
			e.printStackTrace();
		}
	}
	public void carga_incial_datos() {
//		String insert_equipo = 
	}
	public void insert(Object obj ) {
		String insert = "";
		if(obj instanceof Jugador) {
//			insert = "insert into Jugador "
		}else if(obj instanceof Equipo) {
			System.out.println(obj);
		}
	}
	public static void main(String[] args) {
		Comandos c = new Comandos();
		c.conexion(ParametrosConexion.url, ParametrosConexion.user, ParametrosConexion.pass);
	}

}
