package models.exceptions;

/**
 * Thrown when a User who is NOT a Researcher (not wrapped by
 * ResearcherDecorator) tries to perform a researcher-only action,
 * e.g. joining a ResearchProject or publishing a paper.
 *
 * Referenced by the use-case diagram on the «extend» edge of
 * "Add Paper" / "Join Project".
 */
public class NotResearcherException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public NotResearcherException(String message) {
        super(message);
    }

    public NotResearcherException(String userName, String action) {
        super(String.format(
                "User '%s' is not a Researcher and cannot perform action: %s.",
                userName, action
        ));
    }
}