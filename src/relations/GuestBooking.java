package relations;

import java.math.BigDecimal;
import java.time.LocalDate;

public class GuestBooking {
    private int bookingID;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private int numberOfGuests;
    private int roomID;
    private String roomTypeName;
    private BigDecimal dailyPrice;
    private String paymentStatus;
    private String reservationStatus;

    public GuestBooking(int bookingID, LocalDate checkInDate, LocalDate checkOutDate, int numberOfGuests,
                        int roomID, String roomTypeName, BigDecimal dailyPrice, String paymentStatus, String reservationStatus) {
        this.bookingID = bookingID;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.numberOfGuests = numberOfGuests;
        this.roomID = roomID;
        this.roomTypeName = roomTypeName;
        this.dailyPrice = dailyPrice;
        this.paymentStatus = paymentStatus;
        this.reservationStatus = reservationStatus;
    }

    public int getBookingID() {
        return bookingID;
    }

    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(LocalDate checkOutDate) {
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

    public String getRoomTypeName() {
        return roomTypeName;
    }

    public void setRoomTypeName(String roomTypeName) {
        this.roomTypeName = roomTypeName;
    }

    public BigDecimal getDailyPrice() {
        return dailyPrice;
    }

    public void setDailyPrice(BigDecimal dailyPrice) {
        this.dailyPrice = dailyPrice;
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
        return "GuestBooking{" +
                "bookingID=" + bookingID +
                ", checkInDate=" + checkInDate +
                ", checkOutDate=" + checkOutDate +
                ", numberOfGuests=" + numberOfGuests +
                ", roomID=" + roomID +
                ", roomTypeName='" + roomTypeName + '\'' +
                ", dailyPrice=" + dailyPrice +
                ", paymentStatus='" + paymentStatus + '\'' +
                ", reservationStatus='" + reservationStatus + '\'' +
                '}';
    }
}

