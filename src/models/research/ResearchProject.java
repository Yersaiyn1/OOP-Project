package models.research;

import models.users.User;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ResearchProject implements Serializable {
    private static final long serialVersionUID = 1L;

    private String projectName;
    private List<User> participants;
    private List<ResearchPaper> publishedPapers;

    public ResearchProject(String projectName) {
        this.projectName = projectName;
        this.participants = new ArrayList<>();
        this.publishedPapers = new ArrayList<>();
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public List<User> getParticipants() {
        return participants;
    }

    public void addParticipant(User user) {
        if (user != null && !participants.contains(user)) {
            participants.add(user);
        }
    }

    public List<ResearchPaper> getPublishedPapers() {
        return publishedPapers;
    }

    public void addPaper(ResearchPaper paper) {
        if (paper != null && !publishedPapers.contains(paper)) {
            publishedPapers.add(paper);
        }
    }

    @Override
    public String toString() {
        return "ResearchProject{name='" + projectName + "'}";
    }
}