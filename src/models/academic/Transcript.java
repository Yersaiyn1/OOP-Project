package models.academic;

import models.users.Student;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Transcript implements Serializable {
    private Student student;
    private Map<Course, Mark> marks;
    private double gpa;

    public Transcript(Student student) {
        this.student = student;
        this.marks = new HashMap<>();
        this.gpa = 0.0;
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

    public void putMark(Course course, Mark mark) {
        if (course != null && mark != null) {
            this.marks.put(course, mark);
            calculateGPA();
        }
    }

    private void calculateGPA() {
        if (marks.isEmpty()) {
            this.gpa = 0.0;
            return;
        }

        double totalWeightedPoints = 0;
        int totalCredits = 0;

        for (Map.Entry<Course, Mark> entry : marks.entrySet()) {
            Course course = entry.getKey();
            Mark mark = entry.getValue();

            double totalMark = mark.calculateTotalMark();
            int credits = course.getCredits();

            double gpaPoints;
            if (totalMark >= 90) gpaPoints = 4.0;
            else if (totalMark >= 80) gpaPoints = 3.0;
            else if (totalMark >= 70) gpaPoints = 2.0;
            else if (totalMark >= 60) gpaPoints = 1.0;
            else gpaPoints = 0.0;

            totalWeightedPoints += gpaPoints * credits;
            totalCredits += credits;
        }

        this.gpa = (totalCredits == 0) ? 0.0 : totalWeightedPoints / totalCredits;
    }
}
