package models.users;

import models.academic.Course;
import models.academic.RecommendationLetter;
import models.academic.Transcript;
import models.enums.Major;
import models.enums.StudyYear;
import models.research.Researcher;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Student extends User implements Serializable {
    private String studentId;
    private Major major;
    private StudyYear year;
    private List<Course> enrolledCourses;
    private Transcript transcript;
    private Researcher supervisor;
    private List<RecommendationLetter> recommendations;
    private double gpa;
    private int failedCoursesCount;

    public Student(String firstName, String lastName, String email, String phone, String password, Major major, StudyYear year) {
        super(UUID.randomUUID().toString(), firstName, lastName, email, phone, password, LocalDateTime.now());
        this.studentId = "S" + UUID.randomUUID().toString().substring(0, 7).toUpperCase();
        this.major = major;
        this.year = year;
        this.enrolledCourses = new ArrayList<>();
        this.transcript = new Transcript(this);
        this.recommendations = new ArrayList<>();
        this.gpa = 0.0;
        this.failedCoursesCount = 0;
    }

    public String getStudentId() {
        return studentId;
    }

    public double getGpa() {
        return transcript.getGpa();
    }

    public int getFailedCoursesCount() {
        return failedCoursesCount;
    }

    public Major getMajor() {
        return major;
    }

    public StudyYear getYear() {
        return year;
    }

    public List<Course> getEnrolledCourses() {
        return enrolledCourses;
    }

    public Transcript getTranscript() {
        return transcript;
    }

    public Researcher getSupervisor() {
        return supervisor;
    }

    public List<RecommendationLetter> getRecommendations() {
        return recommendations;
    }

    public void setMajor(Major major) {
        this.major = major;
    }

    public void setYear(StudyYear year) {
        this.year = year;
    }

    public void registerForCourse(Course course) {
        if (course != null && !enrolledCourses.contains(course)) {
            enrolledCourses.add(course);
        }
    }

    public void assignSupervisor(Researcher supervisor) {
        this.supervisor = supervisor;
    }

    public void incrementFailedCoursesCount() {
        this.failedCoursesCount++;
    }

    public void addRecommendation(RecommendationLetter letter) {
        if (letter != null) {
            this.recommendations.add(letter);
        }
    }
}
