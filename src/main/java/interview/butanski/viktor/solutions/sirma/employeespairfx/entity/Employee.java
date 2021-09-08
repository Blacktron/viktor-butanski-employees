package interview.butanski.viktor.solutions.sirma.employeespairfx.entity;

import java.util.Date;

public class Employee extends Person {
    private String projectId;
    private Date dateFrom;
    private Date dateTo;

    public Employee(String id, String projectId, Date dateFrom, Date dateTo) {
        super(id);
        this.projectId = projectId;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }
}
