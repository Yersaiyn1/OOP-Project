package models.research;

import models.users.User;

/**
 * Decorator that turns a Student (or any User) into a Researcher with
 * a thesis topic. Used for 4th-year students working on theses.
 */
public class StudentResearcher extends ResearcherDecorator {

    private static final long serialVersionUID = 1L;

    private String thesisTopic;

    public StudentResearcher(User wrapped, String thesisTopic) {
        super(wrapped);
        this.thesisTopic = thesisTopic;
    }

    /**
     - Mark the thesis as submitted. In a fuller implementation this would
     - register the thesis with the dean's office.
     */
    public void submitThesis() {
        System.out.printf("[thesis] %s submitted: \"%s\"%n",
                wrapped.getFullName(), thesisTopic);
    }

    public String getThesisTopic()              { return thesisTopic; }
    public void setThesisTopic(String topic)    { this.thesisTopic = topic; }
}