package services;
import relations.*;
import databaseaccess.*;

import java.time.LocalDate;
import java.util.List;

public class GuestService {
    //DB access objects
    private final RoomDataBaseAccess roomDataBaseAccess;
    private final BookingDataBaseAccess bookingDataBaseAccess;
    private final UserDataBaseAccess userDataBaseAccess;
    //DB access objects initialized in the constructor
    public GuestService() {
        this.roomDataBaseAccess = new RoomDataBaseAccess();
        this.bookingDataBaseAccess = new BookingDataBaseAccess();
        this.userDataBaseAccess = new UserDataBaseAccess();
    }

    public boolean addNewBooking(LocalDate checkInDate, LocalDate checkOutDate, int numberOfGuests) {
        int userID = userDataBaseAccess.getCurrentUserID();
        String paymentStatus = "pending";
        String bookingStatus = "pending";
        return false;
    }
    public List<AvailableRoom> viewAvailableRooms(LocalDate checkInDate, LocalDate checkOutDate) {
        return roomDataBaseAccess.getAvailableRooms(checkInDate, checkOutDate);
    }
    public List<GuestBooking> viewMyBookings(){
        int userID = userDataBaseAccess.getCurrentUserID();
        return bookingDataBaseAccess.getUserBookings(userID);
    }
    public void cancelBooking(){}
}
