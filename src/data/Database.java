package data;

import core.Logger;
import models.academic.Course;
import models.users.User;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Database implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final String DB_FILE = "db.dat";

    private final Map<String, User>   users    = new HashMap<>();
    private final Map<String, Object> courses  = new HashMap<>();
    private final Map<String, Object> papers   = new HashMap<>();
    private final Map<String, Object> projects = new HashMap<>();
    private final Map<String, Object> news     = new HashMap<>();
    private final Map<String, Object> requests = new HashMap<>();
    private final List<LogEntry>      logs     = new ArrayList<>();

    private Database() {}

    private static Database INSTANCE = new Database();

    public static Database getInstance() {
        return INSTANCE;
    }

    public Map<String, User> getUsers() {
        return users;
    }

    public Map<String, Object> getCourses() {
        return courses;
    }

    public Map<String, Object> getPapers() {
        return papers;
    }

    public Map<String, Object> getProjects() {
        return projects;
    }

    public Map<String, Object> getNews() {
        return news;
    }

    public Map<String, Object> getRequests() {
        return requests;
    }

    public List<LogEntry> getLogs() {
        return logs;
    }


    public User getUserById(String id) {
        return users.get(id);
    }

    public Course getCourseById(String courseId) {
        Object course = courses.get(courseId);
        if (course instanceof Course) {
            return (Course) course;
        }
        return null;
    }

    public void addCourse(Course course) {
        if (course != null) {
            courses.put(course.getCourseId(), course);
        }
    }

    public boolean removeCourse(String courseId) {
        return courses.remove(courseId) != null;
    }

    public List<Course> getAllCourses() {
        return courses.values().stream()
                .filter(obj -> obj instanceof Course)
                .map(obj -> (Course) obj)
                .collect(Collectors.toList());
    }

    public void save() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(DB_FILE))) {
            this.logs.clear();
            this.logs.addAll(Logger.getInstance().getLogs());
            out.writeObject(this);
            System.out.println("[db] saved to " + DB_FILE);
        } catch (IOException e) {
            System.err.println("[db] save failed: " + e.getMessage());
        }
    }

    public void load() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(DB_FILE))) {
            Database restored = (Database) in.readObject();
            INSTANCE = restored;
            Logger.getInstance().replaceLogs(restored.getLogs());
            System.out.println("[db] loaded from " + DB_FILE);
        } catch (FileNotFoundException e) {
            System.out.println("[db] no save file yet (" + DB_FILE + "), starting fresh");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("[db] load failed: " + e.getMessage());
        }
    }
}
