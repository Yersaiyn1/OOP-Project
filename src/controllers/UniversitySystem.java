package controllers;

import core.AuthService;
import core.Logger;
import data.Database;
import models.research.ResearchPaper;
import models.research.Researcher;
import models.research.ResearcherDecorator;
import models.users.User;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * UniversitySystem — Facade.
 *
 * Single entry point that hides the wiring between Database, AuthService,
 * NewsService and the various controllers. Used by Main and the views to
 * perform high-level use cases without having to know which subsystem
 * does what.
 *
 * Implemented as a small Singleton — there is only ever one running
 * university.
 */
public class UniversitySystem {

    private UniversitySystem() {}

    private static class Holder {
        private static final UniversitySystem INSTANCE = new UniversitySystem();
    }

    public static UniversitySystem getInstance() {
        return Holder.INSTANCE;
    }

    // ----- lifecycle -----

    /**
     * Boot the system: try to load saved state from disk.
     */
    public void start() {
        Database.getInstance().load();
        Logger.getInstance().log("university system started");
    }

    /**
     * Shut down: persist state to disk.
     */
    public void shutdown() {
        Logger.getInstance().log("university system shutting down");
        Database.getInstance().save();
    }

    // ----- auth proxies -----

    public User login(String email, String pwd) {
        return AuthController.login(email, pwd);
    }

    public void logout() {
        AuthController.logout();
    }

    public User currentUser() {
        return AuthController.getCurrentUser();
    }

    // ----- research use cases (per class diagram) -----

    /**
     * Print every paper in the system, sorted by the given Comparator.
     * Demonstrates the Strategy pattern (the comparator is the strategy
     * supplied by the caller, e.g. SortByCitationsStrategy).
     */
    public void printAllPapers(Comparator<ResearchPaper> comparator) {
        List<ResearchPaper> all = collectAllPapers();
        if (comparator != null) {
            all.sort(comparator);
        }
        System.out.println("=== All papers in the system ===");
        for (ResearchPaper p : all) {
            System.out.println("  - " + p);
        }
    }

    /**
     * Top-cited researcher across the given school (department).
     *
     * "School" here matches the {@code department} field on Employee,
     * or — for student researchers — is interpreted as the student's
     * major code. Returns null if no researcher matches.
     */
    public Researcher getTopCitedResearcherOfSchool(String school) {
        Researcher best = null;
        int bestCitations = -1;
        for (Researcher r : collectAllResearchers()) {
            if (!matchesSchool(r, school)) continue;
            int total = totalCitations(r);
            if (total > bestCitations) {
                bestCitations = total;
                best = r;
            }
        }
        return best;
    }

    /**
     * Top-cited researcher considering only papers published in the given year.
     */
    public Researcher getTopCitedResearcherOfYear(int year) {
        Researcher best = null;
        int bestCitations = -1;
        for (Researcher r : collectAllResearchers()) {
            int total = 0;
            for (ResearchPaper p : r.getPapers()) {
                if (p.getPublishDate() != null && p.getPublishDate().getYear() == year) {
                    total += p.getCitations();
                }
            }
            if (total > bestCitations) {
                bestCitations = total;
                best = r;
            }
        }
        return best;
    }

    // ----- helpers -----

    private List<ResearchPaper> collectAllPapers() {
        List<ResearchPaper> out = new ArrayList<>();
        for (Object value : Database.getInstance().getPapers().values()) {
            if (value instanceof ResearchPaper) {
                out.add((ResearchPaper) value);
            }
        }
        return out;
    }

    private List<Researcher> collectAllResearchers() {
        List<Researcher> out = new ArrayList<>();
        for (User u : Database.getInstance().getUsers().values()) {
            if (u instanceof Researcher) {
                out.add((Researcher) u);
            }
        }
        // also pick up any decorator that isn't itself stored as a User
        // (in this project decorators wrap Users that are in the map,
        // but we keep this loop for future-proofing)
        return out;
    }

    private int totalCitations(Researcher r) {
        int total = 0;
        for (ResearchPaper p : r.getPapers()) {
            total += p.getCitations();
        }
        return total;
    }

    private boolean matchesSchool(Researcher r, String school) {
        if (school == null) return true;
        if (r instanceof ResearcherDecorator) {
            User wrapped = ((ResearcherDecorator) r).getWrappedUser();
            // best-effort: match against the user's role + role-specific fields
            // (department, major) by comparing toString — concrete classes
            // expose these and we don't want a hard dependency on Student/Teacher here.
            return wrapped.toString().toLowerCase().contains(school.toLowerCase());
        }
        return false;
    }
}