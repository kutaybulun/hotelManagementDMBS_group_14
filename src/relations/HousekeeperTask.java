package relations;

public class HousekeeperTask {
    private int userID;
    private String housekeeperName;
    private int taskID;
    private String scheduledDate;
    private String taskStatus;
    private int roomID;

    public HousekeeperTask(int userID, String housekeeperName, int taskID, String scheduledDate, String taskStatus, int roomID) {
        this.userID = userID;
        this.housekeeperName = housekeeperName;
        this.taskID = taskID;
        this.scheduledDate = scheduledDate;
        this.taskStatus = taskStatus;
        this.roomID = roomID;
    }

    public int getUserID() {
        return userID;
    }

    public String getHousekeeperName() {
        return housekeeperName;
    }

    public int getTaskID() {
        return taskID;
    }

    public String getScheduledDate() {
        return scheduledDate;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public int getRoomID() {
        return roomID;
    }

    @Override
    public String toString() {
        return "HousekeeperTask{" +
                "userID=" + userID +
                ", housekeeperName='" + housekeeperName + '\'' +
                ", taskID=" + taskID +
                ", scheduledDate='" + scheduledDate + '\'' +
                ", taskStatus='" + taskStatus + '\'' +
                ", roomID=" + roomID +
                '}';
    }
}
