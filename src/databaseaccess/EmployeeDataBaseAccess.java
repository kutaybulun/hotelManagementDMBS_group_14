package databaseaccess;

import db.DBConnection;
import relations.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDataBaseAccess {

    // Create a new employee in the database
    public boolean create(Employee employee) {
        String sql = "INSERT INTO Employee (ename, userID, roleID, hotelID, contactDetails) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, employee.getEname());
            preparedStatement.setInt(2, employee.getUserID());
            preparedStatement.setInt(3, employee.getRoleID());
            preparedStatement.setInt(4, employee.getHotelID());
            preparedStatement.setString(5, employee.getContactDetails());

            int rowsInserted = preparedStatement.executeUpdate();
            return rowsInserted > 0; // Return true if at least one row was inserted
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete an employee record by employeeID
    public boolean delete(int employeeID) {
        String sql = "DELETE FROM Employee WHERE employeeID = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, employeeID);
            int rowsDeleted = preparedStatement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get the next available employeeID
    public int getNextEmployeeID() {
        String sql = "SELECT COALESCE(MAX(employeeID), 0) + 1 AS nextID FROM Employee";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getInt("nextID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 1; // Default to 1 if there are no employees in the table
    }

    // Get Role ID by Role Name
    public int getRoleIDByName(String roleName) {
        String sql = "SELECT roleID FROM EmployeeRole WHERE roleName = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, roleName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("roleID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Role not found
    }


    // View all employees along with role and salary information
    public List<EmployeeWithRole> viewAllEmployeesWithRoles() {
        List<EmployeeWithRole> employeeList = new ArrayList<>();
        String sql = """
        SELECT E.employeeID, E.ename, E.userID, E.roleID, E.hotelID, E.contactDetails, R.roleName, R.dailySalary
        FROM Employee E
        JOIN EmployeeRole R ON E.roleID = R.roleID
    """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                EmployeeWithRole employee = new EmployeeWithRole(
                        resultSet.getInt("employeeID"),
                        resultSet.getString("ename"),
                        resultSet.getInt("userID"),
                        resultSet.getInt("roleID"),
                        resultSet.getInt("hotelID"),
                        resultSet.getString("contactDetails"),
                        resultSet.getString("roleName"),
                        resultSet.getBigDecimal("dailySalary")
                );
                employeeList.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeeList;
    }

}
