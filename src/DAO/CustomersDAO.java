package DAO;


import CustomException.ConnectionException;
import CustomException.NoClientFound;
import couponSys.Customer;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomersDAO
{
    public boolean isCustomerExists(String i_email, String password) throws ConnectionException, SQLException;

    public int getCustomerIdByCredentials (String email, String password) throws ConnectionException, SQLException;

    public int getCustomerIdByEmail (String email) throws ConnectionException, SQLException;

    public void addCustomer(Customer company) throws ConnectionException, SQLException;

    public void updateCustomer(Customer company) throws ConnectionException, SQLException;

    public void deleteCustomer(int companyID) throws ConnectionException, SQLException;

    public ArrayList<Customer> getAllCustomer() throws SQLException, ConnectionException;

    public Customer getOneCustomer(int customerId) throws SQLException, ConnectionException;

}
