package models.academic;

import models.enums.Major;
import models.enums.StudyYear;
import models.users.Student;
import models.users.Teacher;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Course — a single academic offering.
 *
 * Note: a course can have MULTIPLE instructors (per project requirements:
 * "List<Teacher> instructors, NOT a single teacher").
 */
public class Course implements Serializable {

    private static final long serialVersionUID = 1L;

    private String courseId;
    private String name;
    private int credits;
    private Major targetMajor;
    private StudyYear targetYear;

    private final List<Teacher> instructors = new ArrayList<>();
    private final List<Student> enrolledStudents = new ArrayList<>();
    private final List<Lesson> lessons = new ArrayList<>();

    public Course(String courseId, String name, int credits,
                  Major targetMajor, StudyYear targetYear) {
        this.courseId = courseId;
        this.name = name;
        this.credits = credits;
        this.targetMajor = targetMajor;
        this.targetYear = targetYear;
    }

    /**
     * Add an instructor (idempotent).
     */
    public void addInstructor(Teacher t) {
        if (t == null) return;
        if (!instructors.contains(t)) {
            instructors.add(t);
        }
    }

    /**
     * Enroll a student (idempotent). The reciprocal link (student.enrolledCourses)
     * is the responsibility of Student.registerForCourse().
     */
    public void enrollStudent(Student s) {
        if (s == null) return;
        if (!enrolledStudents.contains(s)) {
            enrolledStudents.add(s);
        }
    }

    public void addLesson(Lesson lesson) {
        if (lesson == null) return;
        lessons.add(lesson);
    }

    public String getCourseId()              { return courseId; }
    public String getName()                  { return name; }
    public int getCredits()                  { return credits; }
    public Major getTargetMajor()            { return targetMajor; }
    public StudyYear getTargetYear()         { return targetYear; }
    public List<Teacher> getInstructors()    { return instructors; }
    public List<Student> getEnrolledStudents() { return enrolledStudents; }
    public List<Lesson> getLessons()         { return lessons; }

    public void setName(String name)                 { this.name = name; }
    public void setCredits(int credits)              { this.credits = credits; }
    public void setTargetMajor(Major targetMajor)    { this.targetMajor = targetMajor; }
    public void setTargetYear(StudyYear targetYear)  { this.targetYear = targetYear; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course)) return false;
        return Objects.equals(courseId, ((Course) o).courseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseId);
    }

    @Override
    public String toString() {
        return String.format("Course{%s, '%s', %d cr, %s/%s}",
                courseId, name, credits, targetMajor, targetYear);
    }
}