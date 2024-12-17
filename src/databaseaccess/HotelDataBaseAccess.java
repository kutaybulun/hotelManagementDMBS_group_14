package databaseaccess;

import db.DBConnection;
import relations.*;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HotelDataBaseAccess {

    // Create a new Address record and return the addressID
    public int createAddress(Address address) {
        String sql = "INSERT INTO Address (street, city, state) VALUES (?, ?, ?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, address.getStreet());
            preparedStatement.setString(2, address.getCity());
            preparedStatement.setString(3, address.getState());

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Return -1 if address creation fails
    }

    // Create a new Hotel record in the Hotel table
    public boolean createHotel(Hotel hotel) {
        String sql = "INSERT INTO Hotel (hotelName, addressID, contactNumber) VALUES (?, ?, ?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, hotel.getHotelName());
            preparedStatement.setInt(2, hotel.getAddressID());
            preparedStatement.setString(3, hotel.getContactNumber());

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete a Hotel by hotelID
    public boolean deleteHotel(int hotelID) {
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
        String sql = "SELECT COALESCE(MAX(hotelID), 0) + 1 AS nextID FROM Hotel";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getInt("nextID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 1; // Default to 1 if no records exist
    }

    // Get the next available Address ID
    public int getNextAddressID() {
        String sql = "SELECT COALESCE(MAX(addressID), 0) + 1 AS nextID FROM Address";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getInt("nextID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 1; // Default to 1 if no records exist
    }

    // View all hotels along with their addresses
    // View all hotels along with their addresses
    public List<HotelWithAddress> viewAllHotelsWithAddresses() {
        List<HotelWithAddress> hotelList = new ArrayList<>();
        String sql = """
            SELECT 
                H.hotelID, 
                H.hotelName, 
                A.street, 
                A.city, 
                A.state, 
                H.contactNumber
            FROM Hotel H
            JOIN Address A ON H.addressID = A.addressID
        """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                HotelWithAddress hotelWithAddress = new HotelWithAddress(
                        resultSet.getInt("hotelID"),
                        resultSet.getString("hotelName"),
                        resultSet.getString("street"),
                        resultSet.getString("city"),
                        resultSet.getString("state"),
                        resultSet.getString("contactNumber")
                );
                hotelList.add(hotelWithAddress);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hotelList;
    }
}

