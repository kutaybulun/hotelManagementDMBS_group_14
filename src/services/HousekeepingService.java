package services;
import relations.*;
import databaseaccess.*;
import java.util.List;

public class HousekeepingService {
    private final HousekeepingTaskDataBaseAccess housekeepingTaskDataBaseAccess;

    public HousekeepingService() {
        this.housekeepingTaskDataBaseAccess = new HousekeepingTaskDataBaseAccess();
    }

    // View the current user's pending tasks
    public List<HousekeepingTask> viewMyPendingTasks() {
        return housekeepingTaskDataBaseAccess.getPendingTasks();
    }

    // View the current user's completed tasks
    public List<HousekeepingTask> viewMyCompletedTasks() {
        return housekeepingTaskDataBaseAccess.getCompletedTasks();
    }

    // Update a task's status to "completed" for the current user
    public boolean updateMyTaskStatus(int taskID) {
        return housekeepingTaskDataBaseAccess.updateTaskStatusToCompleted(taskID);
    }
    public void viewMyCleaningSchedule(){}
}
