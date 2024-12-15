package relations;

public class MostBookedRoomType {
    private String roomTypeName;
    private int bookingCount;

    public MostBookedRoomType(String roomTypeName, int bookingCount) {
        this.roomTypeName = roomTypeName;
        this.bookingCount = bookingCount;
    }

    public String getRoomTypeName() {
        return roomTypeName;
    }

    public void setRoomTypeName(String roomTypeName) {
        this.roomTypeName = roomTypeName;
    }

    public int getBookingCount() {
        return bookingCount;
    }

    public void setBookingCount(int bookingCount) {
        this.bookingCount = bookingCount;
    }
}

