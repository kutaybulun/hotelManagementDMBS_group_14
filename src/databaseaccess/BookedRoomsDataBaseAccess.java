package databaseaccess;

import db.DBConnection;
import relations.BookedRooms;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BookedRoomsDataBaseAccess {

    // Create a new entry in BookedRooms to link bookingID and roomID
    public boolean create(BookedRooms bookedRooms) {
        String sql = "INSERT INTO BookedRooms (bookingID, roomID) VALUES (?, ?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, bookedRooms.getBookingID());
            preparedStatement.setInt(2, bookedRooms.getRoomID());
            int rowsInserted = preparedStatement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
