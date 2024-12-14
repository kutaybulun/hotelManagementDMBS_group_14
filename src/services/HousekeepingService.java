package services;
import relations.*;
import databaseaccess.*;

public class HousekeepingService {
    //DB access objects
    private final RoomDataBaseAccess roomDataBaseAccess;

    //DB access objects initialized in the constructor
    public HousekeepingService() {
        this.roomDataBaseAccess = new RoomDataBaseAccess();
    }

    public void viewMyPendingTasks(){}
    public void viewMyCompletedTasks(){}
    public void updateMyTaskStatus(){}
    public void viewMyCleaningSchedule(){}
}
