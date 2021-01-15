package Facade;

import couponSys.Company;
import couponSys.Customer;

import java.sql.SQLException;
import java.util.ArrayList;

public class AdminFacade extends ClientFacade
{
	public AdminFacade()
	{
	
	}
	
	@Override
	public boolean login(String email, String password)
	{
		return false;
	}
	
	public void addCompany(Company companyToAdd) throws SQLException, InterruptedException
	{
		this.companiesDAO.addCompany(companyToAdd);
	}
	
	public void updateCompany(Company companyToUpdate) throws SQLException, InterruptedException
	{
		this.companiesDAO.updateCompany(companyToUpdate);
	}
	
	public void deleteCompany(int companyID) throws SQLException, InterruptedException
	{
		this.companiesDAO.deleteCompany(companyID);
	}
	
	public ArrayList<Company> getAllCompanies() throws SQLException, InterruptedException
	{
		return this.companiesDAO.getAllCompany();
	}
	
	public Company getOneCompany(int companyId) throws SQLException, InterruptedException
	{
		return this.companiesDAO.getOneCompany(companyId);
	}
	
	public void addCustomer(Customer customerToAdd) throws SQLException, InterruptedException
	{
		this.customersDAO.addCustomer(customerToAdd);
	}
	
	public void updateCustomer(Customer customerToAdd) throws SQLException, InterruptedException
	{
		this.customersDAO.updateCustomer(customerToAdd);
	}
	
	public void deleteCustomer(int customerID) throws SQLException, InterruptedException
	{
		this.customersDAO.deleteCustomer(customerID);
	}
	
	public ArrayList<Customer> getAllCustomers() throws SQLException, InterruptedException
	{
		return this.customersDAO.getAllCustomer();
	}
	
	public Customer getOneCustomers(int customerID) throws SQLException, InterruptedException
	{
		return this.customersDAO.getOneCustomer(customerID);
	}
}
