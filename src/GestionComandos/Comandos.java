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
					players_number = ((Number) rs.getObject(1)).intValue();
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
			try {
				System.out.println("========================");
				String select = "select * from players ";
				PreparedStatement pst3 = conn.prepareStatement(select);
				ResultSet rs2  = pst3.executeQuery();
				while(rs2.next()) {
					System.out.println(rs2.getInt(1) + " - " + rs2.getString(2) + " - " 
							+rs2.getString(3)+ " - " +rs2.getInt(4)+ " - " +rs2.getString(5) );
				}
				//Llamamos al Procedimiento para insertar jugadores
				CallableStatement cs = conn.prepareCall("{call insertPlayers(?,?,?,?)}");
				cs.setString(1, ((Jugador) obj).getPlayer_name());
				cs.setString(2, ((Jugador) obj).getPosition());
				cs.setInt(3, ((Jugador) obj).getId_team());
				cs.setString(4, ((Jugador) obj).getTeam_name());
				cs.executeUpdate();

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
			try {
				conn = DriverManager.getConnection(ParametrosConexion.url,ParametrosConexion.user,ParametrosConexion.pass);

				String select = "select * from teams ";
				PreparedStatement pst3 = conn.prepareStatement(select);
				ResultSet rs2  = pst3.executeQuery();
				while(rs2.next()) {
					System.out.println(rs2.getInt(1) + " - " + rs2.getString(2));
				}
				CallableStatement cs = null;
				cs =  conn.prepareCall("{call insertTeams(?)}");
				cs.setString(1, ((Equipo) obj).getTeam_name());
				cs.executeUpdate();

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
	//Simular Jornada 
	public void  simular_jornada() {
		ArrayList<Clasificacion> jornada = new ArrayList<Clasificacion>();
		Clasificacion c = new Clasificacion("",0,0,0,0);
		int numeroPartidos = 0;
		String query = "select count(*) from matchs";
		String query_golesA = "Select GoalsA from matchs";
		String query_golesB = "Select GoalsB from matchs";
		
		System.out.println("Clasificacion ");
		select_clasificacion();
		System.out.println("==========================");
		System.out.println("Partidos");
		select_partidos();
		
		try {
			Connection conn = DriverManager.getConnection(ParametrosConexion.url,ParametrosConexion.user,ParametrosConexion.pass);
			int i=0;
			PreparedStatement pst = conn.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			int golesA,golesB;
			while(rs.next()) {
				numeroPartidos = ((Number) rs.getObject(1)).intValue();
			}
			while(i<=numeroPartidos) {
				PreparedStatement pst1 = conn.prepareStatement(query_golesA);
				ResultSet rs1 = pst1.executeQuery();
				PreparedStatement pst2= conn.prepareStatement(query_golesB);
				ResultSet rs2  = pst2.executeQuery();
				while(rs1.next() && rs2.next()) {
					golesA = ((Number) rs1.getObject(1)).intValue();
					golesB = ((Number) rs2.getObject(1)).intValue();
					
					if(golesA > golesB) {
						String queryA= "update  classification set wins = 1 where team='FcBarcelona'";
						String queryA_B = "update  classification set points =3 where team ='FcBarcelona'";
						
						String queryB= "update classification set loses = 1 where team ='Movistas_Inter'";
						PreparedStatement pst3 = conn.prepareStatement(queryA);
						pst3.executeUpdate();
						PreparedStatement pst4 = conn.prepareStatement(queryA_B);
						pst4.executeUpdate();
						PreparedStatement pst5 = conn.prepareStatement(queryB);
						pst5.executeUpdate();
					}else if(golesB>golesA) {
						String queryA= "update classification set loses = 1 where team ='ElPozo_Murcia'";
						
						String queryB= "update classification set wins = 1 where team ='Cartagena'";
						String queryA_B = "update classification set points = 3 where team ='Cartagena'";
						
						PreparedStatement pst3 = conn.prepareStatement(queryA);
						pst3.executeUpdate();
						PreparedStatement pst4 = conn.prepareStatement(queryB);
						pst4.executeUpdate();
						PreparedStatement pst5 = conn.prepareStatement(queryA_B);
						pst5.executeUpdate();
						
					}else if(golesB==golesA) {
						String queryA= "update classification set draws = 1 where team = 'Zaragoza'";
						String queryA_B = "update classification set points = 1 where team = 'Zaragoza'";
						String queryB= "update classification set draws = 1 where team = 'Santa Coloma'";
						String queryB_B = "update classification set points = 1 where team = 'Santa Coloma'";
						
						PreparedStatement pst3 = conn.prepareStatement(queryA);
						pst3.executeUpdate();
						PreparedStatement pst4 = conn.prepareStatement(queryA_B);
						pst4.executeUpdate();
						PreparedStatement pst5 = conn.prepareStatement(queryB);
						pst5.executeUpdate();
						PreparedStatement pst6 = conn.prepareStatement(queryB_B);
						pst6.executeUpdate();
					}
				}
				i++;
			}
			System.out.println("==========================");
			System.out.println("Clasificacion despues de los partidos ");
			select_clasificacion();
		} catch (Exception e) {
			System.out.println("Error");
			System.out.println("=================");
			e.printStackTrace();
		}
	}
	public void select_partidos() {
		String query  = "Select * from matchs";
		try {
			Connection conn = DriverManager.getConnection(ParametrosConexion.url,ParametrosConexion.user,ParametrosConexion.pass);
			PreparedStatement pst = conn.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				System.out.println(rs.getInt(1)+" - "+rs.getString(2)+" - "+rs.getInt(3)+" - "+rs.getString(4)+" - "+rs.getInt(5));
			}
		} catch (Exception e) {
			System.out.println("Error ");
			System.out.println("==========");
			e.printStackTrace();
		}
	}
	public void select_clasificacion() {
		String query  = "Select * from classification";
		try {
			Connection conn = DriverManager.getConnection(ParametrosConexion.url,ParametrosConexion.user,ParametrosConexion.pass);
			PreparedStatement pst = conn.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				System.out.println(rs.getInt(1)+" - "+rs.getString(2)+" - "+rs.getInt(3)+" - "+rs.getInt(4)+" - "
			+rs.getInt(5)+" - "+rs.getInt(6));
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
				System.out.println("Hola:" + player);
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
		ArrayList<Integer> id_equipos = new ArrayList<Integer>(); 
		String query = "Select id_equip from teams";
		int id_number =0;
		try {
			Connection conn = DriverManager.getConnection(ParametrosConexion.url,ParametrosConexion.user,ParametrosConexion.pass);
			PreparedStatement pst = conn.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				id_number = rs.getInt(1);
				id_equipos.add(id_number);
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		//Creamos Los jugadores 
		Jugador j1 = new Jugador("Alex Lluch Romeu", "Portero",id_equipos.get(0),"FcBarcelona");
		Jugador j2 = new Jugador("Jesus Nazaret", "Cierre", id_equipos.get(0), "FcBarcelona");
		Jugador j3 = new Jugador("Adolfo ", "Ala", id_equipos.get(0), "FcBarcelona");
		Jugador j4 = new Jugador("Leandro Rodrigues", "Pivot", id_equipos.get(0), "FcBarcelona");
		Jugador j5 = new Jugador("Mario Rivillos", "Ala", id_equipos.get(0), "FcBarcelona");

		//JUgadores elPozo
		Jugador j6 = new Jugador("Carlos Eduardo", "Portero", id_equipos.get(1), "ElPozo Murcia");
		Jugador j7 = new Jugador("Gines Gabarron", "Cierre", id_equipos.get(1), "ElPozo Murcia");
		Jugador j8 = new Jugador("Antonio Fernando", "Ala", id_equipos.get(1), "ElPozo Murcia");
		Jugador j9 = new Jugador("Alejandro Yepes", "Pivot", id_equipos.get(1), "ElPozo Murcia");
		Jugador j10 = new Jugador("Miguel", "Ala", id_equipos.get(1), "ElPozo Murcia");

		//Jugadores Movistar Inter
		Jugador j11 = new Jugador("Alejandro Gonsalez", "Portero", id_equipos.get(2), "Movistar Inter");
		Jugador j12 = new Jugador("Marlon Oliveira", "Cierre", id_equipos.get(2), "Movistar Inter");
		Jugador j13 = new Jugador("Fabricio Bastesini", "Ala", id_equipos.get(2), "Movistar Inter");
		Jugador j14 = new Jugador("Francisco Humberto", "Pivot", id_equipos.get(2), "Movistar Inter");
		Jugador j15 = new Jugador("Adrian Alonso", "Ala", id_equipos.get(2), "Movistar Inter");

		//Jugadores Cartagena
		Jugador j16 = new Jugador("Alejandro Rivera", "Portero", id_equipos.get(3), "Cartagena");
		Jugador j17 = new Jugador("Marlon Rivallos", "Cierre", id_equipos.get(3), "Cartagena");
		Jugador j18 = new Jugador("Carlos Martines", "Ala", id_equipos.get(3), "Cartagena");
		Jugador j19 = new Jugador("Marc Morales", "Pivot", id_equipos.get(3), "Cartagena");
		Jugador j20 = new Jugador("Adrian Sanchez", "Ala", id_equipos.get(3), "Cartagena");

		//Jugadores Zaragoza
		Jugador j21 = new Jugador("Alejandro Oliviera", "Portero", id_equipos.get(4), "Zaragoza");
		Jugador j22 = new Jugador("Carlos Rivallos", "Cierre", id_equipos.get(4), "Zaragoza");
		Jugador j23 = new Jugador("Marc Martines", "Ala", id_equipos.get(4), "Zaragoza");
		Jugador j24 = new Jugador("Adrian Morales", "Pivot", id_equipos.get(4), "Zaragoza");
		Jugador j25 = new Jugador("Sean Sanches", "Ala", id_equipos.get(4), "Zaragoza");

		//Jugadores Santa Coloma 
		Jugador j26 = new Jugador("Carlos Oliviera", "Portero", id_equipos.get(5), "Santa Coloma");
		Jugador j27 = new Jugador("Marcos Rivallos", "Cierre", id_equipos.get(5), "Santa Coloma");
		Jugador j28 = new Jugador("Marcelo Martines", "Ala", id_equipos.get(5), "Santa Coloma");
		Jugador j29 = new Jugador("Adolf Morales", "Pivot", id_equipos.get(5), "Santa Coloma");

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


	public void alterTable() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(ParametrosConexion.url,ParametrosConexion.user,ParametrosConexion.pass);
			CallableStatement cs = conn.prepareCall("{call altertableteams()}");
			cs.executeUpdate();
		} catch (Exception e) {
			System.out.println("Error");
			System.out.println("=================");
			e.printStackTrace();
		}
	}
	public void updateClassification() {
		Connection conn = null;
		try {

		} catch (Exception e) {
			System.out.println("Error");
			System.out.println("=================");
			e.printStackTrace();
		}
	}

	/*Procemidientos `para mirar si las tablas estan vacias o no */
	public boolean consultarTablaEquipos() {
		boolean confirm = false;
		String query = "Select * from teams";
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(ParametrosConexion.url,ParametrosConexion.user,ParametrosConexion.pass);
			PreparedStatement pst = conn.prepareStatement(query);
			ResultSet rs =pst.executeQuery();
			while(rs.next()) {
				confirm =  true;
			}
		} catch (Exception e) {
			System.out.println("Error");
			System.out.println("=================");
			e.printStackTrace();
		}
		return confirm;
	}
	public boolean consultarTablajugadores() {
		boolean confirm = false;
		String query = "Select * from players";
		Connection conn= null;
		try {
			conn = DriverManager.getConnection(ParametrosConexion.url,ParametrosConexion.user,ParametrosConexion.pass);
			PreparedStatement pst = conn.prepareStatement(query);
			ResultSet rs =pst.executeQuery();
			while(rs.next()) {
				confirm =  true;
			}
		} catch (Exception e) {
			System.out.println("Error");
			System.out.println("=================");
			e.printStackTrace();
		}
		return confirm;
	}
	public boolean consultarTablapartidos() {
		boolean confirm = false;
		String query = "Select * from matchs";
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(ParametrosConexion.url,ParametrosConexion.user,ParametrosConexion.pass);
			PreparedStatement pst = conn.prepareStatement(query);
			ResultSet rs =pst.executeQuery();
			while(rs.next()) {
				confirm =  true;
			}
		} catch (Exception e) {
			System.out.println("Error");
			System.out.println("=================");
			e.printStackTrace();
		}
		return confirm;
	}
	public boolean consultarTablaClasificacion() {
		boolean confirm = false;
		String query = "Select * from classification";
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(ParametrosConexion.url,ParametrosConexion.user,ParametrosConexion.pass);
			PreparedStatement pst = conn.prepareStatement(query);
			ResultSet rs =pst.executeQuery();
			while(rs.next()) {
				confirm =  true;
			}
		} catch (Exception e) {
			System.out.println("Error");
			System.out.println("=================");
			e.printStackTrace();
		}
		return confirm;
	}
}

