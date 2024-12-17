package relations;

public class PendingCleaningTask {
    private int roomID;
    private String scheduledDate;

    public PendingCleaningTask(int roomID, String scheduledDate) {
        this.roomID = roomID;
        this.scheduledDate = scheduledDate;
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public String getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(String scheduledDate) {
        this.scheduledDate = scheduledDate;
    }

    @Override
    public String toString() {
        return "PendingCleaningTask{" +
                "roomID=" + roomID +
                ", scheduledDate='" + scheduledDate + '\'' +
                '}';
    }
}

