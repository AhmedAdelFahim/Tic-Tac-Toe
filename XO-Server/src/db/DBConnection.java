package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static Connection connectionInstance = null;
    private DBConnection(){

    }

    public static synchronized Connection getInstance()
    {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            /* Create connection url. */
            String mysqlConnUrl = "jdbc:mysql://localhost:3306/tic_tac_toe?serverTimezone=UTC";

            /* db user name. */
            String mysqlUserName = "root";

            /* db password. */
            String mysqlPassword = "";

            if(connectionInstance == null){
                connectionInstance =  DriverManager.getConnection(mysqlConnUrl, mysqlUserName , mysqlPassword);
            }
            System.out.println("DB Connect Successed");

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("DB Connect Failed");
            e.printStackTrace();
        }
        return connectionInstance;
    }


    public static synchronized void closeConnection(){
        try {
            connectionInstance.close();
        } catch (SQLException e) {
            System.out.println("DB disconnect Failed");
            e.printStackTrace();
        }
        connectionInstance = null;
        System.out.println("DB disconnect Successed");
    }
}
