package DAO;

import couponSys.Company;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CompaniesDAO {

    boolean isCompanyExists(String i_email, String password) throws InterruptedException, SQLException;

    void addCompany(Company company) throws SQLException, InterruptedException;

    void updateCompany(Company company) throws SQLException, InterruptedException;

    void deleteCompany(int companyID) throws InterruptedException, SQLException;

    ArrayList<Company> getAllCompany() throws SQLException, InterruptedException;

    Company getOneCompany(int companyId) throws SQLException, InterruptedException;


}
