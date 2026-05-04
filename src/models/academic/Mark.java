package models.academic;

import models.users.Student;

import java.io.Serializable;

/**
 * Mark — a student's mark for a single course.
 *
 * Weighting per project rules: first*0.3 + second*0.3 + final*0.4
 * Letter grade follows the standard KBTU scale.
 */
public class Mark implements Serializable {

    private static final long serialVersionUID = 1L;

    private double firstAttestation;
    private double secondAttestation;
    private double finalExam;

    private Course course;
    private Student student;

    public Mark(Student student, Course course) {
        this.student = student;
        this.course = course;
        this.firstAttestation  = 0;
        this.secondAttestation = 0;
        this.finalExam         = 0;
    }

    public Mark(Student student, Course course,
                double firstAttestation, double secondAttestation, double finalExam) {
        this.student = student;
        this.course = course;
        this.firstAttestation = firstAttestation;
        this.secondAttestation = secondAttestation;
        this.finalExam = finalExam;
    }

    /**
     * Weighted total: 30% / 30% / 40%.
     */
    public double getTotal() {
        return firstAttestation * 0.3 + secondAttestation * 0.3 + finalExam * 0.4;
    }

    /**
     * Letter grade per KBTU-style scale.
     */
    public String getLetterGrade() {
        double t = getTotal();
        if (t >= 95) return "A";
        if (t >= 90) return "A-";
        if (t >= 85) return "B+";
        if (t >= 80) return "B";
        if (t >= 75) return "B-";
        if (t >= 70) return "C+";
        if (t >= 65) return "C";
        if (t >= 60) return "C-";
        if (t >= 55) return "D+";
        if (t >= 50) return "D";
        return "F";
    }

    /**
     * Passing threshold: total >= 50 AND final >= 50 (KBTU rule).
     */
    public boolean isPassing() {
        return getTotal() >= 50 && finalExam >= 50;
    }

    public double getFirstAttestation() {
        return firstAttestation;
    }

    public double getSecondAttestation() {
        return secondAttestation;
    }

    public double getFinalExam() {
        return finalExam;
    }

    public Course getCourse() {
        return course;
    }

    public Student getStudent() {
        return student;
    }

    public void setFirstAttestation(double v) {
        this.firstAttestation = v;
    }

    public void setSecondAttestation(double v) {
        this.secondAttestation = v;
    }

    public void setFinalExam(double v) {
        this.finalExam = v;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @Override
    public String toString() {
        return String.format("Mark{course=%s, total=%.1f, grade=%s}",
                course == null ? "?" : course.getName(),
                getTotal(),
                getLetterGrade());
    }
}