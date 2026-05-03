package core.strategy;

import models.research.ResearchPaper;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SortByDateStrategy implements SortStrategy<ResearchPaper> {
    @Override
    public void sort(List<ResearchPaper> papers) {
        if (papers != null) {
            Collections.sort(papers, Comparator.comparing(ResearchPaper::getPublishDate));
        }
    }
}
