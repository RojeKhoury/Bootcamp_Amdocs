package Facade;


import couponSys.Category;
import couponSys.Coupon;
import couponSys.Customer;

import java.util.ArrayList;

public class CustomerFacade extends ClientFacade
{
	private int customerID;
	
	public CustomerFacade()
	{
	
	}
	
	@Override
	public boolean login(String email, String password)
	{
		return false;
	}
	
	public void purchaseCoupon(Coupon coupon)
	{
	
	}
	
	public ArrayList<Coupon> getCustomerCoupons()
	{
		return null;
	}
	
	public ArrayList<Coupon> getCustomerCoupons(Category category)
	{
		return null;
	}
	
	public ArrayList<Coupon> getCustomerCoupons(double maxPrice)
	{
		return null;
	}
	
	public Customer getCustomerDetails()
	{
		return null;
	}
}
