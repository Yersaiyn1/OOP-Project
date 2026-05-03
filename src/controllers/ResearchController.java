package controllers;

import core.AuthService;
import core.Logger;
import core.strategy.SortStrategy;
import data.Database;
import models.exceptions.NotResearcherException;
import models.research.ResearchPaper;
import models.research.ResearchProject;
import models.research.Researcher;
import models.users.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller for research operations: papers, projects, listings.
 */
public final class ResearchController {

    private ResearchController() {}

    /**
     * The current user (must be a Researcher) adds a paper.
     */
    public static boolean addPaper(ResearchPaper p) {
        User current = AuthService.getInstance().getCurrentUser();
        if (current == null) {
            System.out.println("[research] login required");
            return false;
        }
        if (!(current instanceof Researcher)) {
            throw new NotResearcherException(current.getFullName(), "add paper");
        }
        if (p == null) return false;
        ((Researcher) current).addPaper(p);
        Database.getInstance().getPapers().put(p.getDoi(), p);
        Logger.getInstance().log(current, "added paper " + p.getDoi());
        return true;
    }

    /**
     * The current user joins a project. NotResearcherException is propagated
     * (so callers see why) but logged here.
     */
    public static boolean joinProject(ResearchProject project) {
        User current = AuthService.getInstance().getCurrentUser();
        if (current == null) {
            System.out.println("[research] login required");
            return false;
        }
        if (project == null) return false;
        try {
            project.addParticipant(current);
            Database.getInstance().getProjects().put(project.getProjectId(), project);
            Logger.getInstance().log(current, "joined project " + project.getProjectId());
            return true;
        } catch (NotResearcherException e) {
            System.out.println("[research] " + e.getMessage());
            return false;
        }
    }

    /**
     * Print every paper system-wide using the given sort strategy.
     */
    public static void printAllPapersSorted(SortStrategy strategy) {
        if (!AuthService.getInstance().isLoggedIn()) {
            System.out.println("[research] login required");
            return;
        }
        List<ResearchPaper> all = listAllPapers();
        List<ResearchPaper> sorted = (strategy == null) ? all : strategy.sort(all);
        System.out.println("=== Papers (sorted) ===");
        for (ResearchPaper p : sorted) {
            System.out.println("  - " + p);
        }
    }

    public static List<ResearchPaper> listAllPapers() {
        List<ResearchPaper> out = new ArrayList<>();
        for (Object v : Database.getInstance().getPapers().values()) {
            if (v instanceof ResearchPaper) out.add((ResearchPaper) v);
        }
        return out;
    }

    public static List<ResearchProject> listAllProjects() {
        List<ResearchProject> out = new ArrayList<>();
        for (Object v : Database.getInstance().getProjects().values()) {
            if (v instanceof ResearchProject) out.add((ResearchProject) v);
        }
        return out;
    }
}