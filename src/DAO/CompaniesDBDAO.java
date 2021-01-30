package DAO;

import Connections.ConnectionPool;
import CustomException.ConnectionException;
import couponSys.Category;
import couponSys.Company;
import couponSys.Coupon;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CompaniesDBDAO implements CompaniesDAO {

    ConnectionPool connectionPool;
    final String selectByNameCompanyStatement="SELECT * FROM COMPANIES WHERE NAME = ?";
    final String updateCompanyStatement = "UPDATE COMPANIES SET EMAIL = ? , PASSWORD = ? WHERE (ID = ?) AND (NAME = ?) ";
    final String addCompanyStatement = "INSERT INTO COMPANIES (NAME,EMAIL,PASSWORD) VALUES (?,?,?)";
    final String selectAllCompanyStatement = "SELECT * FROM COMPANIES";
    final String selectByIdCompanyStatement = "SELECT * FROM COMPANIES WHERE ID = ?";
    final String selectByCouponsOfCompanyStatement = "SELECT * FROM COUPONS WHERE COMPANIES_ID = ?";
    final String selectByCredentialsCompanyStatement = "SELECT * FROM COMPANIES WHERE (EMAIL = ?) AND (PASSWORD = ?)";
    final String selectByEmailCompanyStatement = "SELECT * FROM COMPANIES WHERE (EMAIL = ?) ";
    final String deleteCompanyStatement = "DELETE FROM COMPANIES WHERE ID = ?";
    final String selectIdOfThisCredentialStatement = "SELECT ID FROM COMPANIES WHERE (EMAIL = ?) AND (PASSWORD = ?)";

    /*
     * Please Notice
     *   -The public methods are handling connection issues if the the connection is working
     *   -The private "covered" function are doing the sql query build and execution
     * CoveredFunction Editing the Statement and then execute it
     * */

    public CompaniesDBDAO()  {
        connectionPool= ConnectionPool.getInstance();
    }

    @Override
    public int getCompanyIdByCredentials (String email, String password) throws ConnectionException, SQLException {
        Connection connection = ConnectionPool.getConnection();
        int result =coveredGetCompanyIdByCredentials (email,password,connection);
        connectionPool.restoreConnection(connection);
        return result;
    }
    private int coveredGetCompanyIdByCredentials (String email, String password,Connection connection) throws SQLException {

            connection.setAutoCommit(false);
            PreparedStatement insertPreparedStatement = connection.prepareStatement(selectIdOfThisCredentialStatement);
            insertPreparedStatement.setString(1, email);
            insertPreparedStatement.setString(2, password);
            ResultSet resultSet = insertPreparedStatement.executeQuery();
            connection.commit();
            if (resultSet.next()) {
                return resultSet.getInt("ID");
            }
            return -1;
    }



    @Override
    public boolean isCompanyExists(String i_email, String password) throws SQLException, ConnectionException {
        Connection connection = ConnectionPool.getConnection();
            boolean result= coveredIsCompanyExists(i_email, password,connection);
            connectionPool.restoreConnection(connection);
            return result;
    }
    private boolean coveredIsCompanyExists(String i_email, String password,Connection connection) throws SQLException {
            connection.setAutoCommit(false);
            PreparedStatement insertPreparedStatement= connection.prepareStatement(selectByCredentialsCompanyStatement);
            insertPreparedStatement.setString(1, i_email);
            insertPreparedStatement.setString(2, password);
            ResultSet resultSet=insertPreparedStatement.executeQuery();

            connection.commit();
            return resultSet.next();
    }

    @Override
    public void addCompany(Company company) throws ConnectionException, SQLException {
        Connection connection = ConnectionPool.getConnection();
            coveredAddCompany(company,connection);
            connectionPool.restoreConnection(connection);
    }
    private void coveredAddCompany(Company company,Connection connection) throws SQLException {

            connection.setAutoCommit(false);

            PreparedStatement insertPreparedStatement = connection.prepareStatement(addCompanyStatement);
            insertPreparedStatement.setString(1, company.getName());
            insertPreparedStatement.setString(2, company.getEmail());
            insertPreparedStatement.setString(3, company.getPassword());
            insertPreparedStatement.executeUpdate();
            connection.commit();

    }

    @Override
    public void updateCompany(Company company) throws ConnectionException, SQLException {
        Connection connection = ConnectionPool.getConnection();
        coveredUpdateCompany(company,connection);
        connectionPool.restoreConnection(connection);

    }
    private void coveredUpdateCompany(Company company,Connection connection) throws SQLException {
            connection.setAutoCommit(false);

            PreparedStatement insertPreparedStatement = connection.prepareStatement(updateCompanyStatement);

            insertPreparedStatement.setString(1, company.getEmail());
            insertPreparedStatement.setString(2, company.getPassword());
            insertPreparedStatement.setInt(3, company.getId());
            insertPreparedStatement.setString(4, company.getName());
            insertPreparedStatement.executeUpdate();

            connection.commit();
    }

    @Override
    public void deleteCompany(int companyID) throws SQLException, ConnectionException {
        Connection connection = ConnectionPool.getConnection();
        coveredDeleteCompany(companyID,connection);
        connectionPool.restoreConnection(connection);


    }
    private void coveredDeleteCompany(int companyID,Connection connection) throws SQLException {

            connection.setAutoCommit(false);
            PreparedStatement insertPreparedStatement = connection.prepareStatement(deleteCompanyStatement);
            insertPreparedStatement.setInt(1, companyID);
            insertPreparedStatement.executeUpdate();

            connection.commit();

    }

    @Override
    public ArrayList<Company> getAllCompany() throws ConnectionException, SQLException {
        Connection connection = ConnectionPool.getConnection();
        ArrayList<Company>companies=coveredGetAllCompany(connection);
        connectionPool.restoreConnection(connection);
        return companies;
    }
    public ArrayList<Company> coveredGetAllCompany(Connection connection) throws SQLException, ConnectionException {

            connection.setAutoCommit(false);

            ArrayList<Company> companies= new ArrayList<>();
            PreparedStatement preparedStatement= connection.prepareStatement(selectAllCompanyStatement);
            ResultSet resultSet =preparedStatement.executeQuery();
            while (resultSet.next()){
                Company company = new Company(resultSet.getInt("ID"),resultSet.getString("NAME"), resultSet.getString("EMAIL"),resultSet.getString("PASSWORD"),getCompanyCoupons(resultSet.getInt("ID")));
                companies.add(company);
            }
            connection.commit();

            return companies;
    }


    private ArrayList<Coupon> getCompanyCoupons(int companyId) throws ConnectionException, SQLException {
        Connection connection = ConnectionPool.getConnection();//if null throwes exception
        ArrayList<Coupon> coupons = coveredGetCompanyCoupons(companyId,connection);
        connectionPool.restoreConnection(connection);
        return coupons;
    }
    private ArrayList<Coupon> coveredGetCompanyCoupons(int companyId,Connection connection) throws SQLException {

            connection.setAutoCommit(false);
            PreparedStatement insertPreparedStatement= connection.prepareStatement(selectByCouponsOfCompanyStatement);
            insertPreparedStatement.setInt(1, companyId);
            ResultSet resultSet =insertPreparedStatement.executeQuery();
            ArrayList<Coupon> coupons= new ArrayList<Coupon>();;

            while (resultSet.next()){
                coupons.add(new Coupon(resultSet.getInt("ID"),resultSet.getInt("COMPANIES_ID"),
                        Category.values()[resultSet.getInt("CATEGORIES_ID")],
                        resultSet.getString("TITLE"),resultSet.getString("DESCRIPTION"),resultSet.getDate("START_DATE"),resultSet.getDate("END_DATE"),resultSet.getInt("AMOUNT"),resultSet.getDouble("PRICE"),resultSet.getString("IMAGE")));
            }

            connection.commit();
            return coupons;

    }


    public boolean isCompanyExistsValidate(String email) throws ConnectionException, SQLException {

        Connection connection = ConnectionPool.getConnection();
            boolean result = coveredIsCompanyExistsValidate(email,connection);
            connectionPool.restoreConnection(connection);
            return result;

    }
    public boolean coveredIsCompanyExistsValidate(String email,Connection connection) throws SQLException {

            connection.setAutoCommit(false);

            PreparedStatement insertPreparedStatement= connection.prepareStatement(selectByEmailCompanyStatement);
            insertPreparedStatement.setString(1, email);
            ResultSet resultSet=insertPreparedStatement.executeQuery();
            connection.commit();

            return resultSet.next();
    }

    @Override
    public Company getOneCompany(int companyId) throws ConnectionException, SQLException{
        Connection connection = ConnectionPool.getConnection();
            Company company= coveredGetOneCompany(companyId, connection);
            connectionPool.restoreConnection(connection);
            return company;

    }
    public Company coveredGetOneCompany(int companyId,Connection connection) throws SQLException, ConnectionException {
            connection.setAutoCommit(false);

            Company company=null;
            PreparedStatement insertPreparedStatement= connection.prepareStatement(selectByIdCompanyStatement);
            insertPreparedStatement.setInt(1, companyId);
            ResultSet resultSet =insertPreparedStatement.executeQuery();
            while (resultSet.next()){
                company = new Company(resultSet.getInt("ID"),resultSet.getString("NAME"), resultSet.getString("EMAIL"),resultSet.getString("PASSWORD"),getCompanyCoupons(resultSet.getInt("ID")));
            }
            connection.commit();
            return company;
    }




    @Override
    public Company getCompanyByName(String i_name) throws ConnectionException, SQLException{
        Connection connection = ConnectionPool.getConnection();
        Company company= coveredGetCompanyByName(i_name, connection);
        connectionPool.restoreConnection(connection);
        return company;

    }
    private Company coveredGetCompanyByName(String i_name,Connection connection) throws SQLException, ConnectionException {
        connection.setAutoCommit(false);

        Company company=null;
        PreparedStatement insertPreparedStatement= connection.prepareStatement(selectByNameCompanyStatement);
        insertPreparedStatement.setString(1, i_name);
        ResultSet resultSet =insertPreparedStatement.executeQuery();
        while (resultSet.next()){
            company = new Company(resultSet.getInt("ID"),resultSet.getString("NAME"), resultSet.getString("EMAIL"),resultSet.getString("PASSWORD"),getCompanyCoupons(resultSet.getInt("ID")));
        }
        connection.commit();
        return company;
    }
}
