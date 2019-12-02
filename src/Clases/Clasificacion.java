package Clases;

public class Clasificacion {
	int position_id;
	String team;
	int wins,loses,draws,points;
	//Builder
	public Clasificacion(String team, int wins, int loses, int draws, int points) {
		if(position_id <=0) {
			this.position_id = 0;
		}else {
			this.position_id = this.position_id+1;
		}
		this.team = team;
		this.wins = wins;
		this.loses = loses;
		this.draws = draws;
		this.points = points;
	}
	//Getters
	public int getPosition_id() {
		return position_id;
	}
	public String getTeam() {
		return team;
	}
	public int getWins() {
		return wins;
	}
	public int getLoses() {
		return loses;
	}
	public int getDraws() {
		return draws;
	}
	public int getPoints() {
		return points;
	}
	//Setters
	public void setPosition_id(int position_id) {
		this.position_id = position_id;
	}
	public void setTeam(String team) {
		this.team = team;
	}
	public void setWins(int wins) {
		this.wins = wins;
	}
	public void setLoses(int loses) {
		this.loses = loses;
	}
	public void setDraws(int draws) {
		this.draws = draws;
	}
	public void setPoints(int points) {
		this.points = points;
	}
	@Override
	public String toString() {
		return "Clasificacion [position_id=" + position_id + ", team=" + team + ", wins=" + wins + ", loses=" + loses
				+ ", draws=" + draws + ", points=" + points + "]";
	}

	
}
