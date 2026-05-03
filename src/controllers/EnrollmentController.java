package controllers;

import core.AuthService;
import core.Logger;
import data.Database;
import models.academic.Course;
import models.users.Student;
import models.users.User;
import models.exceptions.CreditLimitExceededException;

import java.util.List;
import java.util.ArrayList;

public class EnrollmentController {

    private EnrollmentController() {
    }

    public static boolean enrollStudentInCourse(String studentId, String courseId) {
        User currentUser = AuthService.getInstance().getCurrentUser();
        if (currentUser == null) {
            Logger.getInstance().log(null, "Attempt to enroll student by unauthenticated user.");
            return false;
        }

        Student student = (Student) Database.getInstance().getUserById(studentId);
        Course course = Database.getInstance().getCourseById(courseId);

        if (student == null) {
            Logger.getInstance().log(currentUser, "Enrollment failed: Student with ID " + studentId + " not found.");
            return false;
        }
        if (course == null) {
            Logger.getInstance().log(currentUser, "Enrollment failed: Course with ID " + courseId + " not found.");
            return false;
        }

        if (student.getEnrolledCourses().contains(course)) {
            Logger.getInstance().log(currentUser, "Enrollment failed: Student " + student.getId() + " already enrolled in course " + course.getCourseId());
            return false;
        }

        try {
            student.registerForCourse(course);
            course.enrollStudent(student);
            Logger.getInstance().log(currentUser, "Student " + student.getId() + " enrolled in course " + course.getCourseId());
            return true;
        } catch (CreditLimitExceededException e) {
            Logger.getInstance().log(currentUser, "Enrollment failed for student " + student.getId() + " in course " + course.getCourseId() + ": " + e.getMessage());
            return false;
        } catch (Exception e) {
            Logger.getInstance().log(currentUser, "An unexpected error occurred during enrollment: " + e.getMessage());
            return false;
        }
    }

    public static boolean dropStudentFromCourse(String studentId, String courseId) {
        User currentUser = AuthService.getInstance().getCurrentUser();
        if (currentUser == null) {
            Logger.getInstance().log(null, "Attempt to drop student from course by unauthenticated user.");
            return false;
        }

        Student student = (Student) Database.getInstance().getUserById(studentId);
        Course course = Database.getInstance().getCourseById(courseId);

        if (student == null || course == null) {
            Logger.getInstance().log(currentUser, "Drop failed: Student or Course not found (Student ID: " + studentId + ", Course ID: " + courseId + ").");
            return false;
        }

        if (!student.getEnrolledCourses().contains(course)) {
            Logger.getInstance().log(currentUser, "Drop failed: Student " + student.getId() + " not enrolled in course " + course.getCourseId());
            return false;
        }

        student.getEnrolledCourses().remove(course);
        course.getEnrolledStudents().remove(student);
        Logger.getInstance().log(currentUser, "Student " + student.getId() + " dropped from course " + course.getCourseId());
        return true;
    }

    public static List<Course> getStudentEnrollments(String studentId) {
        User currentUser = AuthService.getInstance().getCurrentUser();
        if (currentUser == null) {
            Logger.getInstance().log(null, "Attempt to get enrollments by unauthenticated user.");
            return null;
        }

        Student student = (Student) Database.getInstance().getUserById(studentId);
        if (student == null) {
            Logger.getInstance().log(currentUser, "Failed to get enrollments: Student with ID " + studentId + " not found.");
            return null;
        }
        return student.getEnrolledCourses();
    }
}
