package databaseaccess;

import db.DBConnection;
import relations.AvailableRoom;
import relations.Room;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RoomDataBaseAccess {

    // Create a new Room record in the Room table
    public boolean create(Room room) {
        String sql = "INSERT INTO Room (roomTypeID, price, roomStatus, hotelID) VALUES (?, ?, ?, ?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, room.getRoomTypeID());
            preparedStatement.setBigDecimal(2, room.getPrice()); // BigDecimal used here for price
            preparedStatement.setString(3, room.getRoomStatus());
            preparedStatement.setInt(4, room.getHotelID());

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete a Room record by roomID only if the room is not booked
    public boolean delete(int roomID) {
        String checkIfBookedSql = "SELECT COUNT(*) FROM BookedRooms WHERE roomID = ?";
        String deleteRoomSql = "DELETE FROM Room WHERE roomID = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement checkStatement = connection.prepareStatement(checkIfBookedSql);
             PreparedStatement deleteStatement = connection.prepareStatement(deleteRoomSql)) {

            // Check if the room is booked
            checkStatement.setInt(1, roomID);
            ResultSet resultSet = checkStatement.executeQuery();
            if (resultSet.next() && resultSet.getInt(1) > 0) {
                System.out.println("Room cannot be deleted because it is currently booked.");
                return false; // Room is booked, so we do not delete it
            }

            // Delete the room
            deleteStatement.setInt(1, roomID);
            return deleteStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get the next available Room ID
    public int getNextRoomID() {
        String sql = "SELECT MAX(roomID) + 1 AS nextID FROM Room";
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

    // Get available rooms for the specified date range
    public List<AvailableRoom> getAvailableRooms(LocalDate checkInDate, LocalDate checkOutDate) {
        String sql = "SELECT R.roomID, RT.roomTypeName, R.price " +
                "FROM Room R " +
                "JOIN RoomType RT ON R.roomTypeID = RT.roomTypeID " +
                "WHERE R.roomStatus = 'available' " +
                "AND R.roomID NOT IN ( " +
                "    SELECT BR.roomID " +
                "    FROM BookedRooms BR " +
                "    JOIN Booking B ON BR.bookingID = B.bookingID " +
                "    WHERE B.checkInDate < ? " +
                "    AND B.checkOutDate > ? " +
                ")";

        List<AvailableRoom> availableRooms = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            // Set parameters for date range
            preparedStatement.setDate(1, java.sql.Date.valueOf(checkOutDate));
            preparedStatement.setDate(2, java.sql.Date.valueOf(checkInDate));

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                AvailableRoom availableRoom = new AvailableRoom(
                        resultSet.getInt("roomID"),
                        resultSet.getString("roomTypeName"),
                        resultSet.getBigDecimal("price")
                );
                availableRooms.add(availableRoom);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return availableRooms;
    }
    public boolean updateRoomStatus(int roomID, String newStatus) {
        String sql = "UPDATE Room SET roomStatus = ? WHERE roomID = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, newStatus);
            preparedStatement.setInt(2, roomID);

            return preparedStatement.executeUpdate() > 0; // Returns true if the update was successful
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
