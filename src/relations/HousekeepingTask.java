package relations;

import java.util.Date;

public class HousekeepingTask {
    private int taskID;
    private int roomID;
    private int assignedTo;
    private Date scheduledDate;
    private String taskStatus;

    public HousekeepingTask(int taskID, int roomID, int assignedTo, Date scheduledDate, String taskStatus) {
        this.taskID = taskID;
        this.roomID = roomID;
        this.assignedTo = assignedTo;
        this.scheduledDate = scheduledDate;
        this.taskStatus = taskStatus;
    }

    public int getTaskID() {
        return taskID;
    }

    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public int getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(int assignedTo) {
        this.assignedTo = assignedTo;
    }

    public Date getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(Date scheduledDate) {
        this.scheduledDate = scheduledDate;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    @Override
    public String toString() {
        return "HousekeepingTask{" +
                "taskID=" + taskID +
                ", roomID=" + roomID +
                ", assignedTo=" + assignedTo +
                ", scheduledDate=" + scheduledDate +
                ", taskStatus='" + taskStatus + '\'' +
                '}';
    }
}
