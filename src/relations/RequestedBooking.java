package relations;

public class RequestedBooking {
    private int bookingID;
    private int userID;
    private String checkInDate;
    private String checkOutDate;
    private int numberOfGuests;
    private int roomID;

    public RequestedBooking(int bookingID, int userID, String checkInDate, String checkOutDate, int numberOfGuests, int roomID) {
        this.bookingID = bookingID;
        this.userID = userID;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.numberOfGuests = numberOfGuests;
        this.roomID = roomID;
    }

    public int getBookingID() {
        return bookingID;
    }

    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(String checkInDate) {
        this.checkInDate = checkInDate;
    }

    public String getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(String checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public void setNumberOfGuests(int numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    @Override
    public String toString() {
        return "RequestedBooking{" +
                "bookingID=" + bookingID +
                ", userID=" + userID +
                ", checkInDate='" + checkInDate + '\'' +
                ", checkOutDate='" + checkOutDate + '\'' +
                ", numberOfGuests=" + numberOfGuests +
                ", roomID=" + roomID +
                '}';
    }
}
