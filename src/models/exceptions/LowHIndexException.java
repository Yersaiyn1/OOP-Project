package models.exceptions;

/**
 * Thrown by Student.assignSupervisor() when the candidate Researcher
 * has an h-index below the required threshold (3).
 *
 * Referenced by the use-case diagram on the «extend» edge of
 * "Assign Supervisor".
 */
public class LowHIndexException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public LowHIndexException(String message) {
        super(message);
    }

    public LowHIndexException(String researcherName, int hIndex, int requiredMin) {
        super(String.format(
                "Researcher '%s' has h-index %d, below the required minimum of %d for supervision.",
                researcherName, hIndex, requiredMin
        ));
    }
}