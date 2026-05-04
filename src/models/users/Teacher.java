package models.users;

import core.interfaces.Reportable;
import core.strategy.ReportStrategy;
import core.strategy.TeacherReportStrategy;
import models.academic.Course;
import models.academic.Lesson;
import models.academic.Mark;
import models.academic.RecommendationLetter;
import models.academic.Report;
import models.enums.TeacherTitle;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Teacher — instructor; can put marks, take attendance, write recommendations.
 *
 * If the title is PROFESSOR, TeacherFactory auto-wraps the Teacher into
 * a TeacherResearcher (Decorator pattern).
 */
public class Teacher extends Employee implements Reportable {

    private static final long serialVersionUID = 1L;

    private TeacherTitle title;
    private final List<Course> coursesTaught = new ArrayList<>();
    private double rating;            // 0..5 running average
    private int ratingCount;          // number of ratings received

    public Teacher(String id, String firstName, String lastName,
                   String email, String password, String phone,
                   double salary, LocalDate hireDate, String department,
                   TeacherTitle title) {
        super(id, firstName, lastName, email, password, phone,
                salary, hireDate, department);
        this.title = title;
        this.rating = 0;
        this.ratingCount = 0;
    }

    @Override
    public String getRole() {
        return "Teacher";
    }

    public void putMark(Student s, Course c, Mark m) {
        if (s == null || c == null || m == null) return;
        m.setStudent(s);
        m.setCourse(c);
        s.getTranscript().addMark(c, m);
        if (!m.isPassing()) {
            s.incrementFailedCourses();
        }
    }

    public List<Student> viewStudents(Course c) {
        if (c == null) return new ArrayList<>();
        return c.getEnrolledStudents();
    }

    /**
     * Add a course to this teacher's load (and register the teacher with
     * the course as one of its instructors).
     */
    public void manageCourse(Course c) {
        if (c == null) return;
        if (!coursesTaught.contains(c)) {
            coursesTaught.add(c);
        }
        c.addInstructor(this);
    }

    public boolean isProfessor() {
        return title == TeacherTitle.PROFESSOR;
    }

    public void takeAttendance(Course c) {
        if (c == null || c.getLessons().isEmpty()) return;
        Lesson latest = c.getLessons().get(c.getLessons().size() - 1);
        for (Student s : c.getEnrolledStudents()) {
            latest.markAttendance(s, true);
        }
    }

    public Report generateMarkReport(Course c) {
        ReportStrategy s = new TeacherReportStrategy();
        Map<String, Object> data = new HashMap<>();
        data.put("teacher", this);
        data.put("course", c);
        return s.build(data);
    }

    public RecommendationLetter writeRecommendation(Student s, String content, String purpose) {
        if (s == null) return null;
        RecommendationLetter letter = new RecommendationLetter(this, s, content, purpose);
        s.addRecommendation(letter);
        return letter;
    }

    /**
     * Receive a rating from a Student (0..5). Updates the running average.
     * Package-private convention: only Student.rateTeacher() should call this.
     */
    public void receiveRating(double newRating) {
        if (newRating < 0 || newRating > 5) return;
        double total = rating * ratingCount + newRating;
        ratingCount++;
        rating = total / ratingCount;
    }

    @Override
    public Report generateReport() {
        Report r = new Report("Teacher Report — " + getFullName());
        r.put("teacher", getFullName());
        r.put("title", title);
        r.put("courses", coursesTaught.size());
        r.put("rating", String.format("%.2f / 5.0", rating));
        return r;
    }

    public void setTitle(TeacherTitle title) {
        this.title = title;
    }

    public TeacherTitle getTitle() {
        return title;
    }

    public List<Course> getCoursesTaught() {
        return coursesTaught;
    }

    public double getRating() {
        return rating;
    }

    public int getRatingCount() {
        return ratingCount;
    }


}