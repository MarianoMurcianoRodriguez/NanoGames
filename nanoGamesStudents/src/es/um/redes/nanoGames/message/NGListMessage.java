package es.um.redes.nanoGames.message;

/*
Include here the specification of this particular message
*/
//TODO

public class NGListMessage extends NGMessage {


	@Override
	public byte[] toByteArray() {
		//TODO Transform the internal representation into a byte array ready to be trasmitted
		return null;
	}

	//Constructor
	public NGListMessage(byte code, String[] items) {
		//TODO Adapt this constructor to your specific list message
	}

	//public static NGListMessage readFromIS(byte code, DataInputStream dis) {
		//TODO Decode the message received from the Input Stream
	//}

	//TODO Replace this method according to your specific message
	public String[] getItems() {
		return null;
	}
}
