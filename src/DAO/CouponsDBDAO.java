package DAO;

import Connections.ConnectionPool;
import CustomException.ConnectionException;
import CustomException.CouponAlreadyExistsException;
import DAO.CouponsDAO;
import couponSys.*;
import couponSys.Coupon;
import java.sql.*;
import java.util.ArrayList;

public class CouponsDBDAO implements CouponsDAO {

    ConnectionPool connectionPool;

    final String updateCouponStatement = "UPDATE COUPONS SET ID = ?  ,TITLE = ? , DESCRIPTION = ? , START_DATE = ? ,END_DATE = ? , AMOUNT = ? ,PRICE = ?,IMAGE = ? , COMPANIES_ID = ? , CATEGORIES_ID = ? WHERE ID = ? ";
    final String addCouponStatement = "INSERT INTO COUPONS (ID,TITLE,DESCRIPTION,START_DATE,END_DATE,AMOUNT,PRICE,IMAGE,COMPANIES_ID,CATEGORIES_ID) VALUES (?,?,?,?,?,?,?,?,?,?)";
    final String selectAllCouponStatement = "SELECT * FROM COUPONS";
    final String selectByIdCouponStatement = "SELECT * FROM COUPONS WHERE ID = ?";
    final String selectByEndBeforeDateCouponStatement = "SELECT * FROM COUPONS WHERE END_DATE < ?";
    final String deleteBoughtCouponStatement = "DELETE FROM CUSTOMERS_VS_COUPONS WHERE (COUPON_ID = ?) AND (CUSTOMERS_ID = ?)";
    final String selectBoughtCouponStatement = "SELECT * FROM CUSTOMERS_VS_COUPONS WHERE (COUPON_ID = ?) AND (CUSTOMERS_ID = ?)";
    final String addBoughtCouponStatement = "INSERT INTO CUSTOMERS_VS_COUPONS (COUPON_ID,CUSTOMERS_ID) VALUES (?,?)";
    final String deleteCouponStatement = "DELETE FROM COUPONS WHERE ID = ?";
    final String deletePurchasedCouponStatement = "DELETE FROM CUSTOMERS_VS_COUPONS WHERE COUPON_ID = ?";
    /*
     * Please Notice
     *   -The public methods are handling connection issues if the the connection is working
     *   -The private "covered" function are doing the sql query build and execution
     * CoveredFunction Editing the Statement and then execute it
     * */

    public CouponsDBDAO()  {
        connectionPool= ConnectionPool.getInstance();
    }
    private Date fixDate(Date date) {
        //if(date.getYear()>1000)
           // return new Date(date.getYear()-1900,date.getMonth(),date.getDate());
        return date;
    }


    @Override
    public void addCoupon(Coupon coupon) throws CouponAlreadyExistsException, ConnectionException, SQLException {
        Connection connection = ConnectionPool.getConnection();
            coveredAddCoupon(coupon,connection);
            connectionPool.restoreConnection(connection);
    }
    private void coveredAddCoupon(Coupon coupon,Connection connection) throws CouponAlreadyExistsException, SQLException, ConnectionException {

            if(getOneCoupon(coupon.getId())!=null)
                    throw new CouponAlreadyExistsException(coupon.toString());

            connection.setAutoCommit(false);

            PreparedStatement insertPreparedStatement = connection.prepareStatement(addCouponStatement);
            insertPreparedStatement.setInt(1, coupon.getId());
            insertPreparedStatement.setString(2, coupon.getTitle());
            insertPreparedStatement.setString(3, coupon.getDesciption());
            insertPreparedStatement.setDate(4, fixDate(coupon.getStartDate()));
            insertPreparedStatement.setDate(5, fixDate(coupon.getEndDate()));
            insertPreparedStatement.setInt(6,coupon.getAmount());
            insertPreparedStatement.setDouble(7, coupon.getPrice());
            insertPreparedStatement.setString(8, coupon.getImage());
            insertPreparedStatement.setInt(9, coupon.getCompanyID());
            insertPreparedStatement.setInt(10, coupon.getCategory().ordinal());
            insertPreparedStatement.executeUpdate();
            connection.commit();
    }

    @Override
    public void updateCoupon(Coupon coupon) throws SQLException, ConnectionException {
        Connection connection = ConnectionPool.getConnection();
            coveredUpdateCoupon(coupon,connection);
            connectionPool.restoreConnection(connection);
    }
    private void coveredUpdateCoupon(Coupon coupon,Connection connection) throws SQLException {
            connection.setAutoCommit(false);

            PreparedStatement insertPreparedStatement = connection.prepareStatement(updateCouponStatement);
            insertPreparedStatement.setInt(1, coupon.getId());
            insertPreparedStatement.setString(2, coupon.getTitle());
            insertPreparedStatement.setString(3, coupon.getDesciption());
            insertPreparedStatement.setDate(4, fixDate(coupon.getStartDate()));
            insertPreparedStatement.setDate(5, fixDate(coupon.getEndDate()));
            insertPreparedStatement.setInt(6,coupon.getAmount());
            insertPreparedStatement.setDouble(7, coupon.getPrice());
            insertPreparedStatement.setString(8, coupon.getImage());
            insertPreparedStatement.setInt(9, coupon.getCompanyID());
            insertPreparedStatement.setInt(10, coupon.getCategory().ordinal());
            insertPreparedStatement.setInt(11, coupon.getId());
            insertPreparedStatement.executeUpdate();

            connection.commit();
    }

    @Override
    public ArrayList<Coupon> getAllCoupon() throws SQLException, ConnectionException {
        Connection connection = ConnectionPool.getConnection();
            ArrayList<Coupon> coupons = coveredGetAllCoupon(connection);
            connectionPool.restoreConnection(connection);
            return coupons;

    }
    private ArrayList<Coupon> coveredGetAllCoupon(Connection connection) throws SQLException {
            connection.setAutoCommit(false);
            Coupon coupon=null;
            ArrayList<Coupon> coupons= new ArrayList<Coupon>();
            PreparedStatement preparedStatement= connection.prepareStatement(selectAllCouponStatement);
            ResultSet resultSet =preparedStatement.executeQuery();
            while (resultSet.next()){
                coupon = new Coupon(resultSet.getInt("ID"),resultSet.getInt("COMPANIES_ID"),Category.values()[resultSet.getInt("CATEGORIES_ID")],resultSet.getString("TITLE"),resultSet.getString("DESCRIPTION"),fixDate(resultSet.getDate("START_DATE")),fixDate(resultSet.getDate("END_DATE")),resultSet.getInt("AMOUNT"),resultSet.getDouble("PRICE"),resultSet.getString("IMAGE"));
                coupons.add(coupon);
            }
            connection.commit();
            return coupons;
    }

    @Override
    public Coupon getOneCoupon(int couponId) throws ConnectionException, SQLException {
        Connection connection = ConnectionPool.getConnection();
        Coupon coupon= coveredGetOneCoupon(couponId,connection);
        connectionPool.restoreConnection(connection);
        return coupon;

    }
    private Coupon coveredGetOneCoupon(int couponId,Connection connection) throws SQLException {

            connection.setAutoCommit(false);

            Coupon coupon=null;
            PreparedStatement insertPreparedStatement= connection.prepareStatement(selectByIdCouponStatement);
            insertPreparedStatement.setInt(1, couponId);
            ResultSet resultSet =insertPreparedStatement.executeQuery();
            while (resultSet.next()){
                coupon = new Coupon(resultSet.getInt("ID"),resultSet.getInt("COMPANIES_ID"),Category.values()[resultSet.getInt("CATEGORIES_ID")],resultSet.getString("TITLE"),resultSet.getString("DESCRIPTION"), fixDate(resultSet.getDate("START_DATE")),fixDate(resultSet.getDate("END_DATE")),resultSet.getInt("AMOUNT"),resultSet.getDouble("PRICE"),resultSet.getString("IMAGE"));
            }

            connection.commit();
            return coupon;

    }

    @Override
    public void addCouponPurchase(int customerID,int couponID) throws ConnectionException, SQLException, CouponAlreadyExistsException {
        Connection connection = ConnectionPool.getConnection();

        if(checkIfCouponAlreadyHadBeenPurchased(customerID, couponID)) {

            throw new CouponAlreadyExistsException(getOneCoupon(couponID).toString());
        }

            coveredAddCouponPurchase(customerID, couponID,connection);
            connectionPool.restoreConnection(connection);

    }
    private void coveredAddCouponPurchase(int customerID,int couponID,Connection connection) throws SQLException {
        connection.setAutoCommit(false);

        PreparedStatement insertPreparedStatement = connection.prepareStatement(addBoughtCouponStatement);
        insertPreparedStatement.setInt(1, couponID);
        insertPreparedStatement.setInt(2, customerID);
        insertPreparedStatement.executeUpdate();

        connection.commit();
    }

    public boolean checkIfCouponAlreadyHadBeenPurchased(int customerID, int couponID) throws SQLException, ConnectionException {

        Connection connection = ConnectionPool.getConnection();
        boolean result= coveredCheckIfCouponAlreadyHadBeenPurchased(customerID,couponID,connection);
        connectionPool.restoreConnection(connection);
        return result;
    }
    public boolean coveredCheckIfCouponAlreadyHadBeenPurchased(int customerID,int couponID,Connection connection) throws SQLException {
        connection.setAutoCommit(false);

        PreparedStatement preparedStatement = connection.prepareStatement(selectBoughtCouponStatement);
        preparedStatement.setInt(1, couponID);
        preparedStatement.setInt(2, customerID);
        ResultSet resultSet = preparedStatement.executeQuery();
        connection.commit();

        return resultSet.next();
    }



    @Override
    public void deleteCouponPurchase(int customerID,int couponID) throws ConnectionException, SQLException {
        Connection connection = ConnectionPool.getConnection();
            coveredDeleteCouponPurchase(customerID,couponID,connection);
            connectionPool.restoreConnection(connection);
    }
    private void coveredDeleteCouponPurchase(int customerID,int couponID,Connection connection) throws SQLException, ConnectionException {

            connection.setAutoCommit(false);

            PreparedStatement insertPreparedStatement = connection.prepareStatement(deleteBoughtCouponStatement);
            insertPreparedStatement.setInt(1, couponID);
            insertPreparedStatement.setInt(2, customerID);
            insertPreparedStatement.executeUpdate();

            connection.commit();
            int newAmount=getOneCoupon(couponID).getAmount()+1;
            Coupon coupon=getOneCoupon(couponID);
            coupon.setAmount(newAmount);
            updateCoupon(coupon);
    }

    @Override
    public void deleteCoupon(int couponID) throws ConnectionException, SQLException {
        Connection connection = ConnectionPool.getConnection();
            coveredDeleteCoupon(couponID,connection);
            connectionPool.restoreConnection(connection);
    }
    private void coveredDeleteCoupon(int couponID,Connection connection) throws ConnectionException, SQLException {

            connection.setAutoCommit(false);

            PreparedStatement insertPreparedStatement = connection.prepareStatement(deleteCouponStatement);
            insertPreparedStatement.setInt(1, couponID);
            insertPreparedStatement.executeUpdate();

            connection.commit();
            deleteCouponPurchaseWithId(couponID);
    }

    public ArrayList<Coupon> getCouponBeforeDate(Date date) throws SQLException, ConnectionException {
        Connection connection = ConnectionPool.getConnection();
            ArrayList<Coupon> coupons = coveredGetCouponBeforeDate(date,connection);
            connectionPool.restoreConnection(connection);
            return coupons;
    }
    private ArrayList<Coupon> coveredGetCouponBeforeDate(Date date,Connection connection) throws SQLException {
            connection.setAutoCommit(false);
            Coupon coupon=null;
            ArrayList<Coupon> coupons= new ArrayList<Coupon>();
            PreparedStatement preparedStatement = connection.prepareStatement(selectByEndBeforeDateCouponStatement);
            preparedStatement.setDate(1, date);
            preparedStatement.executeQuery();

            ResultSet resultSet =preparedStatement.executeQuery();

            while (resultSet.next()){
                coupon = new Coupon(resultSet.getInt("ID"),resultSet.getInt("COMPANIES_ID"),Category.values()[resultSet.getInt("CATEGORIES_ID")],resultSet.getString("TITLE"),resultSet.getString("DESCRIPTION"),fixDate(resultSet.getDate("START_DATE")),fixDate(resultSet.getDate("END_DATE")),resultSet.getInt("AMOUNT"),resultSet.getDouble("PRICE"),resultSet.getString("IMAGE"));
                coupons.add(coupon);
            }

            connection.commit();
            return coupons;
    }

    public void deleteCouponPurchaseWithId(int couponID) throws ConnectionException, SQLException {
        Connection connection = ConnectionPool.getConnection();
            coveredDeleteCouponPurchaseWithId(couponID,connection);
            connectionPool.restoreConnection(connection);
    }
    private void coveredDeleteCouponPurchaseWithId(int couponID,Connection connection) throws SQLException {
            connection.setAutoCommit(false);

            PreparedStatement insertPreparedStatement = connection.prepareStatement(deletePurchasedCouponStatement);
            insertPreparedStatement.setInt(1, couponID);
            insertPreparedStatement.executeUpdate();

            connection.commit();
    }

    public ArrayList<Coupon> deleteCouponPurchaseWithCustomerID(int customerID) throws SQLException, ConnectionException {
        Connection connection = ConnectionPool.getConnection();
            ArrayList<Coupon> coupons=coveredDeleteCouponPurchaseWithCustomerID(customerID,connection);
            connectionPool.restoreConnection(connection);
            return coupons;
    }
    private ArrayList<Coupon> coveredDeleteCouponPurchaseWithCustomerID(int customerID,Connection connection) throws SQLException {

            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(deletePurchasedCouponStatement);
            preparedStatement.setInt(1, customerID);
            preparedStatement.executeUpdate();
            Coupon coupon=null;
            ArrayList<Coupon> coupons= new ArrayList<>();
            ResultSet resultSet =preparedStatement.executeQuery();

            while (resultSet.next()){
                coupon = new Coupon(resultSet.getInt("ID"),resultSet.getInt("COMPANIES_ID"),Category.values()[resultSet.getInt("CATEGORIES_ID")],resultSet.getString("TITLE"),resultSet.getString("DESCRIPTION"),fixDate(resultSet.getDate("START_DATE")),fixDate(resultSet.getDate("END_DATE")),resultSet.getInt("AMOUNT"),resultSet.getDouble("PRICE"),resultSet.getString("IMAGE"));
                coupons.add(coupon);
            }
            connection.commit();
            return coupons;
    }
}
