package controllers;

import core.AuthService;
import core.Logger;
import data.Database;
import models.academic.Course;
import models.users.Manager;
import models.users.Teacher;
import models.users.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Controller for course catalog operations.
 */
public final class CourseController {

    private CourseController() {}

    /**
     * List every course in the catalog.
     */
    public static Collection<Course> listAll() {
        if (!AuthService.getInstance().isLoggedIn()) {
            System.out.println("[course] login required");
            return List.of();
        }
        List<Course> out = new ArrayList<>();
        for (Object v : Database.getInstance().getCourses().values()) {
            if (v instanceof Course) out.add((Course) v);
        }
        return out;
    }

    /**
     * Find a course by id.
     */
    public static Course findById(String id) {
        Object v = Database.getInstance().getCourses().get(id);
        return (v instanceof Course) ? (Course) v : null;
    }

    /**
     * Add a course to the catalog. Managers only.
     */
    public static boolean addCourse(Course c) {
        User current = AuthService.getInstance().getCurrentUser();
        if (!(current instanceof Manager)) {
            System.out.println("[course] manager privileges required");
            return false;
        }
        if (c == null) return false;
        Database.getInstance().getCourses().put(c.getCourseId(), c);
        Logger.getInstance().log(current, "added course " + c.getCourseId());
        return true;
    }

    /**
     * Assign a teacher to a course. Managers only.
     */
    public static boolean assignTeacher(Teacher t, Course c) {
        User current = AuthService.getInstance().getCurrentUser();
        if (!(current instanceof Manager)) {
            System.out.println("[course] manager privileges required");
            return false;
        }
        if (t == null || c == null) return false;
        ((Manager) current).assignCourse(t, c);
        Logger.getInstance().log(current,
                "assigned " + t.getFullName() + " to " + c.getCourseId());
        return true;
    }
}