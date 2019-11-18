package Clases;

import GestionComandos.Comandos;

public class Equipo {
	//Atributos Tabla
	int id_team;
	String team_name;
	//Getters
	public int getId_team() {
		return id_team;
	}
	public String getTeam_name() {
		return team_name;
	}
	//Setters
	public void setId_team(int id_team) {
		this.id_team = id_team;
	}
	public void setTeam_name(String team_name) {
		this.team_name = team_name;
	}
	//Builder
	public Equipo(String team_name) {
		if(id_team <=0) {
			this.id_team=0;
		}else {
			this.id_team = id_team+1;
		}
		this.team_name = team_name;
	}
	//ToString
	@Override
	public String toString() {
		return "Equipo [id_team=" + id_team + ", team_name=" + team_name + "]";
	}
	
}
