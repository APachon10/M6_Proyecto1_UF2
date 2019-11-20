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

public class Comandos_SQLite {

	public static Connection connect(String db_Adress) {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(db_Adress);
			System.out.println("Conectado con Exito ");
		} catch (Exception e) {
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
	public void crearTablas_SQL(Connection conn) {
		//Comando Creacion Tabla Teams
		String teams_table = "CREATE TABLE TEAMS (ID_equip integer PRIMARY KEY AUTO_INCREMENT,nom_equip VARCHAR(20));";
		Statement pst = null;
		try {
			pst.execute(teams_table);
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
				+ "FOREIGN KEY (ID_equip) REFERENCES TEAMS (ID_equip));";
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
		Jugador j16 = new Jugador("Alejandro Rivera", "Portero", 4, "Cartagen");
		Jugador j17 = new Jugador("Marlon Rivallos", "Cierre", 4, "Cartagen");
		Jugador j18 = new Jugador("Carlos Martines", "Ala", 4, "Cartagen");
		Jugador j19 = new Jugador("Marc Morales", "Pivot", 4, "Cartagen");
		Jugador j20 = new Jugador("Adrian Sanchez", "Ala", 4, "Cartagen");

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


		//Los insertamos dentro de la tabla 
		String insert_equipo = "insert into players(nom_jugador,posicio,ID_equip,nom_equip) values(?,?,?,?);";

		Connection conn = null;
		try {
			conn = DriverManager.getConnection(ParametrosConexion.url,ParametrosConexion.user,ParametrosConexion.pass);
			PreparedStatement pst1,pst2,pst3,pst4,pst5,pst6,pst7,pst8,pst9,pst10,pst11,pst12,pst13,pst14,pst15,pst16,pst17,pst18,pst19,pst20,pst21,pst22,pst23,pst24,pst25,pst26,pst27,pst28,pst29,pst30;

			pst1 = conn.prepareStatement(insert_equipo);
			pst1.setString(1, j1.getPlayer_name());
			pst1.setString(2, j1.getPosition());
			pst1.setInt(3, j1.getId_team());
			pst1.setString(4, j1.getTeam_name());

			pst1.executeUpdate();

			pst2 = conn.prepareStatement(insert_equipo);
			pst2.setString(1, j2.getPlayer_name());
			pst2.setString(2, j2.getPosition());
			pst2.setInt(3, j2.getId_team());
			pst2.setString(4, j2.getTeam_name());

			pst2.executeUpdate();

			pst6 = conn.prepareStatement(insert_equipo);
			pst6.setString(1, j6.getPlayer_name());
			pst6.setString(2, j6.getPosition());
			pst6.setInt(3, j6.getId_team());
			pst6.setString(4, j6.getTeam_name());

			pst6.executeUpdate();

			pst7 = conn.prepareStatement(insert_equipo);
			pst7.setString(1, j7.getPlayer_name());
			pst7.setString(2, j7.getPosition());
			pst7.setInt(3, j7.getId_team());
			pst7.setString(4, j7.getTeam_name());

			pst7.executeUpdate();

			pst11 = conn.prepareStatement(insert_equipo);
			pst11.setString(1, j11.getPlayer_name());
			pst11.setString(2, j11.getPosition());
			pst11.setInt(3, j11.getId_team());
			pst11.setString(4, j11.getTeam_name());

			pst11.executeUpdate();

			pst16 = conn.prepareStatement(insert_equipo);
			pst16.setString(1, j16.getPlayer_name());
			pst16.setString(2, j16.getPosition());
			pst16.setInt(3, j16.getId_team());
			pst16.setString(4, j16.getTeam_name());

			pst16.executeUpdate();

			pst17 = conn.prepareStatement(insert_equipo);
			pst17.setString(1, j17.getPlayer_name());
			pst17.setString(2, j17.getPosition());
			pst17.setInt(3, j17.getId_team());
			pst17.setString(4, j17.getTeam_name());

			pst17.executeUpdate();	

			pst21 = conn.prepareStatement(insert_equipo);
			pst21.setString(1, j21.getPlayer_name());
			pst21.setString(2, j21.getPosition());
			pst21.setInt(3, j21.getId_team());
			pst21.setString(4, j21.getTeam_name());

			pst21.executeUpdate();	

			pst22 = conn.prepareStatement(insert_equipo);
			pst22.setString(1, j22.getPlayer_name());
			pst22.setString(2, j22.getPosition());
			pst22.setInt(3, j22.getId_team());
			pst22.setString(4, j22.getTeam_name());

			pst22.executeUpdate();	

			pst26 = conn.prepareStatement(insert_equipo);
			pst26.setString(1, j26.getPlayer_name());
			pst26.setString(2, j26.getPosition());
			pst26.setInt(3, j26.getId_team());
			pst26.setString(4, j26.getTeam_name());

			pst26.executeUpdate();	

			pst27 = conn.prepareStatement(insert_equipo);
			pst27.setString(1, j27.getPlayer_name());
			pst27.setString(2, j27.getPosition());
			pst27.setInt(3, j27.getId_team());
			pst27.setString(4, j27.getTeam_name());

			pst27.executeUpdate();	

		} catch (Exception e) {
			System.out.println("Error");
			System.out.println("=================");
			e.printStackTrace();
		}
	}
	public void carga_inicial_equipos() {
		//Creamos Los jugadores 
		Equipo eq1 = new Equipo("FcBarcelona");
		Equipo eq2 = new Equipo("ElPozo Murcia");
		Equipo eq3 = new Equipo("Movistar Inter");
		Equipo eq4 = new Equipo("Cartagen");
		Equipo eq5 = new Equipo("Zaragoza");
		Equipo eq6 = new Equipo("Santa Coloma");
		//Los insertamos dentro de la tabla 
		String insert_equipo = "insert into teams(nom_equip) values(?);";

		Connection conn = null;
		try {
			conn = DriverManager.getConnection(ParametrosConexion.url,ParametrosConexion.user,ParametrosConexion.pass);
			PreparedStatement pst1,pst2,pst3,pst4,pst5,pst6;

			pst1 = conn.prepareStatement(insert_equipo);

			pst1.setString(1, eq1.getTeam_name());
			pst1.executeUpdate();

			pst2 = conn.prepareStatement(insert_equipo);

			pst2.setString(1, eq2.getTeam_name());
			pst2.executeUpdate();

			pst3 = conn.prepareStatement(insert_equipo);

			pst3.setString(1, eq3.getTeam_name());
			pst3.executeUpdate();

			pst4 = conn.prepareStatement(insert_equipo);

			pst4.setString(1, eq4.getTeam_name());
			pst4.executeUpdate();

			pst5 = conn.prepareStatement(insert_equipo);

			pst5.setString(1, eq5.getTeam_name());
			pst5.executeUpdate();

			pst6 = conn.prepareStatement(insert_equipo);

			pst6.setString(1, eq6.getTeam_name());
			pst6.executeUpdate();

		} catch (Exception e) {
			System.out.println("Error");
			System.out.println("=================");
			e.printStackTrace();
		}
	}
}
