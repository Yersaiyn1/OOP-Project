package models.academic;

import models.users.Student;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * AttendanceRecord — BONUS feature.
 *
 * One immutable record per Lesson.markAttendance() call.
 * Used for generating attendance reports per course/student.
 */
public class AttendanceRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String recordId;
    private final Student student;
    private final Lesson lesson;
    private final boolean present;
    private final LocalDateTime timestamp;

    public AttendanceRecord(Student student, Lesson lesson, boolean present) {
        this.recordId = "ATT-" + UUID.randomUUID().toString().substring(0, 8);
        this.student = student;
        this.lesson = lesson;
        this.present = present;
        this.timestamp = LocalDateTime.now();
    }

    public String getRecordId()           { return recordId; }
    public Student getStudent()           { return student; }
    public Lesson getLesson()             { return lesson; }
    public boolean isPresent()            { return present; }
    public LocalDateTime getTimestamp()   { return timestamp; }

    @Override
    public String toString() {
        String studentName = (student == null) ? "?" : student.getFullName();
        return String.format("Attendance{%s, %s, %s, present=%s}",
                recordId, studentName, lesson == null ? "?" : lesson.getLessonId(), present);
    }
}