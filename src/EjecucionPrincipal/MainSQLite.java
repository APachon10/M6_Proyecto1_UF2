package EjecucionPrincipal;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import Clases.Equipo;
import Clases.Jugador;
import GestionComandos.Comandos;
import GestionComandos.Comandos_SQLite;
import Interfaces.ParametrosConexion;


public class MainSQLite {
	public static void main(String[] args) {
		Comandos_SQLite c = new Comandos_SQLite();
		
		String db_Adress = "jdbc:sqlite:../M6_Proyecto1_UF2/footballManager.db";
		System.out.println(db_Adress);
		
		File f = new File(db_Adress);

		if (!f.exists()) {
			Connection conn = c.connect(db_Adress);
			Menu(conn);
		}else {
			Connection conn = c.connect(db_Adress);
			Comandos_SQLite c2 = new Comandos_SQLite();
			c2.crearTablas_SQL(conn);
			c2.carga_inicial_equipos();
			c2.carga_incial_jugadores();
			Menu(conn);
		} 
	}
	public static void Menu(Connection conn) {
		Scanner scan = new Scanner(System.in);
		int opcion = 0;
		String confirmacion = "";
		do {
			System.out.println("====================================");
			System.out.println("Que quieres hacer?"
					+ "\n1 - Mostrar Equipos de la Liga"
					+ "\n2 - Mostrar Informacion de Jugadores por Equipo"
					+ "\n3 - Validar si todos los equipos tienen 5 jugadores como minimo "
					+ "\n4 - Insertar Jugador"
					+ "\n5 - Eliminar Jugador"
					+ "\n6 - Intercambiar Jugadores entre Equipos");
			System.out.println("=================");
			System.out.print("Opcion:");
			opcion = scan.nextInt();
			switch (opcion) {
			case 1:
				Comandos_SQLite c1 = new Comandos_SQLite();
				c1.select_equipos_liga(conn);
				break;
			case 2:
				Comandos_SQLite c2 = new Comandos_SQLite();
				c2.mostrar_info_jugadores_por_equipo(conn);
				break;
			case 3:
				Comandos_SQLite c3 = new Comandos_SQLite();
				c3.contarjugadores(conn);
				break;
			case 4:
				Scanner scan2 = new Scanner(System.in); 
				Comandos_SQLite c4 = new Comandos_SQLite();
				String player_name,pos,team_name;
				int id_team;
				System.out.print("Nombre Jugador:");
				player_name = scan.next();
				System.out.print("Posicion:");
				pos = scan.next();
				System.out.print("id de Equipo: ");
				id_team  = scan.nextInt();
				System.out.print("Nombre Equipo");
				team_name = scan.next();

				Object obj = new Jugador(player_name, pos, id_team, team_name);
				c4.insert(obj,conn);
				break;
			case 5:
				Scanner scan3 = new Scanner(System.in); 
				Comandos_SQLite c5 = new Comandos_SQLite();
				int id_player = 0;

				System.out.print("Introduce la id del jugador que quieres eliminar? ");
				id_player= scan.nextInt();

				c5.Eliminar(id_player, conn);
			}
			System.out.print("Quieres Continuar?");
			confirmacion = scan.next();
		} while (!confirmacion.equalsIgnoreCase("No"));
		System.out.println("===========================");
		System.out.println("Sesion Terminada");
	}
}
