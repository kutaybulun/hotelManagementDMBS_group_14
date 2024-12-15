package databaseaccess;

import db.DBConnection;
import relations.User;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDataBaseAccess {
    private int currentUserID = -1; //-1 is no users logged-in
    // Method to sign up a new user
    public boolean signUp(User user) {
        String sql = "INSERT INTO Users (username, userpassword, userType, contactDetails) VALUES (?, ?, ?, ?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, user.getUserName());
            preparedStatement.setString(2, user.getUserPassword());
            preparedStatement.setString(3, user.getUserType());
            preparedStatement.setString(4, user.getContactDetails());

            int result = preparedStatement.executeUpdate();
            return result > 0; // returns true if 1 or more rows were affected
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int userID) {
        String sql = "DELETE FROM Users WHERE userID = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, userID);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to log in a user
    public User logIn(String username, String password) {
        String sql = "SELECT * FROM Users WHERE username = ? AND userpassword = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                User user = new User(
                        resultSet.getInt("userID"),
                        resultSet.getString("username"),
                        resultSet.getString("userpassword"),
                        resultSet.getString("userType"),
                        resultSet.getString("contactDetails")
                );
                //store the UserID of current user.
                this.currentUserID = user.getUserID();
                return user;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getNextUserID() {
        String sql = "SELECT MAX(userID) FROM Users";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int maxUserID = resultSet.getInt(1); // Get the max userID from the result
                return maxUserID + 1; // Increment it by 1
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 1; // Default to 1 if there are no users in the table
    }
    // will call this to do user-specific operations
    public int getCurrentUserID() {
        return currentUserID;
    }
    // log-out so that it won't mess the current user index
    public void logOut() {
        this.currentUserID = -1;
    }

    // Method to get the user type of the currently logged-in user
    public String getCurrentUserType() {
        if (currentUserID == -1) {
            System.out.println("No user is currently logged in.");
            return null;
        }

        String sql = "SELECT userType FROM Users WHERE userID = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, currentUserID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("userType");
            } else {
                return null; // No user found with this userID
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    //to view all Users
    public List<User> viewAllUsers() {
        List<User> userList = new ArrayList<>();
        String sql = "SELECT * FROM Users";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                User user = new User(
                        resultSet.getInt("userID"),
                        resultSet.getString("username"),
                        resultSet.getString("userpassword"),
                        resultSet.getString("userType"),
                        resultSet.getString("contactDetails")
                );
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }
}
