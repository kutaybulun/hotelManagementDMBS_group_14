import java.sql.*;
public class DBConnection {
    final static String DB_URL = "jdbc:mysql://localhost:3306/test_1";
    final static String USER = "kutaybulun";
    final static String PASS = "12345";

    //in mySQL
    // CREATE DATABASE test_1;
    // to drop: DROP DATABASE test_1;
    // to show: SHOW DATABASES;
    public static Connection getConnection() {
        Connection conn = null;

        try{
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            if(conn != null) {
                System.out.println("\n CONNECTED TO DATABASE!!");
            }
        } catch (SQLException e){
            System.out.println("\n !!!CONNECTION FAILED TO DATABASE!!!");
        }
        return conn;
    }

    public static void main(String[] args) {
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
    }
}
