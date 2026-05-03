package models.research;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ResearchPaper implements Serializable {
    private static final long serialVersionUID = 1L;

    private String title;
    private List<String> authors;
    private int pages;
    private String journal;

    public ResearchPaper(String title, int pages, String journal) {
        this.title = title;
        this.authors = new ArrayList<>();
        this.pages = pages;
        this.journal = journal;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void addAuthor(String author) {
        if (author != null && !authors.contains(author)) {
            authors.add(author);
        }
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public String getJournal() {
        return journal;
    }

    public void setJournal(String journal) {
        this.journal = journal;
    }

    @Override
    public String toString() {
        return "ResearchPaper{title='" + title + "', journal='" + journal + "'}";
    }
}