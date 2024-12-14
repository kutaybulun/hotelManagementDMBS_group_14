package databaseaccess;

import db.DBConnection;
import relations.Booking;

import java.sql.*;

public class BookingDataBaseAccess {

    // Create a new Booking record in the Booking table
    public boolean create(Booking booking) {
        String sql = "INSERT INTO Booking (userID, checkInDate, checkOutDate, numberOfGuests, paymentStatus, reservationStatus) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, booking.getUserID());
            preparedStatement.setDate(2, java.sql.Date.valueOf(booking.getCheckInDate())); // Converts LocalDate to SQL Date
            preparedStatement.setDate(3, java.sql.Date.valueOf(booking.getCheckOutDate())); // Converts LocalDate to SQL Date
            preparedStatement.setInt(4, booking.getNumberOfGuests());
            preparedStatement.setString(5, booking.getPaymentStatus());
            preparedStatement.setString(6, booking.getReservationStatus());

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean delete(int bookingID) {
        String sql = "DELETE FROM Booking WHERE bookingID = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, bookingID);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int getNextBookingID() {
        String sql = "SELECT MAX(bookingID) + 1 AS nextID FROM Booking";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getInt("nextID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 1; // default to 1 if no rows exist
    }
}
