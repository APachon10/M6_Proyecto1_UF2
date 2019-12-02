package Clases;

public class Partido {
	int match_id;
	String teamA;
	int golsA;
	String teamB;
	int golsB;

	//Builder
	public Partido(String teamA, int golsA, String teamB, int golsB) {
		if(match_id <=0) {
			this.match_id = 0;
		}else {
			this.match_id = this.match_id +1;
		}
		this.match_id = match_id;
		this.teamA = teamA;
		this.golsA = golsA;
		this.teamB = teamB;
		this.golsB = golsB;
	}
	//Getters
	public int getMatch_id() {
		return match_id;
	}
	public String getTeamA() {
		return teamA;
	}
	public int getGolsA() {
		return golsA;
	}
	public String getTeamB() {
		return teamB;
	}
	public int getGolsB() {
		return golsB;
	}
	//Setters
	public void setMatch_id(int match_id) {
		this.match_id = match_id;
	}
	public void setTeamA(String teamA) {
		this.teamA = teamA;
	}
	public void setGolsA(int golsA) {
		this.golsA = golsA;
	}
	public void setTeamB(String teamB) {
		this.teamB = teamB;
	}
	public void setGolsB(int golsB) {
		this.golsB = golsB;
	}
	@Override
	public String toString() {
		return "Partido [match_id=" + match_id + ", teamA=" + teamA + ", golsA=" + golsA + ", teamB=" + teamB
				+ ", golsB=" + golsB + "]";
	}
	
}
