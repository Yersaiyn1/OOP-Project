package controllers;

import core.AuthService;
import core.Logger;
import data.Database;
import models.academic.Course;
import models.academic.Mark;
import models.users.Student;
import models.users.Teacher;
import models.users.User;

import java.util.List;
import java.util.stream.Collectors;

public class MarkController {

    private MarkController() {
    }

    public static Mark addMark(String studentId, String courseId, double firstAttestation, double secondAttestation, double finalExam) {
        User currentUser = AuthService.getInstance().getCurrentUser();
        if (currentUser == null) {
            Logger.getInstance().log(null, "Attempt to add mark by unauthenticated user.");
            return null;
        }
        if (!(currentUser instanceof Teacher)) {
            Logger.getInstance().log(currentUser, "Unauthorized attempt to add mark by user " + currentUser.getId());
            return null;
        }

        Student student = (Student) Database.getInstance().getUserById(studentId);
        Course course = Database.getInstance().getCourseById(courseId);

        if (student == null) {
            Logger.getInstance().log(currentUser, "Failed to add mark: Student with ID " + studentId + " not found.");
            return null;
        }
        if (course == null) {
            Logger.getInstance().log(currentUser, "Failed to add mark: Course with ID " + courseId + " not found.");
            return null;
        }

        if (!student.getEnrolledCourses().contains(course)) {
            Logger.getInstance().log(currentUser, "Failed to add mark: Student " + studentId + " is not enrolled in course " + courseId);
            return null;
        }

        Mark mark = new Mark(course, student);
        mark.setFirstAttestation(firstAttestation);
        mark.setSecondAttestation(secondAttestation);
        mark.setFinalExam(finalExam);

        student.getTranscript().putMark(course, mark);
        Logger.getInstance().log(currentUser, "Mark added for student " + studentId + " in course " + courseId);
        return mark;
    }

    public static boolean updateMark(String studentId, String courseId, double firstAttestation, double secondAttestation, double finalExam) {
        User currentUser = AuthService.getInstance().getCurrentUser();
        if (currentUser == null) {
            Logger.getInstance().log(null, "Attempt to update mark by unauthenticated user.");
            return false;
        }
        if (!(currentUser instanceof Teacher)) {
            Logger.getInstance().log(currentUser, "Unauthorized attempt to update mark by user " + currentUser.getId());
            return false;
        }

        Student student = (Student) Database.getInstance().getUserById(studentId);
        Course course = Database.getInstance().getCourseById(courseId);

        if (student == null || course == null) {
            Logger.getInstance().log(currentUser, "Failed to update mark: Student or Course not found (Student ID: " + studentId + ", Course ID: " + courseId + ").");
            return false;
        }

        Mark mark = student.getTranscript().getMarks().get(course);
        if (mark == null) {
            Logger.getInstance().log(currentUser, "Failed to update mark: No existing mark for student " + studentId + " in course " + courseId);
            return false;
        }

        mark.setFirstAttestation(firstAttestation);
        mark.setSecondAttestation(secondAttestation);
        mark.setFinalExam(finalExam);
        student.getTranscript().putMark(course, mark);
        Logger.getInstance().log(currentUser, "Mark updated for student " + studentId + " in course " + courseId);
        return true;
    }

    public static Mark getMarkForStudentInCourse(String studentId, String courseId) {
        User currentUser = AuthService.getInstance().getCurrentUser();
        if (currentUser == null) {
            Logger.getInstance().log(null, "Attempt to get mark by unauthenticated user.");
            return null;
        }

        Student student = (Student) Database.getInstance().getUserById(studentId);
        Course course = Database.getInstance().getCourseById(courseId);

        if (student == null || course == null) {
            Logger.getInstance().log(currentUser, "Failed to get mark: Student or Course not found (Student ID: " + studentId + ", Course ID: " + courseId + ").");
            return null;
        }

        return student.getTranscript().getMarks().get(course);
    }

    public static List<Mark> getAllMarksForStudent(String studentId) {
        User currentUser = AuthService.getInstance().getCurrentUser();
        if (currentUser == null) {
            Logger.getInstance().log(null, "Attempt to get all marks for student by unauthenticated user.");
            return null;
        }

        Student student = (Student) Database.getInstance().getUserById(studentId);
        if (student == null) {
            Logger.getInstance().log(currentUser, "Failed to get all marks: Student with ID " + studentId + " not found.");
            return null;
        }
        return new ArrayList<>(student.getTranscript().getMarks().values());
    }
}
