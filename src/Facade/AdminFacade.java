package Facade;

import CustomException.CantDeleteException;
import CustomException.ConnectionException;
import CustomException.NoClientFound;
import DAO.CompaniesDBDAO;
import DAO.CustomersDBDAO;
import DAO.CouponsDBDAO;
import couponSys.Company;
import couponSys.Coupon;
import couponSys.Customer;

import java.sql.SQLException;
import java.util.ArrayList;

public class AdminFacade extends ClientFacade
{
	private final String AdminEmail="admin@admin.com";
	private final String AdminPassword="admin";



	public AdminFacade()  {
		super.companiesDAO = new CompaniesDBDAO();
		super.customersDAO = new CustomersDBDAO();
		super.couponsDAO = new CouponsDBDAO();
	}
	
	@Override
	public boolean login(String email, String password)
	{
		if(email.equals(AdminEmail) && password.equals(AdminPassword))
		{
			System.out.println("Login Successfully -> "+email);
			return true;
		}
		else{
			System.out.println("Login Failed");
			return false;
		}

	}
	
	public void addCompany(Company companyToAdd)
	{
		try {
			Company company = companiesDAO.getOneCompany(companyToAdd.getId());

			if (company == null) {
				if (!companiesDAO.isCompanyExistsValidate(companyToAdd.getEmail())) {//Creating new Company
					companiesDAO.addCompany(companyToAdd);
					System.out.println(companyToAdd.getId() + " Add Successfully");
				} else {
					System.out.println("Email: " + companyToAdd.getEmail() + " exist!");
				}
			} else {
				System.out.println("ID: " + companyToAdd.getId() + " exist!");
			}
		}catch (ConnectionException | SQLException e) {
			System.out.println(e.getMessage()+" while trying to add Company");
		}
	}
	
	public void updateCompany(Company companyToUpdate)
	{
		try
		{   //check if the company exist
			if(companiesDAO.getOneCompany(companyToUpdate.getId())!=null) {
				//check if the user didnt change any forbidden fields
				if (companiesDAO.getOneCompany(companyToUpdate.getId()).getName().equals(companyToUpdate.getName())) {
					this.companiesDAO.updateCompany(companyToUpdate);
					System.out.println("Company had been updated!");
				} else {
					System.out.println("You can't change the Name or ID");
				}
			}else
			{
				System.out.println("Coulnt find the company");
			}
		} catch (ConnectionException | SQLException e) {
			System.out.println(e.getMessage()+" while trying to update Company");
		}
	}
	
	public void deleteCompany(int companyID)
	{
		try {
			if(companiesDAO.getOneCompany(companyID)!=null)
			{
			Company company = companiesDAO.getOneCompany(companyID);
			ArrayList<Coupon> coupons;

			//deleting coupons first
			coupons = company.getCoupons();
			coupons.forEach(coupon ->
							{
								try {
									//delete coupons that already had been purchased
									couponsDAO.deleteCouponPurchaseWithId(coupon.getId());
									//the coupon is being deleted only if it didnt get any exception while deleting the coupons that already had been purchased (SMART)
									couponsDAO.deleteCoupon(coupon.getId());
								} catch (ConnectionException | SQLException e) {
									System.out.println(e.getMessage()+" while trying to delete company's coupon");
								}
							}

					);
			//IF all the coupons deleted so we are ready to delete the company
			if(!companiesDAO.getOneCompany(companyID).getCoupons().isEmpty())
				throw new CantDeleteException("Companyies Coupon hasnt been deleted");
			companiesDAO.deleteCompany(companyID);
			}
			else{
				System.out.println("Couldn't find this Company");
			}
		}catch (ConnectionException | SQLException e) {
			e.printStackTrace();
		}catch (CantDeleteException d)
		{
			System.out.println(d.getMessage());
		}
	}
	
	public ArrayList<Company> getAllCompanies()
	{
		try{

			ArrayList<Company> companies = this.companiesDAO.getAllCompany();
			if (companies != null)
				return this.companiesDAO.getAllCompany();
			System.out.println("didnt find any company");
			return null;
		} catch (ConnectionException | SQLException e) {
			System.out.println("*Error* Couldn't get connection to data base or Sql execution issue\n"+e.getMessage());
			return new ArrayList<Company>();
		}
	}
	
	public Company getOneCompany(int companyId)
	{
		try {
			return this.companiesDAO.getOneCompany(companyId);
		} catch (ConnectionException | SQLException e) {
			System.out.println(e.getMessage()+" while trying to get Customer");
			return null;
		}
	}
	
	public void addCustomer(Customer customerToAdd)
	{

		try{
			//check if it does exist by id
			if (customersDAO.getOneCustomer(customerToAdd.getId()) == null) {
				//checking if the email exists
				if (customersDAO.getCustomerIdByEmail(customerToAdd.getEmail()) == -1) {
					this.customersDAO.addCustomer(customerToAdd);
					System.out.println(customerToAdd.getId() + " Add Successfully");
				} else {
					System.out.println("Customer exist");
				}
			} else {
				System.out.println("Customer exist");
			}
		} catch (ConnectionException | SQLException e) {
			System.out.println(e.getMessage()+" while trying to add Customer");
		}

	}
	
	public void updateCustomer(Customer customerToAdd)
	{

		try{
			//check if it does exist by id
			if(customersDAO.getOneCustomer(customerToAdd.getId())!=null) {
				if (customersDAO.getOneCustomer(customerToAdd.getId()).getEmail().equals(customerToAdd.getEmail())) {
					this.customersDAO.updateCustomer(customerToAdd);
					System.out.println(customerToAdd.getId() + " Updated Successfully");
				} else {
					System.out.println("You cant update name or id");
				}
			}
			else
			{
				System.out.println("Couldnt find the Customer");
			}
		}catch (ConnectionException | SQLException e) {
			System.out.println(e.getMessage()+" while trying to add Customer");
		}
	}
	
	public void deleteCustomer(int customerID) {
		try{
			Customer customer = customersDAO.getOneCustomer(customerID);
			//check if it does exist by id
			if(customer!=null){
				ArrayList<Coupon> coupons ;
				coupons = customer.getCoupons();
				//
				coupons
						.forEach(coupon -> {
							try {
								//first delete the cupons that he already bought
								couponsDAO.deleteCouponPurchase(customerID, coupon.getId());
							} catch (ConnectionException | SQLException e) {
								System.out.println(e.getMessage() + " while trying to delete coupon that was purchased");
							}
						});
				//check if i deleted all the coupons without errors
				if (!customersDAO.getOneCustomer(customerID).getCoupons().isEmpty()) {
					throw new CantDeleteException("Customer cupons hasnt been deleted");
				}
				//delete the customer if all good
				this.customersDAO.deleteCustomer(customerID);
			}else
			{
				System.out.println("Didnt find the customer");
			}

		}catch (ConnectionException | SQLException e) {
			System.out.println(e.getMessage()+" while trying to delete Customer");
		}catch(CantDeleteException d)
		{
			System.out.println(d.getMessage());
		}
	}
	
	public ArrayList<Customer> getAllCustomers()
	{
		try{
			return this.customersDAO.getAllCustomer();
		}catch (ConnectionException | SQLException e) {
			System.out.println(e.getMessage()+" while trying to get Customers");
			return new ArrayList<>();
		}

	}
	
	public Customer getOneCustomer(int customerID)
	{
		Customer customer= null;
		try {
			//check if it does exist by id
			customer = this.customersDAO.getOneCustomer(customerID);
			if(customer==null)
				System.out.println("couldn't find the customer");
			return customer;

		} catch (SQLException | ConnectionException e) {
			System.out.println(e.getMessage()+" while trying to get Customer");
			return null;
		}

	}
}
