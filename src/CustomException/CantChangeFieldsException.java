package CustomException;

public class CantChangeFieldsException extends Exception {
	public CantChangeFieldsException (String field1,String field2)
	{
		super("you cant change "+field1+" or "+field2);
	}
	public CantChangeFieldsException (String field1)
	{
		super("you cant change "+field1);
	}
	{

	}
}
