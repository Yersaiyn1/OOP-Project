package controllers;

import core.Logger;
import data.Database;
import models.research.ResearchPaper;
import models.research.ResearchProject;
import models.research.Researcher;
import models.users.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ResearchController implements Serializable {
    private static final long serialVersionUID = 1L;

    public static void addPaper(Researcher researcher, ResearchPaper paper) {
        if (researcher != null && paper != null) {
            researcher.addPaper(paper);
            Logger.getInstance().log((User) researcher, "added research paper: " + paper.getTitle());
        }
    }

    public static void joinProject(Researcher researcher, ResearchProject project) {
        if (researcher != null && project != null) {
            researcher.joinProject(project);
            Logger.getInstance().log((User) researcher, "joined project: " + project.getProjectName());
        }
    }

    public static List<ResearchPaper> getAllPapers(Researcher researcher) {
        if (researcher != null) {
            return researcher.getPapers();
        }
        return new ArrayList<>();
    }

    public static List<ResearchProject> getAllProjects(Researcher researcher) {
        if (researcher != null) {
            return researcher.getProjects();
        }
        return new ArrayList<>();
    }
}