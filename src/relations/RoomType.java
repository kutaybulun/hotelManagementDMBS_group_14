package relations;

public class RoomType {
    private int roomTypeID;
    private String roomTypeName;

    public RoomType(int roomTypeID, String roomTypeName) {
        this.roomTypeID = roomTypeID;
        this.roomTypeName = roomTypeName;
    }

    public int getRoomTypeID() {
        return roomTypeID;
    }

    public void setRoomTypeID(int roomTypeID) {
        this.roomTypeID = roomTypeID;
    }

    public String getRoomTypeName() {
        return roomTypeName;
    }

    public void setRoomTypeName(String roomTypeName) {
        this.roomTypeName = roomTypeName;
    }

    @Override
    public String toString() {
        return "RoomType{" +
                "roomTypeID=" + roomTypeID +
                ", roomTypeName='" + roomTypeName + '\'' +
                '}';
    }
}
