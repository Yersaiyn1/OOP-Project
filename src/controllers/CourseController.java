package controllers;

import core.AuthService;
import core.Logger;
import data.Database;
import models.academic.Course;
import models.enums.Major;
import models.enums.StudyYear;
import models.users.User;
import core.builder.CourseBuilder;

import java.util.List;

public class CourseController {

    private CourseController() {
    }

    public static Course createCourse(String name, int credits, Major targetMajor, StudyYear targetYear) {
        User currentUser = AuthService.getInstance().getCurrentUser();
        if (currentUser == null) {
            Logger.getInstance().log("Attempt to create course by unauthenticated user.");
            return null;
        }

        Course newCourse = new CourseBuilder()
                .withName(name)
                .withCredits(credits)
                .withTargetMajor(targetMajor)
                .withTargetYear(targetYear)
                .build();

        Database.getInstance().addCourse(newCourse);
        Logger.getInstance().log(currentUser.getId(), "Course '" + name + "' created.");
        return newCourse;
    }

    public static Course getCourseById(String courseId) {
        return Database.getInstance().getCourseById(courseId);
    }

    public static List<Course> getAllCourses() {
        return (List<Course>) Database.getInstance().getAllCourses();
    }

    public static boolean updateCourse(String courseId, String newName, int newCredits, Major newMajor, StudyYear newYear) {
        User currentUser = AuthService.getInstance().getCurrentUser();
        if (currentUser == null) {
            Logger.getInstance().log("Attempt to update course by unauthenticated user.");
            return false;
        }

        Course course = Database.getInstance().getCourseById(courseId);
        if (course == null) {
            Logger.getInstance().log(currentUser.getId(), "Attempt to update non-existent course with ID: " + courseId);
            return false;
        }

        course.setName(newName);
        course.setCredits(newCredits);
        course.setTargetMajor(newMajor);
        course.setTargetYear(newYear);
        Logger.getInstance().log(currentUser.getId(), "Course '" + course.getName() + "' (ID: " + courseId + ") updated.");
        return true;
    }

    public static boolean deleteCourse(String courseId) {
        User currentUser = AuthService.getInstance().getCurrentUser();
        if (currentUser == null) {
            Logger.getInstance().log("Attempt to delete course by unauthenticated user.");
            return false;
        }

        boolean deleted = Database.getInstance().removeCourse(courseId);
        if (deleted) {
            Logger.getInstance().log(currentUser.getId(), "Course with ID: " + courseId + " deleted.");
        } else {
            Logger.getInstance().log(currentUser.getId(), "Attempt to delete non-existent course with ID: " + courseId);
        }
        return deleted;
    }
}
