package databaseaccess;

import db.DBConnection;
import relations.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//EXAMPLE IMPLEMENTATION NOT ALL THE METHODS MAY BE NECESSARY!!
public class UserDataBaseAccess {

    // Create (Insert) a new user
    public void createUser(User user) {
        String query = "INSERT INTO User (userName, password, userType, contactDetails) VALUES (?, ?, ?, ?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, user.getUserName());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getUserType());
            statement.setString(4, user.getContactDetails());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Read (Select) a user by ID
    public User getUserById(int userID) {
        String query = "SELECT * FROM User WHERE userID = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, userID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new User(
                        resultSet.getInt("userID"),
                        resultSet.getString("userName"),
                        resultSet.getString("password"),
                        resultSet.getString("userType"),
                        resultSet.getString("contactDetails")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Read (Select) a user by username
    public User getUserByUsername(String userName) {
        String query = "SELECT * FROM User WHERE userName = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, userName);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new User(
                        resultSet.getInt("userID"),
                        resultSet.getString("userName"),
                        resultSet.getString("password"),
                        resultSet.getString("userType"),
                        resultSet.getString("contactDetails")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Read (Select) all users
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM User";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                users.add(new User(
                        resultSet.getInt("userID"),
                        resultSet.getString("userName"),
                        resultSet.getString("password"),
                        resultSet.getString("userType"),
                        resultSet.getString("contactDetails")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    // Update user information
    public void updateUser(User user) {
        String query = "UPDATE User SET userName = ?, password = ?, userType = ?, contactDetails = ? WHERE userID = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, user.getUserName());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getUserType());
            statement.setString(4, user.getContactDetails());
            statement.setInt(5, user.getUserID());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete a user by ID
    public void deleteUser(int userID) {
        String query = "DELETE FROM User WHERE userID = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, userID);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Check if a username exists
    public boolean doesUsernameExist(String userName) {
        String query = "SELECT 1 FROM User WHERE userName = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, userName);
            ResultSet resultSet = statement.executeQuery();

            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Get the user type for a specific user
    public String getUserType(String userID) {
        String query = "SELECT userType FROM User WHERE userID = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, userID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("userType");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
