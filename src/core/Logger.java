package core;

import data.LogEntry;
import models.users.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Singleton Logger.
 *
 * Records every important action (login, enrollment, mark submission,
 * registration, etc.) into a per-process list. The same list is shared
 * with Database so logs survive save/load.
 *
 * Singleton with lazy holder pattern (thread-safe and simple).
 */
public class Logger implements Serializable {

    private static final long serialVersionUID = 1L;

    private final List<LogEntry> logs = new ArrayList<>();

    private Logger() {}

    private static class Holder {
        private static final Logger INSTANCE = new Logger();
    }

    public static Logger getInstance() {
        return Holder.INSTANCE;
    }

    /**
     * Record an action by a user.
     *
     * @param user   the actor (may be null for system-level events)
     * @param action human-readable description
     */
    public void log(User user, String action) {
        LogEntry entry = new LogEntry(user, action);
        logs.add(entry);
        System.out.println("[log] " + entry);
    }

    /**
     * Convenience overload for system-level events.
     */
    public void log(String action) {
        log(null, action);
    }

    /**
     * Read-only view of the audit log.
     */
    public List<LogEntry> getLogs() {
        return Collections.unmodifiableList(logs);
    }

    /**
     * Used by Database.load() to restore the log list after deserialization.
     */
    public void replaceLogs(List<LogEntry> restored) {
        this.logs.clear();
        if (restored != null) {
            this.logs.addAll(restored);
        }
    }
}