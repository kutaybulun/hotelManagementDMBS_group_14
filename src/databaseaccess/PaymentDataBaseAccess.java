package databaseaccess;

import db.DBConnection;
import relations.Payment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PaymentDataBaseAccess {

    // Create a new Payment record in the Payment table
    public boolean create(Payment payment) {
        String sql = "INSERT INTO Payment (bookingID, amount, paymentDate) VALUES (?, ?, ?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, payment.getBookingID());
            preparedStatement.setBigDecimal(2, payment.getAmount());
            preparedStatement.setDate(3, java.sql.Date.valueOf(payment.getPaymentDate()));

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete a Payment record by paymentID
    public boolean delete(int paymentID) {
        String sql = "DELETE FROM Payment WHERE paymentID = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, paymentID);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get the next available Payment ID
    public int getNextPaymentID() {
        String sql = "SELECT MAX(paymentID) + 1 AS nextID FROM Payment";
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
