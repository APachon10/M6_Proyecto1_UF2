package Clases;

import GestionComandos.Comandos;

public class Jugador {
	//Atributos Tabla
	int id_player=0;
	String player_name="",position="";
	int id_team =0;
	String team_name="";
	//Getters
	public int getId_player() {
		return id_player;
	}
	public String getPlayer_name() {
		return player_name;
	}
	public String getPosition() {
		return position;
	}
	public int getId_team() {
		return id_team;
	}
	public String getTeam_name() {
		return team_name;
	}
	//Setters
	public void setId_player(int id_player) {
		this.id_player = id_player;
	}
	public void setPlayer_name(String player_name) {
		this.player_name = player_name;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public void setId_team(int id_team) {
		this.id_team = id_team;
	}
	public void setTeam_name(String team_name) {
		this.team_name = team_name;
	}
	//Builder
	public Jugador(String player_name, String position, int id_team, String team_name) {
		this.id_player = id_player;
		this.player_name = player_name;
		this.position = position;
		this.id_team = id_team;
		this.team_name = team_name;
	}
	//To String
	@Override
	public String toString() {
		return "Jugador [id_player=" + id_player + ", player_name=" + player_name + ", position=" + position
				+ ", id_team=" + id_team + ", team_name=" + team_name + "]";
	}

	

}
