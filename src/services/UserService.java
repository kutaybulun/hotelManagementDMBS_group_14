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
