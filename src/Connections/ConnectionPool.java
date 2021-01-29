package Connections;


import CustomException.ConnectionException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

public class ConnectionPool {


    public static final String JDBC_DRIVER="com.mysql.jdbc.Driver";
    public static final String DB_URL= "jdbc:mysql://127.0.0.1:3306/mydb?useSSL=false&serverTimezone=UTC";
    public static final String user ="root";
    public static final String password="Roje@1995";//Roje@1995";
    private static final Set<Connection> pool = new LinkedHashSet<Connection>();
    private static final Set<Connection> poolSave = new LinkedHashSet<Connection>();
    public static final int poolSize=8;
    private static ConnectionPool single_instance = null;

    public static ConnectionPool getInstance()
    {
        if(single_instance == null)
            synchronized(ConnectionPool.class) {
            if(single_instance == null) {
                single_instance = new ConnectionPool();
                initializeConnections();
            }
        }

        return single_instance;
    }

    private static void initializeConnections() {
        if (poolSize > 0) {
            for (int i = 0; i < poolSize; i++) {
                try {
                    Class.forName(JDBC_DRIVER);
                    Connection temp = DriverManager.getConnection(DB_URL , user ,password);

                    pool.add(temp);
                    poolSave.add(temp);
                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public void restoreConnection(Connection connection) {
        if (connection != null) {
            synchronized (pool) {
                pool.add(connection);
                // wake up waiting threads in the pool
                pool.notifyAll();
            }
        }
    }
    public void closeAllConnections() throws SQLException {

        while(!poolSave.isEmpty()) {
                for ( Connection con: poolSave) {
                    con.close();
                    System.out.println( con +"<<==Deleted successfully==>>");
                }
                poolSave.clear();
        }
    }

    public static Connection getConnection() throws ConnectionException {
        synchronized (pool) {
                WaitForConnection();
                Connection result = null;//Default if no connection available
                if (!pool.isEmpty()) {
                    for (Connection con: pool) {
                        result=con;

                    }
                    pool.remove(result);
                }
                if(result==null)
                {
                    throw new ConnectionException();
                }
                return result;

        }
    }

    private static void WaitForConnection()  {
        //Wait if there is no Connection available
        long future = System.currentTimeMillis() + 100;
        long remaining = 100;
        while (pool.isEmpty() && remaining > 0) {

            try {
                pool.wait(remaining);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            remaining = future - System.currentTimeMillis();
        }
    }



}