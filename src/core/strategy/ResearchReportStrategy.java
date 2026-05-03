package core.strategy;

import models.academic.Report;
import models.research.ResearchPaper;
import models.research.ResearchProject;
import models.research.Researcher;
import models.research.ResearcherDecorator;

import java.util.Map;

/**
 * ResearchReportStrategy — builds a report describing a Researcher's
 * publication record, projects and h-index.
 *
 * Expected keys:
 *   "researcher" -> Researcher
 */
public class ResearchReportStrategy implements ReportStrategy {

    private static final long serialVersionUID = 1L;

    @Override
    public Report build(Map<String, Object> data) {
        Researcher r = (Researcher) data.get("researcher");

        String authorName = "Unknown";
        if (r instanceof ResearcherDecorator) {
            authorName = ((ResearcherDecorator) r).getWrappedUser().getFullName();
        }

        Report report = new Report("Research Report — " + authorName);
        if (r == null) return report;

        report.put("researcher", authorName);
        report.put("hIndex", r.getHIndex());
        report.put("paperCount", r.getPapers().size());
        report.put("projectCount", r.getProjects().size());

        int totalCitations = 0;
        for (ResearchPaper p : r.getPapers()) {
            totalCitations += p.getCitations();
        }
        report.put("totalCitations", totalCitations);

        int i = 1;
        for (ResearchPaper p : r.getPapers()) {
            report.put("paper_" + i, p.toString());
            i++;
        }
        i = 1;
        for (ResearchProject project : r.getProjects()) {
            report.put("project_" + i, project.toString());
            i++;
        }
        return report;
    }
}