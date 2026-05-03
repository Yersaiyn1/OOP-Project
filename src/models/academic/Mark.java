package models.academic;

import models.users.Student;
import java.io.Serializable;

public class Mark implements Serializable {
    private double firstAttestation;
    private double secondAttestation;
    private double finalExam;
    private Course course;
    private Student student;

    public Mark(Course course, Student student) {
        this.course = course;
        this.student = student;
        this.firstAttestation = 0.0;
        this.secondAttestation = 0.0;
        this.finalExam = 0.0;
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

    public void setFirstAttestation(double firstAttestation) {
        this.firstAttestation = firstAttestation;
    }

    public void setSecondAttestation(double secondAttestation) {
        this.secondAttestation = secondAttestation;
    }

    public void setFinalExam(double finalExam) {
        this.finalExam = finalExam;
    }

    public double calculateTotalMark() {
        return (firstAttestation * 0.3) + (secondAttestation * 0.3) + (finalExam * 0.4);
    }
}
