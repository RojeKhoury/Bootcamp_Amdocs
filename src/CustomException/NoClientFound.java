package CustomException;

public class NoClientFound extends Exception {

	public NoClientFound (String errorMessage) {
		super("Couldnt find the client:"+errorMessage);
	}
	public NoClientFound () {
		super("Couldnt find the client");
	}
}
