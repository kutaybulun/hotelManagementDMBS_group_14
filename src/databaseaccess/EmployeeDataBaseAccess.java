package databaseaccess;

import db.DBConnection;
import relations.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeDataBaseAccess {

    // Create a new Employee record in the Employee table
    public boolean create(Employee employee) {
        String sql = "INSERT INTO Employee (ename, erole, hotelID, contactDetails) VALUES (?, ?, ?, ?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, employee.getEName());
            preparedStatement.setString(2, employee.getERole());
            preparedStatement.setInt(3, employee.getHotelID());
            preparedStatement.setString(4, employee.getContactDetails());

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete an Employee by ID
    public boolean delete(int employeeID) {
        String sql = "DELETE FROM Employee WHERE employeeID = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, employeeID);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get the next available Employee ID
    public int getNextEmployeeID() {
        String sql = "SELECT MAX(employeeID) + 1 AS nextID FROM Employee";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getInt("nextID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 1; // If no records exist, return 1
    }
}
