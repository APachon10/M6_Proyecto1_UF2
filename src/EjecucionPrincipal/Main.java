package EjecucionPrincipal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

import Clases.Equipo;
import Clases.Jugador;
import GestionComandos.Comandos;
import Interfaces.ParametrosConexion;

public class Main implements ParametrosConexion{
	public static void main(String[] args) {
		Comandos c = new Comandos();
		Connection conn = c.conexion(ParametrosConexion.url, ParametrosConexion.user, ParametrosConexion.pass);
		Menu(conn);
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
				break;
			case 2:
				
				break;
			}
			System.out.print("Quieres Continuar?");
			confirmacion = scan.next();
		} while (!confirmacion.equals("No"));
	}
}
