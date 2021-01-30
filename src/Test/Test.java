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
import DAO.CompaniesDAO;
public class Test {
    public static CompaniesDAO companiesDao= new CompaniesDBDAO();
    public static CustomersDAO customersDao= new CustomersDBDAO();
    public static CouponsDAO couponsDao= new CouponsDBDAO();
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
        TestingExceptions();

    }
    public static void testAdminCreation() {
        String email="admin@admin.com";
        String password="admin";
        LoginManager loginManager;
        ClientFacade clientFacade;
        loginManager= LoginManager.getInstance();
        clientFacade= loginManager.login(email,password, ClientType.Administrator);
        if(clientFacade!=null) {
            System.out.println("*Test* <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Adding Customers Start >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            ((AdminFacade) clientFacade).addCustomer(new Customer( "Elias", "Khoury", "customer1@gmail.com", "customer1"));
            ((AdminFacade) clientFacade).addCustomer(new Customer( "Marsel", "Jamawei", "customer2@gmail.com", "customer2"));
            ((AdminFacade) clientFacade).addCustomer(new Customer( "Ahmad", "Salama", "customer3@gmail.com", "customer3"));
            System.out.println("*Test* <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Adding Customers Done >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

            System.out.println("*Test* Printing Customers");
            System.out.println(((AdminFacade) clientFacade).getAllCustomers());

            System.out.println("*Test* <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Adding Companies Start >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            ((AdminFacade) clientFacade).addCompany(new Company( "KFC", "company1@gmail.com", "customer4"));
            ((AdminFacade) clientFacade).addCompany(new Company( "Vic",  "company2@gmail.com", "customer4"));
            ((AdminFacade) clientFacade).addCompany(new Company("MCD",  "company3@gmail.com", "customer4"));
            System.out.println("*Test* <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Adding Companies Done >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

            System.out.println("*Test* <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Printing Companies >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            ArrayList <Company> companies=((AdminFacade) clientFacade).getAllCompanies();

            System.out.println(companies);

            System.out.println("*Test* <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Customer update "+((AdminFacade) clientFacade).getCustomerIDByCredentials("customer1@gmail.com","customer1")+" Start >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            ((AdminFacade) clientFacade).updateCustomer(new Customer(((AdminFacade) clientFacade).getCustomerIDByCredentials("customer1@gmail.com","customer1"),"elias", "Khoury", "customer1@gmail.com", "customer1"));
            System.out.println("*Test* <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Customer update Done >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            System.out.println("*Test* <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Update Results >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            System.out.println("*Test* <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Printing One Customer >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            Customer customer=((AdminFacade) clientFacade).getOneCustomer(((AdminFacade) clientFacade).getCustomerIDByCredentials("customer1@gmail.com","customer1"));
                System.out.println(customer);



            System.out.println("*Test*  <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Company update Start >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            ((AdminFacade) clientFacade).updateCompany(new Company(((AdminFacade) clientFacade).getCompanyByName("KFC").getId(),"KFC", "company1@gmail.com", "KFCPassword"));
            System.out.println("*Test* <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Company update Done >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            System.out.println("*Test*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Update Results >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            System.out.println("*Test*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Printing One Company >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            System.out.println(((AdminFacade) clientFacade).getOneCompany(((AdminFacade) clientFacade).getCompanyByName("KFC").getId()));
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
            System.out.println("Company: KFC");

            System.out.println("*Test*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Adding Company's Coupons -> Start>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            ((CompanyFacade)clientFacade).addCoupon(new Coupon(((CompanyFacade)clientFacade).getCompanyByName("KFC").getId(), Category.Food,"5 Nagets","Chicken Naget Description",new Date(2019, Calendar.MARCH,1),new Date(2021, Calendar.MARCH,20),2,60.90,"KFC_5_Nagets.png"));
            ((CompanyFacade)clientFacade).addCoupon(new Coupon(((CompanyFacade)clientFacade).getCompanyByName("KFC").getId(), Category.Food,"4 Nagets","Chicken Naget Description",new Date(2019, Calendar.MARCH,20),new Date(2021, Calendar.FEBRUARY,5),2,44.90,"KFC_4_Nagets.png"));
            ((CompanyFacade)clientFacade).addCoupon(new Coupon(((CompanyFacade)clientFacade).getCompanyByName("KFC").getId(), Category.Restaurant,"Family dinner","Chicken Breast and Naget Description",new Date(2020, Calendar.DECEMBER,1),new Date(2021, Calendar.FEBRUARY,10),5,80.90,"KFC_4_Family_Dinner.png"));
            ((CompanyFacade)clientFacade).addCoupon(new Coupon(((CompanyFacade)clientFacade).getCompanyByName("KFC").getId(), Category.Electricity,"test for delete","Will be deleted",new Date(2020, Calendar.DECEMBER,1),new Date(2021, Calendar.FEBRUARY,8),2,80.90,"KFC_4_Family_Dinner.png"));
            System.out.println("*Test*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Adding Company's Coupons -> End>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

            System.out.println("*Test* <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Printing Company's Coupons>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            System.out.println( ((CompanyFacade)clientFacade).getCompanyCoupons());

            System.out.println("*Test* <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<updating Company's Coupon "+((CompanyFacade)clientFacade).getCouponByTitle("5 Nagets").getId()+" Start>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            ((CompanyFacade)clientFacade).updateCoupon(new Coupon(((CompanyFacade)clientFacade).getCouponByTitle("5 Nagets").getId(),((CompanyFacade)clientFacade).getCompanyId(), Category.Food,"5 Nagets","Chicken Naget Description and drinks",new Date(2019, Calendar.MARCH,1),new Date(2021, Calendar.MARCH,1),2,60.90,"KFC_5_Nagets&Drinks.png"));
            System.out.println("*Test*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< updating Company's Coupon -> End>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            System.out.println("*Test*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Printing updated coupons>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            System.out.println( ((CompanyFacade)clientFacade).getCompanyCoupons());


            System.out.println("*Test*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Printing coupons by Category Food -> Start >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            System.out.println( ((CompanyFacade)clientFacade).getCompanyCoupons(Category.Food));
            System.out.println("*Test* <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Printing coupons by Category Food -> End");

            System.out.println("*Test* <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Printing coupons by Max price 50 -> Start>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            System.out.println( ((CompanyFacade)clientFacade).getCompanyCoupons(50));
            System.out.println("*Test* <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Printing coupons by Max price 50-> End>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

            System.out.println("*Test* <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Printing Company details>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
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
            System.out.println("Company: Vic");
            System.out.println("*Test*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Adding Company's Coupons -> Start>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            ((CompanyFacade)clientFacade).addCoupon(new Coupon(((CompanyFacade)clientFacade).getCompanyByName("Vic").getId(), Category.Vacation,"Vic Trip EURO","description ex1",new Date(2019, Calendar.MARCH,1),new Date(2020, Calendar.MAY,5),2,4000,"Europe.png"));
            ((CompanyFacade)clientFacade).addCoupon(new Coupon(((CompanyFacade)clientFacade).getCompanyByName("Vic").getId(), Category.Vacation,"Vic Trip USA","description ex2",new Date(2019, Calendar.MARCH,1),new Date(2021, Calendar.FEBRUARY,24),2,5000,"USA.png"));
            ((CompanyFacade)clientFacade).addCoupon(new Coupon(((CompanyFacade)clientFacade).getCompanyByName("Vic").getId(), Category.Restaurant,"Vic 3 Michlean Stars Restaurant","description ex3",new Date(2020, Calendar.DECEMBER,1),new Date(2021, Calendar.FEBRUARY,1),2,1499.90,"Restaurant.png"));
            System.out.println("*Test*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Adding Company's Coupons -> End>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

            System.out.println("*Test* <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Printing Company's Coupons>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            System.out.println( ((CompanyFacade)clientFacade).getCompanyCoupons());

            System.out.println("*Test* <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Printing Company details>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
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
            System.out.println("Company: MCD");
            System.out.println("*Test* <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Adding Company's Coupons -> Start>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            ((CompanyFacade)clientFacade).addCoupon(new Coupon(((CompanyFacade)clientFacade).getCompanyByName("MCD").getId(), Category.Food,"MC Royal","description ex1",new Date(2019, (Calendar.MARCH),1),new Date(2020, Calendar.MARCH,1),2,60.90,"MCD_Royal.png"));
            ((CompanyFacade)clientFacade).addCoupon(new Coupon(((CompanyFacade)clientFacade).getCompanyByName("MCD").getId(), Category.Food,"MC Royal Double","description ex2",new Date(2019, Calendar.MARCH,1),new Date(2021, Calendar.DECEMBER,27),2,44.90,"MCD_Double_Royal.png"));
            ((CompanyFacade)clientFacade).addCoupon(new Coupon(((CompanyFacade)clientFacade).getCompanyByName("MCD").getId(), Category.Restaurant,"Birthday in MCD","description ex3",new Date(2020, Calendar.DECEMBER,1),new Date(2021, Calendar.FEBRUARY,1),2,80.90,"MCD_Family_Birthday.png"));
            System.out.println("*Test* <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Adding Company's Coupons -> End>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

            System.out.println("*Test* <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Printing Company's Coupons>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            System.out.println( ((CompanyFacade)clientFacade).getCompanyCoupons());

            System.out.println("*Test*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Deleting coupon -> Start>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            ((CompanyFacade)clientFacade).deleteCoupon(((CompanyFacade)clientFacade).getCouponByTitle("Birthday in MCD").getId());
            System.out.println("*Test* <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Deleting coupon -> End>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

            System.out.println("*Test* <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Printing Company details>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
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
        //only for the tests
        loginManager= LoginManager.getInstance();
        clientFacade= loginManager.login(email,password, ClientType.Customer);
        if(clientFacade!=null) {
            System.out.println("Company: elias ");
            System.out.println("*Test* <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Customer purchase Coupons -> Start >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            ((CustomerFacade)clientFacade).purchaseCoupon(((CustomerFacade)clientFacade).getCouponByTitle("Vic Trip USA").getId());
            ((CustomerFacade)clientFacade).purchaseCoupon(((CustomerFacade)clientFacade).getCouponByTitle("Vic Trip EURO").getId());
            ((CustomerFacade)clientFacade).purchaseCoupon(((CustomerFacade)clientFacade).getCouponByTitle("Vic Trip USA").getId());//Should get exist exception
            ((CustomerFacade)clientFacade).purchaseCoupon(((CustomerFacade)clientFacade).getCouponByTitle("MC Royal Double").getId());
            System.out.println("*Test* <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Customer purchase Coupons -> End >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

            System.out.println("*Test* <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Printing Customer's coupons by Category(Vacation) Start >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            System.out.println( ((CustomerFacade)clientFacade).getCustomerCoupons(Category.Vacation));
            System.out.println("*Test* <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Printing coupons by Category(Vacation)  End >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

            System.out.println("*Test* <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Printing Customer's coupons by Max price 100 Start >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            System.out.println( ((CustomerFacade)clientFacade).getCustomerCoupons(100));
            System.out.println("*Test* <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Printing coupons by Max price 100 End >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

            System.out.println("*Test* <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Printing Customer details >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
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
            System.out.println("Company: marcel ");
            System.out.println("*Test* <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<  Customer purchase Coupons Start >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            ((CustomerFacade)clientFacade).purchaseCoupon(((CustomerFacade)clientFacade).getCouponByTitle("4 Nagets").getId());
            ((CustomerFacade)clientFacade).purchaseCoupon(((CustomerFacade)clientFacade).getCouponByTitle("5 Nagets").getId());
            ((CustomerFacade)clientFacade).purchaseCoupon(((CustomerFacade)clientFacade).getCouponByTitle("MC Royal").getId());
            System.out.println("*Test* <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Customer purchase Coupons End >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

            System.out.println("*Test* <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Printing Customer's coupons by Category (Vacation) Start >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            System.out.println( ((CustomerFacade)clientFacade).getCustomerCoupons(Category.Vacation));
            System.out.println("*Test* <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Printing coupons by Category (Vacation) End >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

            System.out.println("*Test* <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Printing Customer's coupons by Max price 100 Start>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            System.out.println( ((CustomerFacade)clientFacade).getCustomerCoupons(100));
            System.out.println("*Test* <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Printing coupons by Max price 100 End >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

            System.out.println("*Test* <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Printing Customer details>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
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
            System.out.println("Company: ahmad");
            System.out.println("*Test* <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Customer purchase Coupons Start>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            ((CustomerFacade)clientFacade).purchaseCoupon(((CustomerFacade)clientFacade).getCouponByTitle("Vic 3 Michlean Stars Restaurant").getId());
            ((CustomerFacade)clientFacade).purchaseCoupon(((CustomerFacade)clientFacade).getCouponByTitle("Vic 3 Michlean Stars Restaurant").getId());
            System.out.println("*Test* <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Customer purchase Coupons End>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

            System.out.println("*Test* <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Printing Customer's coupons by Category Start >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            System.out.println( ((CustomerFacade)clientFacade).getCustomerCoupons(Category.Vacation));
            System.out.println("*Test* <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Printing coupons by Category End>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

            System.out.println("*Test* <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Printing Customer's coupons by Max price 100 Start >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            System.out.println( ((CustomerFacade)clientFacade).getCustomerCoupons(100));
            System.out.println("*Test* <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Printing coupons by Max price 100 End>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

            System.out.println("*Test* <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Printing Customer details>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            System.out.println( ((CustomerFacade)clientFacade).getCustomerDetails());

        }
        else
        {
            System.out.println("Error");
        }

    }



    public static void testCompanyDeletingCoupon()
    {
        String email="company2@gmail.com";
        String password="customer4";
        LoginManager loginManager;
        ClientFacade clientFacade;
        loginManager= LoginManager.getInstance();
        clientFacade= loginManager.login(email,password, ClientType.Company);
        if(clientFacade != null) {
            System.out.println("*Test* <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Show company coupons before deleting>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            System.out.println(((CompanyFacade) clientFacade).getCompanyCoupons());
            System.out.println("*Test* <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Deleting coupon Vic Trip USA-> Start>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            ((CompanyFacade) clientFacade).deleteCoupon(((CompanyFacade) clientFacade).getCouponByTitle("Vic Trip USA").getId());
            System.out.println("*Test* <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Deleting coupon Vic Trip USA-> End>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            System.out.println("*Test* <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Show company coupons after deleting>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
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
            System.out.println("*Test* <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Printing Companies before delete KFC>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            System.out.println(((AdminFacade) clientFacade).getAllCompanies());
            ((AdminFacade) clientFacade).deleteCompany(((AdminFacade)clientFacade).getCompanyByName("KFC").getId());
            System.out.println("*Test* <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Printing Companies after delete KFC>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            System.out.println(((AdminFacade) clientFacade).getAllCompanies());
            System.out.println("*Test* <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Printing Customer before delete >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            System.out.println(((AdminFacade) clientFacade).getAllCustomers());
            ((AdminFacade) clientFacade).deleteCustomer(((AdminFacade)clientFacade).getCustomerIDByCredentials("customer3@gmail.com","customer3"));
            System.out.println("*Test* <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Printing Customer after delete>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            System.out.println(((AdminFacade) clientFacade).getAllCustomers());
        }
    }
    public static void TestingExceptions() {
        System.out.println("*Test* <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Testing Exceptions >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        ClientFacade clientFacade=getAdmin();
        ((AdminFacade) clientFacade).addCompany(new Company("MCD",  "company3@gmail.com", "customer4"));
        ((AdminFacade) clientFacade).updateCompany(new Company(((AdminFacade) clientFacade).getCompanyByName("MCD").getId(),"MC",  "company3@gmail.com", "customer4"));

        clientFacade=getCompany3();
        ((CompanyFacade) clientFacade).updateCoupon(new Coupon(((CompanyFacade)clientFacade).getCouponByTitle("MC Royal Double").getId(),((CompanyFacade)clientFacade).getCompanyByName("MCD").getId(), Category.Food,"MCRoyal Double","description ex2",new Date(2019, Calendar.MARCH,1),new Date(2021, Calendar.DECEMBER,27),2,44.90,"MCD_Double_Royal.png"));
        ((CompanyFacade) clientFacade).updateCoupon(new Coupon(((CompanyFacade)clientFacade).getCouponByTitle("MC Royal Double").getId(),((CompanyFacade)clientFacade).getCompanyByName("MCD").getId()+1, Category.Food,"MC Royal Double","description ex2",new Date(2019, Calendar.MARCH,1),new Date(2021, Calendar.DECEMBER,27),2,44.90,"MCD_Double_Royal.png"));
        ((CompanyFacade) clientFacade).addCoupon(new Coupon(((CompanyFacade)clientFacade).getCompanyByName("Vic").getId(), Category.Restaurant,"Vic 3 Michlean Stars Restaurant","description ex3",new Date(2020, Calendar.DECEMBER,1),new Date(2021, Calendar.FEBRUARY,1),2,1499.90,"Restaurant.png"));
        ((CompanyFacade) clientFacade).deleteCoupon(100000000);

        clientFacade=getCustomer3();
        ((CustomerFacade) clientFacade).purchaseCoupon(((CustomerFacade)clientFacade).getCouponByTitle("MC Royal Double").getId());
        System.out.println("*Test* <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< The End >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }
    public static ClientFacade getAdmin() {
        String email="admin@admin.com";
        String password="admin";
        LoginManager loginManager;
        loginManager= LoginManager.getInstance();
        return loginManager.login(email,password, ClientType.Administrator);

    }
    public static ClientFacade getCompany3() {
        LoginManager loginManager;
        loginManager= LoginManager.getInstance();
        return loginManager.login("company3@gmail.com","customer4", ClientType.Company);
    }
    public static ClientFacade getCustomer3() {
        LoginManager loginManager;
        loginManager= LoginManager.getInstance();
        return loginManager.login("customer1@gmail.com","customer1", ClientType.Customer);
    }
    public static void DBTestcleaner() throws SQLException, InterruptedException {
        CouponsDAO couponsDAO = new CouponsDBDAO();
        CustomersDAO customersDAO = new CustomersDBDAO();
        CompaniesDAO companiesDAO = new CompaniesDBDAO();
        for(int i = 0; i<=1000;i++)
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
