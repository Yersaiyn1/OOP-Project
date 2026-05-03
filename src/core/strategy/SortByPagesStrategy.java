package core.strategy;

import models.research.ResearchPaper;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Sort papers by page count — longest first.
 */
public class SortByPagesStrategy implements SortStrategy {

    private static final long serialVersionUID = 1L;

    @Override
    public List<ResearchPaper> sort(List<ResearchPaper> items) {
        if (items == null) return new ArrayList<>();
        List<ResearchPaper> copy = new ArrayList<>(items);
        Comparator<ResearchPaper> byPages =
                (a, b) -> Integer.compare(b.getPages(), a.getPages());
        copy.sort(byPages);
        return copy;
    }
}