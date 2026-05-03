package models.research;

import models.users.User;

/**
 * Decorator that turns any Employee into a Researcher associated
 * with a research lab.
 */
public class EmployeeResearcher extends ResearcherDecorator {

    private static final long serialVersionUID = 1L;

    private String labName;

    public EmployeeResearcher(User wrapped, String labName) {
        super(wrapped);
        this.labName = labName;
    }

    public String getLabName() {
        return labName;
    }
    public void setLabName(String labName) {
        this.labName = labName;
    }
}