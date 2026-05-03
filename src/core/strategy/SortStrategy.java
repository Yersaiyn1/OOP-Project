package core.strategy;

import models.research.ResearchPaper;

import java.io.Serializable;
import java.util.List;

/**
 * Strategy interface for sorting research papers.
 * Returns a NEW sorted list (does not mutate the input).
 */
public interface SortStrategy extends Serializable {

    List<ResearchPaper> sort(List<ResearchPaper> items);
}