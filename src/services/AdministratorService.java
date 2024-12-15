package services;
import relations.*;
import databaseaccess.*;

import java.math.BigDecimal;
import java.util.List;

public class AdministratorService {
    private final RoomDataBaseAccess roomDataBaseAccess;
    private final UserDataBaseAccess userDataBaseAccess;
    private final BookingDataBaseAccess bookingDataBaseAccess;

    public AdministratorService() {
        this.roomDataBaseAccess = new RoomDataBaseAccess();
        this.userDataBaseAccess = new UserDataBaseAccess();
        this.bookingDataBaseAccess = new BookingDataBaseAccess();
    }

    public boolean addRoom(int roomTypeID, BigDecimal price, String status, int hotelID){
        int roomID = roomDataBaseAccess.getNextRoomID();
        Room room = new Room(roomID, roomTypeID, price, status, hotelID);
        return roomDataBaseAccess.create(room);
    }

    public boolean deleteRoom(int roomID){
        return roomDataBaseAccess.delete(roomID);
    }

    public void manageRoomStatus(){}

    public boolean addUserAccount(String username, String password, String userType, String contactDetails){
        int userID = userDataBaseAccess.getNextUserID();
        User user = new User(userID, username, password, userType, contactDetails);
        return userDataBaseAccess.signUp(user);
    }

    public boolean removeUserAccount(int userID) {
        return userDataBaseAccess.delete(userID);
    }

    public List<User> viewUserAccounts() {
        return userDataBaseAccess.viewAllUsers();
    }

    public void generateRevenueReport(){}

    public List<BookingRecord> viewAllBookingRecords(){
        return bookingDataBaseAccess.viewAllBookingRecords();
    }

    public void viewAllHousekeepingRecords(){}
    public List<MostBookedRoomType> viewMostBookedRoomTypes() {
        return bookingDataBaseAccess.viewMostBookedRoomTypes();
    }
    public void viewAllEmployeesWithRole(){}

}
