package relations;

import java.util.Date;

public class Booking {
    private int bookingID;
    private int userID;
    private Date checkInDate;
    private Date checkOutDate;
    private int numberOfGuests;
    private String paymentStatus;
    private String reservationStatus;

    public Booking(int bookingID, int userID, Date checkInDate, Date checkOutDate, int numberOfGuests, String paymentStatus, String reservationStatus) {
        this.bookingID = bookingID;
        this.userID = userID;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.numberOfGuests = numberOfGuests;
        this.paymentStatus = paymentStatus;
        this.reservationStatus = reservationStatus;
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

    public Date getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(Date checkOutDate) {
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

    @Override
    public String toString() {
        return "Booking{" +
                "bookingID=" + bookingID +
                ", userID=" + userID +
                ", checkInDate=" + checkInDate +
                ", checkOutDate=" + checkOutDate +
                ", numberOfGuests=" + numberOfGuests +
                ", paymentStatus='" + paymentStatus + '\'' +
                ", reservationStatus='" + reservationStatus + '\'' +
                '}';
    }
}

