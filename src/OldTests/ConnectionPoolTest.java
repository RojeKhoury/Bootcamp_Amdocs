package OldTests;




import Connections.ConnectionPool;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.sql.Connection;
import java.sql.SQLException;
public class ConnectionPoolTest {

    private static ConnectionPool pool1 = ConnectionPool.getInstance();

    private static CountDownLatch start = new CountDownLatch(1);

    private static CountDownLatch end;

    public static void main(String[] args) throws InterruptedException {

        int threadCount =100;
        end = new CountDownLatch(threadCount);

        int count = 20;
        AtomicInteger got = new AtomicInteger();
        AtomicInteger notGot = new AtomicInteger();

        for (int i = 0; i < threadCount; i++) {
            Thread thread = new Thread(
                    new ConnectionRunner(count, got, notGot),
                    "ConnectionRunnerThread");
            thread.start();
        }

        start.countDown();
        end.await();

        try {
            pool1.closeAllConnections();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("T");
        }

        System.out.println("total invoke:" + (threadCount * count));
        System.out.println("got connection:" + got);
        System.out.println("not got connection " + notGot);
    }

    private static class ConnectionRunner implements Runnable {

        private int count;

        private AtomicInteger got;

        private AtomicInteger notGot;

        public ConnectionRunner(int count, AtomicInteger got,
                                AtomicInteger notGot) {
            this.count = count;
            this.got = got;
            this.notGot = notGot;
        }

        @Override
        public void run() {
            try {
                start.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            while (this.count > 0) {
                try {
                    Connection connection = pool1.getConnection();
                    if (connection != null) {
                        try {
                            connection.createStatement();
                           // connection.commit();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        } finally {
                            pool1.restoreConnection(connection);
                            got.incrementAndGet();
                        }
                    } else {
                        notGot.incrementAndGet();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    this.count--;
                }
            }
            end.countDown();
        }
    }
}