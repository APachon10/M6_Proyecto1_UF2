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
	public void crearTabla() {
		
	}
}
