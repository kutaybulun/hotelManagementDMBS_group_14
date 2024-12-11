package relations;

public class User {
    // Fields corresponding to the User table in the database
    private int userID;
    private String userName;
    private String password;
    private String userType;      // guest, administrator, receptionist, housekeeping
    private String contactDetails;

    public User(int userID, String userName, String password, String userType, String contactDetails) {
        this.userID = userID;
        this.userName = userName;
        this.password = password;
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
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
                ", userName='" + userName + '\'' +
                ", userType='" + userType + '\'' +
                ", contactDetails='" + contactDetails + '\'' +
                '}';
    }
}

