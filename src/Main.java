import services.UserService;
import relations.User;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserService userService = new UserService();

        while (true) {
            System.out.println("\nWelcome to the Hotel Management System");
            System.out.println("1. Sign Up");
            System.out.println("2. Log In");
            System.out.println("3. Check Current User Type");
            System.out.println("4. Log Out");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline character

            switch (choice) {
                case 1 -> {
                    System.out.println("\n--- Sign Up ---");
                    System.out.print("Enter username: ");
                    String username = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String password = scanner.nextLine();
                    System.out.print("Enter user type (guest, administrator, receptionist, housekeeping): ");
                    String userType = scanner.nextLine();
                    System.out.print("Enter contact details: ");
                    String contactDetails = scanner.nextLine();

                    boolean success = userService.signUp(username, password, userType, contactDetails);
                    if (success) {
                        System.out.println("Sign up successful! You can now log in.");
                    } else {
                        System.out.println("Sign up failed. Try again.");
                    }
                }
                case 2 -> {
                    System.out.println("\n--- Log In ---");
                    System.out.print("Enter username: ");
                    String username = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String password = scanner.nextLine();

                    User user = userService.logIn(username, password);
                    if (user != null) {
                        System.out.println("Login successful! Welcome, " + user.getUserName());
                    } else {
                        System.out.println("Invalid login credentials.");
                    }
                }
                case 3 -> {
                    System.out.println("\n--- Check User Type ---");
                    String userType = userService.getCurrentUserType();
                    if (userType != null) {
                        System.out.println("Current user's role is: " + userType);
                    } else {
                        System.out.println("No user is logged in.");
                    }
                }
                case 4 -> {
                    System.out.println("\n--- Log Out ---");
                    userService.logOut();
                    System.out.println("You have been logged out.");
                }
                case 5 -> {
                    System.out.println("Exiting the system...");
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
