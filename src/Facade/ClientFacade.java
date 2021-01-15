package Facade;

import DAO.CompaniesDAO;
import DAO.CustomersDAO;
import Test.CouponsDAO;

public abstract class ClientFacade
{
	protected CompaniesDAO companiesDAO;
	protected CustomersDAO customersDAO;
	protected CouponsDAO couponsDAO;
	
	public abstract boolean login(String email, String password);
	
}
