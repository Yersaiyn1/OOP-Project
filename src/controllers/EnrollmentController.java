package controllers;

import core.AuthService;
import core.Logger;
import models.academic.Course;
import models.exceptions.CourseFailLimitException;
import models.exceptions.CreditLimitExceededException;
import models.exceptions.LowHIndexException;
import models.research.Researcher;
import models.users.Student;
import models.users.User;

/**
 * Controller for course registration and supervisor assignment.
 */
public final class EnrollmentController {

    private EnrollmentController() {}

    /**
     * Register the currently logged-in student for a course.
     * Catches business-rule violations and reports them on the console.
     *
     * @return true on success, false on rejection
     */
    public static boolean registerForCourse(Course c) {
        User current = AuthService.getInstance().getCurrentUser();
        if (!(current instanceof Student)) {
            System.out.println("[enrollment] only students can register for courses");
            return false;
        }
        Student s = (Student) current;
        try {
            s.registerForCourse(c);
            Logger.getInstance().log(s, "registered for course " + c.getCourseId());
            return true;
        } catch (CreditLimitExceededException e) {
            System.out.println("[enrollment] " + e.getMessage());
        } catch (CourseFailLimitException e) {
            System.out.println("[enrollment] " + e.getMessage());
        }
        return false;
    }

    /**
     * Assign a supervisor to the currently logged-in student.
     */
    public static boolean assignSupervisor(Researcher r) {
        User current = AuthService.getInstance().getCurrentUser();
        if (!(current instanceof Student)) {
            System.out.println("[enrollment] only students can be assigned a supervisor");
            return false;
        }
        Student s = (Student) current;
        try {
            s.assignSupervisor(r);
            Logger.getInstance().log(s, "assigned supervisor (h-index " + r.getHIndex() + ")");
            return true;
        } catch (LowHIndexException e) {
            System.out.println("[enrollment] " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("[enrollment] " + e.getMessage());
        }
        return false;
    }
}