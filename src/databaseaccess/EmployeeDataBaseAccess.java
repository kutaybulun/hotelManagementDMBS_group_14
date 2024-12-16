package databaseaccess;

import db.DBConnection;
import relations.Employee;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDataBaseAccess {

    // Create a new employee in the database
    public boolean create(Employee employee) {
        String sql = "INSERT INTO Employee (ename, erole, dailysalary, hotelID, contactDetails) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, employee.getEname());
            preparedStatement.setString(2, employee.getErole());
            preparedStatement.setBigDecimal(3, employee.getDailysalary());
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
        String sql = "SELECT MAX(employeeID) FROM Employee";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int maxEmployeeID = resultSet.getInt(1); // Get the max employeeID from the result
                return maxEmployeeID + 1; // Increment it by 1
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 1; // Default to 1 if there are no employees in the table
    }

    // View all employees in the Employee table
    public List<Employee> viewAllEmployees() {
        List<Employee> employeeList = new ArrayList<>();
        String sql = "SELECT * FROM Employee";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Employee employee = new Employee(
                        resultSet.getInt("employeeID"),
                        resultSet.getString("ename"),
                        resultSet.getString("erole"),
                        resultSet.getBigDecimal("dailysalary"),
                        resultSet.getInt("hotelID"),
                        resultSet.getString("contactDetails")
                );
                employeeList.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeeList;
    }

}
