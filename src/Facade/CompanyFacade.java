package Facade;

import CustomException.AlreadyUsedException;
import CustomException.CantChangeFieldsException;
import CustomException.ConnectionException;
import CustomException.CouponAlreadyExistsException;
import DAO.CompaniesDBDAO;
import DAO.CouponsDAO;
import DAO.CouponsDBDAO;
import couponSys.Category;
import couponSys.Company;
import couponSys.Coupon;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class CompanyFacade extends ClientFacade
{
	public int getCompanyId() {
		return companyID;
	}


	private int companyID;
	
	public CompanyFacade()  {
		super.companiesDAO = new CompaniesDBDAO();
		super.couponsDAO = new CouponsDBDAO();
 	}
	
	@Override
	public boolean login(String email, String password) {
		try{
			//if it exist
			boolean flag = companiesDAO.isCompanyExists(email, password);

			if (flag) {

					companyID = companiesDAO.getCompanyIdByCredentials(email, password);
				//-1 mean it couldnt find the user for some reason(never happen)
				if (companyID != -1) {
					System.out.println("Login Successfully -> " + email);
					return true;
				}

			}
			//flag => false=> cant login
			System.out.println("Login Failed");
			return false;
		} catch (ConnectionException | SQLException e) {
			System.out.println(e.getMessage()+"\nFailed to login");
			return false;
		}

	}
	
	public void addCoupon(Coupon couponToAdd)
	{
		try {
			int companyId = couponToAdd.getCompanyID();
			AtomicBoolean CouponDuplicate = new AtomicBoolean(false);
			ArrayList<Coupon> coupons;
			Company company = companiesDAO.getOneCompany(companyId);
			coupons = company.getCoupons();


			//Checking duplicate in Title
			coupons.forEach(coupon -> {
				if (coupon.getTitle().equals(couponToAdd.getTitle())) {
					CouponDuplicate.set(true);
				}
			});

			if (!CouponDuplicate.get()) {

				this.couponsDAO.addCoupon(couponToAdd);
				System.out.println(" Add Successfully");
			} else {
				System.out.println("This Title is already used!");
			}
		} catch (CouponAlreadyExistsException | SQLException e) {
			System.out.println(e.getMessage());
		} catch (ConnectionException e) {
			System.out.println(e.getMessage()+"while adding Coupon");
		}

	}
	
	public void updateCoupon(Coupon couponToUpdate)
	{
		try {
			if(couponsDAO.getOneCoupon(couponToUpdate.getId())!=null) {
				if (couponsDAO.getOneCoupon(couponToUpdate.getId()).getCompanyID()== couponToUpdate.getCompanyID()) {
					System.out.println(couponsDAO.getOneCoupon(couponToUpdate.getId()).getTitle()+" "+couponToUpdate.getTitle());
					if (!couponsDAO.getOneCoupon(couponToUpdate.getId()).getTitle().equals(couponToUpdate.getTitle()))
					{
						throw new CantChangeFieldsException("title");
					}
					this.couponsDAO.updateCoupon(couponToUpdate);
					System.out.println("Coupon " + couponToUpdate.getId() + " had been updated!");
				} else {
					throw new CantChangeFieldsException("ID","CompanyID");
				}
			}
			else
			{
				System.out.println("Coulnt find the coupon");
			}
		} catch (ConnectionException | SQLException e) {
			System.out.println(e.getMessage()+"while trying to update coupon");
		}catch(CantChangeFieldsException e)
		{
			System.out.println(e.getMessage());
		}

	}
	
	public void deleteCoupon(int couponID)
	{
		try {
			if(couponsDAO.getOneCoupon(couponID)!=null) {
				System.out.println("Deleting coupon");
				this.couponsDAO.deleteCouponPurchaseWithId(couponID);
				this.couponsDAO.deleteCoupon(couponID);
			}else{
				System.out.println("Couldnt find the coupon");
			}
		} catch (ConnectionException | SQLException e) {
			System.out.println(e.getMessage()+"while trying to delete coupon");
		}
	}
	
	public ArrayList<Coupon> getCompanyCoupons(){
		try {
			ArrayList<Coupon> coupons = companiesDAO.getOneCompany(companyID).getCoupons();
			return companiesDAO.getOneCompany(companyID).getCoupons();
		} catch (ConnectionException | SQLException e) {
			System.out.println(e.getMessage()+"while trying to get company coupons");
			return new ArrayList<Coupon>();
		}
	}
	
	public ArrayList<Coupon> getCompanyCoupons(Category category) {
		try {
			ArrayList<Coupon> coupons, categoryCoupons = new ArrayList<Coupon>();
			coupons = companiesDAO.getOneCompany(companyID).getCoupons();
			coupons.forEach(coupon -> {
				if (coupon.getCategory() == category) categoryCoupons.add(coupon);
			});
			return categoryCoupons;
		} catch (ConnectionException | SQLException e) {
			System.out.println(e.getMessage()+"while trying to get company coupon by category");
			return new ArrayList<Coupon>();
		}
	}
	
	public ArrayList<Coupon> getCompanyCoupons(double maxPrice) {
		try {
		ArrayList<Coupon> coupons ,categoryCoupons= new ArrayList<Coupon>();
		coupons = companiesDAO.getOneCompany(companyID).getCoupons();
		coupons.forEach(coupon -> {if(coupon.getPrice()<=maxPrice)categoryCoupons.add(coupon);});
		return categoryCoupons;
		} catch (ConnectionException | SQLException e) {
			System.out.println(e.getMessage()+"while trying to get company coupon by Max price");
			return new ArrayList<Coupon>();
		}
	}
	
	public Company getCompanyDetails()  {
		try {
			Company company;
			company = companiesDAO.getOneCompany(companyID);
			if (company != null)
				return company;
			System.out.println("Couldnt get the company details");
			return null;
		} catch (ConnectionException | SQLException e) {
			System.out.println(e.getMessage()+"while trying to get company details");
			return null;
		}
	}

	public Coupon getCouponByTitle(String i_title)
	{
		//only for tests
		try {
			return couponsDAO.getCompanyByTitle(i_title);
		} catch (ConnectionException | SQLException e) {
			System.out.println("Couldnt find coupons with this title");
			return null;
		}
	}

	public Company getCompanyByName(String i_name)
	{
		try {
			return companiesDAO.getCompanyByName(i_name);
		} catch (ConnectionException | SQLException e) {
			System.out.println("*Error* Couldn't find the company");
			return null;
		}
	}
}
