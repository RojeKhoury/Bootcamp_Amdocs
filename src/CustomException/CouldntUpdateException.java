package CustomException;

public class CouldntUpdateException extends Exception{
	public CouldntUpdateException(String i_whereIsTheIssue) {
		super("You can't change the field:  "+i_whereIsTheIssue);
	}
}
