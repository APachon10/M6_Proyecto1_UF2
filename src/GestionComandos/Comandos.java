package GestionComandos;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

import Clases.Clasificacion;
import Clases.Equipo;
import Clases.Jugador;
import Clases.Partido;
import Interfaces.ParametrosConexion;

public class Comandos implements ParametrosConexion{
	public Connection conexion (String url,String user,String password) {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(ParametrosConexion.url,ParametrosConexion.user,ParametrosConexion.pass);
			System.out.println("Conectado con exito ");
		} catch (Exception e) {

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
			for (int i = 0; i < 6; i++) {
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

				System.out.println("========================");
				String select = "select * from players ";
				PreparedStatement pst3 = conn.prepareStatement(select);
				ResultSet rs2  = pst3.executeQuery();
				while(rs2.next()) {
					System.out.println(rs2.getInt(1) + " - " + rs2.getString(2) + " - " 
							+rs2.getString(3)+ " - " +rs2.getInt(4)+ " - " +rs2.getString(5) );

				}
				pst1 = conn.prepareStatement(insert);
				pst1.setString(1, ((Jugador) obj).getPlayer_name());
				pst1.setString(2, ((Jugador) obj).getPosition());
				pst1.setInt(3, ((Jugador) obj).getId_team());
				pst1.setString(4, ((Jugador) obj).getTeam_name());

				pst1.executeUpdate();

				System.out.println("========================");
				String select2 = "select * from players ";
				PreparedStatement pst2 = conn.prepareStatement(select2);
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

				String select = "select * from teams ";
				PreparedStatement pst3 = conn.prepareStatement(select);
				ResultSet rs2  = pst3.executeQuery();
				while(rs2.next()) {
					System.out.println(rs2.getInt(1) + " - " + rs2.getString(2));
				}
				pst1 = conn.prepareStatement(insert);
				pst1.setString(1, ((Equipo) obj).getTeam_name());

				pst1.executeUpdate();
				System.out.println("========================");
				String select2 = "select * from teams ";
				PreparedStatement pst2 = conn.prepareStatement(select2);
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
		try {
			conn = DriverManager.getConnection(ParametrosConexion.url,ParametrosConexion.user,ParametrosConexion.pass);
			CallableStatement cs = conn.prepareCall("{call CreateTables()}");
			cs.executeUpdate();
		} catch (Exception e) {
			System.out.println("Error");
			System.out.println("=================");
			e.printStackTrace();
		}
	}

	public void carga_incial_jugadores() {
		Comandos c = new Comandos();
		ArrayList<Jugador> lista_jugadores = c.crear_Lista_Jugadores();
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(ParametrosConexion.url,ParametrosConexion.user,ParametrosConexion.pass);
			CallableStatement cs=null;
			for (Jugador player : lista_jugadores) {
				cs =  conn.prepareCall("{call insertPlayers(?,?,?,?)}");
				cs.setString(1, player.getPlayer_name());
				cs.setString(2, player.getPosition());
				cs.setInt(3, player.getId_team());
				cs.setString(4, player.getTeam_name());
				cs.executeUpdate();
			}
		} catch (Exception e) {
			System.out.println("Error");
			System.out.println("=================");
			e.printStackTrace();
		}
	}
	public void carga_inicial_equipos() {
		Connection conn = null;
		Comandos c = new Comandos();
		ArrayList<Equipo> Equipos = c.crear_Lista_Equipos();

		try {
			conn = DriverManager.getConnection(ParametrosConexion.url,ParametrosConexion.user,ParametrosConexion.pass);
			CallableStatement cs=null;
			for (Equipo equipo : Equipos) {
				cs =  conn.prepareCall("{call insertTeams(?)}");
				cs.setString(1, equipo.getTeam_name());
				cs.executeUpdate();
			}
		} catch (Exception e) {
			System.out.println("Error");
			System.out.println("=================");
			e.printStackTrace();
		}
	}
	public void carga_inicial_partidos() {
		Connection conn = null;
		Comandos c = new Comandos();
		ArrayList<Partido> Partidos = c.crear_Lista_partidos();

		try {
			conn = DriverManager.getConnection(ParametrosConexion.url,ParametrosConexion.user,ParametrosConexion.pass);
			CallableStatement cs=null;
			for (Partido partido : Partidos) {
				cs =  conn.prepareCall("{call insertMatches(?,?,?,?)}");
				cs.setString(1,partido.getTeamA());
				cs.setInt(2,partido.getGolsA());
				cs.setString(3,partido.getTeamB());
				cs.setInt(4,partido.getGolsB());
				cs.executeUpdate();
			}
		} catch (Exception e) {
			System.out.println("Error");
			System.out.println("=================");
			e.printStackTrace();
		}

	}
	public void carga_inicial_clasificacion() {
		Comandos c = new Comandos();
		Connection conn = null;
		ArrayList<Equipo> lista_equipos = c.crear_Lista_Equipos();
		ArrayList<Clasificacion> nombre_equipos =new ArrayList<Clasificacion>();
		Clasificacion c2 = null;
		for (Equipo equipo : lista_equipos) {
			c2 = new Clasificacion(equipo.getTeam_name());
			nombre_equipos.add(c2);
		}
		CallableStatement cs = null;
		try {
			conn = DriverManager.getConnection(ParametrosConexion.url,ParametrosConexion.user,ParametrosConexion.pass);
			for (Clasificacion clasificacion : nombre_equipos) {
				cs =conn.prepareCall("{call InsertClassification(?,?,?,?,?)}");
				cs.setString(1, clasificacion.getTeam());
				cs.setInt(2, 0);
				cs.setInt(3,0);
				cs.setInt(4, 0);
				cs.setInt(5, 0);
				
				cs.executeUpdate();
			}
		} catch (Exception e) {
			System.out.println("Error");
			System.out.println("=================");
			e.printStackTrace();
		}
	}

	public ArrayList crear_Lista_Equipos() {
		ArrayList<Equipo> Equipos = new ArrayList<Equipo>();
		//Creamos Los Equipos
		Equipo eq1 = new Equipo("FcBarcelona");
		Equipo eq2 = new Equipo("ElPozo_Murcia");
		Equipo eq3 = new Equipo("Movistar_Inter");
		Equipo eq4 = new Equipo("Cartagena");
		Equipo eq5 = new Equipo("Zaragoza");
		Equipo eq6 = new Equipo("Santa Coloma");

		// los insertamos en el ArrayList
		Equipos.add(eq1);
		Equipos.add(eq2);
		Equipos.add(eq3);
		Equipos.add(eq4);
		Equipos.add(eq5);
		Equipos.add(eq6);

		return Equipos;
	}
	public ArrayList crear_Lista_Jugadores() {
		ArrayList<Jugador> jugadores = new ArrayList<Jugador>();

		//Creamos Los jugadores 
		Jugador j1 = new Jugador("Alex Lluch Romeu", "Portero", 1, "FcBarcelona");
		Jugador j2 = new Jugador("Jesus Nazaret", "Cierre", 1, "FcBarcelona");
		Jugador j3 = new Jugador("Adolfo ", "Ala", 1, "FcBarcelona");
		Jugador j4 = new Jugador("Leandro Rodrigues", "Pivot", 1, "FcBarcelona");
		Jugador j5 = new Jugador("Mario Rivillos", "Ala", 1, "FcBarcelona");

		//JUgadores elPozo
		Jugador j6 = new Jugador("Carlos Eduardo", "Portero", 2, "ElPozo Murcia");
		Jugador j7 = new Jugador("Gines Gabarron", "Cierre", 2, "ElPozo Murcia");
		Jugador j8 = new Jugador("Antonio Fernando", "Ala", 2, "ElPozo Murcia");
		Jugador j9 = new Jugador("Alejandro Yepes", "Pivot", 2, "ElPozo Murcia");
		Jugador j10 = new Jugador("Miguel", "Ala", 2, "ElPozo Murcia");

		//Jugadores Movistar Inter
		Jugador j11 = new Jugador("Alejandro Gonsalez", "Portero", 3, "Movistar Inter");
		Jugador j12 = new Jugador("Marlon Oliveira", "Cierre", 3, "Movistar Inter");
		Jugador j13 = new Jugador("Fabricio Bastesini", "Ala", 3, "Movistar Inter");
		Jugador j14 = new Jugador("Francisco Humberto", "Pivot", 3, "Movistar Inter");
		Jugador j15 = new Jugador("Adrian Alonso", "Ala", 3, "Movistar Inter");

		//Jugadores Cartagena
		Jugador j16 = new Jugador("Alejandro Rivera", "Portero", 4, "Cartagena");
		Jugador j17 = new Jugador("Marlon Rivallos", "Cierre", 4, "Cartagena");
		Jugador j18 = new Jugador("Carlos Martines", "Ala", 4, "Cartagena");
		Jugador j19 = new Jugador("Marc Morales", "Pivot", 4, "Cartagena");
		Jugador j20 = new Jugador("Adrian Sanchez", "Ala", 4, "Cartagena");

		//Jugadores Zaragoza
		Jugador j21 = new Jugador("Alejandro Oliviera", "Portero", 5, "Zaragoza");
		Jugador j22 = new Jugador("Carlos Rivallos", "Cierre", 5, "Zaragoza");
		Jugador j23 = new Jugador("Marc Martines", "Ala", 5, "Zaragoza");
		Jugador j24 = new Jugador("Adrian Morales", "Pivot", 5, "Zaragoza");
		Jugador j25 = new Jugador("Sean Sanches", "Ala", 5, "Zaragoza");

		//Jugadores Santa Coloma 
		Jugador j26 = new Jugador("Carlos Oliviera", "Portero", 6, "Santa Coloma");
		Jugador j27 = new Jugador("Marcos Rivallos", "Cierre", 6, "Santa Coloma");
		Jugador j28 = new Jugador("Marcelo Martines", "Ala", 6, "Santa Coloma");
		Jugador j29 = new Jugador("Adolf Morales", "Pivot", 6, "Santa Coloma");

		jugadores.add(j1);
		jugadores.add(j2);
		jugadores.add(j3);
		jugadores.add(j4);
		jugadores.add(j5);
		jugadores.add(j6);
		jugadores.add(j7);
		jugadores.add(j8);
		jugadores.add(j9);
		jugadores.add(j10);
		jugadores.add(j11);
		jugadores.add(j12);
		jugadores.add(j13);
		jugadores.add(j14);
		jugadores.add(j15);
		jugadores.add(j16);
		jugadores.add(j17);
		jugadores.add(j18);
		jugadores.add(j19);
		jugadores.add(j20);
		jugadores.add(j21);
		jugadores.add(j22);
		jugadores.add(j23);
		jugadores.add(j24);
		jugadores.add(j25);
		jugadores.add(j26);
		jugadores.add(j27);
		jugadores.add(j28);
		jugadores.add(j29);

		return jugadores;
	}
	public ArrayList crear_Lista_partidos() {
		ArrayList<Partido> lista_partidos = new ArrayList<Partido>();
		Partido p1 = new Partido("FcBarcelona",5,"Movistar_Inter",4);
		Partido p2 = new Partido("ElPozo_Murcia",0,"Cartagena",4);
		Partido p3 = new Partido("Zaragoza",1,"Santa Coloma",1);

		lista_partidos.add(p1);
		lista_partidos.add(p3);
		lista_partidos.add(p2);
		return lista_partidos;
	}
}

