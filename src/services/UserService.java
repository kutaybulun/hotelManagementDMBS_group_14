package services;

import databaseaccess.*;
import relations.*;

public class UserService {
    private final UserDataBaseAccess userDataBaseAccess;
    private final EmployeeDataBaseAccess employeeDataBaseAccess;

    public UserService() {
        this.userDataBaseAccess = new UserDataBaseAccess();
        this.employeeDataBaseAccess = new EmployeeDataBaseAccess();
    }

    /*public boolean signUp(String username, String password, String userType, String contactDetails) {
        int userID = userDataBaseAccess.getNextUserID();
        User user = new User(userID, username, password, userType, contactDetails);
        boolean isUserCreated = userDataBaseAccess.signUp(user);

        if (isUserCreated && (userType.equals("administrator") || userType.equals("receptionist") || userType.equals("housekeeping"))) {
            int roleID = employeeDataBaseAccess.getRoleIDByName(userType);
            if (roleID == -1) {
                userDataBaseAccess.delete(userID); // Rollback the user creation
                return false;
            }

            int employeeID = employeeDataBaseAccess.getNextEmployeeID();
            Employee employee = new Employee(employeeID, username, userID, roleID, 1, contactDetails); // Default hotelID to 1 (can be updated)
            boolean isEmployeeCreated = employeeDataBaseAccess.create(employee);

            if (!isEmployeeCreated) {
                userDataBaseAccess.delete(userID); // Rollback the user creation
                return false;
            }
        }

        return isUserCreated;
    }*/

    public boolean signUp(String username, String password, String userType, String contactDetails) {
        int userID = userDataBaseAccess.getNextUserID();
        User user = new User(userID, username, password, userType, contactDetails);
        return userDataBaseAccess.signUp(user);
    }

    public User logIn(String username, String password) {
        return userDataBaseAccess.logIn(username, password);
    }

    public String getCurrentUserType() {
        return userDataBaseAccess.getCurrentUserType();
    }

    public void logOut() {
        userDataBaseAccess.logOut();
    }
}
