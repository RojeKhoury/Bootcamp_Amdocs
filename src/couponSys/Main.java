package couponSys;

import DAO.CustomersDAO;
import DAO.CustomersDBDAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Main
{
    public static void main(String [] args)
    {


        ArrayList<String> cars = new ArrayList<String>();
        Company c;
        Date time1=new Date(2000,Calendar.JANUARY, 11, 19,12);
        Date time2=new Date(2020,1, 20, 19,12);
        ArrayList<Coupon> coupns;
        coupns = new ArrayList<Coupon>();
        Coupon x = new Coupon (10,0,Category.Food,"test1","test2",time1,time2,2,1.1,"Roje/test1");
        coupns.add(x);
        //Customer man=new Customer(101,"Roje","Khoury","test@gmail.com","mypass",coupns);
        Customer man=new Customer(101,"Roje","Khoury","test@gmail.com","mypass",coupns);

        //Company company=new Company(9,"King","R-je","Khury",null);
        Company companyUpdate=new Company(9,"King","Roje","Khoury",null);
        //Company companyUpdate=new Company(9,"King","Roje","Khoury",null);
        try
        {
            //COMPANY TESTS
            //CompaniesDAO companiesDAO= new CompaniesDBDAO();
            //companiesDAO.addCompany(company);
            //System.out.println(companiesDAO.getAllCompany().toString());
            //System.out.println(companiesDAO.getOneCompany(8).toString());
            //System.out.println(companiesDAO.isCompanyExists("Marcel222","Marcel33"));
            //companiesDAO.updateCompany(companyUpdate);
            //-------------------------------------------------------------------------

            customerTest();
            for (int i = 0 ; i< 10; i++) {
                //companiesDAO.deleteCompany(i);
            }
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage().toString());
        }
       // System.out.println(man.toString());
    }
    public static void customerTest() throws SQLException, InterruptedException {
        Date time1=new Date(2000,Calendar.JANUARY, 11, 19,12);
        Date time2=new Date(2020,1, 20, 19,12);
        ArrayList<Coupon> coupns;
        coupns = new ArrayList<Coupon>();
        Coupon x = new Coupon (10,0,Category.Food,"test1","test2",time1,time2,2,1.1,"Roje/test1");
        coupns.add(x);
        Customer man=new Customer(102,"Roje","Khoury","test@gmail.com","mypass1",coupns);
        CustomersDAO customersDAO= new CustomersDBDAO();
        customersDAO.addCustomer(man);
        System.out.println(customersDAO.getAllCustomer().toString());
        customersDAO.deleteCustomer(102);
        System.out.println(customersDAO.getAllCustomer().toString());
        System.out.println("Exist-> "+customersDAO.isCustomerExists("test@gmail.com","mypass1"));
        System.out.println("Customer 101-> "+ customersDAO.getOneCustomer(101));
        System.out.println(customersDAO.getAllCustomer());



    }
}


