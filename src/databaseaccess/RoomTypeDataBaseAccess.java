package databaseaccess;

import db.DBConnection;
import relations.RoomType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoomTypeDataBaseAccess {

    // Create a new RoomType record in the RoomType table
    public boolean create(RoomType roomType) {
        String sql = "INSERT INTO RoomType (roomTypeName) VALUES (?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, roomType.getRoomTypeName());

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete a RoomType record by roomTypeID
    public boolean delete(int roomTypeID) {
        String sql = "DELETE FROM RoomType WHERE roomTypeID = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, roomTypeID);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get the next available RoomType ID
    public int getNextRoomTypeID() {
        String sql = "SELECT MAX(roomTypeID) + 1 AS nextID FROM RoomType";
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

