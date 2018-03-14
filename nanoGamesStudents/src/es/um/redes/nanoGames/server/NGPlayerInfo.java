package es.um.redes.nanoGames.server;

public class NGPlayerInfo {
	//Constructor to make copies
	public NGPlayerInfo(NGPlayerInfo p) {
		this.nick = new String(p.nick);
		this.status = p.status;
		this.score = p.score;
	}
	
	//Default constructor
	public NGPlayerInfo() {
		
	}
	
	//TODO Include additional fields if required
	public String nick; //Nickname of the user
	public byte status; //Current status of the user (according to the automata)
	public int score;  //Current score of the user
}
