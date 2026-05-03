package core.strategy;

import models.academic.Course;
import models.academic.Mark;
import models.academic.Report;
import models.academic.Transcript;
import models.users.Student;

import java.util.Map;

/**
 * AcademicReportStrategy — builds a report with the student's
 * transcript: courses, marks, total credits and GPA.
 *
 * Expected keys in data:
 *   "student"    -> Student
 *   "transcript" -> Transcript
 */
public class AcademicReportStrategy implements ReportStrategy {

    private static final long serialVersionUID = 1L;

    @Override
    public Report build(Map<String, Object> data) {
        Student s = (Student) data.get("student");
        Transcript t = (Transcript) data.get("transcript");

        String title = (s == null)
                ? "Academic Report"
                : "Academic Report — " + s.getFullName();
        Report r = new Report(title);

        if (s != null) {
            r.put("student",  s.getFullName());
            r.put("studentId", s.getStudentId());
            r.put("major",    s.getMajor());
            r.put("year",     s.getYear());
        }
        if (t != null) {
            r.put("courses", t.getMarks().size());
            r.put("gpa",     String.format("%.2f", t.calculateGPA()));
            int i = 1;
            for (Map.Entry<Course, Mark> e : t.getMarks().entrySet()) {
                r.put("course_" + i, String.format("%s — %s (total %.1f)",
                        e.getKey().getName(),
                        e.getValue().getLetterGrade(),
                        e.getValue().getTotal()));
                i++;
            }
        }
        return r;
    }
}