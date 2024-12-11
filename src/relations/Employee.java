package relations;

public class Employee {
    private int employeeID;
    private String name;
    private String role;
    private int hotelID;
    private String contactDetails;

    public Employee(int employeeID, String name, String role, int hotelID, String contactDetails) {
        this.employeeID = employeeID;
        this.name = name;
        this.role = role;
        this.hotelID = hotelID;
        this.contactDetails = contactDetails;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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
                ", name='" + name + '\'' +
                ", role='" + role + '\'' +
                ", hotelID=" + hotelID +
                ", contactDetails='" + contactDetails + '\'' +
                '}';
    }
}
