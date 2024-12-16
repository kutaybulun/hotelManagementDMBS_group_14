package relations;

import java.math.BigDecimal;

public class EmployeeRole {
    private int roleID;
    private String roleName;
    private BigDecimal dailySalary;

    // Constructor with all fields
    public EmployeeRole(int roleID, String roleName, BigDecimal dailySalary) {
        this.roleID = roleID;
        this.roleName = roleName;
        this.dailySalary = dailySalary;
    }

    // Constructor without roleID (used when creating a new role)
    public EmployeeRole(String roleName, BigDecimal dailySalary) {
        this.roleName = roleName;
        this.dailySalary = dailySalary;
    }

    // Getters and Setters
    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
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
        return "EmployeeRole{" +
                "roleID=" + roleID +
                ", roleName='" + roleName + '\'' +
                ", dailySalary=" + dailySalary +
                '}';
    }
}

