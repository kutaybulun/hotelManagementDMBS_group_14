import services.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final UserService userService = new UserService();
    private static final GuestService guestService = new GuestService();
    private static final AdministratorService adminService = new AdministratorService();
    private static final ReceptionistService receptionistService = new ReceptionistService();
    private static final HousekeepingService housekeepingService = new HousekeepingService();

    public static void main(String[] args) {
        boolean isRunning = true;

        while (isRunning) {
            System.out.println("\nWelcome to the Hotel Management System");
            System.out.println("1. Sign Up");
            System.out.println("2. Log In");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Clear the buffer

            switch (choice) {
                case 1 -> signUp();
                case 2 -> logIn();
                case 3 -> {
                    System.out.println("Exiting... Goodbye!");
                    isRunning = false;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void signUp() {
        System.out.println("\n--- Sign Up ---");
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        String userType;
        while (true) {
            System.out.print("Enter user type (guest, receptionist, housekeeping): ");
            userType = scanner.nextLine().trim().toLowerCase();
            if (!userType.equals("administrator")) {
                break; // Valid user type, exit loop
            } else {
                System.out.println("Error: You cannot create an administrator account via sign-up.");
            }
        }

        System.out.print("Enter contact details (email or phone): ");
        String contactDetails = scanner.nextLine();

        boolean success = userService.signUp(username, password, userType, contactDetails);
        System.out.println(success ? "Sign-up successful! You can now log in." : "Sign-up failed. Please try again.");
    }


    private static void logIn() {
        System.out.println("\n--- Log In ---");
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        var loggedInUser = userService.logIn(username, password);

        if (loggedInUser != null) {
            System.out.println("\nLogin successful! Welcome, " + loggedInUser.getUserName() + "!");
            String userType = userService.getCurrentUserType();

            switch (userType) {
                case "administrator" -> adminMenu();
                case "guest" -> guestMenu();
                case "receptionist" -> receptionistMenu();
                case "housekeeping" -> housekeepingMenu();
                default -> System.out.println("This role has no associated functionalities at the moment.");
            }
        } else {
            System.out.println("Invalid login credentials. Please try again.");
        }
    }

    // --- ADMINISTRATOR MENU ---
    private static void adminMenu() {
        boolean isAdminActive = true;
        while (isAdminActive) {
            System.out.println("\n--- Administrator Menu ---");
            System.out.println("1. Add Room");
            System.out.println("2. Delete Room");
            System.out.println("3. Add User Account");
            System.out.println("4. Remove User Account");
            System.out.println("5. View User Accounts");
            System.out.println("6. View All Booking Records");
            System.out.println("7. View Most Booked Room Type");
            System.out.println("8. Add Room Type");
            System.out.println("9. View Room Types");
            System.out.println("10. View All Employees With Role");
            System.out.println("11. Update Employee Hotel");
            System.out.println("12. View Hotels with Addresses");
            System.out.println("13. Generate Monthly Revenue Report");
            System.out.println("14. View All Housekeeping Records");
            System.out.println("15. View Hotel Star Ratings");
            System.out.println("16. Create Hotel");
            System.out.println("17. Manage Room Status");
            System.out.println("18. Logout");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Clear the buffer

            switch (choice) {
                case 1 -> addRoom();
                case 2 -> deleteRoom();
                case 3 -> addUserAccount();
                case 4 -> removeUserAccount();
                case 5 -> viewUserAccounts();
                case 6 -> viewAllBookingRecords();
                case 7 -> viewMostBookedRoomType();
                case 8 -> addRoomType();
                case 9 -> viewRoomTypes();
                case 10 -> viewAllEmployeesWithRole();
                case 11 -> updateEmployeeHotel();
                case 12 -> viewAllHotelsWithAddress();
                case 13 -> generateMonthlyRevenueReport();
                case 14 -> viewAllHousekeepingRecords();
                case 15 -> viewHotelStarRatings();
                case 16 -> createHotel();
                case 17 -> manageRoomStatus();
                case 18-> {
                    System.out.println("Logging out...");
                    userService.logOut();
                    isAdminActive = false;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // --- RECEPTIONIST MENU ---
    private static void receptionistMenu() {
        boolean isReceptionistActive = true;
        while (isReceptionistActive) {
            System.out.println("\n--- Receptionist Menu ---");
            System.out.println("1. View Requested Bookings");
            System.out.println("2. View Rooms to be Cleaned");
            System.out.println("3. View Housekeepers and Availability");
            System.out.println("4. View All Bookings");
            System.out.println("5. Add New Booking");
            System.out.println("6. Assign Housekeeping Task");
            System.out.println("7. Modify Booking (Check-In / Check-Out)");
            System.out.println("8. Delete Booking");
            System.out.println("9. View Due Payment for a Booking");
            System.out.println("10. Process Payment");
            System.out.println("11. View Available Rooms");
            System.out.println("12. Logout");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Clear the buffer

            switch (choice) {
                case 1 -> viewRequestedBookings();
                case 2 -> viewRoomsToBeCleaned();
                case 3 -> viewHousekeepersAndAvailability();
                case 4 -> viewAllBookings();
                case 5 -> addNewBookingByReceptionist();
                case 6 -> assignHousekeepingTask();
                case 7 -> modifyBooking();
                case 8 -> deleteBooking();
                case 9 -> viewTotalPaymentForReceptionist();
                case 10 -> processPayment();
                case 11 -> viewAvailableRooms();
                case 12 -> {
                    System.out.println("Logging out...");
                    userService.logOut();
                    isReceptionistActive = false;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // --- GUEST MENU ---
    private static void guestMenu() {
        boolean isGuestActive = true;
        while (isGuestActive) {
            System.out.println("\n--- Guest Menu ---");
            System.out.println("1. Add New Booking");
            System.out.println("2. View Available Rooms");
            System.out.println("3. View My Bookings");
            System.out.println("4. Cancel Booking");
            System.out.println("5. View Payment Due For a Booking");
            System.out.println("6. Leave Star Rating");
            System.out.println("7. Logout");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Clear the buffer

            switch (choice) {
                case 1 -> addNewBooking();
                case 2 -> viewAvailableRooms();
                case 3 -> viewMyBookings();
                case 4 -> cancelBooking();
                case 5 -> viewTotalPaymentForGuest();
                case 6 -> leaveStarRating();
                case 7 -> {
                    System.out.println("Logging out...");
                    userService.logOut();
                    isGuestActive = false;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    //--- HOUSEKEEPING MENU ---
    private static void housekeepingMenu() {
        boolean isHousekeepingActive = true;
        while (isHousekeepingActive) {
            System.out.println("\n--- Housekeeping Menu ---");
            System.out.println("1. View My Pending Tasks");
            System.out.println("2. View My Completed Tasks");
            System.out.println("3. Update Task Status to Completed");
            System.out.println("4. View My Cleaning Schedule");
            System.out.println("5. Logout");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Clear the buffer

            switch (choice) {
                case 1 -> viewMyPendingTasks();
                case 2 -> viewMyCompletedTasks();
                case 3 -> updateMyTaskStatus();
                case 4 -> viewMyCleaningSchedule();
                case 5 -> {
                    System.out.println("Logging out...");
                    userService.logOut();
                    isHousekeepingActive = false;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // --- STATIC METHODS FOR RECEPTIONIST MENU ---
    private static void viewRequestedBookings() {
        receptionistService.viewRequestedBookings().forEach(System.out::println);
    }

    private static void viewRoomsToBeCleaned() {
        List<Integer> roomIDs = receptionistService.viewRoomsToBeCleaned();
        if (roomIDs.isEmpty()) {
            System.out.println("No rooms currently marked for cleaning.");
        } else {
            roomIDs.forEach(roomID -> System.out.println("Room ID: " + roomID));
        }
    }

    private static void viewHousekeepersAndAvailability() {
        receptionistService.viewAllHouseKeepersRecordsAndAvailability().forEach(System.out::println);
    }

    private static void viewAllBookings() {
        receptionistService.viewAllBookings().forEach(System.out::println);
    }

    private static void addNewBookingByReceptionist() {
        System.out.print("Enter User ID: ");
        int userID = scanner.nextInt();
        scanner.nextLine(); // Clear the buffer

        System.out.print("Enter Check-In Date (YYYY-MM-DD): ");
        LocalDate checkInDate = LocalDate.parse(scanner.nextLine());

        System.out.print("Enter Check-Out Date (YYYY-MM-DD): ");
        LocalDate checkOutDate = LocalDate.parse(scanner.nextLine());

        System.out.print("Enter number of guests: ");
        int numberOfGuests = scanner.nextInt();
        scanner.nextLine(); // Clear the buffer

        System.out.print("Enter Room IDs (comma-separated, e.g., 1,2,3): ");
        String roomIDsInput = scanner.nextLine();
        List<Integer> roomIDs = parseRoomIDs(roomIDsInput);

        boolean success = receptionistService.addNewBooking(userID, checkInDate, checkOutDate, numberOfGuests, roomIDs);
        System.out.println(success ? "Booking added successfully!" : "Failed to add booking.");
    }

    private static void assignHousekeepingTask() {
        System.out.print("Enter Room ID: ");
        int roomID = scanner.nextInt();
        System.out.print("Enter User ID of Housekeeper: ");
        int userID = scanner.nextInt();
        System.out.print("Enter Scheduled Date (YYYY-MM-DD): ");
        LocalDate date = LocalDate.parse(scanner.next());
        boolean success = receptionistService.assignHouseKeepingTask(roomID, userID, date, "pending");
        System.out.println(success ? "Task assigned successfully!" : "Failed to assign task.");
    }
    private static void deleteBooking() {
        System.out.print("Enter Booking ID to delete: ");
        int bookingID = scanner.nextInt();
        boolean success = receptionistService.deleteBooking(bookingID);
        System.out.println(success ? "Booking deleted successfully!" :
                "Error: Only cancelled bookings can be deleted.");
    }

    private static void modifyBooking() {
        System.out.print("Enter Booking ID: ");
        int bookingID = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Choose action (check-in / check-out): ");
        String action = scanner.nextLine().trim().toLowerCase();
        boolean success = receptionistService.modifyBooking(bookingID, action);
        if (success) {
            System.out.println("Booking updated successfully for action: " + action);
        } else {
            if ("check-in".equals(action)) {
                System.out.println("Error: Booking must be pending to check-in.");
            } else if ("check-out".equals(action)) {
                System.out.println("Error: Payment must be completed before check-out.");
            } else {
                System.out.println("Invalid action. Please enter 'check-in' or 'check-out'.");
            }
        }
    }

    private static void viewTotalPaymentForReceptionist() {
        System.out.print("Enter Booking ID to view payment: ");
        int bookingID = scanner.nextInt();
        scanner.nextLine(); // Clear the buffer

        BigDecimal totalPayment = receptionistService.getTotalPayment(bookingID);
        if (totalPayment.compareTo(BigDecimal.ZERO) > 0) {
            System.out.println("Total payment for booking ID " + bookingID + " is: $" + totalPayment);
        } else {
            System.out.println("No payment found for the given booking ID.");
        }
    }

    private static void processPayment() {
        System.out.print("Enter Booking ID to process payment for: ");
        int bookingID = scanner.nextInt();

        boolean success = receptionistService.processPayment(bookingID);
        if (success) {
            System.out.println("Payment successfully processed for booking ID: " + bookingID);
        } else {
            System.out.println("Failed to process payment. Please check if the booking exists and is eligible for payment.");
        }
    }

    // --- STATIC METHODS FOR GUEST MENU ---
    private static void addNewBooking() {
        System.out.print("Enter Check-In Date (YYYY-MM-DD): ");
        LocalDate checkInDate = LocalDate.parse(scanner.nextLine());

        System.out.print("Enter Check-Out Date (YYYY-MM-DD): ");
        LocalDate checkOutDate = LocalDate.parse(scanner.nextLine());

        System.out.print("Enter number of guests: ");
        int numberOfGuests = scanner.nextInt();
        scanner.nextLine(); // Clear the buffer

        System.out.print("Enter Room IDs (comma-separated, e.g., 1,2,3): ");
        String roomIDsInput = scanner.nextLine();
        List<Integer> roomIDs = parseRoomIDs(roomIDsInput);

        boolean success = guestService.addNewBooking(checkInDate, checkOutDate, numberOfGuests, roomIDs);
        System.out.println(success ? "Booking added successfully!" : "Failed to add booking.");
    }

    private static void viewAvailableRooms() {
        try {
            System.out.print("Enter Check-In Date (YYYY-MM-DD): ");
            LocalDate checkInDate = LocalDate.parse(scanner.nextLine());

            System.out.print("Enter Check-Out Date (YYYY-MM-DD): ");
            LocalDate checkOutDate = LocalDate.parse(scanner.nextLine());

            // Ensure check-out date is after check-in date
            if (!checkOutDate.isAfter(checkInDate)) {
                System.out.println("Check-Out date must be after Check-In date. Please try again.");
                return; // Exit the method early
            }

            var rooms = guestService.viewAvailableRooms(checkInDate, checkOutDate);

            if (rooms.isEmpty()) {
                System.out.println("No available rooms for the specified dates.");
            } else {
                System.out.println("\n--- Available Rooms ---");
                rooms.forEach(System.out::println);
            }

        } catch (Exception e) {
            System.out.println("Invalid date format. Please enter the date in YYYY-MM-DD format.");
        }
    }

    private static void viewMyBookings() {
        var bookings = guestService.viewMyBookings();
        bookings.forEach(System.out::println);
    }

    private static void cancelBooking() {
        System.out.print("Enter Booking ID to cancel: ");
        int bookingID = scanner.nextInt();
        boolean success = guestService.cancelBooking(bookingID);
        if (success) {
            System.out.println("Booking canceled successfully!");
        } else {
            System.out.println("Failed to cancel booking. Ensure it is your booking, pending, and unpaid.");
        }
    }

    private static void viewTotalPaymentForGuest() {
        System.out.print("Enter Booking ID to view payment: ");
        int bookingID = scanner.nextInt();
        scanner.nextLine(); // Clear the buffer

        BigDecimal totalPayment = guestService.getTotalPayment(bookingID);
        if (totalPayment == null) {
            System.out.println("You do not have permission to view the payment for this booking. Please enter a valid booking ID that belongs to you.");
        } else if (totalPayment.compareTo(BigDecimal.ZERO) > 0) {
            System.out.println("Total payment for booking ID " + bookingID + " is: $" + totalPayment);
        } else {
            System.out.println("No payment found for the given booking ID.");
        }
    }

    private static void leaveStarRating() {
        System.out.print("Enter Booking ID: ");
        int bookingID = scanner.nextInt();
        System.out.print("Enter Star Rating (0-5): ");
        int rating = scanner.nextInt();
        boolean success = guestService.leaveStarRating(bookingID, rating);
        System.out.println(success ? "Rating submitted successfully!" : "Failed to submit rating. Enter a correct star rating and make sure this is your booking");
    }

    // --- STATIC METHODS FOR ADMIN MENU ---
    private static void createHotel() {
        System.out.print("Enter Hotel Name: ");
        String hotelName = scanner.nextLine();
        System.out.print("Enter Street: ");
        String street = scanner.nextLine();
        System.out.print("Enter City: ");
        String city = scanner.nextLine();
        System.out.print("Enter State: ");
        String state = scanner.nextLine();
        System.out.print("Enter Contact Number: ");
        String contactNumber = scanner.nextLine();
        boolean success = adminService.createHotel(hotelName, street, city, state, contactNumber);

        if (success) {
            System.out.println("Hotel created successfully!");
        } else {
            System.out.println("Error: Failed to create hotel. Please check all inputs and try again.");
        }
    }
    private static void manageRoomStatus() {
        System.out.print("Enter Room ID to update: ");
        int roomID = scanner.nextInt();
        scanner.nextLine(); // Clear the buffer

        System.out.print("Enter New Room Type ID: ");
        int newRoomTypeID = scanner.nextInt();

        boolean success = adminService.updateRoomType(roomID, newRoomTypeID);
        System.out.println(success ? "Room type updated successfully!" : "Error: Failed to update room type. Please check the details and try again.");
    }

    private static void addRoom() {
        System.out.print("Enter Room Type ID: ");
        int roomTypeID = scanner.nextInt();
        System.out.print("Enter Price: ");
        BigDecimal price = scanner.nextBigDecimal();
        scanner.nextLine();
        System.out.print("Enter Status: ");
        String status = scanner.nextLine();
        System.out.print("Enter Hotel ID: ");
        int hotelID = scanner.nextInt();
        boolean success = adminService.addRoom(roomTypeID, price, status, hotelID);
        System.out.println(success ? "Room added successfully!" : "Failed to add room.");
    }

    private static void deleteRoom() {
        System.out.print("Enter Room ID to delete: ");
        int roomID = scanner.nextInt();
        boolean success = adminService.deleteRoom(roomID);
        System.out.println(success ? "Room deleted successfully!" : "Failed to delete room.");
    }

    private static void addUserAccount() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.print("Enter user type (guest, administrator, receptionist, housekeeping): ");
        String userType = scanner.nextLine();
        System.out.print("Enter contact details (email or phone): ");
        String contactDetails = scanner.nextLine();
        boolean success = adminService.addUserAccount(username, password, userType, contactDetails);
        System.out.println(success ? "User account created successfully!" : "Failed to create user account.");
    }

    private static void removeUserAccount() {
        System.out.print("Enter User ID to delete: ");
        int userID = scanner.nextInt();
        boolean success = adminService.removeUserAccount(userID);
        System.out.println(success ? "User account deleted successfully!" : "Failed to delete user account.");
    }

    private static void viewUserAccounts() {
        var users = adminService.viewUserAccounts();
        users.forEach(System.out::println);
    }

    private static void viewAllBookingRecords() {
        var bookingRecords = adminService.viewAllBookingRecords();
        bookingRecords.forEach(System.out::println);
    }

    private static void viewMostBookedRoomType() {
        var mostBookedRoomTypes = adminService.viewMostBookedRoomTypes();
        mostBookedRoomTypes.forEach(System.out::println);
    }

    private static void addRoomType() {
        System.out.print("Enter Room Type Name: ");
        String roomTypeName = scanner.nextLine();
        boolean success = adminService.addRoomType(roomTypeName);
        System.out.println(success ? "Room type added successfully!" : "Failed to add room type.");
    }

    private static void viewRoomTypes() {
        System.out.println("\n--- View Room Types ---");
        var roomTypes = adminService.viewRoomTypes();
        if (roomTypes.isEmpty()) {
            System.out.println("No room types available.");
        } else {
            roomTypes.forEach(System.out::println);
        }
    }

    private static void viewAllEmployeesWithRole() {
        System.out.println("\n--- View All Employees With Roles ---");
        var employees = adminService.viewAllEmployeesWithRole();
        if (employees.isEmpty()) {
            System.out.println("No employees found.");
        } else {
            employees.forEach(System.out::println);
        }
    }

    private static void viewAllHotelsWithAddress() {
        var hotels = adminService.viewAllHotelsWithAddress();
        if (hotels.isEmpty()) {
            System.out.println("No hotels with addresses found.");
        } else {
            for (var hotel : hotels) {
                System.out.println(hotel);
            }
        }
    }

    private static void updateEmployeeHotel() {
        System.out.print("Enter Employee ID to update: ");
        int employeeID = scanner.nextInt();
        System.out.print("Enter new Hotel ID for the employee: ");
        int hotelID = scanner.nextInt();

        boolean success = adminService.updateEmployeeHotel(employeeID, hotelID);
        if (success) {
            System.out.println("Employee's hotel updated successfully!");
        } else {
            System.out.println("Failed to update employee's hotel. Please check the employee ID and Hotel ID and try again.");
        }
    }

    private static void generateMonthlyRevenueReport() {
        System.out.println("\n--- Generate Monthly Revenue Report ---");

        System.out.print("Enter Year (YYYY): ");
        int year = scanner.nextInt();

        System.out.print("Enter Month (1-12): ");
        int month = scanner.nextInt();

        if (month < 1 || month > 12) {
            System.out.println("Invalid month. Please enter a value between 1 and 12.");
            return;
        }

        var reportList = adminService.generateMonthlyRevenueReport(year, month);

        if (reportList.isEmpty()) {
            System.out.println("No report data available for the given month and year.");
        } else {
            System.out.println("\n--- Monthly Revenue Report for " + month + "/" + year + " ---");
            for (var report : reportList) {
                System.out.println(report);
            }
        }
    }

    private static void viewAllHousekeepingRecords() {
        var records = adminService.viewAllHousekeepingRecords();
        System.out.println("\n--- Housekeeping Records ---");
        if (records.isEmpty()) {
            System.out.println("No housekeeping records found.");
        } else {
            for (var record : records) {
                System.out.println(record);
            }
        }
    }

    private static void viewHotelStarRatings() {
        var hotelStarRatings = adminService.viewStarRatingsForHotels();
        hotelStarRatings.forEach(System.out::println);
    }

    // --- STATIC METHODS FOR HOUSEKEEPING MENU ---
    private static void viewMyPendingTasks() {
        housekeepingService.viewMyPendingTasks().forEach(System.out::println);
    }

    private static void viewMyCompletedTasks() {
        housekeepingService.viewMyCompletedTasks().forEach(System.out::println);
    }

    private static void updateMyTaskStatus() {
        System.out.print("Enter Task ID to mark as completed: ");
        int taskID = scanner.nextInt();
        boolean success = housekeepingService.updateMyTaskStatus(taskID);
        System.out.println(success ? "Task updated successfully!" : "Failed to update task status. Make sure the task is assigned to you.");
    }

    // Helper method to parse room IDs entered as a comma-separated string (e.g., "1,2,3")
    private static List<Integer> parseRoomIDs(String roomIDsInput) {
        List<Integer> roomIDs = new ArrayList<>();
        String[] roomIDStrings = roomIDsInput.split(",");
        for (String idStr : roomIDStrings) {
            try {
                roomIDs.add(Integer.parseInt(idStr.trim())); // Parse and trim each room ID
            } catch (NumberFormatException e) {
                System.out.println("Invalid room ID format: " + idStr);
            }
        }
        return roomIDs;
    }

    private static void viewMyCleaningSchedule() {
        var schedule = housekeepingService.viewMyCleaningSchedule();
        System.out.println("\n--- My Cleaning Schedule ---");
        if (schedule.isEmpty()) {
            System.out.println("No tasks in your cleaning schedule.");
        } else {
            for (var task : schedule) {
                System.out.println(task);
            }
        }
    }




}
