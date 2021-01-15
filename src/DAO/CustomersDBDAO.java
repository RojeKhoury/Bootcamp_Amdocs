package DAO;

import Connections.ConnectionPool;
import DAO.CustomersDAO;
import couponSys.Customer;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomersDBDAO implements CustomersDAO
{
    ConnectionPool connectionPool;

    final String updateCustomerStatement = "UPDATE CUSTOMERS SET ID = ? , FIRST_NAME = ? , LAST_NAME = ? , EMAIL = ? , PASSWORD = ? WHERE ID = ? ";
    final String addCustomerStatement = "INSERT INTO CUSTOMERS (ID,FIRST_NAME,LAST_NAME,EMAIL,PASSWORD) VALUES (?,?,?,?,?)";
    final String selectAllCustomerStatement = "SELECT * FROM CUSTOMERS";
    final String selectByIdCustomerStatement = "SELECT * FROM CUSTOMERS WHERE ID = ?";
    final String selectByCredentialsCustomerStatement = "SELECT * FROM CUSTOMERS WHERE (EMAIL = ?) AND (PASSWORD = ?)";
    final String deleteCustomerStatement = "DELETE FROM CUSTOMERS WHERE ID = ?";

    public CustomersDBDAO() throws InterruptedException, SQLException {
        connectionPool= ConnectionPool.getInstance();
    }

    @Override
    public boolean isCustomerExists(String i_email, String password) throws InterruptedException, SQLException {
        Connection connection = ConnectionPool.getConnection();
        connection.setAutoCommit(false);

        boolean result=false;
        PreparedStatement insertPreparedStatement= connection.prepareStatement(selectByCredentialsCustomerStatement);
        insertPreparedStatement.setString(1, i_email);
        insertPreparedStatement.setString(2, password);
        ResultSet resultSet=insertPreparedStatement.executeQuery();
        while(resultSet.next())
        {
            result=true;
        }

        connection.commit();
        connectionPool.restoreConnection(connection);
        return result;
    }

    @Override
    public void addCustomer(Customer customer) throws SQLException, InterruptedException {
        Connection connection = ConnectionPool.getConnection();
        connection.setAutoCommit(false);

        PreparedStatement insertPreparedStatement = connection.prepareStatement(addCustomerStatement);
        insertPreparedStatement.setInt(1, customer.getId());
        insertPreparedStatement.setString(2, customer.getFirstName());
        insertPreparedStatement.setString(3, customer.getLastName());
        insertPreparedStatement.setString(4, customer.getEmail());
        insertPreparedStatement.setString(5, customer.getPassword());
        insertPreparedStatement.executeUpdate();

        connection.commit();
        connectionPool.restoreConnection(connection);
    }

    @Override
    public void updateCustomer(Customer customer) throws SQLException, InterruptedException {
        Connection connection = ConnectionPool.getConnection();
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
        connectionPool.restoreConnection(connection);
    }

    @Override
    public void deleteCustomer(int customerID) throws InterruptedException, SQLException {
        Connection connection = ConnectionPool.getConnection();
        connection.setAutoCommit(false);

        PreparedStatement insertPreparedStatement = connection.prepareStatement(deleteCustomerStatement);
        insertPreparedStatement.setInt(1, customerID);
        insertPreparedStatement.executeUpdate();

        connection.commit();
        connectionPool.restoreConnection(connection);
    }

    @Override
    public ArrayList<Customer> getAllCustomer() throws SQLException, InterruptedException {
        Connection connection = ConnectionPool.getConnection();
        connection.setAutoCommit(false);

        ArrayList<Customer> customers= new ArrayList<Customer>();
        PreparedStatement preparedStatement= connection.prepareStatement(selectAllCustomerStatement);
        ResultSet resultSet =preparedStatement.executeQuery();
        while (resultSet.next()){
            Customer customer = new Customer(resultSet.getInt("ID"),resultSet.getString("FIRST_NAME"),resultSet.getString("LAST_NAME"), resultSet.getString("EMAIL"),resultSet.getString("PASSWORD"),null);
            customers.add(customer);
        }
        connection.commit();

        connectionPool.restoreConnection(connection);
        return customers;
    }

    @Override
    public Customer getOneCustomer(int customerId) throws SQLException, InterruptedException{
        Connection connection = ConnectionPool.getConnection();
        connection.setAutoCommit(false);

        Customer customer=null;
        PreparedStatement insertPreparedStatement= connection.prepareStatement(selectByIdCustomerStatement);
        insertPreparedStatement.setInt(1, customerId);
        ResultSet resultSet =insertPreparedStatement.executeQuery();
        while (resultSet.next()){
            customer = new Customer(resultSet.getInt("ID"),resultSet.getString("FIRST_NAME"),resultSet.getString("LAST_NAME"), resultSet.getString("EMAIL"),resultSet.getString("PASSWORD"),null);
        }

        connection.commit();
        connectionPool.restoreConnection(connection);
        return customer;
    }
}
