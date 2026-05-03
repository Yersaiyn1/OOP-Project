package core.strategy;

import models.research.ResearchPaper;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Sort papers by citation count — most cited first.
 */
public class SortByCitationsStrategy implements SortStrategy {

    private static final long serialVersionUID = 1L;

    @Override
    public List<ResearchPaper> sort(List<ResearchPaper> items) {
        if (items == null) return new ArrayList<>();
        List<ResearchPaper> copy = new ArrayList<>(items);
        Comparator<ResearchPaper> byCitations =
                (a, b) -> Integer.compare(b.getCitations(), a.getCitations());
        copy.sort(byCitations);
        return copy;
    }
}