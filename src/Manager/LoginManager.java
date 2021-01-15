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
	
	private static CustomersDBDAO customers;
	private static CompaniesDBDAO companies;
	
	private LoginManager()
	{
		try
		{
			this.customers = new CustomersDBDAO();
			this.companies = new CompaniesDBDAO();
		}
		catch(Exception exception)
		{
			//TODO: Handle this Exception
		}
		
	}

	public static LoginManager getInstance()
	{
		if(instance == null)
		{
			instance = new LoginManager();
		}
		
		return instance;
	}
	
	public static ClientFacade login(String email, String password, ClientType clientType) throws SQLException, InterruptedException
	{
		ClientFacade clientToReturn = null;
		boolean isValid = isLoginDetailsValid(email, password);
		if(isValid)
		{
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
		}
		
		return clientToReturn;
	}
	
	private static boolean isLoginDetailsValid(String email, String password) throws SQLException, InterruptedException
	{
		boolean isCustomerExists = customers.isCustomerExists(email,password);
		boolean isCompanyExists = companies.isCompanyExists(email, password);
		
		return isCustomerExists || isCompanyExists;
	}
	
}