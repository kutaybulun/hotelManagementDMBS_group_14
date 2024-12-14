package relations;

public class Employee {
    private int employeeID;
    private String ename;
    private String erole;
    private int hotelID;
    private String contactDetails;

    public Employee(int employeeID, String name, String role, int hotelID, String contactDetails) {
        this.employeeID = employeeID;
        this.ename = name;
        this.erole = role;
        this.hotelID = hotelID;
        this.contactDetails = contactDetails;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public String getEName() {
        return ename;
    }

    public void setEName(String name) {
        this.ename = name;
    }

    public String getERole() {
        return erole;
    }

    public void setERole(String role) {
        this.erole = role;
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
                ", name='" + ename + '\'' +
                ", role='" + erole + '\'' +
                ", hotelID=" + hotelID +
                ", contactDetails='" + contactDetails + '\'' +
                '}';
    }
}
