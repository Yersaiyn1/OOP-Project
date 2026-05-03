package core.strategy;

import models.research.ResearchPaper;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SortByCitationsStrategy implements SortStrategy<ResearchPaper> {
    @Override
    public void sort(List<ResearchPaper> papers) {
        if (papers != null) {
            Collections.sort(papers, Comparator.comparingInt(ResearchPaper::getCitations).reversed());
        }
    }
}
