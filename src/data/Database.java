package data;

import core.Logger;
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

/**
 * Singleton Database.
 *
 * In-memory store for every persistent object: users, courses, papers,
 * projects, news, requests + the audit log. Persists to {@code db.dat}
 * via Java serialization.
 *
 * The maps use generic Object values so other teammates' classes
 * (Course, ResearchPaper, ResearchProject, News, Request) can be added
 * without forcing this file to import them. Concrete code typically
 * casts on read, which is fine because keys are typed.
 *
 * Usage:
 *   Database.getInstance().getUsers().put(user.getId(), user);
 *   Database.getInstance().save();
 *   ...
 *   Database.getInstance().load();
 */
public class Database implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final String DB_FILE = "db.dat";

    // --- collections ---
    private final Map<String, User>   users    = new HashMap<>();
    private final Map<String, Object> courses  = new HashMap<>();   // <courseId, Course>
    private final Map<String, Object> papers   = new HashMap<>();   // <doi, ResearchPaper>
    private final Map<String, Object> projects = new HashMap<>();   // <projectId, ResearchProject>
    private final Map<String, Object> news     = new HashMap<>();   // <newsId, News>
    private final Map<String, Object> requests = new HashMap<>();   // <requestId, Request>
    private final List<LogEntry>      logs     = new ArrayList<>();

    private Database() {}

    private static Database INSTANCE = new Database();

    public static Database getInstance() {
        return INSTANCE;
    }

    // --- accessors ---
    public Map<String, User>   getUsers()    { return users; }
    public Map<String, Object> getCourses()  { return courses; }
    public Map<String, Object> getPapers()   { return papers; }
    public Map<String, Object> getProjects() { return projects; }
    public Map<String, Object> getNews()     { return news; }
    public Map<String, Object> getRequests() { return requests; }
    public List<LogEntry>      getLogs()     { return logs; }

    // --- persistence ---

    /**
     * Serialize the whole database to {@value #DB_FILE} in the current
     * working directory.
     */
    public void save() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(DB_FILE))) {
            // sync logs from Logger before saving
            this.logs.clear();
            this.logs.addAll(Logger.getInstance().getLogs());
            out.writeObject(this);
            System.out.println("[db] saved to " + DB_FILE);
        } catch (IOException e) {
            System.err.println("[db] save failed: " + e.getMessage());
        }
    }

    /**
     * Load database state from {@value #DB_FILE} if the file exists.
     * On success, replaces the singleton instance.
     */
    public void load() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(DB_FILE))) {
            Database restored = (Database) in.readObject();
            INSTANCE = restored;
            // restore Logger view
            Logger.getInstance().replaceLogs(restored.getLogs());
            System.out.println("[db] loaded from " + DB_FILE);
        } catch (FileNotFoundException e) {
            System.out.println("[db] no save file yet (" + DB_FILE + "), starting fresh");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("[db] load failed: " + e.getMessage());
        }
    }
}