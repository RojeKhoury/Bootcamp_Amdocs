package Test;

import couponSys.Coupon;
import couponSys.Customer;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CouponsDAO {

    public void addCoupon(Coupon coupon) throws SQLException, InterruptedException;

    public void updateCoupon(Coupon coupon) throws SQLException, InterruptedException;

    public void deleteCoupon(int couponID) throws InterruptedException, SQLException;

    public ArrayList<Coupon> getAllCoupon() throws SQLException, InterruptedException;

    public Coupon getOneCoupon(int couponId) throws SQLException, InterruptedException;

    public void addCouponPurchase();

    public void deleteCouponPurchase();
}
