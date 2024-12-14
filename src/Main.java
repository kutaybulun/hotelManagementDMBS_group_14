import services.UserService;
import relations.User;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserService userService = new UserService();
        boolean running = true;

        while (running) {
            System.out.println("Welcome to the Hotel Management System");
            System.out.println("1. Sign Up");
            System.out.println("2. Log In");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    // Handle User Sign-Up
                    System.out.println("--- Sign Up ---");
                    System.out.print("Enter username: ");
                    String username = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String password = scanner.nextLine();
                    System.out.print("Enter contact details: ");
                    String contactDetails = scanner.nextLine();
                    System.out.print("Enter user type: ");
                    String userType = scanner.nextLine();

                    boolean signUpSuccess = userService.signUp(username, password, userType, contactDetails);
                    if (signUpSuccess) {
                        System.out.println("Sign up successful! You can now log in.");
                    } else {
                        System.out.println("Sign up failed. Please try again.");
                    }
                    break;

                case 2:
                    // Handle User Log-In
                    System.out.println("--- Log In ---");
                    System.out.print("Enter username: ");
                    String loginUsername = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String loginPassword = scanner.nextLine();

                    User user = userService.logIn(loginUsername, loginPassword);
                    if (user != null) {
                        System.out.println("Login successful! Welcome, " + user.getUserName() + " (" + user.getUserType() + ")");
                        // You can now use the `user` object to perform user-specific operations
                    } else {
                        System.out.println("Invalid login credentials. Please try again.");
                    }
                    break;

                case 3:
                    System.out.println("Exiting the system...");
                    running = false;
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }

        scanner.close();
    }
}
