package es.um.redes.nanoGames.message;

import java.io.DataInputStream;
import java.io.IOException;

public abstract class NGMessage {
	protected byte opcode;

	public static final byte OP_INVALID_CODE = 0;
	public static final byte OP_LIST = 1;
	//All codes for the rest of messages
	//TODO
	
	//Returns the opcode of the message
	public byte getOpcode() {
		return opcode;

	}

	//Method to be implemented specifically by each subclass of NGMessage
	protected abstract byte[] toByteArray();

	//Reads the opcode of the incoming message and uses the subclass to parse the rest of the message
	public static NGMessage readMessageFromSocket(DataInputStream dis) throws IOException { 
		//We use the operation to differentiate among all the subclasses
		switch (operation) {
		//TODO additional messages
		//The following case is just an example
		case (OP_LIST): {
			return NGListMessage.readFromIS(operation, dis);
		}
		default:
			System.err.println("Unknown message type received:"+code);
		}
		return null;
	}

	//The following method is just an example
	public static NGMessage makeListMessage(byte code, String[] items) {
		return (new NGListMessage(code, items));
	}
}
