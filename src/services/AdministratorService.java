package services;
import relations.*;
import databaseaccess.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class AdministratorService {
    private final EmployeeDataBaseAccess employeeDataBaseAccess;
    private final RoomDataBaseAccess roomDataBaseAccess;
    private final UserDataBaseAccess userDataBaseAccess;
    private final BookingDataBaseAccess bookingDataBaseAccess;
    private final RoomTypeDataBaseAccess roomTypeDataBaseAccess;
    private final HotelDataBaseAccess hotelDataBaseAccess;
    private final PaymentDataBaseAccess paymentDataBaseAccess;

    public AdministratorService() {
        this.roomDataBaseAccess = new RoomDataBaseAccess();
        this.userDataBaseAccess = new UserDataBaseAccess();
        this.bookingDataBaseAccess = new BookingDataBaseAccess();
        this.roomTypeDataBaseAccess = new RoomTypeDataBaseAccess();
        this.employeeDataBaseAccess = new EmployeeDataBaseAccess();
        this.hotelDataBaseAccess = new HotelDataBaseAccess();
        this.paymentDataBaseAccess = new PaymentDataBaseAccess();
    }

    public boolean addRoom(int roomTypeID, BigDecimal price, String status, int hotelID){
        int roomID = roomDataBaseAccess.getNextRoomID();
        Room room = new Room(roomID, roomTypeID, price, status, hotelID);
        return roomDataBaseAccess.create(room);
    }

    public boolean addRoomType (String roomType){
        int roomTypeID = roomTypeDataBaseAccess.getNextRoomTypeID();
        RoomType roomType1 = new RoomType(roomTypeID, roomType);
        return roomTypeDataBaseAccess.create(roomType1);
    }

    // Method to view all room types
    public List<RoomType> viewRoomTypes() {
        return roomTypeDataBaseAccess.viewRoomTypes();
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

    public List<BookingRecord> viewAllBookingRecords(){
        return bookingDataBaseAccess.viewAllBookingRecords();
    }

    public List<MostBookedRoomType> viewMostBookedRoomTypes() {
        return bookingDataBaseAccess.viewMostBookedRoomTypes();
    }
    // View all employees along with their roles and salary information
    public List<EmployeeWithRole> viewAllEmployeesWithRole() {
        return employeeDataBaseAccess.viewAllEmployeesWithRoles();
    }

    public boolean updateEmployeeHotel(int employeeID, int hotelID) {
        EmployeeDataBaseAccess employeeDataBaseAccess = new EmployeeDataBaseAccess();
        return employeeDataBaseAccess.updateEmployeeHotel(employeeID, hotelID);
    }

    // View all hotels with their address information
    public List<HotelWithAddress> viewAllHotelsWithAddress() {
        return hotelDataBaseAccess.viewAllHotelsWithAddresses();
    }

    public List<MonthlyRevenueReport> generateMonthlyRevenueReport(int year, int month) {
        List<MonthlyRevenueReport> reportList = new ArrayList<>();

        // Get all hotels with addresses
        var hotels = hotelDataBaseAccess.viewAllHotelsWithAddresses();
        for (var hotel : hotels) {
            int hotelID = hotel.getHotelID();
            String hotelName = hotel.getHotelName();

            // Get total revenue for the hotel
            BigDecimal totalRevenue = paymentDataBaseAccess.getTotalRevenue(hotelID, year, month);
            if (totalRevenue == null) {
                totalRevenue = BigDecimal.ZERO; // Default to 0 if no revenue
            }

            // Get total salary expense for the hotel
            BigDecimal totalExpenses = employeeDataBaseAccess.getTotalSalaryExpense(hotelID, year, month);
            if (totalExpenses == null) {
                totalExpenses = BigDecimal.ZERO; // Default to 0 if no expenses
            }

            // Calculate profit (revenue - expenses)
            BigDecimal profit = totalRevenue.subtract(totalExpenses);

            // Create the report
            MonthlyRevenueReport report = new MonthlyRevenueReport(hotelName, totalRevenue, totalExpenses, profit);
            reportList.add(report);
        }

        return reportList;
    }

    public List<HousekeeperTask> viewAllHousekeepingRecords() {
        HousekeepingTaskDataBaseAccess housekeepingDataBaseAccess = new HousekeepingTaskDataBaseAccess();
        return housekeepingDataBaseAccess.getAllHousekeepingRecords();
    }


}
