package DAO;

import Connections.ConnectionPool;
import DAO.CompaniesDAO;
import couponSys.Company;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CompaniesDBDAO implements CompaniesDAO {

    ConnectionPool connectionPool;

    final String updateCompanyStatement = "UPDATE COMPANIES SET ID = ? , NAME = ? , EMAIL = ? , PASSWORD = ? WHERE ID = ? ";
    final String addCompanyStatement = "INSERT INTO COMPANIES (ID,NAME,EMAIL,PASSWORD) VALUES (?,?,?,?)";
    final String selectAllCompanyStatement = "SELECT * FROM COMPANIES";
    final String selectByIdCompanyStatement = "SELECT * FROM COMPANIES WHERE ID = ?";
    final String selectByCredentialsCompanyStatement = "SELECT * FROM COMPANIES WHERE (EMAIL = ?) AND (PASSWORD = ?)";
    final String deleteCompanyStatement = "DELETE FROM COMPANIES WHERE ID = ?";

    public CompaniesDBDAO() throws InterruptedException, SQLException {
        connectionPool= ConnectionPool.getInstance();
    }

    @Override
    public boolean isCompanyExists(String i_email, String password) throws InterruptedException, SQLException {
        Connection connection = ConnectionPool.getConnection();
        connection.setAutoCommit(false);
        boolean result=false;

        PreparedStatement insertPreparedStatement= connection.prepareStatement(selectByCredentialsCompanyStatement);
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
    public void addCompany(Company company) throws SQLException, InterruptedException {
        Connection connection = ConnectionPool.getConnection();
        connection.setAutoCommit(false);

        PreparedStatement insertPreparedStatement = connection.prepareStatement(addCompanyStatement);
        insertPreparedStatement.setInt(1, company.getId());
        insertPreparedStatement.setString(2, company.getName());
        insertPreparedStatement.setString(3, company.getEmail());
        insertPreparedStatement.setString(4, company.getPassword());
        insertPreparedStatement.executeUpdate();

        connection.commit();
        connectionPool.restoreConnection(connection);
    }

    @Override
    public void updateCompany(Company company) throws SQLException, InterruptedException {
        Connection connection = ConnectionPool.getConnection();
        connection.setAutoCommit(false);

        PreparedStatement insertPreparedStatement = connection.prepareStatement(updateCompanyStatement);
        insertPreparedStatement.setInt(1, company.getId());
        insertPreparedStatement.setString(2, company.getName());
        insertPreparedStatement.setString(3, company.getEmail());
        insertPreparedStatement.setString(4, company.getPassword());
        insertPreparedStatement.setInt(5, company.getId());
        insertPreparedStatement.executeUpdate();

        connection.commit();
        connectionPool.restoreConnection(connection);
    }

    @Override
    public void deleteCompany(int companyID) throws InterruptedException, SQLException {
        Connection connection = ConnectionPool.getConnection();
        connection.setAutoCommit(false);

        PreparedStatement insertPreparedStatement = connection.prepareStatement(deleteCompanyStatement);
        insertPreparedStatement.setInt(1, companyID);
        insertPreparedStatement.executeUpdate();

        connection.commit();
        connectionPool.restoreConnection(connection);
    }

    @Override
    public ArrayList<Company> getAllCompany() throws SQLException, InterruptedException {
        Connection connection = ConnectionPool.getConnection();
        connection.setAutoCommit(false);

        ArrayList<Company> companies= new ArrayList<Company>();
       PreparedStatement preparedStatement= connection.prepareStatement(selectAllCompanyStatement);
       ResultSet resultSet =preparedStatement.executeQuery();
        while (resultSet.next()){
            Company company = new Company(resultSet.getInt("ID"),resultSet.getString("NAME"), resultSet.getString("EMAIL"),resultSet.getString("PASSWORD"),null);
            companies.add(company);
        }
        connection.commit();

        connectionPool.restoreConnection(connection);
        return companies;
    }

    @Override
    public Company getOneCompany(int companyId) throws SQLException, InterruptedException{
        Connection connection = ConnectionPool.getConnection();
        connection.setAutoCommit(false);

        Company company=null;
        PreparedStatement insertPreparedStatement= connection.prepareStatement(selectByIdCompanyStatement);
        insertPreparedStatement.setInt(1, companyId);
        ResultSet resultSet =insertPreparedStatement.executeQuery();
        while (resultSet.next()){
            company = new Company(resultSet.getInt("ID"),resultSet.getString("NAME"), resultSet.getString("EMAIL"),resultSet.getString("PASSWORD"),null);
        }

        connection.commit();
        connectionPool.restoreConnection(connection);
        return company;
    }
}
