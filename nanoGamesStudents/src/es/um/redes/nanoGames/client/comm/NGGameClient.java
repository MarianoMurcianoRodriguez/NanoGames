package es.um.redes.nanoGames.client.comm;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

//This class provides the functionality required to exchange messages between the client and the game server 
public class NGGameClient {
	private Socket socket;
	protected DataOutputStream dos;
	protected DataInputStream dis;
	
	private static final int SERVER_PORT = 6969;

	public NGGameClient(String serverName) {
		//Creation of the socket and streams
		//TODO
	}

	public boolean verifyToken(long token) throws IOException {
		//SND(token) and RCV(TOKEN_VALID) or RCV(TOKEN_INVALID)
		//TODO
		//Make message (NGMessage.makeXXMessage)
		//Send messge (dos.write())
		//Receive response (NGMessage.readMessageFromSocket)
	}
	
	public boolean registerNickname(String nick) throws IOException {
		//SND(nick) and RCV(NICK_OK) or RCV(NICK_DUPLICATED)
		//TODO
	}

	//TODO
	//add additional methods for all the messages to be exchanged between client and game server
	
	
	//Used by the shell in order to check if there is data available to read 
	public boolean isDataAvailable() throws IOException {
		return (dis.available() != 0);
	}
	

	//To close the communication with the server
	public void disconnect() {
		//TODO
	}
}
