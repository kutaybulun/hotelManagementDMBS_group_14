package databaseaccess;

import db.DBConnection;
import relations.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingDataBaseAccess {

    // Create a new Booking record in the Booking table
    public boolean create(Booking booking) {
        String sql = "INSERT INTO Booking (userID, checkInDate, checkOutDate, numberOfGuests, paymentStatus, reservationStatus) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, booking.getUserID());
            preparedStatement.setDate(2, Date.valueOf(booking.getCheckInDate()));
            preparedStatement.setDate(3, Date.valueOf(booking.getCheckOutDate()));
            preparedStatement.setInt(4, booking.getNumberOfGuests());
            preparedStatement.setString(5, booking.getPaymentStatus());
            preparedStatement.setString(6, booking.getReservationStatus());

            int rowsInserted = preparedStatement.executeUpdate();
            return rowsInserted > 0; // Return true if at least one row was inserted
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
    //query to view all booking records, returns a BookingRecord list, used in administrator service.
    public List<BookingRecord> viewAllBookingRecords() {
        String sql =
                "SELECT " +
                "    B.bookingID, " +
                "    U.username AS guestName, " +
                "    B.checkInDate, " +
                "    B.checkOutDate, " +
                "    B.numberOfGuests, " +
                "    B.paymentStatus, " +
                "    B.reservationStatus, " +
                "    GROUP_CONCAT(R.roomID SEPARATOR ', ') AS bookedRooms " +
                "FROM " +
                "    Booking B " +
                "    LEFT JOIN Users U ON B.userID = U.userID " +
                "    LEFT JOIN BookedRooms BR ON B.bookingID = BR.bookingID " +
                "    LEFT JOIN Room R ON BR.roomID = R.roomID " +
                "GROUP BY B.bookingID";

        List<BookingRecord> bookingRecords = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                BookingRecord bookingRecord = new BookingRecord(
                        resultSet.getInt("bookingID"),
                        resultSet.getString("guestName"),
                        resultSet.getString("checkInDate"),
                        resultSet.getString("checkOutDate"),
                        resultSet.getInt("numberOfGuests"),
                        resultSet.getString("paymentStatus"),
                        resultSet.getString("reservationStatus"),
                        resultSet.getString("bookedRooms") // Comma-separated list of room IDs
                );
                bookingRecords.add(bookingRecord);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bookingRecords;
    }

    // View the most booked room types
    public List<MostBookedRoomType> viewMostBookedRoomTypes() {
        String sql =
        "SELECT RT.roomTypeName, COUNT(*) AS booking_count " +
        "FROM Room R " +
        "JOIN BookedRooms BR ON R.roomID = BR.roomID " +
        "JOIN RoomType RT ON R.roomTypeID = RT.roomTypeID " +
        "GROUP BY RT.roomTypeName " +
        "ORDER BY booking_count DESC";

        List<MostBookedRoomType> mostBookedRoomTypes = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                MostBookedRoomType roomType = new MostBookedRoomType(
                        resultSet.getString("roomTypeName"),
                        resultSet.getInt("booking_count")
                );
                mostBookedRoomTypes.add(roomType);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return mostBookedRoomTypes;
    }

    // get user bookings
    public List<GuestBooking> getUserBookings(int userID) {
        String sql = "SELECT " +
                "    B.bookingID, " +
                "    B.checkInDate, " +
                "    B.checkOutDate, " +
                "    B.numberOfGuests, " +
                "    BR.roomID, " +
                "    RT.roomTypeName, " +
                "    R.price AS dailyPrice, " +
                "    B.paymentStatus, " +
                "    B.reservationStatus " +
                "FROM Booking B " +
                "LEFT JOIN BookedRooms BR ON B.bookingID = BR.bookingID " +
                "LEFT JOIN Room R ON BR.roomID = R.roomID " +
                "LEFT JOIN RoomType RT ON R.roomTypeID = RT.roomTypeID " +
                "WHERE B.userID = ?";

        List<GuestBooking> guestBookings = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, userID);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                GuestBooking guestBooking = new GuestBooking(
                        resultSet.getInt("bookingID"),
                        resultSet.getDate("checkInDate").toLocalDate(),
                        resultSet.getDate("checkOutDate").toLocalDate(),
                        resultSet.getInt("numberOfGuests"),
                        resultSet.getInt("roomID"),
                        resultSet.getString("roomTypeName"),
                        resultSet.getBigDecimal("dailyPrice"),
                        resultSet.getString("paymentStatus"),
                        resultSet.getString("reservationStatus")
                );
                guestBookings.add(guestBooking);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return guestBookings;
    }


}
