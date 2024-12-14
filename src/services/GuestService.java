package services;
import relations.*;
import databaseaccess.*;

public class GuestService {
    //DB access objects
    private final RoomDataBaseAccess roomDataBaseAccess;

    //DB access objects initialized in the constructor
    public GuestService() {
        this.roomDataBaseAccess = new RoomDataBaseAccess();
    }

    public void addNewBooking(){}
    public void viewAvailableRooms(){}
    public void viewMyBookings(){}
    public void cancelBooking(){}
}
