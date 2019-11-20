package GestionComandos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

import Clases.Equipo;
import Clases.Jugador;
import Interfaces.ParametrosConexion;

public class Comandos implements ParametrosConexion{
	public void crearBasedeDatos() {
		
		String drive = "jdbc:mysql://localhost:3306/";
		String query = "Create database footballmanager";
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(drive,ParametrosConexion.user,ParametrosConexion.pass);
			PreparedStatement pst = conn.prepareStatement(query);
			pst.executeUpdate();
		} catch (Exception e) {
			System.out.println("Error ");
			System.out.println("==========");
			e.printStackTrace();
		}
		
	}
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
	//Funcionalidad 1 
	public void select_equipos_liga(Connection conn) {
		String select_equipos = "select * from teams ";
		try {
			PreparedStatement pst = conn.prepareStatement(select_equipos); 
			ResultSet rs = pst.executeQuery();
			System.out.println("==============================");
			while(rs.next()) {
				System.out.println(rs.getInt(1)+" - "+rs.getString(2));
			}
			System.out.println("==============================");
		} catch (Exception e) {
			System.out.println("Error");
			System.out.println("=================");
			e.printStackTrace();
		}
	}
	//Funcionalidad 2
	public void mostrar_info_jugadores_por_equipo(Connection conn) {
		Scanner scan = new Scanner(System.in);
		String team_name= "";
		System.out.println("Que equipo quieres inspeccionar? ");
		team_name = scan.next();
		String select_players = "select * from players where nom_equip = "+"'"+team_name+"'";
		try {
			PreparedStatement pst = conn.prepareStatement(select_players); 
			ResultSet rs = pst.executeQuery();
			System.out.println("==============================");
			while(rs.next()) {
				System.out.println(rs.getInt(1)+" - "+rs.getString(2)+" - "+rs.getString(3)+" - "+rs.getInt(4)
				+" - "+rs.getString(5));
			}
			System.out.println("==============================");
		} catch (Exception e) {
			System.out.println("Error");
			System.out.println("=================");
			e.printStackTrace();
		}
	}
	//Funcionalidad 3
	public void contarjugadores(Connection conn) {
		int players_number = 0;
		try {
			for (int i = 0; i < 2; i++) {
				String validacion = "SELECT COUNT(*) from teams where ID_equip=" + i;
				PreparedStatement pst = conn.prepareStatement(validacion);

				ResultSet rs = pst.executeQuery();
				while(rs.next()) {
					players_number = rs.getInt(1);
				}

				if(players_number == 5) {
					System.out.println("El equipo tiene almenos 5 jugadores");
				}else{
					System.out.println("El equipo no tiene almenos 5 jugadores");
				}
			} 
		}catch (Exception e) {
			System.out.println("Error");
			System.out.println("=================");
			e.printStackTrace();
		}
	}
	//Funcionalidad 4 ( insertar)
	public void insert(Object obj,Connection conn) {
		String insert = "";
		if(obj instanceof Jugador) {
			insert = "insert into players(nom_jugador,posicio,ID_equip,nom_equip) values(?,?,?,?)";
			try {
				PreparedStatement pst1;

				pst1 = conn.prepareStatement(insert);
				pst1.setString(1, ((Jugador) obj).getPlayer_name());
				pst1.setString(2, ((Jugador) obj).getPosition());
				pst1.setInt(3, ((Jugador) obj).getId_team());
				pst1.setString(4, ((Jugador) obj).getTeam_name());

				pst1.executeUpdate();
				
				System.out.println("========================");
				String select = "select * from players ";
				PreparedStatement pst2 = conn.prepareStatement(select);
				ResultSet rs  = pst2.executeQuery();
				while(rs.next()) {
					System.out.println(rs.getInt(1) + " - " + rs.getString(2) + " - " 
					+rs.getString(3)+ " - " +rs.getInt(4)+ " - " +rs.getString(5) );
					
				}
			} catch (Exception e) {
				System.out.println("Error");
				System.out.println("=================");
				e.printStackTrace();
			}
		}else if(obj instanceof Equipo) {
			insert = "insert into teams(nom_equip) values(?)";
			try {
				conn = DriverManager.getConnection(ParametrosConexion.url,ParametrosConexion.user,ParametrosConexion.pass);
				PreparedStatement pst1;

				pst1 = conn.prepareStatement(insert);
				pst1.setString(1, ((Equipo) obj).getTeam_name());

				pst1.executeUpdate();
				System.out.println("========================");
				String select = "select * from teams ";
				PreparedStatement pst2 = conn.prepareStatement(select);
				ResultSet rs  = pst2.executeQuery();
				while(rs.next()) {
					System.out.println(rs.getInt(1) + " - " + rs.getString(2));
					
				}
			} catch (Exception e) {
				System.out.println("Error");
				System.out.println("=================");
				e.printStackTrace();
			}
		}
	} 
	//Funcionalidad 4 
	public void Eliminar(int id,Connection conn){
		String delete_query = "delete from players where ID_jugador = "+id;
		try {
			PreparedStatement pst = conn.prepareStatement(delete_query);
			pst.executeUpdate();
			System.out.println("========================");
			String select = "select * from players ";
			PreparedStatement pst2 = conn.prepareStatement(select);
			ResultSet rs  = pst2.executeQuery();
			while(rs.next()) {
				System.out.println(rs.getInt(1) + " - " + rs.getString(2) + " - " 
				+rs.getString(3)+ " - " +rs.getInt(4)+ " - " +rs.getString(5) );
				
			}
		} catch (Exception e) {
			System.out.println("Error ");
			System.out.println("==========");
			e.printStackTrace();
		}
	}
	//Carga Inicial de Datos 
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
				+ "posicio VARCHAR(20), "
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
	public void carga_incial_jugadores() {
		//Creamos Los jugadores 
		Jugador j1 = new Jugador("Alberto Pachon", "Cierre", 1, "FcBarcelona");
		//Los insertamos dentro de la tabla 
		String insert_equipo = "insert into players(nom_jugador,posici�,ID_equip,nom_equip) values(?,?,?,?)";

		Connection conn = null;
		try {
			conn = DriverManager.getConnection(ParametrosConexion.url,ParametrosConexion.user,ParametrosConexion.pass);
			PreparedStatement pst1,pst2,pst3,pst4,pst5;

			pst1 = conn.prepareStatement(insert_equipo);
			pst1.setString(1, j1.getPlayer_name());
			pst1.setString(2, j1.getPosition());
			pst1.setInt(3, j1.getId_team());
			pst1.setString(4, j1.getTeam_name());

			pst1.executeUpdate();

		} catch (Exception e) {
			System.out.println("Error");
			System.out.println("=================");
			e.printStackTrace();
		}
	}
	public void carga_inicial_equipos() {

	}

}

