package relations;

public class Employee {
    private int employeeID;
    private String ename;
    private int userID;
    private int roleID;
    private int hotelID;
    private String contactDetails;

    // Constructor with all fields (used when retrieving from the database)
    public Employee(int employeeID, String ename, int userID, int roleID, int hotelID, String contactDetails) {
        this.employeeID = employeeID;
        this.ename = ename;
        this.userID = userID;
        this.roleID = roleID;
        this.hotelID = hotelID;
        this.contactDetails = contactDetails;
    }

    // Constructor without employeeID (used when creating a new employee)
    public Employee(String ename, int userID, int roleID, int hotelID, String contactDetails) {
        this.ename = ename;
        this.userID = userID;
        this.roleID = roleID;
        this.hotelID = hotelID;
        this.contactDetails = contactDetails;
    }

    // Getters and Setters
    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }

    public int getHotelID() {
        return hotelID;
    }

    public void setHotelID(int hotelID) {
        this.hotelID = hotelID;
    }

    public String getContactDetails() {
        return contactDetails;
    }

    public void setContactDetails(String contactDetails) {
        this.contactDetails = contactDetails;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeID=" + employeeID +
                ", ename='" + ename + '\'' +
                ", userID=" + userID +
                ", roleID=" + roleID +
                ", hotelID=" + hotelID +
                ", contactDetails='" + contactDetails + '\'' +
                '}';
    }
}
