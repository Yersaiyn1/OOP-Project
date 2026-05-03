package models.academic;

import models.users.Student;
import java.io.Serializable;
import java.time.LocalDate;

public class AttendanceRecord implements Serializable {
    private static final long serialVersionUID = 1L;

    private Student student;
    private Course course;
    private LocalDate date;
    private boolean isPresent;

    public AttendanceRecord(Student student, Course course, LocalDate date, boolean isPresent) {
        this.student = student;
        this.course = course;
        this.date = date;
        this.isPresent = isPresent;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public boolean isPresent() {
        return isPresent;
    }

    public void setPresent(boolean present) {
        isPresent = present;
    }
}