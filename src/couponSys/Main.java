package couponSys;
import Connections.ConnectionPool;
import DailyJobs.CouponExpirationDailyJob;

import java.sql.SQLException;
import static Test.Test.DBTestcleaner;
import static Test.Test.testAll;


public class Main{


    public static void main(String [] args) throws SQLException, InterruptedException {
        //DBTestcleaner();

        //DailyJobs.CouponExpirationDailyJob dailyJob1 =new DailyJobs.CouponExpirationDailyJob();
        //Thread dailyJob = new Thread(dailyJob1);
        //dailyJob.start();
        testAll();
        DBTestcleaner();
        //ConnectionPool connectionPool=ConnectionPool.getInstance();
        //connectionPool.closeAllConnections();
    }
}


