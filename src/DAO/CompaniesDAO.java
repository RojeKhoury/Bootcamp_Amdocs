package DAO;

import CustomException.ConnectionException;
import couponSys.Company;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CompaniesDAO {

    public boolean isCompanyExists(String i_email, String password) throws SQLException, ConnectionException;

    public boolean isCompanyExistsValidate(String email) throws ConnectionException, SQLException;

    public int getCompanyIdByCredentials (String email, String password) throws ConnectionException, SQLException;

    public void addCompany(Company company) throws ConnectionException, SQLException;

    public void updateCompany(Company company) throws ConnectionException, SQLException;

    public void deleteCompany(int companyID) throws SQLException, ConnectionException;

    public ArrayList<Company> getAllCompany() throws ConnectionException, SQLException;

    public Company getOneCompany(int companyId) throws ConnectionException, SQLException;

    public Company getCompanyByName(String i_name) throws ConnectionException, SQLException;

}
