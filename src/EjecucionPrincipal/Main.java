package EjecucionPrincipal;

import GestionComandos.Comandos;
import Interfaces.ParametrosConexion;

public class Main implements ParametrosConexion{
	public static void main(String[] args) {
		Comandos c = new Comandos();
		c.conexion(ParametrosConexion.url, ParametrosConexion.user, ParametrosConexion.pass);
		c.crearTablas_SQL();
	}
	public void Menu() {
		
	}
}
