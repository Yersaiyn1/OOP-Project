package core.strategy;

import models.research.ResearchPaper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Sort papers by publish date — newest first.
 *
 * Demonstrates Comparator (the project requires explicit Comparator usage).
 */
public class SortByDateStrategy implements SortStrategy {

    private static final long serialVersionUID = 1L;

    @Override
    public List<ResearchPaper> sort(List<ResearchPaper> items) {
        if (items == null) return new ArrayList<>();
        List<ResearchPaper> copy = new ArrayList<>(items);
        copy.sort(new Comparator<ResearchPaper>() {
            @Override
            public int compare(ResearchPaper a, ResearchPaper b) {
                LocalDate da = a.getPublishDate();
                LocalDate db = b.getPublishDate();
                if (da == null && db == null) return 0;
                if (da == null) return 1;
                if (db == null) return -1;
                return db.compareTo(da); // newest first
            }
        });
        return copy;
    }
}