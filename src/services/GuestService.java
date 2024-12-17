package services;
import relations.*;
import databaseaccess.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class GuestService {
    //DB access objects
    private final RoomDataBaseAccess roomDataBaseAccess;
    private final BookingDataBaseAccess bookingDataBaseAccess;
    private final UserDataBaseAccess userDataBaseAccess;
    private final BookedRoomsDataBaseAccess bookedRoomsDataBaseAccess;
    //DB access objects initialized in the constructor
    public GuestService() {
        this.roomDataBaseAccess = new RoomDataBaseAccess();
        this.bookingDataBaseAccess = new BookingDataBaseAccess();
        this.userDataBaseAccess = new UserDataBaseAccess();
        this.bookedRoomsDataBaseAccess = new BookedRoomsDataBaseAccess();
    }

    public List<AvailableRoom> viewAvailableRooms(LocalDate checkInDate, LocalDate checkOutDate) {
        return roomDataBaseAccess.getAvailableRooms(checkInDate, checkOutDate);
    }
    public List<GuestBooking> viewMyBookings(){
        int userID = userDataBaseAccess.getCurrentUserID();
        return bookingDataBaseAccess.getUserBookings(userID);
    }
    public boolean cancelBooking(int bookingID) {
        int userID = userDataBaseAccess.getCurrentUserID();
        return bookingDataBaseAccess.cancelBooking(bookingID, userID);
    }

    // Add a new booking
    public boolean addNewBooking(LocalDate checkInDate, LocalDate checkOutDate, int numberOfGuests, int roomID) {
        int bookingID = bookingDataBaseAccess.getNextBookingID(); // Get next available booking ID
        int userID = userDataBaseAccess.getCurrentUserID();
        System.out.println("Current User ID: " + userID); // Debug line to verify userID
        String paymentStatus = "pending";
        String reservationStatus = "pending";

        // 1. Create a new booking
        boolean isBookingCreated = bookingDataBaseAccess.create(new Booking(bookingID, userID, checkInDate, checkOutDate, numberOfGuests, paymentStatus, reservationStatus));

        if (isBookingCreated) {
            // 2. Link the booking to the room via BookedRooms
            boolean isRoomBooked = bookedRoomsDataBaseAccess.create(new BookedRooms(bookingID, roomID));

            if (isRoomBooked) {
                return true; // Booking was successful
            } else {
                // Rollback the booking if room could not be linked
                bookingDataBaseAccess.delete(bookingID);
            }
        }
        return false; // Booking failed
    }

    public BigDecimal getTotalPayment(int bookingID) {
        int userID = userDataBaseAccess.getCurrentUserID();
        BigDecimal totalPayment = bookingDataBaseAccess.getTotalPaymentForGuest(userID, bookingID);
        return totalPayment != null ? totalPayment : null; // Return null if booking does not belong to the user
    }

}
