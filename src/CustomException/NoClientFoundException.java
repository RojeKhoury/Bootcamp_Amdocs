package CustomException;

public class NoClientFoundException extends Exception {

	public NoClientFoundException(String errorMessage) {
		super("*Error* Couldnt find the client:"+errorMessage);
	}
	public NoClientFoundException() {
		super("*Error* Couldnt find the client");
	}
}
