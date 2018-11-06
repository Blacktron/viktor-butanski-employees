package com.company;

public class Pair {
    private String employeeID1;
    private String employeeID2;
    private String projectID;
    private int daysWorkedTogether;

    public Pair(String employeeID1, String employeeID2, String projectID, int daysWorkedTogether) {
        this.employeeID1 = employeeID1;
        this.employeeID2 = employeeID2;
        this.projectID = projectID;
        this.daysWorkedTogether = daysWorkedTogether;
    }

    public String getEmployeeID1() {
        return employeeID1;
    }

    public void setEmployeeID1(String employeeID1) {
        this.employeeID1 = employeeID1;
    }

    public String getEmployeeID2() {
        return employeeID2;
    }

    public void setEmployeeID2(String employeeID2) {
        this.employeeID2 = employeeID2;
    }

    public String getProjectID() {
        return projectID;
    }

    public void setProjectID(String projectID) {
        this.projectID = projectID;
    }

    public int getDaysWorkedTogether() {
        return daysWorkedTogether;
    }

    public void setDaysWorkedTogether(int daysWorkedTogether) {
        this.daysWorkedTogether = daysWorkedTogether;
    }
}