package relations;

public class CheckedOutBooking {
    private int bookingID;
    private int roomID;
    private String checkOutDate;

    public CheckedOutBooking(int bookingID, int roomID, String checkOutDate) {
        this.bookingID = bookingID;
        this.roomID = roomID;
        this.checkOutDate = checkOutDate;
    }

    public int getBookingID() {
        return bookingID;
    }

    public int getRoomID() {
        return roomID;
    }

    public String getCheckOutDate() {
        return checkOutDate;
    }

    @Override
    public String toString() {
        return "CheckedOutBooking{" +
                "bookingID=" + bookingID +
                ", roomID=" + roomID +
                ", checkOutDate='" + checkOutDate + '\'' +
                '}';
    }
}
