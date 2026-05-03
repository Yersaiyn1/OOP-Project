package controllers;

import core.AuthService;
import core.Logger;
import models.academic.Course;
import models.academic.Mark;
import models.users.Student;
import models.users.Teacher;
import models.users.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller for putting and viewing marks.
 */
public final class MarkController {

    private MarkController() {}

    /**
     * Teacher puts a mark for a student in a course.
     */
    public static boolean putMark(Student s, Course c,
                                  double first, double second, double finalExam) {
        User current = AuthService.getInstance().getCurrentUser();
        if (!(current instanceof Teacher)) {
            System.out.println("[mark] only teachers can put marks");
            return false;
        }
        if (s == null || c == null) return false;

        Teacher t = (Teacher) current;
        Mark m = new Mark(s, c, first, second, finalExam);
        t.putMark(s, c, m);
        Logger.getInstance().log(t, String.format(
                "put mark for %s in %s: total=%.1f, %s",
                s.getFullName(), c.getCourseId(), m.getTotal(), m.getLetterGrade()));
        return true;
    }

    /**
     * Student views their own marks.
     */
    public static List<Mark> viewMyMarks() {
        User current = AuthService.getInstance().getCurrentUser();
        if (!(current instanceof Student)) {
            System.out.println("[mark] login as a student to view marks");
            return new ArrayList<>();
        }
        return ((Student) current).viewMarks();
    }
}