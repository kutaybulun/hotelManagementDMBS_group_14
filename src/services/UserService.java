package services;

import databaseaccess.UserDataBaseAccess;
import relations.User;

public class UserService {
    private final UserDataBaseAccess userDataBaseAccess;

    public UserService() {
        this.userDataBaseAccess = new UserDataBaseAccess();
    }

    public boolean signUp(String username, String password, String userType, String contactDetails) {
        int userID = userDataBaseAccess.getNextUserID();
        User user = new User(userID, username, password, userType, contactDetails);
        return userDataBaseAccess.signUp(user);
    }

    public User logIn(String username, String password) {
        return userDataBaseAccess.logIn(username, password);
    }
}
