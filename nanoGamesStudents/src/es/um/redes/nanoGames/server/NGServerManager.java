package es.um.redes.nanoGames.server;

import java.util.HashMap;
import java.util.Set;

import es.um.redes.nanoGames.server.roomManager.NGRoomManager;

/**
 * This class contains the general status of the whole server (without the logic related to particular games)
 */
class NGServerManager {
	
	//Players registered in this server
	private HashMap<String,NGPlayerInfo> players = new HashMap<String,NGPlayerInfo>();
	//Current rooms and their related RoomManagers
	//TODO Data structure to relate rooms and RoomManagers
	
	NGServerManager() {
	}
	
	public void registerRoomManager(NGRoomManager rm) {
		//When a new room manager is registered we assigned it to a room 
		//TODO
	}
	
	//Returns the set of existing rooms
	//public synchronized getRoomList() {
		//TODO
	//}
	
	//Given a room it returns the description
	public synchronized String getRoomDescription(room) {
		//We make use of the RoomManager to obtain an updated description of the room
		return rooms.get(room).getDescription();
	}
	
	//False is returned if the nickname is already registered, True otherwise and the player is registered
	public synchronized boolean addPlayer(NGPlayerInfo player) {
		//TODO
	}
	
	//The player is removed from the list
	public synchronized void removePlayer(NGPlayerInfo player) {
		//TODO
	}
	
	//A player request to enter in a room. If the access is granted the RoomManager is returned
	public synchronized NGRoomManager enterRoom(NGPlayerInfo p, room) {
		//TODO Check if the room exists
		if (roomManager.registerPlayer(p)) {
				return rm;
		}
		else
			return null;
	}
	
	//A player leaves the room 
	public synchronized void leaveRoom(NGPlayerInfo p, byte room) {
		//TODO Check if the room exists
		roomManager.removePlayer(p);
	}
}
