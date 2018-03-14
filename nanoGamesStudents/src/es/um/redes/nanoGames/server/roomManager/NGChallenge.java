package es.um.redes.nanoGames.server.roomManager;

public class NGChallenge {
	public short challengeNumber;
	//TODO Change the challenge to represent accurately your game challenge
	public String challenge;
	
	//Status initialization
	NGChallenge() {
		challengeNumber = 0;
		challenge = null;
	}

	public NGChallenge(short currentChallengeNumber, String currentChallenge) {
		this.challengeNumber = currentChallengeNumber;
		challenge = currentChallenge;
	}
	

}
