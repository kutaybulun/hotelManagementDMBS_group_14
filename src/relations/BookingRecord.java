package relations;

public class BookingRecord {
    private int bookingID;
    private String guestName;
    private String checkInDate;
    private String checkOutDate;
    private int numberOfGuests;
    private String paymentStatus;
    private String reservationStatus;
    private String bookedRooms; // This will store room IDs as a single comma-separated string

    public BookingRecord(int bookingID, String guestName, String checkInDate, String checkOutDate, int numberOfGuests, String paymentStatus, String reservationStatus, String bookedRooms) {
        this.bookingID = bookingID;
        this.guestName = guestName;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.numberOfGuests = numberOfGuests;
        this.paymentStatus = paymentStatus;
        this.reservationStatus = reservationStatus;
        this.bookedRooms = bookedRooms;
    }

    public int getBookingID() {
        return bookingID;
    }

    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
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

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getReservationStatus() {
        return reservationStatus;
    }

    public void setReservationStatus(String reservationStatus) {
        this.reservationStatus = reservationStatus;
    }

    public String getBookedRooms() {
        return bookedRooms;
    }

    public void setBookedRooms(String bookedRooms) {
        this.bookedRooms = bookedRooms;
    }
    @Override
    public String toString() {
        return "BookingRecord{" +
                "bookingID=" + bookingID +
                ", guestName='" + guestName + '\'' +
                ", checkInDate=" + checkInDate +
                ", checkOutDate=" + checkOutDate +
                ", numberOfGuests=" + numberOfGuests +
                ", paymentStatus='" + paymentStatus + '\'' +
                ", reservationStatus='" + reservationStatus + '\'' +
                ", bookedRooms='" + bookedRooms + '\'' +
                '}';
    }

}

