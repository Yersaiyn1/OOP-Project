package models.users;

import models.academic.Course;
import models.academic.RecommendationLetter;
import models.academic.Transcript;
import models.enums.Major;
import models.enums.StudyYear;
import models.research.Researcher;

import java.util.ArrayList;
import java.util.List;

public class Student extends User {
    private String studentId;
    private Major major;
    private StudyYear year;
    private double gpa;
    private int failedCoursesCount;
    private List<Course> enrolledCourses;
    private Transcript transcript;
    private Researcher supervisor;
    private List<RecommendationLetter> recommendations; // BONUS

    public Student(String studentId) {
        this.studentId = studentId;
        this.enrolledCourses = new ArrayList<>();
        this.transcript = new Transcript();
        this.recommendations = new ArrayList<>();
    }

    // Read-only
    public String getStudentId() { return studentId; }
    public double getGpa() { return gpa; }
    public int getFailedCoursesCount() { return failedCoursesCount; }

    // Standard
    public Major getMajor() { return major; }
    public void setMajor(Major major) { this.major = major; }

    public StudyYear getYear() { return year; }
    public void setYear(StudyYear year) { this.year = year; }

    public List<Course> getEnrolledCourses() { return enrolledCourses; }
    // No setter — managed via registerForCourse()

    public Transcript getTranscript() { return transcript; }
    // No public setter — created in constructor

    public Researcher getSupervisor() { return supervisor; }
    // No setter — use assignSupervisor() (validates h-index)

    public List<RecommendationLetter> getRecommendations() {
        return recommendations;
    } // BONUS
}
