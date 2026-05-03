package models.research;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * ResearchPaper — a published research output.
 * Implements Comparable to satisfy the "use Comparable" requirement
 * (default ordering: by publish date, newest first).
 */
public class ResearchPaper implements Comparable<ResearchPaper>, Serializable {

    private static final long serialVersionUID = 1L;

    private String doi;
    private String title;
    private final List<String> authors;
    private String journal;
    private int pages;
    private LocalDate publishDate;
    private int citations;
    private String abstractText;
    private final List<String> keywords;

    public ResearchPaper(String doi, String title, String journal,
                         int pages, LocalDate publishDate) {
        this.doi = doi;
        this.title = title;
        this.journal = journal;
        this.pages = pages;
        this.publishDate = publishDate;
        this.citations = 0;
        this.authors = new ArrayList<>();
        this.keywords = new ArrayList<>();
    }

    public void addAuthor(String name) {
        if (name != null) authors.add(name);
    }

    public void addKeyword(String keyword) {
        if (keyword != null) keywords.add(keyword);
    }

    public String getDoi()                 { return doi; }
    public String getTitle()               { return title; }
    public List<String> getAuthors()       { return authors; }
    public String getJournal()             { return journal; }
    public int getPages()                  { return pages; }
    public LocalDate getPublishDate()      { return publishDate; }
    public int getCitations()              { return citations; }
    public String getAbstractText()        { return abstractText; }
    public List<String> getKeywords()      { return keywords; }

    public void setDoi(String doi)                       { this.doi = doi; }
    public void setTitle(String title)                   { this.title = title; }
    public void setJournal(String journal)               { this.journal = journal; }
    public void setPages(int pages)                      { this.pages = pages; }
    public void setPublishDate(LocalDate publishDate)    { this.publishDate = publishDate; }
    public void setCitations(int citations)              { this.citations = citations; }
    public void setAbstractText(String abstractText)     { this.abstractText = abstractText; }

    /**
     * Default ordering: most recent first. SortByDateStrategy uses this.
     */
    @Override
    public int compareTo(ResearchPaper o) {
        if (o == null || o.publishDate == null) return -1;
        if (this.publishDate == null) return 1;
        return o.publishDate.compareTo(this.publishDate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ResearchPaper)) return false;
        return Objects.equals(doi, ((ResearchPaper) o).doi);
    }

    @Override
    public int hashCode() {
        return Objects.hash(doi);
    }

    @Override
    public String toString() {
        return String.format("Paper{doi=%s, '%s', %s, %d cit, %d p}",
                doi, title, publishDate, citations, pages);
    }
}