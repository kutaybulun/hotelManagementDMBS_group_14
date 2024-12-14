package relations;

public class User {
    // Fields corresponding to the User table in the database
    private int userID;
    private String username;
    private String userpassword;
    private String userType;      // guest, administrator, receptionist, housekeeping
    private String contactDetails;

    public User(int userID, String username, String userpassword, String userType, String contactDetails) {
        this.userID = userID;
        this.username = username;
        this.userpassword = userpassword;
        this.userType = userType;
        this.contactDetails = contactDetails;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return username;
    }

    public void setUserName(String username) {
        this.username = username;
    }

    public String getUserPassword() {
        return userpassword;
    }

    public void setUserPassword(String userpassword) {
        this.userpassword = userpassword;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getContactDetails() {
        return contactDetails;
    }

    public void setContactDetails(String contactDetails) {
        this.contactDetails = contactDetails;
    }

    @Override
    public String toString() {
        return "User{" +
                "userID=" + userID +
                ", userName='" + username + '\'' +
                ", userType='" + userType + '\'' +
                ", contactDetails='" + contactDetails + '\'' +
                '}';
    }
}

