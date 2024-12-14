package services;
import relations.*;
import databaseaccess.*;

import java.math.BigDecimal;

public class AdministratorService {
    private final RoomDataBaseAccess roomDataBaseAccess;

    public AdministratorService() {
        this.roomDataBaseAccess = new RoomDataBaseAccess();
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
    public void addUserAccount(){}
    public void removeUserAccount(){}
    public void viewUserAccounts(){}
    public void generateRevenueReport(){}
    public void viewAllBookingRecords(){}
    public void viewAllHousekeepingRecords(){}
    public void viewMostBookedRoomTypes(){}
    public void viewAllEmployeesWithRole(){}

}
