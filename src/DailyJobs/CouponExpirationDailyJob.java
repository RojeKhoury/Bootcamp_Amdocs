package DailyJobs;

import CustomException.ConnectionException;
import DAO.CouponsDAO;
import DAO.CouponsDBDAO;
import couponSys.Coupon;

import javax.swing.plaf.nimbus.State;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;
import java.sql.Date;

public class CouponExpirationDailyJob implements Runnable{
    CouponsDAO coupunsDao;
    static boolean quit = false;

    public CouponExpirationDailyJob()  {
        this.coupunsDao = new CouponsDBDAO();
    }


    @Override
    public void run()  {
        while(!this.quit)
        {
            Thread.currentThread().getId();
            job();

            try {
                wait(100*60*60*24);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public void job()
    {
        try {
            java.util.Date date = new java.util.Date();
            LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            ArrayList<Coupon> coupons = coupunsDao.getCouponBeforeDate(new Date(localDate.getYear() - 1900, localDate.getMonthValue() - 1, localDate.getDayOfMonth()));
            if (coupons.isEmpty())
                System.out.println("work is done");
            else
                System.out.println(coupons);
            //Deleteing the coupons
            coupons.forEach(coupon -> {
                try {
                    coupunsDao.deleteCoupon(coupon.getId());
                } catch (ConnectionException | SQLException e) {
                    System.out.println(e.getMessage()+"During daily coupons deleting job");
                }
            });

            try {
                Thread.sleep(1000 * 60 * 60 * 24);
            } catch (InterruptedException e) {
            }
        } catch (ConnectionException | SQLException e) {
            System.out.println(e.getMessage()+"During daily coupons deleting job");
        }
    }

    public static void stop(Thread dailyJob)
    {
        System.out.println("Going to stop the job!");
            dailyJob.interrupt();
    }

}
