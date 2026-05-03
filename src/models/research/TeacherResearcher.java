package models.research;

import models.users.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Decorator that turns a Teacher (typically a PROFESSOR) into a Researcher
 * with a list of received grants.

 * TeacherFactory auto-wraps every PROFESSOR into a TeacherResearcher.
 */
public class TeacherResearcher extends ResearcherDecorator {

    private static final long serialVersionUID = 1L;

    private final List<String> grantsReceived = new ArrayList<>();

    public TeacherResearcher(User wrapped) {
        super(wrapped);
    }

    public void applyForGrant(String name) {
        if (name == null || name.isBlank()) return;
        grantsReceived.add(name);
        System.out.printf("[grant] %s applied for \"%s\"%n",
                wrapped.getFullName(), name);
    }

    public List<String> getGrantsReceived() {
        return Collections.unmodifiableList(grantsReceived);
    }
}