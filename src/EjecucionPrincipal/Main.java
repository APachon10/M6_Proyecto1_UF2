package EjecucionPrincipal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Scanner;

import Clases.Equipo;
import Clases.Jugador;
import GestionComandos.Comandos;
import Interfaces.ParametrosConexion;

public class Main implements ParametrosConexion{
	public static void main(String[] args) {
		/*Hacemos la Conexion */
		Comandos c = new Comandos();
		Connection conn = c.conexion(ParametrosConexion.url, ParametrosConexion.user, ParametrosConexion.pass);
		conn = c.conexion(ParametrosConexion.url, ParametrosConexion.user, ParametrosConexion.pass);
		/*Metemos los Datos dentro de la tabla */
		/*c.crearTablas_SQL();
		if(c.consultarTablaEquipos() == false && c.consultarTablajugadores()== false && c.consultarTablapartidos()==false && c.consultarTablaClasificacion()==false) {
			c.carga_inicial_equipos();
			c.carga_incial_jugadores();
			c.carga_inicial_partidos();
			c.carga_inicial_clasificacion();
		}*/
		
		/*Nos vamos hacia el Menu*/
		Menu(conn);
	}
	public static void Menu(Connection conn) {
		/*Iniciamos un Scanner para insertar Datos */
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
					+ "\n6 - Intercambiar Jugadores entre Equipos"
					+ "\n7 - Alter Table Teams"
					+ "\n8 - Simular Jornada");
			System.out.println("=================");
			System.out.print("Opcion:");
			opcion = scan.nextInt();
			switch (opcion) {
			case 1:
				Comandos c1 = new Comandos();
				c1.select_equipos_liga(conn);
				break;
			case 2:
				Comandos c2 = new Comandos();
				c2.mostrar_info_jugadores_por_equipo(conn);
				break;
			case 3:
				Comandos c3 = new Comandos();
				c3.contarjugadores(conn);
				break;
			case 4:
				Scanner scan2 = new Scanner(System.in); 
				Comandos c4 = new Comandos();
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
				Comandos c5 = new Comandos();
				int id_player = 0;

				System.out.print("Introduce la id del jugador que quieres eliminar? ");
				id_player= scan.nextInt();

				c5.Eliminar(id_player, conn);
			case 7:
				Comandos c6 = new Comandos();
				c6.alterTable();
				break;
			case 8:
				Comandos c7 = new Comandos();
				c7.simular_jornada();
				break;
			}
			System.out.print("Quieres Continuar?");
			confirmacion = scan.next();
		} while (!confirmacion.equalsIgnoreCase("No"));
		System.out.println("===========================");
		System.out.println("Sesion Terminada");
	}
}
