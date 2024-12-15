import services.UserService;
import services.GuestService;
import services.AdministratorService;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;
import java.math.BigDecimal;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final UserService userService = new UserService();
    private static final GuestService guestService = new GuestService();
    private static final AdministratorService adminService = new AdministratorService();

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
        System.out.print("Enter user type (guest, administrator, receptionist, housekeeping): ");
        String userType = scanner.nextLine();
        System.out.print("Enter contact details (email or phone): ");
        String contactDetails = scanner.nextLine();

        boolean success = userService.signUp(username, password, userType, contactDetails);
        if (success) {
            System.out.println("Sign-up successful! You can now log in.");
        } else {
            System.out.println("Sign-up failed. Please try again.");
        }
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
                default -> System.out.println("This role has no associated functionalities at the moment.");
            }
        } else {
            System.out.println("Invalid login credentials. Please try again.");
        }
    }

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
            System.out.println("8. Logout");
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
                case 8 -> {
                    System.out.println("Logging out...");
                    userService.logOut();
                    isAdminActive = false;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void guestMenu() {
        boolean isGuestActive = true;
        while (isGuestActive) {
            System.out.println("\n--- Guest Menu ---");
            System.out.println("1. Add New Booking");
            System.out.println("2. View Available Rooms");
            System.out.println("3. View My Bookings");
            System.out.println("4. Logout");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Clear the buffer

            switch (choice) {
                case 1 -> addNewBooking();
                case 2 -> viewAvailableRooms();
                case 3 -> viewMyBookings();
                case 4 -> {
                    System.out.println("Logging out...");
                    userService.logOut();
                    isGuestActive = false;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void addRoom() {
        System.out.print("Enter Room Type ID: ");
        int roomTypeID = scanner.nextInt();
        System.out.print("Enter Price: ");
        BigDecimal price = scanner.nextBigDecimal();
        scanner.nextLine(); // Clear the buffer
        System.out.print("Enter Status (available, booked, cleaning): ");
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
        for (var user : users) {
            System.out.println(user);
        }
    }

    private static void viewAllBookingRecords() {
        var bookingRecords = adminService.viewAllBookingRecords();
        for (var record : bookingRecords) {
            System.out.println(record);
        }
    }

    private static void viewMostBookedRoomType() {
        var mostBookedRoomTypes = adminService.viewMostBookedRoomTypes();
        for (var type : mostBookedRoomTypes) {
            System.out.println(type);
        }
    }

    private static void addNewBooking() {
        System.out.print("Enter Check-In Date (YYYY-MM-DD): ");
        LocalDate checkInDate = LocalDate.parse(scanner.nextLine());
        System.out.print("Enter Check-Out Date (YYYY-MM-DD): ");
        LocalDate checkOutDate = LocalDate.parse(scanner.nextLine());
        System.out.print("Enter number of guests: ");
        int numberOfGuests = scanner.nextInt();
        System.out.print("Enter Room ID: ");
        int roomID = scanner.nextInt();

        boolean success = guestService.addNewBooking(checkInDate, checkOutDate, numberOfGuests, roomID);
        System.out.println(success ? "Booking added successfully!" : "Failed to add booking.");
    }
    //update this
    private static void viewAvailableRooms() {
        var rooms = guestService.viewAvailableRooms(LocalDate.now(), LocalDate.now().plusDays(7));
        for (var room : rooms) {
            System.out.println(room);
        }
    }

    private static void viewMyBookings() {
        var bookings = guestService.viewMyBookings();
        for (var booking : bookings) {
            System.out.println(booking);
        }
    }
}
