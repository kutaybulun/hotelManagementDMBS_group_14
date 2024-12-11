package relations;

public class BookedRooms {
    private int bookingID;
    private int roomID;

    public BookedRooms(int bookingID, int roomID) {
        this.bookingID = bookingID;
        this.roomID = roomID;
    }

    public int getBookingID() {
        return bookingID;
    }

    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    @Override
    public String toString() {
        return "BookedRooms{" +
                "bookingID=" + bookingID +
                ", roomID=" + roomID +
                '}';
    }
}
