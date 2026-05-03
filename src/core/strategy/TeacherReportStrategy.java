package core.strategy;

import models.academic.Course;
import models.academic.Report;
import models.users.Student;
import models.users.Teacher;

import java.util.Map;

/**
 * TeacherReportStrategy — BONUS strategy.
 *
 * Builds a "mark report" for a course taught by a Teacher: lists every
 * enrolled student with their letter grade + total mark for the course.
 *
 * Expected keys:
 *   "teacher" -> Teacher
 *   "course"  -> Course
 */
public class TeacherReportStrategy implements ReportStrategy {

    private static final long serialVersionUID = 1L;

    @Override
    public Report build(Map<String, Object> data) {
        Teacher t = (Teacher) data.get("teacher");
        Course c  = (Course) data.get("course");

        String title = (c == null)
                ? "Teacher Report"
                : "Teacher Report — " + c.getName();
        Report report = new Report(title);

        if (t != null) {
            report.put("teacher", t.getFullName());
            report.put("title",   t.getTitle());
        }
        if (c != null) {
            report.put("course",      c.getName());
            report.put("courseId",    c.getCourseId());
            report.put("studentCount", c.getEnrolledStudents().size());

            int i = 1;
            for (Student s : c.getEnrolledStudents()) {
                if (s.getTranscript().getMarks().containsKey(c)) {
                    var mark = s.getTranscript().getMarks().get(c);
                    report.put("row_" + i, String.format("%s — %s (total %.1f)",
                            s.getFullName(),
                            mark.getLetterGrade(),
                            mark.getTotal()));
                } else {
                    report.put("row_" + i, s.getFullName() + " — (no mark yet)");
                }
                i++;
            }
        }
        return report;
    }
}