package databaseaccess;

import db.DBConnection;
import relations.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HousekeepingTaskDataBaseAccess {

    // Create a new Housekeeping Task record in the HousekeepingSchedule table
    public boolean create(HousekeepingTask housekeepingTask) {
        String sql = "INSERT INTO HousekeepingSchedule (roomID, assignedTo, scheduledDate, taskStatus) VALUES (?, ?, ?, ?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, housekeepingTask.getRoomID());
            preparedStatement.setInt(2, housekeepingTask.getAssignedTo());
            preparedStatement.setDate(3, java.sql.Date.valueOf(housekeepingTask.getScheduledDate()));
            preparedStatement.setString(4, housekeepingTask.getTaskStatus());

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete a Housekeeping Task by ID
    public boolean delete(int taskID) {
        String sql = "DELETE FROM HousekeepingSchedule WHERE taskID = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, taskID);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get the next available Housekeeping Task ID
    public int getNextTaskID() {
        String sql = "SELECT COALESCE(MAX(taskID), 0) + 1 AS nextID FROM HousekeepingSchedule";
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

    // Method to get all housekeepers with their assigned tasks
    public List<HousekeeperTask> getHousekeeperTaskDetails() {
        String sql = """
            SELECT 
                U.userID,
                U.username AS housekeeperName,
                HS.taskID,
                HS.scheduledDate,
                HS.taskStatus,
                HS.roomID
            FROM 
                Users U
                LEFT JOIN HousekeepingSchedule HS ON U.userID = HS.assignedTo
            WHERE 
                U.userType = 'housekeeping'
            ORDER BY U.userID, HS.scheduledDate;
        """;

        List<HousekeeperTask> housekeeperTasks = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                HousekeeperTask task = new HousekeeperTask(
                        resultSet.getInt("userID"),
                        resultSet.getString("housekeeperName"),
                        resultSet.getInt("taskID"),
                        resultSet.getString("scheduledDate"),
                        resultSet.getString("taskStatus"),
                        resultSet.getInt("roomID")
                );
                housekeeperTasks.add(task);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return housekeeperTasks;
    }
}
