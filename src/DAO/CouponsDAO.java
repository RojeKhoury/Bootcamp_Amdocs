package DAO;

import CustomException.ConnectionException;
import CustomException.CouponAlreadyExistsException;
import couponSys.Coupon;
import couponSys.Customer;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

public interface CouponsDAO {

    public void addCoupon(Coupon coupon) throws CouponAlreadyExistsException, ConnectionException, SQLException;

    public void updateCoupon(Coupon coupon) throws SQLException, ConnectionException;

    public void deleteCoupon(int couponID) throws ConnectionException, SQLException;

    public ArrayList<Coupon> getAllCoupon() throws SQLException, ConnectionException;

    public ArrayList<Coupon> getCouponBeforeDate(Date date) throws SQLException, ConnectionException;

    public Coupon getOneCoupon(int couponId) throws ConnectionException, SQLException;

    public void addCouponPurchase(int customerID,int couponID) throws ConnectionException, SQLException, CouponAlreadyExistsException;

    public void deleteCouponPurchase(int customerID,int couponID) throws ConnectionException, SQLException;

    public void deleteCouponPurchaseWithId(int couponID) throws ConnectionException, SQLException;
}
