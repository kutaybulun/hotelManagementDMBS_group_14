package databaseaccess;

import db.DBConnection;
import relations.*;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
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

    // this is to delete from a booking
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

    // used for creating the relations class objects to hold the data, gets the next bookingID
    public int getNextBookingID() {
        String sql = "SELECT COALESCE(MAX(bookingID), 0) FROM Booking";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int maxBookingID = resultSet.getInt(1); // Get the max bookingID (or 0 if no rows)
                return maxBookingID + 1; // Increment it by 1
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 1; // Default to 1 if there are no bookings in the table
    }

    //query to view all booking records, returns a BookingRecord list, used in administrator service. also used for receptionist
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

    // View the most booked room types via a query and stores it in MostBookedRoomType
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

    // used to get a Users bookings stores it in GuestBooking class
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

    // Method to retrieve requested bookings and to store it in RequestedBooking class
    public List<RequestedBooking> getRequestedBookings() {
        String sql = """
            SELECT 
                B.bookingID, 
                B.userID, 
                B.checkInDate, 
                B.checkOutDate, 
                B.numberOfGuests, 
                BR.roomID 
            FROM 
                Booking B 
                JOIN BookedRooms BR 
                ON B.bookingID = BR.bookingID 
            WHERE 
                B.reservationStatus = 'pending';
        """;

        List<RequestedBooking> requestedBookings = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                RequestedBooking booking = new RequestedBooking(
                        resultSet.getInt("bookingID"),
                        resultSet.getInt("userID"),
                        resultSet.getString("checkInDate"),
                        resultSet.getString("checkOutDate"),
                        resultSet.getInt("numberOfGuests"),
                        resultSet.getInt("roomID")
                );
                requestedBookings.add(booking);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return requestedBookings;
    }

    // Method to get checked-out bookings for a specific date
    public List<CheckedOutBooking> getCheckedOutBookingsByDate(LocalDate checkOutDate) {
        String sql = """
            SELECT 
                B.bookingID, 
                BR.roomID, 
                B.checkOutDate
            FROM 
                Booking B 
                JOIN BookedRooms BR ON B.bookingID = BR.bookingID 
            WHERE 
                B.reservationStatus = 'checked-out'
                AND B.checkOutDate = ?;
        """;

        List<CheckedOutBooking> checkedOutBookings = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            // Set the check-out date in the prepared statement
            preparedStatement.setDate(1, java.sql.Date.valueOf(checkOutDate));

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                CheckedOutBooking booking = new CheckedOutBooking(
                        resultSet.getInt("bookingID"),
                        resultSet.getInt("roomID"),
                        resultSet.getString("checkOutDate")
                );
                checkedOutBookings.add(booking);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return checkedOutBookings;
    }
    //this is to set a booking the cancelled if it is unpaid and pending
    public boolean cancelBooking(int bookingID, int userID) {
        String sql = "UPDATE Booking SET reservationStatus = 'cancelled' WHERE bookingID = ? AND userID = ? AND reservationStatus = 'pending' AND paymentStatus = 'pending'";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, bookingID);
            preparedStatement.setInt(2, userID);

            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated > 0; // Returns true if a booking was canceled
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Booking getBookingByID(int bookingID) {
        String sql = "SELECT * FROM Booking WHERE bookingID = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, bookingID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new Booking(
                        resultSet.getInt("bookingID"),
                        resultSet.getInt("userID"),
                        resultSet.getDate("checkInDate").toLocalDate(),
                        resultSet.getDate("checkOutDate").toLocalDate(),
                        resultSet.getInt("numberOfGuests"),
                        resultSet.getString("paymentStatus"),
                        resultSet.getString("reservationStatus")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Return null if booking not found or an error occurs
    }

    public boolean update(Booking booking) {
        String sql = "UPDATE Booking SET checkInDate = ?, checkOutDate = ?, paymentStatus = ?, reservationStatus = ?, numberOfGuests = ? WHERE bookingID = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setDate(1, java.sql.Date.valueOf(booking.getCheckInDate()));
            preparedStatement.setDate(2, java.sql.Date.valueOf(booking.getCheckOutDate()));
            preparedStatement.setString(3, booking.getPaymentStatus());
            preparedStatement.setString(4, booking.getReservationStatus());
            preparedStatement.setInt(5, booking.getNumberOfGuests());
            preparedStatement.setInt(6, booking.getBookingID());

            return preparedStatement.executeUpdate() > 0; // Returns true if update is successful
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public List<Integer> getRoomIDsByBookingID(int bookingID) {
        String sql = "SELECT roomID FROM BookedRooms WHERE bookingID = ?";
        List<Integer> roomIDs = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, bookingID);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                roomIDs.add(resultSet.getInt("roomID"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roomIDs;
    }

    //for receptionists to see the amount to pay or a booking
    public BigDecimal getTotalPayment(int bookingID) {
        String sql = """
        SELECT 
            SUM(DATEDIFF(B.checkOutDate, B.checkInDate) * R.price) AS totalPayment
        FROM 
            Booking B
            JOIN BookedRooms BR ON B.bookingID = BR.bookingID
            JOIN Room R ON BR.roomID = R.roomID
        WHERE 
            B.bookingID = ? 
            AND B.paymentStatus != 'paid'
        GROUP BY 
            B.bookingID;
    """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, bookingID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getBigDecimal("totalPayment");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return BigDecimal.ZERO; // If no payment is found, return 0
    }
    //for guests to see the amount to pay or a booking
    public BigDecimal getTotalPaymentForGuest(int userID, int bookingID) {
        String sql = """
        SELECT 
            SUM(DATEDIFF(B.checkOutDate, B.checkInDate) * R.price) AS totalPayment
        FROM 
            Booking B
            JOIN BookedRooms BR ON B.bookingID = BR.bookingID
            JOIN Room R ON BR.roomID = R.roomID
        WHERE 
            B.bookingID = ? 
            AND B.userID = ?
            AND B.paymentStatus != 'paid'
        GROUP BY 
            B.bookingID;
    """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, bookingID);
            preparedStatement.setInt(2, userID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getBigDecimal("totalPayment");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return BigDecimal.ZERO; // Return 0 if no payment is found
    }

    public boolean updatePaymentStatus(int bookingID, String newStatus, Connection connection) {
        String sql = "UPDATE Booking SET paymentStatus = ? WHERE bookingID = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, newStatus);
            preparedStatement.setInt(2, bookingID);

            return preparedStatement.executeUpdate() > 0; // Return true if the update affected 1+ rows
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Return false if an error occurs
        }
    }


    // Update star rating for a specific booking
    public boolean leaveStarRating(int bookingID, int userID, int rating) {
        if (rating < 0 || rating > 5) {
            return false;
        }

        String sql = """
        UPDATE StarRating 
        SET rating = ? 
        WHERE bookingID = ? 
        AND EXISTS (
            SELECT 1 FROM Booking 
            WHERE bookingID = ? 
            AND userID = ? 
            AND reservationStatus = 'checked-out'
        );
    """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, rating);
            preparedStatement.setInt(2, bookingID);
            preparedStatement.setInt(3, bookingID);
            preparedStatement.setInt(4, userID);

            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Calculate the average star rating for each hotel
    public List<HotelStarRating> calculateHotelStarRatings() {
        List<HotelStarRating> hotelStarRatings = new ArrayList<>();
        String sql = """
        SELECT H.hotelID, H.hotelName, AVG(S.rating) AS averageRating
        FROM Hotel H
        JOIN Room R ON H.hotelID = R.hotelID
        JOIN BookedRooms BR ON R.roomID = BR.roomID
        JOIN Booking B ON BR.bookingID = B.bookingID
        JOIN StarRating S ON B.bookingID = S.bookingID
        WHERE S.rating != -1
        GROUP BY H.hotelID, H.hotelName;
    """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                HotelStarRating hotelStarRating = new HotelStarRating(
                        resultSet.getInt("hotelID"),
                        resultSet.getString("hotelName"),
                        resultSet.getDouble("averageRating")
                );
                hotelStarRatings.add(hotelStarRating);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return hotelStarRatings;
    }

    public List<Integer> getRoomsWithCleaningStatus() {
        String sql = "SELECT roomID FROM Room WHERE roomStatus = 'cleaning'";
        List<Integer> roomIDs = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                roomIDs.add(resultSet.getInt("roomID"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return roomIDs;
    }

}
