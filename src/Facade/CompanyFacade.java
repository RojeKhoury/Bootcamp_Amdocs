package Facade;

import couponSys.Category;
import couponSys.Company;
import couponSys.Coupon;

import java.sql.SQLException;
import java.util.ArrayList;

public class CompanyFacade extends ClientFacade
{
	private int companyID;
	
	public CompanyFacade()
	{
	
	}
	
	@Override
	public boolean login(String email, String password)
	{
		return false;
	}
	
	public void addCoupon(Coupon couponToAdd) throws SQLException, InterruptedException
	{
		this.couponsDAO.addCoupon(couponToAdd);
	}
	
	public void updateCoupon(Coupon couponToUpdate) throws SQLException, InterruptedException
	{
		this.couponsDAO.updateCoupon(couponToUpdate);
	}
	
	public void deleteCoupon(int couponID) throws SQLException, InterruptedException
	{
		this.couponsDAO.deleteCoupon(couponID);
	}
	
	public ArrayList<Coupon> getCompanyCoupons()
	{
		return null; //this.couponsDAO.
	}
	
	public ArrayList<Coupon> getCompanyCoupons(Category category)
	{
		return null;
	}
	
	public ArrayList<Coupon> getCompanyCoupons(double maxPrice)
	{
		return null;
	}
	
	public Company getCompanyDetails()
	{
		return null;
	}
}
