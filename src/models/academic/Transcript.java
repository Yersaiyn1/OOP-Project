package models.academic;

import core.interfaces.Reportable;
import core.strategy.AcademicReportStrategy;
import core.strategy.ReportStrategy;
import models.users.Student;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Transcript — a student's official academic record.
 *
 * Stores marks keyed by Course. Implements Reportable so it can be
 * passed to AcademicReportStrategy.
 */
public class Transcript implements Reportable, Serializable {

    private static final long serialVersionUID = 1L;

    private Student student;
    private final Map<Course, Mark> marks = new HashMap<>();
    private double gpa;

    public Transcript(Student student) {
        this.student = student;
        this.gpa = 0.0;
    }

    public void addMark(Course c, Mark m) {
        if (c == null || m == null) return;
        marks.put(c, m);
        calculateGPA();
    }

    /**
     * GPA = sum(letter_gpa * credits) / sum(credits)
     * Letter -> GPA mapping per KBTU 4.0 scale.
     */
    public double calculateGPA() {
        double weighted = 0;
        int totalCredits = 0;
        for (Map.Entry<Course, Mark> e : marks.entrySet()) {
            int credits = e.getKey().getCredits();
            double points = letterToGpa(e.getValue().getLetterGrade());
            weighted += points * credits;
            totalCredits += credits;
        }
        this.gpa = (totalCredits == 0) ? 0.0 : weighted / totalCredits;
        return this.gpa;
    }

    @Override
    public Report generateReport() {
        ReportStrategy strategy = new AcademicReportStrategy();
        Map<String, Object> data = new HashMap<>();
        data.put("student", student);
        data.put("transcript", this);
        return strategy.build(data);
    }

    private static double letterToGpa(String letter) {
        switch (letter) {
            case "A":  return 4.00;
            case "A-": return 3.67;
            case "B+": return 3.33;
            case "B":  return 3.00;
            case "B-": return 2.67;
            case "C+": return 2.33;
            case "C":  return 2.00;
            case "C-": return 1.67;
            case "D+": return 1.33;
            case "D":  return 1.00;
            default:   return 0.00; // F
        }
    }

    public Student getStudent() {
        return student;
    }

    public Map<Course, Mark> getMarks() {
        return marks;
    }

    public double getGpa() {
        return gpa;
    }

    public void setStudent(Student s) {
        this.student = s;
    }

    @Override
    public String toString() {
        String name = (student == null) ? "?" : student.getFullName();
        return String.format("Transcript{%s, %d courses, GPA=%.2f}",
                name, marks.size(), gpa);
    }
}