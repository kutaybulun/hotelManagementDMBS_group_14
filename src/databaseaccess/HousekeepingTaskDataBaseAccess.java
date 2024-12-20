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

    private final UserDataBaseAccess userDataBaseAccess;

    public HousekeepingTaskDataBaseAccess() {
        this.userDataBaseAccess = new UserDataBaseAccess();
    }

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

    // Retrieve pending housekeeping tasks for the current housekeeper
    public List<HousekeepingTask> getPendingTasks() {
        int housekeeperID = userDataBaseAccess.getCurrentUserID();
        String sql = "SELECT taskID, roomID, assignedTo, scheduledDate, taskStatus " +
                "FROM HousekeepingSchedule " +
                "WHERE assignedTo = ? AND taskStatus = 'pending'";

        List<HousekeepingTask> tasks = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, housekeeperID);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                tasks.add(new HousekeepingTask(
                        resultSet.getInt("taskID"),
                        resultSet.getInt("roomID"),
                        resultSet.getInt("assignedTo"),
                        resultSet.getDate("scheduledDate").toLocalDate(),
                        resultSet.getString("taskStatus")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tasks;
    }

    // Retrieve completed housekeeping tasks for the current housekeeper
    public List<HousekeepingTask> getCompletedTasks() {
        int housekeeperID = userDataBaseAccess.getCurrentUserID();
        String sql = "SELECT taskID, roomID, assignedTo, scheduledDate, taskStatus " +
                "FROM HousekeepingSchedule " +
                "WHERE assignedTo = ? AND taskStatus = 'completed'";

        List<HousekeepingTask> tasks = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, housekeeperID);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                tasks.add(new HousekeepingTask(
                        resultSet.getInt("taskID"),
                        resultSet.getInt("roomID"),
                        resultSet.getInt("assignedTo"),
                        resultSet.getDate("scheduledDate").toLocalDate(),
                        resultSet.getString("taskStatus")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tasks;
    }

    // Update the status of a housekeeping task to 'completed' for the current housekeeper only
    public boolean updateTaskStatusToCompleted(int taskID) {
        int housekeeperID = userDataBaseAccess.getCurrentUserID();
        String sql = "UPDATE HousekeepingSchedule " +
                "SET taskStatus = 'completed' " +
                "WHERE taskID = ? AND assignedTo = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, taskID);
            preparedStatement.setInt(2, housekeeperID); // Ensure the task is assigned to the current housekeeper
            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<HousekeeperTask> getAllHousekeepingRecords() {
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
            JOIN HousekeepingSchedule HS ON U.userID = HS.assignedTo
        WHERE 
            U.userType = 'housekeeping'
        ORDER BY 
            U.userID, HS.scheduledDate;
    """;

        List<HousekeeperTask> housekeepingRecords = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                HousekeeperTask record = new HousekeeperTask(
                        resultSet.getInt("userID"),
                        resultSet.getString("housekeeperName"),
                        resultSet.getInt("taskID"),
                        resultSet.getString("scheduledDate"),
                        resultSet.getString("taskStatus"),
                        resultSet.getInt("roomID")
                );
                housekeepingRecords.add(record);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return housekeepingRecords;
    }

    public List<PendingCleaningTask> viewMyCleaningSchedule() {
        int housekeeperID = userDataBaseAccess.getCurrentUserID();
        String sql = """
        SELECT 
            HS.roomID, 
            HS.scheduledDate
        FROM 
            HousekeepingSchedule HS
        WHERE 
            HS.assignedTo = ? 
            AND HS.taskStatus = 'pending'
        ORDER BY 
            HS.scheduledDate;
    """;

        List<PendingCleaningTask> schedule = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, housekeeperID);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                PendingCleaningTask task = new PendingCleaningTask(
                        resultSet.getInt("roomID"),
                        resultSet.getDate("scheduledDate").toString()
                );
                schedule.add(task);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return schedule;
    }





}
