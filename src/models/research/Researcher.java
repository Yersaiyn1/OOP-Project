package models.research;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;

/**
 * Researcher interface — implemented by ResearcherDecorator hierarchy
 * (StudentResearcher, TeacherResearcher, EmployeeResearcher).
 */
public interface Researcher extends Serializable {

    /**
     * @return the researcher's h-index (cached on the decorator).
     */
    int getHIndex();

    /**
     * @return list of papers authored or co-authored by this researcher.
     */
    List<ResearchPaper> getPapers();

    /**
     - @return research projects this researcher participates in.
     */
    List<ResearchProject> getProjects();

    /**
     - Add a paper to this researcher's publication record.
     */
    void addPaper(ResearchPaper paper);

    /**
     - Join a research project. The project will add this researcher to its
     - participants list.
     */
    void joinProject(ResearchProject project);

    /**
     - Print all papers, sorted by the given Comparator
     - (used by SortStrategy implementations).
     */
    void printPapers(Comparator<ResearchPaper> comparator);
}