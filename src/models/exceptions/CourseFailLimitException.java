package models.exceptions;

/**
 * Thrown when a Student exceeds the maximum allowed number of failed
 * courses (3) and cannot register for new courses.
 */
public class CourseFailLimitException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public CourseFailLimitException(String message) {
        super(message);
    }

    public CourseFailLimitException(String studentName, int fails, int limit) {
        super(String.format(
                "Student '%s' has %d failed courses, exceeding the limit of %d.",
                studentName, fails, limit
        ));
    }
}