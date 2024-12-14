package services;
import relations.*;
import databaseaccess.*;

public class ReceptionistService {
    //DB access objects
    private final RoomDataBaseAccess roomDataBaseAccess;

    //DB access objects initialized in the constructor
    public ReceptionistService() {
        this.roomDataBaseAccess = new RoomDataBaseAccess();
    }

    public void addNewBooking(Booking booking) {}
    public void modifyBooking() {}
    public void deleteBooking() {}
    public void viewBookings() {}
    public void processPayment() {}
    public void assignHouseKeepingTask() {}
    public void viewAllHouseKeepingRecordsAndAvailability() {}
}
