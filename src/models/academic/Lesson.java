package models.academic;

import models.enums.LessonType;
import models.users.Student;
import models.users.Teacher;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Lesson — a single class session within a Course.
 *
 * Holds an attendance map: Student -> Boolean (present?).
 * markAttendance() updates that map and (BONUS) generates an
 * AttendanceRecord for audit purposes.
 */
public class Lesson implements Serializable {

    private static final long serialVersionUID = 1L;

    private String lessonId;
    private LessonType type;
    private LocalDateTime date;
    private Course course;
    private Teacher instructor;
    private final Map<Student, Boolean> attendance = new HashMap<>();

    public Lesson(String lessonId, LessonType type, LocalDateTime date,
                  Course course, Teacher instructor) {
        this.lessonId = lessonId;
        this.type = type;
        this.date = date;
        this.course = course;
        this.instructor = instructor;
    }

    /**
     * Mark a student's attendance for this lesson.
     * Returns the AttendanceRecord that was generated (BONUS).
     */
    public AttendanceRecord markAttendance(Student student, boolean present) {
        if (student == null) return null;
        attendance.put(student, present);
        return new AttendanceRecord(student, this, present);
    }

    public boolean isPresent(Student s) {
        return attendance.getOrDefault(s, false);
    }

    public String getLessonId()                  { return lessonId; }
    public LessonType getType()                  { return type; }
    public LocalDateTime getDate()               { return date; }
    public Course getCourse()                    { return course; }
    public Teacher getInstructor()               { return instructor; }
    public Map<Student, Boolean> getAttendance() { return attendance; }

    public void setType(LessonType type)              { this.type = type; }
    public void setDate(LocalDateTime date)           { this.date = date; }
    public void setCourse(Course course)              { this.course = course; }
    public void setInstructor(Teacher instructor)     { this.instructor = instructor; }

    @Override
    public String toString() {
        return String.format("Lesson{%s, %s, %s, course=%s}",
                lessonId, type, date,
                course == null ? "?" : course.getName());
    }
}