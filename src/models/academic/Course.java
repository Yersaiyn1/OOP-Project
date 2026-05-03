package models.academic;

import models.enums.Major;
import models.enums.StudyYear;
import models.users.Student;
import models.users.Teacher;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Course implements Serializable {
    private String courseId;
    private String name;
    private int credits;
    private Major targetMajor;
    private StudyYear targetYear;
    private List<Teacher> instructors;
    private List<Student> enrolledStudents;
    private List<Lesson> lessons;

    public Course(String name, int credits, Major targetMajor, StudyYear targetYear) {
        this.courseId = UUID.randomUUID().toString();
        this.name = name;
        this.credits = credits;
        this.targetMajor = targetMajor;
        this.targetYear = targetYear;
        this.instructors = new ArrayList<>();
        this.enrolledStudents = new ArrayList<>();
        this.lessons = new ArrayList<>();
    }

    public String getCourseId() {
        return courseId;
    }

    public String getName() {
        return name;
    }

    public int getCredits() {
        return credits;
    }

    public Major getTargetMajor() {
        return targetMajor;
    }

    public StudyYear getTargetYear() {
        return targetYear;
    }

    public List<Teacher> getInstructors() {
        return instructors;
    }

    public List<Student> getEnrolledStudents() {
        return enrolledStudents;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public void setTargetMajor(Major targetMajor) {
        this.targetMajor = targetMajor;
    }

    public void setTargetYear(StudyYear targetYear) {
        this.targetYear = targetYear;
    }

    public void addInstructor(Teacher teacher) {
        if (teacher != null && !instructors.contains(teacher)) {
            instructors.add(teacher);
        }
    }

    public void removeInstructor(Teacher teacher) {
        instructors.remove(teacher);
    }

    public void enrollStudent(Student student) {
        if (student != null && !enrolledStudents.contains(student)) {
            enrolledStudents.add(student);
        }
    }

    public void removeStudent(Student student) {
        enrolledStudents.remove(student);
    }

    public void addLesson(Lesson lesson) {
        if (lesson != null && !lessons.contains(lesson)) {
            lessons.add(lesson);
        }
    }

    public void removeLesson(Lesson lesson) {
        lessons.remove(lesson);
    }
}
