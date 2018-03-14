package es.um.redes.nanoGames.server.roomManager;

import es.um.redes.nanoGames.server.NGPlayerInfo;

public abstract class NGRoomManager {
	String rules;
	String registrationName;
	String description;
	int gameTimeout; //In milliseconds
	
	//the only requirement to add a player is that only MAX_PLAYERS are accepted
	public abstract boolean registerPlayer(NGPlayerInfo p);
	//Rules are returned
	public abstract String getRules();
	//The current status is returned
	public abstract NGRoomStatus checkStatus(NGPlayerInfo p);
	//Check for a new challenge. We can make use of that checking in order to build a new one if the conditions are satisfied 
	public abstract NGChallenge checkChallenge(NGPlayerInfo p);
	//The player provided no answer and we process that situation
	public abstract NGRoomStatus noAnswer(NGPlayerInfo p);
	//The answer provided by the player has to be processed
	public abstract NGRoomStatus answer(NGPlayerInfo p, String answer);
	//The player is removed (maybe the status has to be updated)
	public abstract void removePlayer(NGPlayerInfo p);
	//Creates a copy of the room manager
	public abstract NGRoomManager duplicate();
	//Returns the name of the game
	public abstract String getRegistrationName();
	//Returns the description of the room
	public abstract String getDescription();
	//Returns the current number of players in the room
	public abstract int playersInRoom();
	
	public static NGRoomManager copy(NGRoomManager toCopy) {
		return toCopy.duplicate();
	}
	
	public int getTimeout() {
		return gameTimeout;
	}
}
