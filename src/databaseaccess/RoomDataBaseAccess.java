package databaseaccess;

import db.DBConnection;
import relations.Room;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.math.BigDecimal;

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

    // Delete a Room record by roomID
    public boolean delete(int roomID) {
        String sql = "DELETE FROM Room WHERE roomID = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, roomID);
            return preparedStatement.executeUpdate() > 0;
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
}
