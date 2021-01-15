package DAO;


import couponSys.Customer;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomersDAO
{
    boolean isCustomerExists(String i_email, String password) throws InterruptedException, SQLException;

    void addCustomer(Customer company) throws SQLException, InterruptedException;

    void updateCustomer(Customer company) throws SQLException, InterruptedException;

    void deleteCustomer(int companyID) throws InterruptedException, SQLException;

    ArrayList<Customer> getAllCustomer() throws SQLException, InterruptedException;

    Customer getOneCustomer(int companyId) throws SQLException, InterruptedException;

}
