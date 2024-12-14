package databaseaccess;

import db.DBConnection;
import relations.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDataBaseAccess {

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

    // Method to log in a user
    public User logIn(String username, String password) {
        String sql = "SELECT * FROM Users WHERE username = ? AND userpassword = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new User(
                        resultSet.getInt("userID"),
                        resultSet.getString("username"),
                        resultSet.getString("userpassword"),
                        resultSet.getString("userType"),
                        resultSet.getString("contactDetails")
                );
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
}
