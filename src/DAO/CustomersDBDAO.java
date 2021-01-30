package DAO;

import Connections.ConnectionPool;
import CustomException.ConnectionException;
import couponSys.Coupon;
import couponSys.Customer;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomersDBDAO implements CustomersDAO
{
    ConnectionPool connectionPool;
    /*
     * Please Notice
     *   -The public methods are handling connection issues if the the connection is working
     *   -The private "covered" function are doing the sql query build and execution
     * CoveredFunction Editing the Statement and then execute it
     * */
    final String selectByEmailCustomerStatement = "SELECT * FROM CUSTOMERS WHERE EMAIL = ?";
    final String updateCustomerStatement = "UPDATE CUSTOMERS SET ID = ? , FIRST_NAME = ? , LAST_NAME = ? , EMAIL = ? , PASSWORD = ? WHERE ID = ? ";
    final String addCustomerStatement = "INSERT INTO CUSTOMERS (FIRST_NAME,LAST_NAME,EMAIL,PASSWORD) VALUES (?,?,?,?)";
    final String selectAllCustomerStatement = "SELECT * FROM CUSTOMERS";
    final String selectByIdCustomerStatement = "SELECT * FROM CUSTOMERS WHERE ID = ?";
    final String selectByCredentialsCustomerStatement = "SELECT * FROM CUSTOMERS WHERE (EMAIL = ?) AND (PASSWORD = ?)";
    final String deleteCustomerStatement = "DELETE FROM CUSTOMERS WHERE ID = ?";
    final String selectIdOfThisCredentialStatement = "SELECT ID FROM CUSTOMERS WHERE (EMAIL = ?) AND (PASSWORD = ?)";
    final String selectIdOfThisEmailStatement = "SELECT ID FROM CUSTOMERS WHERE (EMAIL = ?)";
    final String selectByCouponsOfCustomerStatement = "SELECT * FROM CUSTOMERS_VS_COUPONS WHERE CUSTOMERS_ID = ?";

    public CustomersDBDAO() {
        connectionPool= ConnectionPool.getInstance();
    }

    @Override
    public boolean isCustomerExists(String i_email, String password) throws ConnectionException, SQLException {
        Connection connection = ConnectionPool.getConnection();
        boolean result= coveredIsCustomerExists(i_email,password,connection);
        connectionPool.restoreConnection(connection);
        return result;

    }
    private boolean coveredIsCustomerExists(String i_email, String password,Connection connection) throws SQLException {
            connection.setAutoCommit(false);
            PreparedStatement insertPreparedStatement= connection.prepareStatement(selectByCredentialsCustomerStatement);
            insertPreparedStatement.setString(1, i_email);
            insertPreparedStatement.setString(2, password);
            ResultSet resultSet=insertPreparedStatement.executeQuery();
            connection.commit();
            return resultSet.next();

    }

    @Override
    public int getCustomerIdByCredentials(String email, String password) throws ConnectionException, SQLException {
        Connection connection = ConnectionPool.getConnection();
        int result = coveredGetCustomerIdByCredentials(email,password,connection);
        connectionPool.restoreConnection(connection);
        return result;
    }
    public int coveredGetCustomerIdByCredentials(String email, String password,Connection connection) throws SQLException {

            connection.setAutoCommit(false);
            PreparedStatement insertPreparedStatement= connection.prepareStatement(selectIdOfThisCredentialStatement);
            insertPreparedStatement.setString(1, email);
            insertPreparedStatement.setString(2, password);
            ResultSet resultSet=insertPreparedStatement.executeQuery();
            if(resultSet.next())
            {
                connection.commit();
                return resultSet.getInt("ID");
            }
            return -1 ;
    }

    @Override
    public int getCustomerIdByEmail(String email) throws ConnectionException, SQLException {
        Connection connection = ConnectionPool.getConnection();
        int result = coveredCetCustomerIdByEmail(email,connection);
        connectionPool.restoreConnection(connection);
        return result;

    }
    public int coveredCetCustomerIdByEmail(String email,Connection connection) throws SQLException {

            connection.setAutoCommit(false);

            PreparedStatement insertPreparedStatement= connection.prepareStatement(selectIdOfThisEmailStatement);
            insertPreparedStatement.setString(1, email);
            ResultSet resultSet=insertPreparedStatement.executeQuery();
            if(resultSet.next())
            {
                return resultSet.getInt("ID");
            }
            connection.commit();

            return -1;//-1 mean it doesnt exist

    }

    @Override
    public void addCustomer(Customer customer) throws ConnectionException, SQLException {
        Connection connection = ConnectionPool.getConnection();
            coveredAddCustomer(customer,connection);
            connectionPool.restoreConnection(connection);
    }
    private void coveredAddCustomer(Customer customer,Connection connection) throws SQLException {
            connection.setAutoCommit(false);

            PreparedStatement insertPreparedStatement = connection.prepareStatement(addCustomerStatement);
            insertPreparedStatement.setString(1, customer.getFirstName());
            insertPreparedStatement.setString(2, customer.getLastName());
            insertPreparedStatement.setString(3, customer.getEmail());
            insertPreparedStatement.setString(4, customer.getPassword());
            insertPreparedStatement.executeUpdate();

            connection.commit();

    }


    private ArrayList<Coupon> getCustomerCoupons(int customerId) throws ConnectionException, SQLException {
        Connection connection = ConnectionPool.getConnection();
        ArrayList<Coupon> coupons = coveredGetCustomerCoupons(customerId,connection);
        connectionPool.restoreConnection(connection);
        return coupons;

    }
    private ArrayList<Coupon> coveredGetCustomerCoupons(int customerId,Connection connection) throws SQLException, ConnectionException {
            connection.setAutoCommit(false);
            PreparedStatement insertPreparedStatement= connection.prepareStatement(selectByCouponsOfCustomerStatement);
            insertPreparedStatement.setInt(1, customerId);
            ResultSet resultSet =insertPreparedStatement.executeQuery();
            ArrayList<Coupon> coupons;
            coupons = new ArrayList<>();

            CouponsDAO couponsDao= new CouponsDBDAO();

            while (resultSet.next()){
                coupons.add(couponsDao.getOneCoupon(resultSet.getInt("COUPON_ID")));
            }
            connection.commit();
            return coupons;
    }

    @Override
    public void updateCustomer(Customer customer) throws ConnectionException, SQLException {
        Connection connection = ConnectionPool.getConnection();
            coveredUpdateCustomer(customer,connection);
            connectionPool.restoreConnection(connection);
    }
    private void coveredUpdateCustomer(Customer customer,Connection connection) throws SQLException {
            connection.setAutoCommit(false);

            PreparedStatement insertPreparedStatement = connection.prepareStatement(updateCustomerStatement);
            insertPreparedStatement.setInt(1, customer.getId());
            insertPreparedStatement.setString(2, customer.getFirstName());
            insertPreparedStatement.setString(3, customer.getLastName());
            insertPreparedStatement.setString(4, customer.getEmail());
            insertPreparedStatement.setString(5, customer.getPassword());
            insertPreparedStatement.setInt(6, customer.getId());
            insertPreparedStatement.executeUpdate();

            connection.commit();
    }

    @Override
    public void deleteCustomer(int customerID) throws ConnectionException, SQLException {
        Connection connection = ConnectionPool.getConnection();
            deleteCustomer(customerID,connection);
            connectionPool.restoreConnection(connection);
    }
    public void deleteCustomer(int customerID,Connection connection) throws SQLException {
            connection.setAutoCommit(false);
            PreparedStatement insertPreparedStatement = connection.prepareStatement(deleteCustomerStatement);
            insertPreparedStatement.setInt(1, customerID);
            insertPreparedStatement.executeUpdate();
            connection.commit();
    }

    @Override
    public ArrayList<Customer> getAllCustomer() throws SQLException, ConnectionException {
        Connection connection = ConnectionPool.getConnection();
        ArrayList<Customer> customers=coveredGetAllCustomer (connection);
        connectionPool.restoreConnection(connection);
        return customers;

    }
    private ArrayList<Customer> coveredGetAllCustomer (Connection connection) throws SQLException, ConnectionException {

            connection.setAutoCommit(false);

            ArrayList<Customer> customers;
            customers = new ArrayList<>();
            PreparedStatement preparedStatement= connection.prepareStatement(selectAllCustomerStatement);
            ResultSet resultSet =preparedStatement.executeQuery();
            while (resultSet.next()){
                Customer customer = new Customer(resultSet.getInt("ID"),resultSet.getString("FIRST_NAME"),resultSet.getString("LAST_NAME"), resultSet.getString("EMAIL"),resultSet.getString("PASSWORD"),getCustomerCoupons(resultSet.getInt("ID")));
                customers.add(customer);
            }
            connection.commit();
            return customers;


    }
    @Override
    public Customer getOneCustomer(int customerId) throws SQLException, ConnectionException {
        Connection connection = ConnectionPool.getConnection();
        Customer customer=coveredGetOneCustomer(customerId,connection);
        connectionPool.restoreConnection(connection);
        return customer;
    }
    private Customer coveredGetOneCustomer (int customerId,Connection connection) throws SQLException, ConnectionException {

            connection.setAutoCommit(false);
            Customer customer=null;
            PreparedStatement insertPreparedStatement= connection.prepareStatement(selectByIdCustomerStatement);
            insertPreparedStatement.setInt(1, customerId);
            ResultSet resultSet =insertPreparedStatement.executeQuery();
            while (resultSet.next()){
                customer = new Customer(resultSet.getInt("ID"),resultSet.getString("FIRST_NAME"),resultSet.getString("LAST_NAME"), resultSet.getString("EMAIL"),resultSet.getString("PASSWORD"),getCustomerCoupons(resultSet.getInt("ID")));
            }

            connection.commit();
            return customer;


    }
}
