package Facade;

import CustomException.ConnectionException;
import CustomException.CouponAlreadyExistsException;
import DAO.CompaniesDBDAO;
import DAO.CouponsDBDAO;
import DAO.CustomersDBDAO;
import couponSys.Category;
import couponSys.Coupon;
import couponSys.Customer;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class CustomerFacade extends ClientFacade
{
	private int customerID;
	
	public CustomerFacade() {
		super.companiesDAO = new CompaniesDBDAO();
		super.customersDAO = new CustomersDBDAO();
		super.couponsDAO = new CouponsDBDAO();
	}
	
	@Override
	public boolean login(String email, String password) {
		try {
			boolean flag = customersDAO.isCustomerExists(email, password);
			if (flag) {
				customerID = customersDAO.getCustomerIdByCredentials(email, password);
				System.out.println("Login Successfully -> " + email);
			} else {
				System.out.println("Login Failed");
			}
			return flag;
		}catch (ConnectionException | SQLException e) {
			System.out.println(e.getMessage()+"\nFailed to login");
			return false;
		}
	}
	
	public void purchaseCoupon(int couponId)  {
		AtomicBoolean couponExist= new AtomicBoolean(false);
		AtomicBoolean couponExpiered= new AtomicBoolean(false);

		try {

			Coupon coupon = null;
			coupon = couponsDAO.getOneCoupon(couponId);
			if(coupon!=null) {
				Date date = new Date();
				LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				java.sql.Date today = new java.sql.Date(localDate.getYear() - 1900, localDate.getMonthValue() - 1, localDate.getDayOfMonth());


				if (coupon.getAmount() > 0 && today.before(couponsDAO.getOneCoupon(couponId).getEndDate())) {
					coupon.setAmount(coupon.getAmount() - 1);
					System.out.println(coupon.toString());
					couponsDAO.updateCoupon(coupon);
					couponsDAO.addCouponPurchase(customerID, couponId);
					System.out.println("You bought " + couponId);

				} else {

					System.out.println("Coupon " + coupon.getId() + " isn't available");
				}
			}
			else
			{
				System.out.println("Couldnt find the coupon");
			}
		} catch (ConnectionException | SQLException e) {
			System.out.println(e.getMessage()+"while purchase coupon");
		}catch (CouponAlreadyExistsException e)
		{
			System.out.println(e.getMessage());
		}

	}
	
	public ArrayList<Coupon> getCustomerCoupons() {
		try {
			return this.customersDAO.getOneCustomer(customerID).getCoupons();
		} catch (SQLException | ConnectionException e) {
			System.out.println(e.getMessage()+"while getting customers coupon");
			return new ArrayList<>();
		}
	}
	
	public ArrayList<Coupon> getCustomerCoupons(Category category)  {
		try {
			ArrayList<Coupon> coupons, categoryCoupons = new ArrayList<Coupon>();
			coupons = customersDAO.getOneCustomer(customerID).getCoupons();
			coupons.forEach(coupon -> {
				if (coupon.getCategory() == category) categoryCoupons.add(coupon);
			});
			return categoryCoupons;
		} catch (SQLException | ConnectionException e) {
			System.out.println(e.getMessage()+"while getting customers coupon by category");
			return new ArrayList<>();
		}
	}
	
	public ArrayList<Coupon> getCustomerCoupons(double maxPrice) {
		try {
			ArrayList<Coupon> coupons, categoryCoupons = new ArrayList<Coupon>();
			coupons = customersDAO.getOneCustomer(customerID).getCoupons();

			coupons.forEach(coupon -> {
				if (coupon.getPrice() <= maxPrice) categoryCoupons.add(coupon);
			});


			return categoryCoupons;
		}catch (SQLException | ConnectionException e) {
			System.out.println(e.getMessage()+"while getting customers coupon by category");
			return new ArrayList<>();
		}
	}
	
	public Customer getCustomerDetails(){

		try {
			return customersDAO.getOneCustomer(customerID);
		} catch (SQLException | ConnectionException e) {
			System.out.println(e.getMessage()+"while getting customers coupon by category");
			return null;
		}
	}
}
