package es.um.redes.nanoGames.broker;

public class SnmpClientException extends RuntimeException {
	private static final long serialVersionUID = 10L;

	public SnmpClientException() {
    }

    public SnmpClientException(String message) {
        super(message);
    }

    public SnmpClientException(Throwable cause) {
        super(cause);
    }

    public SnmpClientException(String message, Throwable cause) {
        super(message, cause);
    }
    
}