package CustomException;

public class ConnectionException extends Exception{
	public ConnectionException(String errorMessage) {
		super("*Error* Couldn't get connection"+errorMessage);
	}
	public ConnectionException() {
		super("*Error* Couldn't get connection");
	}

}
