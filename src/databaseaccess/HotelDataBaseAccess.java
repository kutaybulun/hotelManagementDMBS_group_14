package databaseaccess;

import db.DBConnection;
import relations.Hotel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HotelDataBaseAccess {

    // Create a new Hotel record in the Hotel table
    public boolean create(Hotel hotel) {
        String sql = "INSERT INTO Hotel (hotelName, location, contactNumber) VALUES (?, ?, ?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, hotel.getHotelName());
            preparedStatement.setString(2, hotel.getLocation());
            preparedStatement.setString(3, hotel.getContactNumber());

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete a Hotel by ID
    public boolean delete(int hotelID) {
        String sql = "DELETE FROM Hotel WHERE hotelID = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, hotelID);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get the next available Hotel ID
    public int getNextHotelID() {
        String sql = "SELECT MAX(hotelID) + 1 AS nextID FROM Hotel";
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

