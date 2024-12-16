package relations;

import java.math.BigDecimal;

public class Employee {
    private int employeeID;
    private String ename;
    private String erole;
    private BigDecimal dailysalary;
    private int hotelID;
    private String contactDetails;

    // Constructor with all fields (used when retrieving from the database)
    public Employee(int employeeID, String ename, String erole, BigDecimal dailysalary, int hotelID, String contactDetails) {
        this.employeeID = employeeID;
        this.ename = ename;
        this.erole = erole;
        this.dailysalary = dailysalary;
        this.hotelID = hotelID;
        this.contactDetails = contactDetails;
    }

    // Constructor without employeeID (used when creating a new employee)
    public Employee(String ename, String erole, BigDecimal dailysalary, int hotelID, String contactDetails) {
        this.ename = ename;
        this.erole = erole;
        this.dailysalary = dailysalary;
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

    public String getErole() {
        return erole;
    }

    public void setErole(String erole) {
        this.erole = erole;
    }

    public BigDecimal getDailysalary() {
        return dailysalary;
    }

    public void setDailysalary(BigDecimal dailysalary) {
        this.dailysalary = dailysalary;
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
                ", erole='" + erole + '\'' +
                ", dailysalary=" + dailysalary +
                ", hotelID=" + hotelID +
                ", contactDetails='" + contactDetails + '\'' +
                '}';
    }
}
