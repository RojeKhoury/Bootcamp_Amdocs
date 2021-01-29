package Test;

import CustomException.ConnectionException;
import DAO.*;
import Facade.AdminFacade;
import Facade.ClientFacade;
import Facade.CompanyFacade;
import Facade.CustomerFacade;
import Manager.ClientType;
import Manager.LoginManager;
import couponSys.Category;
import couponSys.Company;
import couponSys.Coupon;
import couponSys.Customer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.sql.Date;

public class Test {

    public static void testAll() {
        testAdminCreation();
        testCompany1Start();
        testCompany2Start();
        testCompany3Start();
        testCustomer1Start();
        testCustomer2Start();
        testCustomer3Start();
        testCompanyDeletingCoupon();
        deletingClientsByAdmin();

    }
    public static void testAdminCreation() {
        String email="admin@admin.com";
        String password="admin";
        LoginManager loginManager;
        ClientFacade clientFacade;
        loginManager= LoginManager.getInstance();
        clientFacade= loginManager.login(email,password, ClientType.Administrator);
        if(clientFacade!=null) {
            System.out.println("*Test* Adding Customers -> Start");
            ((AdminFacade) clientFacade).addCustomer(new Customer(100, "Elias", "Khoury", "customer1@gmail.com", "customer1"));
            ((AdminFacade) clientFacade).addCustomer(new Customer(101, "Marsel", "Jamawei", "customer2@gmail.com", "customer2"));
            ((AdminFacade) clientFacade).addCustomer(new Customer(102, "Ahmad", "Salama", "customer3@gmail.com", "customer3"));
            System.out.println("*Test* Adding Customers ->Done");

            System.out.println("*Test* Printing Customers");
            System.out.println(((AdminFacade) clientFacade).getAllCustomers());

            System.out.println("*Test* Adding Companies -> Start");
            ((AdminFacade) clientFacade).addCompany(new Company(103, "KFC", "company1@gmail.com", "customer4"));
            ((AdminFacade) clientFacade).addCompany(new Company(104, "Vic",  "company2@gmail.com", "customer4"));
            ((AdminFacade) clientFacade).addCompany(new Company(105, "MCD",  "company3@gmail.com", "customer4"));
            System.out.println("*Test* Adding Companies ->Done");

            System.out.println("*Test* Printing Companies");
            ArrayList <Company> companies=((AdminFacade) clientFacade).getAllCompanies();
            if(companies!=null)
                System.out.println(companies);

            System.out.println("*Test*  Customer update (100)-> Start");
            ((AdminFacade) clientFacade).updateCustomer(new Customer(100, "elias", "Khoury", "customer1@gmail.com", "customer1"));
            System.out.println("*Test*  Customer update (100)->Done");
            System.out.println("*Test* Update Results ");
            System.out.println("*Test* Printing One Customer");
            Customer customer=((AdminFacade) clientFacade).getOneCustomer(100);
            if(customer!=null)
                System.out.println(customer);



            System.out.println("*Test*  Company update -> Start");
            ((AdminFacade) clientFacade).updateCompany(new Company(103, "KFC", "company1@gmail.com", "KFCPassword"));
            System.out.println("*Test*  Company update ->Done");
            System.out.println("*Test* Update Results ");
            System.out.println("*Test* Printing One Company");
            System.out.println(((AdminFacade) clientFacade).getOneCompany(103));
        }
        else
        {
            System.out.println("Error");
        }

    }
    public static void testCompany1Start()  {
        String email="company1@gmail.com";
        String password="KFCPassword";
        LoginManager loginManager;
        ClientFacade clientFacade;
        loginManager= LoginManager.getInstance();
        clientFacade= loginManager.login(email,password, ClientType.Company);
        if(clientFacade!=null) {
            System.out.println("Company: KFC        ID:103");

            System.out.println("*Test* Adding Company's Coupons -> Start");
            ((CompanyFacade)clientFacade).addCoupon(new Coupon(10,((CompanyFacade)clientFacade).getCompanyId(), Category.Food,"5 Nagets","Chicken Naget Description",new Date(2019, Calendar.MARCH,1),new Date(2021, Calendar.MARCH,20),2,60.90,"KFC_5_Nagets.png"));
            ((CompanyFacade)clientFacade).addCoupon(new Coupon(11,((CompanyFacade)clientFacade).getCompanyId(), Category.Food,"4 Nagets","Chicken Naget Description",new Date(2019, Calendar.MARCH,20),new Date(2021, Calendar.FEBRUARY,5),2,44.90,"KFC_4_Nagets.png"));
            ((CompanyFacade)clientFacade).addCoupon(new Coupon(12,((CompanyFacade)clientFacade).getCompanyId(), Category.Restaurant,"Family dinner","Chicken Breast and Naget Description",new Date(2020, Calendar.DECEMBER,1),new Date(2021, Calendar.FEBRUARY,10),5,80.90,"KFC_4_Family_Dinner.png"));
            ((CompanyFacade)clientFacade).addCoupon(new Coupon(13,((CompanyFacade)clientFacade).getCompanyId(), Category.Electricity,"test for delete","Will be deleted",new Date(2020, Calendar.DECEMBER,1),new Date(2021, Calendar.FEBRUARY,8),2,80.90,"KFC_4_Family_Dinner.png"));
            System.out.println("*Test* Adding Company's Coupons -> End");

            System.out.println("*Test* Printing Company's Coupons");
            System.out.println( ((CompanyFacade)clientFacade).getCompanyCoupons());



            System.out.println("*Test* updating Company's Coupon -> Start");
            ((CompanyFacade)clientFacade).updateCoupon(new Coupon(10,((CompanyFacade)clientFacade).getCompanyId(), Category.Food,"5 Nagets","Chicken Naget Description and drinks",new Date(2019, Calendar.MARCH,1),new Date(2021, Calendar.MARCH,1),2,60.90,"KFC_5_Nagets&Drinks.png"));
            System.out.println("*Test* updating Company's Coupon -> End");
            System.out.println("*Test* Printing updated coupons");
            System.out.println( ((CompanyFacade)clientFacade).getCompanyCoupons());


            System.out.println("*Test* Printing coupons by Category Food -> Start");
            System.out.println( ((CompanyFacade)clientFacade).getCompanyCoupons(Category.Food));
            System.out.println("*Test* Printing coupons by Category Food -> End");

            System.out.println("*Test* Printing coupons by Max price -> Start");
            System.out.println( ((CompanyFacade)clientFacade).getCompanyCoupons(50));
            System.out.println("*Test* Printing coupons by Max price -> End");

            System.out.println("*Test* Deleting coupon -> Start");
            ((CompanyFacade)clientFacade).deleteCoupon(13);
            System.out.println("*Test* Deleting coupon -> End");

            System.out.println("*Test* Printing Company details");
            System.out.println( ((CompanyFacade)clientFacade).getCompanyDetails());
        }
        else
        {
            System.out.println("Error");
        }

    }

    public static void testCompany2Start()  {
        String email="company2@gmail.com";
        String password="customer4";
        LoginManager loginManager;
        ClientFacade clientFacade;
        loginManager= LoginManager.getInstance();
        clientFacade= loginManager.login(email,password, ClientType.Company);
        if(clientFacade!=null) {
            System.out.println("Company: Vic        ID:104");
            System.out.println("*Test* Adding Company's Coupons -> Start");
            ((CompanyFacade)clientFacade).addCoupon(new Coupon(14,((CompanyFacade)clientFacade).getCompanyId(), Category.Vacation,"Vic Trip EURO","description ex1",new Date(2019, Calendar.MARCH,1),new Date(2020, Calendar.JANUARY,5),2,4000,"Europe.png"));
            ((CompanyFacade)clientFacade).addCoupon(new Coupon(15,((CompanyFacade)clientFacade).getCompanyId(), Category.Vacation,"Vic Trip USA","description ex2",new Date(2019, Calendar.MARCH,1),new Date(2021, Calendar.JANUARY,24),2,5000,"USA.png"));
            ((CompanyFacade)clientFacade).addCoupon(new Coupon(16,((CompanyFacade)clientFacade).getCompanyId(), Category.Restaurant,"Vic 3 Michlean Stars Restaurant","description ex3",new Date(2020, Calendar.DECEMBER,1),new Date(2021, Calendar.FEBRUARY,1),2,1499.90,"Restaurant.png"));
            System.out.println("*Test* Adding Company's Coupons -> End");

            System.out.println("*Test* Printing Company's Coupons");
            System.out.println( ((CompanyFacade)clientFacade).getCompanyCoupons());

            System.out.println("*Test* Printing Company details");
            System.out.println( ((CompanyFacade)clientFacade).getCompanyDetails());
        }
        else
        {
            System.out.println("Error");
        }

    }
    public static void testCompany3Start()  {
        String email="company3@gmail.com";
        String password="customer4";
        LoginManager loginManager;
        ClientFacade clientFacade;
        loginManager= LoginManager.getInstance();
        clientFacade= loginManager.login(email,password, ClientType.Company);
        if(clientFacade!=null) {
            System.out.println("Company: MCD        ID:105");
            System.out.println("*Test* Adding Company's Coupons -> Start");
            ((CompanyFacade)clientFacade).addCoupon(new Coupon(17,((CompanyFacade)clientFacade).getCompanyId(), Category.Vacation,"MC Royal","description ex1",new Date(2019, (Calendar.MARCH),1),new Date(2020, Calendar.JANUARY,1),2,60.90,"MCD_Royal.png"));
            ((CompanyFacade)clientFacade).addCoupon(new Coupon(18,((CompanyFacade)clientFacade).getCompanyId(), Category.Vacation,"MC Royal Double","description ex2",new Date(2019, Calendar.MARCH,1),new Date(2021, Calendar.JANUARY,27),2,44.90,"MCD_Double_Royal.png"));
            ((CompanyFacade)clientFacade).addCoupon(new Coupon(19,((CompanyFacade)clientFacade).getCompanyId(), Category.Restaurant,"Birthday in MCD","description ex3",new Date(2020, Calendar.DECEMBER,1),new Date(2021, Calendar.FEBRUARY,1),2,80.90,"MCD_Family_Birthday.png"));
            System.out.println("*Test* Adding Company's Coupons -> End");

            System.out.println("*Test* Printing Company's Coupons");
            System.out.println( ((CompanyFacade)clientFacade).getCompanyCoupons());

            System.out.println("*Test* Printing Company details");
            System.out.println( ((CompanyFacade)clientFacade).getCompanyDetails());
        }
        else
        {
            System.out.println("Error");
        }

    }

    public static void testCustomer1Start()  {
        String email="customer1@gmail.com";
        String password="customer1";
        LoginManager loginManager;
        ClientFacade clientFacade;
        loginManager= LoginManager.getInstance();
        clientFacade= loginManager.login(email,password, ClientType.Customer);
        if(clientFacade!=null) {
            System.out.println("Company: elias        ID:100");
            System.out.println("*Test*  Customer purchase Coupons -> Start");
            ((CustomerFacade)clientFacade).purchaseCoupon(12);
            ((CustomerFacade)clientFacade).purchaseCoupon(11);
            ((CustomerFacade)clientFacade).purchaseCoupon(11);
            ((CustomerFacade)clientFacade).purchaseCoupon(15);
            ((CustomerFacade)clientFacade).purchaseCoupon(18);
            System.out.println("*Test* Customer purchase Coupons -> End");

            System.out.println("*Test* Printing Customer's coupons by Max price -> Start");
            System.out.println( ((CustomerFacade)clientFacade).getCustomerCoupons(Category.Vacation));
            System.out.println("*Test* Printing coupons by Max price -> End");

            System.out.println("*Test* Printing Customer's coupons by Max price -> Start");
            System.out.println( ((CustomerFacade)clientFacade).getCustomerCoupons(100));
            System.out.println("*Test* Printing coupons by Max price -> End");

            System.out.println("*Test* Printing Customer details");
            System.out.println( ((CustomerFacade)clientFacade).getCustomerDetails());

        }
        else
        {
            System.out.println("Error");
        }

    }

    public static void testCustomer2Start()  {
        String email="customer2@gmail.com";
        String password="customer2";
        LoginManager loginManager;
        ClientFacade clientFacade;
        loginManager= LoginManager.getInstance();
        clientFacade= loginManager.login(email,password, ClientType.Customer);
        if(clientFacade!=null) {
            System.out.println("Company: marcel        ID:101");
            System.out.println("*Test*  Customer purchase Coupons -> Start");
            ((CustomerFacade)clientFacade).purchaseCoupon(12);
            ((CustomerFacade)clientFacade).purchaseCoupon(15);
            ((CustomerFacade)clientFacade).purchaseCoupon(18);
            System.out.println("*Test* Customer purchase Coupons -> End");

            System.out.println("*Test* Printing Customer's coupons by Max price -> Start");
            System.out.println( ((CustomerFacade)clientFacade).getCustomerCoupons(Category.Vacation));
            System.out.println("*Test* Printing coupons by Max price -> End");

            System.out.println("*Test* Printing Customer's coupons by Max price -> Start");
            System.out.println( ((CustomerFacade)clientFacade).getCustomerCoupons(100));
            System.out.println("*Test* Printing coupons by Max price -> End");

            System.out.println("*Test* Printing Customer details");
            System.out.println( ((CustomerFacade)clientFacade).getCustomerDetails());

        }
        else
        {
            System.out.println("Error");
        }

    }
    public static void testCustomer3Start()  {
        String email="customer3@gmail.com";
        String password="customer3";
        LoginManager loginManager;
        ClientFacade clientFacade;
        loginManager= LoginManager.getInstance();
        clientFacade= loginManager.login(email,password, ClientType.Customer);
        if(clientFacade!=null) {
            System.out.println("Company: ahmad        ID:102");
            System.out.println("*Test*  Customer purchase Coupons -> Start");
            ((CustomerFacade)clientFacade).purchaseCoupon(12);
            ((CustomerFacade)clientFacade).purchaseCoupon(15);
            ((CustomerFacade)clientFacade).purchaseCoupon(18);
            System.out.println("*Test* Customer purchase Coupons -> End");

            System.out.println("*Test* Printing Customer's coupons by Category -> Start");
            System.out.println( ((CustomerFacade)clientFacade).getCustomerCoupons(Category.Vacation));
            System.out.println("*Test* Printing coupons by Category -> End");

            System.out.println("*Test* Printing Customer's coupons by Max price -> Start");
            System.out.println( ((CustomerFacade)clientFacade).getCustomerCoupons(100));
            System.out.println("*Test* Printing coupons by Max price -> End");

            System.out.println("*Test* Printing Customer details");
            System.out.println( ((CustomerFacade)clientFacade).getCustomerDetails());

        }
        else
        {
            System.out.println("Error");
        }

    }



    public static void testCompanyDeletingCoupon()
    {
        String email="company3@gmail.com";
        String password="customer4";
        LoginManager loginManager;
        ClientFacade clientFacade;
        loginManager= LoginManager.getInstance();
        clientFacade= loginManager.login(email,password, ClientType.Company);
        if(clientFacade != null) {
            System.out.println("*Test* Show company coupons before deleting");
            System.out.println(((CompanyFacade) clientFacade).getCompanyCoupons());
            System.out.println("*Test* Deleting coupons -> Start");
            ((CompanyFacade) clientFacade).deleteCoupon(17);
            ((CompanyFacade) clientFacade).deleteCoupon(18);
            System.out.println("*Test* Deleting coupons -> End");
            System.out.println("*Test* Show company coupons after deleting");
            System.out.println(((CompanyFacade) clientFacade).getCompanyCoupons());
        }

    }
    public static void deletingClientsByAdmin() {
        String email="admin@admin.com";
        String password="admin";
        LoginManager loginManager;
        ClientFacade clientFacade;
        loginManager= LoginManager.getInstance();
        clientFacade= loginManager.login(email,password, ClientType.Administrator);
        if(clientFacade!=null) {
            System.out.println("*Test* Printing Companies before delete");
            System.out.println(((AdminFacade) clientFacade).getAllCompanies());
            ((AdminFacade) clientFacade).deleteCompany(104);
            System.out.println("*Test* Printing Companies after delete");
            System.out.println(((AdminFacade) clientFacade).getAllCompanies());
            System.out.println("*Test* Printing Customer before delete");
            System.out.println(((AdminFacade) clientFacade).getAllCustomers());
            ((AdminFacade) clientFacade).deleteCustomer(102);
            System.out.println("*Test* Printing Customer after delete");
            System.out.println(((AdminFacade) clientFacade).getAllCustomers());
        }
    }
    public static void DBTestcleaner() throws SQLException, InterruptedException {
        CouponsDAO couponsDAO = new CouponsDBDAO();
        CustomersDAO customersDAO = new CustomersDBDAO();
        CompaniesDAO companiesDAO = new CompaniesDBDAO();
        for(int i = 0; i<=120;i++)
        {
            try {
                couponsDAO.deleteCouponPurchaseWithId(i);
                couponsDAO.deleteCoupon(i);
                customersDAO.deleteCustomer(i);
                companiesDAO.deleteCompany(i);
            } catch (ConnectionException e) {
                e.printStackTrace();
            }
        }

    }



}
