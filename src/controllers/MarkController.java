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

    public static boolean putAtt1(Student s, Course c, double score) {
        return updateComponent(s, c, 1, score);
    }

    public static boolean putAtt2(Student s, Course c, double score) {
        return updateComponent(s, c, 2, score);
    }

    public static boolean putFinal(Student s, Course c, double score) {
        return updateComponent(s, c, 3, score);
    }

    private static boolean updateComponent(Student s, Course c, int comp, double score) {
        User current = AuthService.getInstance().getCurrentUser();
        if (!(current instanceof Teacher)) {
            System.out.println("[mark] only teachers can put marks");
            return false;
        }
        if (s == null || c == null) return false;
        Teacher t = (Teacher) current;

        Mark m = s.getTranscript().getMarks().get(c);
        if (m == null) {
            m = new Mark(s, c);
            s.getTranscript().addMark(c, m);
        }

        switch (comp) {
            case 1: m.setFirstAttestation(score);  break;
            case 2: m.setSecondAttestation(score); break;
            case 3: m.setFinalExam(score);         break;
        }
        s.getTranscript().calculateGPA();

        String label = comp == 1 ? "att1" : comp == 2 ? "att2" : "final";
        Logger.getInstance().log(t, String.format(
                "set %s for %s in %s: %.1f (total=%.1f %s)",
                label, s.getFullName(), c.getCourseId(),
                score, m.getTotal(), m.getLetterGrade()));
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