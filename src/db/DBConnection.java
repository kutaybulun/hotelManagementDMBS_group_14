package db;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    // Database connection details
    private static final String DB_URL = "jdbc:mysql://localhost:3306/test_1";
    private static final String USER = "kutaybulun";
    private static final String PASS = "12345";
    private static Connection connection;

    // Establishes a single connection to the database
    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(DB_URL, USER, PASS);
                if (connection != null) {
                    System.out.println("\nCONNECTED TO DATABASE!!");
                }
            } catch (SQLException e) {
                System.out.println("\n!!! CONNECTION FAILED TO DATABASE !!!");
                e.printStackTrace();
            }
        }
        return connection;
    }

    // Close the database connection
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("\nDATABASE CONNECTION CLOSED!!");
            }
        } catch (SQLException e) {
            System.out.println("\n!!! ERROR CLOSING THE DATABASE CONNECTION !!!");
            e.printStackTrace();
        }
    }
    //FOR TESTING THE CONNECTION ONLY!!!
    /* public static void main(String[] args) {
        String DB_URL = "jdbc:mysql://localhost:3306/test_1";
        String USER = "kutaybulun";
        String PASS = "12345";

        try{
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            if(conn != null) {
                System.out.println("\n CONNECTED TO DATABASE!!");
                conn.close();
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    } */
}
