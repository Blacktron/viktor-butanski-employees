package interview.butanski.viktor.solutions.sirma.employeespairfx.entity;

public class Pair {
    private String firstEmployeeId;
    private String secondEmployeeId;
    private String projectId;
    private int daysWorkedTogether;

    public Pair(String firstEmployeeId, String secondEmployeeId, String projectId, int daysWorkedTogether) {
        this.firstEmployeeId = firstEmployeeId;
        this.secondEmployeeId = secondEmployeeId;
        this.projectId = projectId;
        this.daysWorkedTogether = daysWorkedTogether;
    }

    public String getFirstEmployeeId() {
        return firstEmployeeId;
    }

    public void setFirstEmployeeId(String firstEmployeeId) {
        this.firstEmployeeId = firstEmployeeId;
    }

    public String getSecondEmployeeId() {
        return secondEmployeeId;
    }

    public void setSecondEmployeeId(String secondEmployeeId) {
        this.secondEmployeeId = secondEmployeeId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public int getDaysWorkedTogether() {
        return daysWorkedTogether;
    }

    public void setDaysWorkedTogether(int daysWorkedTogether) {
        this.daysWorkedTogether = daysWorkedTogether;
    }
}
