package CustomException;

public class NoClientFound extends Exception {

	public NoClientFound (String errorMessage) {
		super("*Error* Couldnt find the client:"+errorMessage);
	}
	public NoClientFound () {
		super("*Error* Couldnt find the client");
	}
}
