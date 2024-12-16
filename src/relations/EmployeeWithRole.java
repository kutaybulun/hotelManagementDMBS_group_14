package relations;

import java.math.BigDecimal;

public class EmployeeWithRole {
    private int employeeID;
    private String ename;
    private int userID;
    private int roleID;
    private int hotelID;
    private String contactDetails;
    private String roleName;
    private BigDecimal dailySalary;

    // Constructor with all fields
    public EmployeeWithRole(int employeeID, String ename, int userID, int roleID, int hotelID,
                            String contactDetails, String roleName, BigDecimal dailySalary) {
        this.employeeID = employeeID;
        this.ename = ename;
        this.userID = userID;
        this.roleID = roleID;
        this.hotelID = hotelID;
        this.contactDetails = contactDetails;
        this.roleName = roleName;
        this.dailySalary = dailySalary;
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

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public BigDecimal getDailySalary() {
        return dailySalary;
    }

    public void setDailySalary(BigDecimal dailySalary) {
        this.dailySalary = dailySalary;
    }

    @Override
    public String toString() {
        return "EmployeeWithRole{" +
                "employeeID=" + employeeID +
                ", ename='" + ename + '\'' +
                ", userID=" + userID +
                ", roleID=" + roleID +
                ", hotelID=" + hotelID +
                ", contactDetails='" + contactDetails + '\'' +
                ", roleName='" + roleName + '\'' +
                ", dailySalary=" + dailySalary +
                '}';
    }
}
