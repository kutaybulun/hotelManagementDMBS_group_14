package databaseaccess;

import db.DBConnection;
import relations.Payment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PaymentDataBaseAccess {

    // Create a new Payment record in the Payment table
    public boolean create(Payment payment, Connection connection) {
        String sql = "INSERT INTO Payment (bookingID, amount, paymentDate) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

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
        String sql = "SELECT COALESCE(MAX(paymentID), 0) FROM Payment";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int maxPaymentID = resultSet.getInt(1); // Get the max bookingID (or 0 if no rows)
                return maxPaymentID + 1; // Increment it by 1
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 1; // Default to 1 if there are no bookings in the table
    }

}
