package models.research;

import models.exceptions.NotResearcherException;
import models.users.User;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * ResearchProject — a long-running research effort with multiple
 * participants and resulting papers.
 *
 * Business rule: only Researchers (users wrapped by ResearcherDecorator)
 * may join. Non-researchers cause NotResearcherException.
 */
public class ResearchProject implements Serializable {

    private static final long serialVersionUID = 1L;

    private String projectId;
    private String topic;
    private LocalDate startDate;
    private final List<ResearchPaper> publishedPapers = new ArrayList<>();
    private final List<Researcher> participants = new ArrayList<>();

    public ResearchProject(String projectId, String topic, LocalDate startDate) {
        this.projectId = projectId;
        this.topic = topic;
        this.startDate = startDate;
    }

    /**
     * Add a participant to this project.
     *
     * Per project rules, the supplied User MUST already be wrapped as a
     * Researcher (ResearcherDecorator). Otherwise NotResearcherException
     * is thrown.
     *
     * @param user a User that should be a Researcher
     * @throws NotResearcherException if the user is not wrapped as a Researcher
     */
    public void addParticipant(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        if (!(user instanceof Researcher)) {
            throw new NotResearcherException(user.getFullName(), "join project " + projectId);
        }
        Researcher r = (Researcher) user;
        if (!participants.contains(r)) {
            participants.add(r);
            r.joinProject(this);
        }
    }

    public void addParticipant(Researcher r) {
        if (r == null) return;
        if (!participants.contains(r)) {
            participants.add(r);
            r.joinProject(this);
        }
    }

    public void addPaper(ResearchPaper p) {
        if (p == null) return;
        publishedPapers.add(p);
    }

    public String getProjectId() {
        return projectId;
    }

    public String getTopic() {
        return topic;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public List<ResearchPaper> getPublishedPapers() {
        return publishedPapers;
    }

    public List<Researcher> getParticipants() {
        return participants;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    @Override
    public String toString() {
        return String.format("Project{%s, '%s', started %s, %d participants, %d papers}",
                projectId, topic, startDate, participants.size(), publishedPapers.size());
    }
}