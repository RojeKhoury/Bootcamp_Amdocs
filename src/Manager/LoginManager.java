package Manager;

import DAO.CompaniesDBDAO;
import DAO.CustomersDBDAO;
import Facade.AdminFacade;
import Facade.ClientFacade;
import Facade.CompanyFacade;
import Facade.CustomerFacade;

import java.sql.SQLException;

public class LoginManager
{
	private static LoginManager instance = null;


	private LoginManager()
	{


	}

	public static int plus(int a, int b)
	{
		return a+b;
	}

	public static LoginManager getInstance()
	{
		if(instance == null)
		{
			instance = new LoginManager();
		}
		return instance;
	}
	
	public ClientFacade login(String email, String password, ClientType clientType)
	{
		System.out.println(clientType+" Login");
		ClientFacade clientToReturn = null;
			switch(clientType)
			{
				case Administrator:

					clientToReturn = new AdminFacade();
					break;
				case Company:
					clientToReturn = new CompanyFacade();
					break;
				case Customer:
					clientToReturn = new CustomerFacade();
					break;
			}

		if(clientToReturn.login(email,password))
			return clientToReturn;
		System.out.println(email+"   "+password);
		return null;
	}

	
}