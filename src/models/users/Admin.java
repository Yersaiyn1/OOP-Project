package models.users;

import data.Database;
import data.LogEntry;

import java.time.LocalDate;
import java.util.List;

/**
 * Admin — system administrator.
 *
 * Per the diagram: addUser, removeUser, updateUser, viewLogs.
 * The diagram also gives Admin an accessLevel (1..N).
 *
 * Admin has direct access to Database (this is the only class outside
 * the data layer that does so on purpose — admins manage everything).
 */
public class Admin extends Employee {

    private static final long serialVersionUID = 1L;

    private int accessLevel;

    public Admin(String id, String firstName, String lastName,
                 String email, String password, String phone,
                 double salary, LocalDate hireDate, String department,
                 int accessLevel) {
        super(id, firstName, lastName, email, password, phone,
                salary, hireDate, department);
        this.accessLevel = accessLevel;
    }

    @Override
    public String getRole() {
        return "Admin";
    }

    /**
     * Register a new user in the system.
     */
    public void addUser(User u) {
        if (u == null) return;
        Database.getInstance().getUsers().put(u.getId(), u);
    }

    /**
     * Remove a user by id.
     */
    public void removeUser(User u) {
        if (u == null) return;
        Database.getInstance().getUsers().remove(u.getId());
    }

    /**
     * Replace an existing user record (matched by id).
     * No-op if the id is not in the database.
     */
    public void updateUser(User u) {
        if (u == null) return;
        if (Database.getInstance().getUsers().containsKey(u.getId())) {
            Database.getInstance().getUsers().put(u.getId(), u);
        }
    }

    /**
     * Read-only view of the system audit log.
     */
    public List<LogEntry> viewLogs() {
        return Database.getInstance().getLogs();
    }

    public int getAccessLevel() { return accessLevel; }
    public void setAccessLevel(int accessLevel) { this.accessLevel = accessLevel; }
}