package models.exceptions;

/**
 * Thrown when a Student tries to register for courses
 * exceeding the 21-credit-per-semester limit.
 */
public class CreditLimitExceededException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public CreditLimitExceededException(String message) {
        super(message);
    }

    public CreditLimitExceededException(int currentCredits, int attemptedCredits, int limit) {
        super(String.format(
                "Cannot register: current credits = %d, attempted to add %d, limit = %d.",
                currentCredits, attemptedCredits, limit
        ));
    }
}