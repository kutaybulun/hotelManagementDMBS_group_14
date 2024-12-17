package db;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    // Database connection details
    private static final String DB_URL = "jdbc:mysql://localhost:3306/hotel_management";
    private static final String USER = "kutaybulun";
    private static final String PASS = "12345";

    // Establishes a new connection every time it's called
    public static Connection getConnection() {
        try {
            Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
            //System.out.println("\nCONNECTED TO DATABASE!!");
            return connection;
        } catch (SQLException e) {
            System.out.println("\n!!! CONNECTION FAILED TO DATABASE !!!");
            e.printStackTrace();
            return null;
        }
    }

    // This method is no longer necessary but can be kept for reference
    public static void closeConnection(Connection connection) {
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
