package Facade;

import DAO.CompaniesDAO;
import DAO.CustomersDAO;
import DAO.CouponsDAO;

import java.sql.SQLException;

public abstract class ClientFacade
{
	protected CompaniesDAO companiesDAO;
	protected CustomersDAO customersDAO;
	protected CouponsDAO couponsDAO;
	
	public abstract boolean login(String email, String password);
	
}
