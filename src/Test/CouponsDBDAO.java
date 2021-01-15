package Test;

import Connections.ConnectionPool;
import couponSys.*;
import couponSys.Coupon;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;

public class CouponsDBDAO implements CouponsDAO{

    ConnectionPool connectionPool;

    final String updateCouponStatement = "UPDATE COUPONS SET ID = ? , TITLE = ? , DESCRIPTION = ? , START_DATE = ? ,END_DATE = ? , AMOUNT = ? ,PRICE = ? , COMPANY_ID = ? , CATEGORY_ID = ? WHERE ID = ? ";
    final String addCouponStatement = "INSERT INTO COUPONS (ID,TITLE,DESCRIPTION,START_DATE,END_DATE,AMOUNT,PRICE,IMAGE,COMPANY_ID,CATEGORY_ID) VALUES (?,?,?,?,?,?,?,?,?,?)";
    final String selectAllCouponStatement = "SELECT * FROM COUPONS";
    final String selectByIdCouponStatement = "SELECT * FROM COUPONS WHERE ID = ?";
    final String selectByCredentialsCouponStatement = "SELECT * FROM COUPONS WHERE (EMAIL = ?) AND (PASSWORD = ?)";
    final String deleteCouponStatement = "DELETE FROM COUPONS WHERE ID = ?";

    public CouponsDBDAO() throws InterruptedException, SQLException {
        connectionPool= ConnectionPool.getInstance();
    }
    @Override
    public void addCoupon(Coupon coupon) throws SQLException, InterruptedException {
        Connection connection = ConnectionPool.getConnection();
        connection.setAutoCommit(false);

        PreparedStatement insertPreparedStatement = connection.prepareStatement(addCouponStatement);
        insertPreparedStatement.setInt(1, coupon.getId());
        insertPreparedStatement.setString(2, coupon.getTitle());
        insertPreparedStatement.setString(3, coupon.getDesciption());
        insertPreparedStatement.setString(4, coupon.getStartDate().toString());
        insertPreparedStatement.setString(5, coupon.getEndDate().toString());
        insertPreparedStatement.setInt(6,coupon.getAmount());
        insertPreparedStatement.setDouble(7, coupon.getPrice());
        insertPreparedStatement.setString(8, coupon.getImage());
        insertPreparedStatement.setInt(9, coupon.getCompanyID());
        insertPreparedStatement.setInt(10, coupon.getCategory().ordinal());
        insertPreparedStatement.executeUpdate();

        connection.commit();
        connectionPool.restoreConnection(connection);
    }

    @Override
    public void updateCoupon(Coupon coupon) throws SQLException, InterruptedException {
        Connection connection = ConnectionPool.getConnection();
        connection.setAutoCommit(false);

        PreparedStatement insertPreparedStatement = connection.prepareStatement(updateCouponStatement);
        insertPreparedStatement.setInt(1, coupon.getId());
        insertPreparedStatement.setString(2, coupon.getTitle());
        insertPreparedStatement.setString(3, coupon.getDesciption());
        insertPreparedStatement.setString(4, coupon.getStartDate().toString());
        insertPreparedStatement.setString(5, coupon.getEndDate().toString());
        insertPreparedStatement.setInt(6,coupon.getAmount());
        insertPreparedStatement.setDouble(7, coupon.getPrice());
        insertPreparedStatement.setString(8, coupon.getImage());
        insertPreparedStatement.setInt(9, coupon.getCompanyID());
        insertPreparedStatement.setInt(10, coupon.getCategory().ordinal());
        insertPreparedStatement.executeUpdate();

        connection.commit();
        connectionPool.restoreConnection(connection);
    }

    @Override
    public ArrayList<Coupon> getAllCoupon() throws SQLException, InterruptedException {
        Connection connection = ConnectionPool.getConnection();
        connection.setAutoCommit(false);
        Coupon coupon=null;
        ArrayList<Coupon> coupons= new ArrayList<Coupon>();
        PreparedStatement preparedStatement= connection.prepareStatement(selectAllCouponStatement);
        ResultSet resultSet =preparedStatement.executeQuery();
        while (resultSet.next()){
            coupon = new Coupon(resultSet.getInt("ID"),resultSet.getInt("COMPANY_ID"),Category.values()[resultSet.getInt("CATEGORY_ID")],resultSet.getString("TITLE"),resultSet.getString("DESCRIPTION"),resultSet.getDate("START_DATE"),resultSet.getDate("END_DATE"),resultSet.getInt("AMOUNT"),resultSet.getDouble("PRICE"),resultSet.getString("IMAGE"));
            coupons.add(coupon);
        }
        connection.commit();

        connectionPool.restoreConnection(connection);
        return coupons;
    }

    @Override
    public Coupon getOneCoupon(int couponId) throws SQLException, InterruptedException {
        Connection connection = ConnectionPool.getConnection();
        connection.setAutoCommit(false);

        Coupon coupon=null;
        PreparedStatement insertPreparedStatement= connection.prepareStatement(selectByIdCouponStatement);
        insertPreparedStatement.setInt(1, couponId);
        ResultSet resultSet =insertPreparedStatement.executeQuery();
        while (resultSet.next()){
            coupon = new Coupon(resultSet.getInt("ID"),resultSet.getInt("COMPANY_ID"),Category.values()[resultSet.getInt("CATEGORY_ID")],resultSet.getString("TITLE"),resultSet.getString("DESCRIPTION"),resultSet.getDate("START_DATE"),resultSet.getDate("END_DATE"),resultSet.getInt("AMOUNT"),resultSet.getDouble("PRICE"),resultSet.getString("IMAGE"));
        }

        connection.commit();
        connectionPool.restoreConnection(connection);
        return coupon;
    }

    @Override
    public void addCouponPurchase() {

    }

    @Override
    public void deleteCouponPurchase() {

    }

    @Override
    public void deleteCoupon(int couponID) throws InterruptedException, SQLException {

    }

}
